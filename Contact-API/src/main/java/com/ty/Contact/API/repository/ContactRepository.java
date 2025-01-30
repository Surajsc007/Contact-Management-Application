package com.ty.Contact.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.Contact.API.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	Optional<Contact> findById(String id);

	void deleteById(String id);

}