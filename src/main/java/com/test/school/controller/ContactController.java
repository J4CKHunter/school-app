package com.test.school.controller;

import com.test.school.dto.AddNewContactMessageRequest;
import com.test.school.dto.DisplayContactFormsResponse;
import com.test.school.dto.converter.ContactConverter;
import com.test.school.model.Contact;
import com.test.school.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public ModelAndView displayContactPage(){
        ModelAndView modelAndView = new ModelAndView("contact.html");
        modelAndView.addObject("addNewContactMessageRequest", new AddNewContactMessageRequest());
        return modelAndView;
    }

    @PostMapping("/saveContactForm")
    public ModelAndView saveContactForm(@Valid @ModelAttribute("addNewContactMessageRequest") AddNewContactMessageRequest addNewContactMessageRequest,
                                        Errors errors){
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to: " + errors);
            return new ModelAndView("contact.html");
        }

        contactService.saveContactForm(addNewContactMessageRequest);

        return new ModelAndView("redirect:/contact");
    }

    @RequestMapping("/displayContactForms/page/{pageNumber}")
    public ModelAndView displayContactForms(@PathVariable(name = "pageNumber") Integer pageNumber,
                                            @RequestParam("sortField") String sortField,
                                            @RequestParam("sortDirection") String sortDirection){

        Page<Contact> contactMessagePage = contactService.findMessagesWithOpenStatus(pageNumber, sortField, sortDirection);
        List<Contact> contactMessages = contactMessagePage.getContent();
        List<DisplayContactFormsResponse> contactResponseMessages = contactService.convertContactMessagesToResponses(contactMessages);

        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("currentPage", pageNumber);

        if(contactMessagePage.getTotalPages() == 0){
            modelAndView.addObject("totalPages", 1);
        }else{
            modelAndView.addObject("totalPages", contactMessagePage.getTotalPages());
        }

        modelAndView.addObject("totalMessages", contactMessagePage.getTotalElements());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDirection", sortDirection);
        modelAndView.addObject("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMessages", contactResponseMessages);

        return modelAndView;
    }

    @GetMapping("/closeContactMessage")
    public ModelAndView closeContactMessage(@RequestParam(value = "id") Integer id){
        contactService.updateMessageStatusToClose(id);
        return new ModelAndView("redirect:/displayContactForms/page/1?sortField=name&sortDirection=asc");
    }
}
