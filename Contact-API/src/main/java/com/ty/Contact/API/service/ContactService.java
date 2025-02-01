package com.ty.Contact.API.service;

import java.io.IOException;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.ty.Contact.API.entity.Contact;
import com.ty.Contact.API.repository.ContactRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    public Page<Contact> getAllContacts(int page, int size) {
        return contactRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Contact getContact(String id) {
        return contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Not Found"));
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }

    public String uploadPhoto(String id, MultipartFile file) throws IOException {
        log.info("Saving picture for user ID : {}", id);
        Contact contact = getContact(id);

        // Store image in GridFS
        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());

        // Store reference in Contact entity
        String photoUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/contacts/image/" + fileId.toHexString()) // URL to retrieve image
                .toUriString();
        contact.setPhotoUrl(photoUrl);
        contactRepository.save(contact);

        return photoUrl;
    }

    public InputStream getPhoto(String fileId) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(
                new org.springframework.data.mongodb.core.query.Query(
                        org.springframework.data.mongodb.core.query.Criteria.where("_id").is(fileId)
                )
        );

        if (file == null) {
            throw new RuntimeException("Image not found with ID: " + fileId);
        }

        return gridFSBucket.openDownloadStream(file.getObjectId());
    }
}
