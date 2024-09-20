package it.gestione.tavoli.GestioneTavoli.repository;

import it.gestione.tavoli.GestioneTavoli.entity.Tavolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TavoloRepository extends JpaRepository<Tavolo, Integer> {
}
