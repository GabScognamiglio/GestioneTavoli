package it.gestione.tavoli.GestioneTavoli.repository;

import it.gestione.tavoli.GestioneTavoli.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByTavoloIdAndData(int tavoloId, LocalDate data);

    boolean existsByTavoloIdAndDataAndOra(int tavoloId, LocalDate data, LocalTime ora);
}
