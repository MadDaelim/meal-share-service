package com.discreteempire.mealshare.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface OrderRepository extends MongoRepository<OrderEntry, UUID> {
}
