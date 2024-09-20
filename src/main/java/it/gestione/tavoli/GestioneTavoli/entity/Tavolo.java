package it.gestione.tavoli.GestioneTavoli.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tavoli")
@Data

public class Tavolo {
    @Id
    private int id;

    private int postiMin;
    private int postiMax;

    @OneToMany(mappedBy = "tavolo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> prenotazioni;

    public Tavolo() {
    }

    public Tavolo(int id, int postiMin, int postiMax) {
        this.id = id;
        this.postiMin = postiMin;
        this.postiMax = postiMax;
    }
}
