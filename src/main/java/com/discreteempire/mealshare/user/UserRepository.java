package com.discreteempire.mealshare.user;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface UserRepository extends MongoRepository<UserEntry, UUID> {
    Optional<UserEntry> findByUsername(String username);
    boolean existsByUsername(String username);
}
