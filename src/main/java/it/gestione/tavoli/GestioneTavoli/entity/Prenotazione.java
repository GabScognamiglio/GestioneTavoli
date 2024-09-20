package it.gestione.tavoli.GestioneTavoli.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "prenotazioni", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tavolo_id", "data", "ora"})
})
@Data
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tavolo_id")
    private Tavolo tavolo;

    private LocalDate data;
    private LocalTime ora;
    private int persone;

    private String nome;
    private String email;
    private String numeroTel;

}
