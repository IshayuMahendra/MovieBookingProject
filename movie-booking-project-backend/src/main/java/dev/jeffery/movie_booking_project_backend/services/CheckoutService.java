package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jeffery.movie_booking_project_backend.dto.CheckoutConfirmationDTO;
import dev.jeffery.movie_booking_project_backend.dto.CheckoutInfoDTO;
import dev.jeffery.movie_booking_project_backend.dto.ConfirmCheckoutRequest;
import dev.jeffery.movie_booking_project_backend.dto.SavedCardDTO;
import dev.jeffery.movie_booking_project_backend.data.Booking;
import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.Promotion;
import dev.jeffery.movie_booking_project_backend.data.Ticket;
import dev.jeffery.movie_booking_project_backend.data.User;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

@Service
public  class CheckoutService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PaymentCardService paymentCardService;

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    // Building checkoutInfo, use to display initial information.
    public CheckoutInfoDTO getcheckoutInfoDTO (String email, String bookingId) {

          User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
                ObjectId userObjectId = user.getId();
        
        
        Booking booking = bookingRepository.findById(new ObjectId(bookingId))
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
     
        
        if (booking.getUserObjectID() == null) {
           booking.setUserObjectID(userObjectId);
            bookingRepository.save(booking);
        }

        var cards = paymentCardService.getCardsByUser(userObjectId);
        
        var savedCardDTOs = cards.stream()
            .map(card -> {
                SavedCardDTO sc = new SavedCardDTO();
                sc.setCardId(card.getId().toHexString());

                String number = card.getCardNumber(); 
                String last4 = number.length() >= 4
                        ? number.substring(number.length() - 4)
                        : number;

                sc.setMaskedNumber("**** **** **** " + last4);
                sc.setExpirationDate(card.getExpirationDate());
                return sc;
            }) // only shows last 4 digits of card
            .toList();
        
        
        
        double subtotal = calculateSubtotal(booking);

        CheckoutInfoDTO dto = new CheckoutInfoDTO();
        dto.setBookingId(bookingId);
        dto.setEmail(user.getEmail());
        dto.setSubtotal(subtotal);
        dto.setSavedCards(savedCardDTOs);
        return dto; 
    }

    // Method use after user clicks "pay". Updates booking status/total with promotion/
    public CheckoutConfirmationDTO confirmCheckout(String email, String bookingId, ConfirmCheckoutRequest request) {
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
                ObjectId userObjectId = user.getId();

        Booking booking = bookingRepository.findById(new ObjectId(bookingId))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        double subtotal = calculateSubtotal(booking);

        double discountAmount = 0.0;

        String promotionId = request.getPromotionCode();
        if (promotionId != null && !promotionId.isBlank()) {

            Promotion promo = promotionRepository.findById(new ObjectId(promotionId))
                    .orElseThrow(() -> new RuntimeException("Promotion not found"));

            
            Date now = new Date();
            if (promo.getExpirationDate() != null && promo.getExpirationDate().before(now)) {
                
                throw new RuntimeException("Promotion has expired.");            
            }

            int percentage = promo.getDiscountPercentage(); 
            discountAmount = subtotal * (percentage / 100.0);
        }

        double total = Math.max(subtotal - discountAmount, 0.0);

        PaymentCard usedCard = null;
        
        if (request.getSavedCardId() != null && !request.getSavedCardId().isBlank()) {
      
            ObjectId savedId = new ObjectId(request.getSavedCardId());
            usedCard = paymentCardRepository.findById(savedId)
                    .orElseThrow(() -> new RuntimeException("Saved card not found"));
        } else {
            if (request.getCardNumber() == null || request.getCardNumber().isBlank()) {
                throw new RuntimeException("Card number is required for payment.");           
            }

            // saves card if request it
            if (request.isSaveCard()) {
                usedCard = paymentCardService.createNewPaymentCard(
                        request.getCardNumber(),
                        request.getExpirationDate(),
                        request.getBillingAddress(),
                        userObjectId
                );
            } else {
                usedCard = null; 
            }

        }
       
        booking.setStatus("CONFIRMED");
        booking.setTotal(total);
        booking.setTypeOfPayment("CARD");
        
        if (usedCard != null) {
             booking.setPaymentCardID(usedCard.getId());
        }

        bookingRepository.save(booking);

        CheckoutConfirmationDTO dto = new CheckoutConfirmationDTO();
        dto.setBookingId(bookingId);
        dto.setSubtotal(subtotal);
        dto.setDiscount(discountAmount);
        dto.setTotal(total);

        var show = showRepository.findById(booking.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        var movie = movieRepository.findById(show.getMovieID())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        String seats = String.join(", ", booking.getSelectedSeats());

        String showTime = show.getShowTime() != null
                ? show.getShowTime().toString()
                : "N/A";

        emailService.sendOrderConfirmationEmail(
                user.getEmail(),
                movie.getTitle(),
                showTime,
                seats,
                total,
                booking.getId().toHexString()
        );

        return dto;
    }


    //Method to get the sum of all tickets
    private double calculateSubtotal(Booking booking) {

    // Load all tickets that belong to this booking
    List<Ticket> ticketsForBooking = ticketRepository.findByBookingID(booking.getId());
    System.out.println();
    System.out.println("Tickets for booking " + booking.getId() + ": " + ticketsForBooking.size());
    ticketsForBooking.forEach(t ->
        System.out.println("  ticket " + t.getId() + " type=" + t.getTicketType() + " price=" + t.getPrice())
    );

    return ticketsForBooking.stream()
            .mapToDouble(Ticket::getPrice)   
            .sum();
}

}
