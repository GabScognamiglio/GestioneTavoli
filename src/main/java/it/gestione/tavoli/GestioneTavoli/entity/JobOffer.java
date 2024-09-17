package it.gestione.tavoli.GestioneTavoli.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "job_offers")
@Data
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate postingDate;

    @Column(nullable = false)
    private String employmentType; // e.g., Full-time, Part-time, Internship

    @Column(nullable = false)
    private double salary;
}
