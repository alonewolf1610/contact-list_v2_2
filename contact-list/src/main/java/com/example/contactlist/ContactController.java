package com.example.contactlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
    private final ContactRepository repository;

    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listContacts(Model model) {
        model.addAttribute("contacts", repository.findAll());
        return "contacts";
    }

    @GetMapping("/edit/{id}")
    public String editContact(@PathVariable("id") Long id, Model model) {
        Contact contact = repository.findById(id).orElse(new Contact());
        model.addAttribute("contact", contact);
        return "edit";
    }

    @PostMapping("/save")
    public String saveContact(Contact contact) {
        if (contact.getId() == null) {
            repository.save(contact);
        } else {
            repository.update(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") Long id) {
        repository.delete(id);
        return "redirect:/";
    }
}

