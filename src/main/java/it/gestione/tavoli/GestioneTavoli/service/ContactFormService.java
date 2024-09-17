package it.gestione.tavoli.GestioneTavoli.service;

import it.gestione.tavoli.GestioneTavoli.dto.ContactFormDTO;
import it.gestione.tavoli.GestioneTavoli.entity.ContactForm;
import it.gestione.tavoli.GestioneTavoli.repository.ContactFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Autowired
    private JavaMailSender emailSender;


    public String saveContactForm(ContactFormDTO contactFormDto){
        ContactForm contactForm = new ContactForm();

        contactForm.setName(contactFormDto.getName());
        contactForm.setEmail(contactFormDto.getEmail());
        contactForm.setMessage(contactFormDto.getMessage());
        contactForm.setDateTime(LocalDateTime.now());

        contactFormRepository.save(contactForm);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("gabbo16s@gmail.com");
        message.setSubject("Nuovo messaggio da " + contactForm.getName());
        message.setText("Nome: " + contactForm.getName() + "\n"
                + "Email: " + contactForm.getEmail() + "\n"
                + "Messaggio: " + contactForm.getMessage());

        emailSender.send(message);

        return "Messaggio salvato e inviato correttamente";
    }

    public Page<ContactForm> getContactForms(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return contactFormRepository.findAll(pageable);
    }

}
