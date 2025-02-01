package com.ty.Contact.API.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ty.Contact.API.entity.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {
    
}