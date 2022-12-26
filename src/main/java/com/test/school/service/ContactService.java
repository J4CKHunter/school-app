package com.test.school.service;

import com.test.school.dto.AddNewContactMessageRequest;
import com.test.school.dto.DisplayContactFormsResponse;
import com.test.school.dto.converter.ContactConverter;
import com.test.school.model.Contact;
import com.test.school.repository.ContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactConverter converter;



    public ContactService(ContactRepository contactRepository, ContactConverter converter) {
        this.contactRepository = contactRepository;
        this.converter = converter;
    }


    public void saveContactForm(AddNewContactMessageRequest addNewContactMessageRequest) {

        Contact contact = converter.convert(addNewContactMessageRequest);
        contactRepository.save(contact);
    }

    public Page<Contact> findMessagesWithOpenStatus(Integer pageNumber, String sortField, String sortDirection) {
        Integer pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,
                sortDirection.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());

        Page<Contact> contactMessagesPage = contactRepository.findContactByStatusType(Contact.StatusType.OPEN, pageable);

        return contactMessagesPage;

    }

    public void updateMessageStatusToClose(Integer id) {
//        contactRepository.updateMessageStatus(Contact.StatusType.CLOSE, id);
        Optional<Contact> optionalContact =  contactRepository.findById(id);
        optionalContact.get().setStatusType(Contact.StatusType.CLOSE);
        contactRepository.save(optionalContact.get());
    }

    public List<DisplayContactFormsResponse> convertContactMessagesToResponses(List<Contact> contactMessages) {
        return contactMessages.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
