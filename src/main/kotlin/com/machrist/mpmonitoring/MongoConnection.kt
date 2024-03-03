package com.machrist.mpmonitoring

import com.mongodb.client.MongoClients


fun main() {
    val mongoClient = MongoClients.create("mongodb://localhost:27017")
    val database = mongoClient.getDatabase("myMongoDb")
    mongoClient.listDatabases().forEach(System.out::println);
}