package dev.jeffery.movie_booking_project_backend.repositories;

import dev.jeffery.movie_booking_project_backend.data.Booking;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Booking, ObjectId> {

}