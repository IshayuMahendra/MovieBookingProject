package dev.jeffery.movie_booking_project_backend.repositories;

import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentCardRepository extends MongoRepository<PaymentCard, ObjectId> {
    List<PaymentCard> findByUserObjectID(ObjectId userObjectID);
}
