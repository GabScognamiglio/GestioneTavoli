package it.gestione.tavoli.GestioneTavoli.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_form")
@Data
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;


    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime dateTime;
}
