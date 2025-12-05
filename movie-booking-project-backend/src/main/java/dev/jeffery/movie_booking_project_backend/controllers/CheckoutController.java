package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.dto.CheckoutInfoDTO;
import dev.jeffery.movie_booking_project_backend.dto.CheckoutConfirmationDTO;
import dev.jeffery.movie_booking_project_backend.dto.ConfirmCheckoutRequest;
import dev.jeffery.movie_booking_project_backend.services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("checkout")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    // Load initial checkout 
    @GetMapping("/{bookingId}")
    public ResponseEntity<CheckoutInfoDTO> getCheckoutInfo(
            @PathVariable String bookingId,
            @RequestParam String email  
    ) {
        CheckoutInfoDTO dto = checkoutService.getcheckoutInfoDTO(email, bookingId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Confirm checkout, user clicks pay 
    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<CheckoutConfirmationDTO> confirmCheckout(
            @PathVariable String bookingId,
            @RequestParam String email,  
            @RequestBody ConfirmCheckoutRequest request
    ) {
        CheckoutConfirmationDTO dto = checkoutService.confirmCheckout(email, bookingId, request);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{bookingId}/preview")
    public ResponseEntity<CheckoutConfirmationDTO> previewTotal(
            @PathVariable String bookingId,
            @RequestParam String email,
            @RequestParam(required = false) String promotionCode
    ) {
        CheckoutConfirmationDTO dto = checkoutService.previewTotal(email, bookingId, promotionCode);
        return ResponseEntity.ok(dto);
    }
}
