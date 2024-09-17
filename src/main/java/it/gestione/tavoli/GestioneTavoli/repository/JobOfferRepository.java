package it.gestione.tavoli.GestioneTavoli.repository;


import it.gestione.tavoli.GestioneTavoli.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByTitleContaining(String title);
}