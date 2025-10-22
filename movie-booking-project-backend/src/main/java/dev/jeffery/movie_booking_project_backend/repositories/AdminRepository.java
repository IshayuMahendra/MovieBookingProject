package dev.jeffery.movie_booking_project_backend.repositories;

import dev.jeffery.movie_booking_project_backend.data.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, ObjectId> {
    Optional<Admin> findByUserID(String userID);
}
