package it.gestione.tavoli.GestioneTavoli.controller;

import it.gestione.tavoli.GestioneTavoli.entity.JobOffer;
import it.gestione.tavoli.GestioneTavoli.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/job-offers")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping
    public List<JobOffer> getAllJobOffers() {
        return jobOfferService.getAllJobOffers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable Long id) {
        Optional<JobOffer> jobOffer = jobOfferService.getJobOfferById(id);
        return jobOffer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
//
//    @PostMapping
//    public JobOffer createJobOffer(@RequestBody JobOffer jobOffer) {
//        return jobOfferService.createJobOffer(jobOffer);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable Long id, @RequestBody JobOffer jobOfferDetails) {
//        JobOffer updatedJobOffer = jobOfferService.updateJobOffer(id, jobOfferDetails);
//        if (updatedJobOffer != null) {
//            return ResponseEntity.ok(updatedJobOffer);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
//        jobOfferService.deleteJobOffer(id);
//        return ResponseEntity.noContent().build();
//    }
}