package it.gestione.tavoli.GestioneTavoli.controller;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import it.gestione.tavoli.GestioneTavoli.dto.ContactFormDTO;
import it.gestione.tavoli.GestioneTavoli.entity.ContactForm;
import it.gestione.tavoli.GestioneTavoli.service.ContactFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    private final Bucket bucket;

    private static final Logger logger = LoggerFactory.getLogger(ContactFormController.class);

    //PER IL RATE LIMITING
    public ContactFormController() {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(3, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

//    @PostMapping
//    public String submitContactForm(@RequestBody @Validated ContactFormDTO contactFormDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "Dati del form di contatto non validi";
//        }
//        if (bucket.tryConsume(1)) {
//            return contactFormService.saveContactForm(contactFormDto);
//        } else {
//            return "Limite di messaggi raggiunto. Riprova più tardi";
//        }
//    }

    @PostMapping
    public ResponseEntity<?> submitContactForm(@RequestBody @Validated ContactFormDTO contactFormDto, BindingResult bindingResult) {
        logger.info("Received contact form DTO: {}", contactFormDto);
        if (bindingResult.hasErrors()) {
            // Creare una lista di errori di validazione
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            // Restituire un errore con codice 400 Bad Request

            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        if (bucket.tryConsume(1)) {
            // Restituire una risposta di successo con codice 200 OK
            return ResponseEntity.ok(Map.of("message", contactFormService.saveContactForm(contactFormDto)));
        } else {
            // Restituire un errore con codice 429 Too Many Requests
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("message", "Limite di messaggi raggiunto. Riprova più tardi"));
        }
    }

    @GetMapping
    public Page<ContactForm> getContactForms (@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy){
        return contactFormService.getContactForms(page, size, sortBy);
    }
}
