package it.gestione.tavoli.GestioneTavoli.service;


import it.gestione.tavoli.GestioneTavoli.entity.JobOffer;
import it.gestione.tavoli.GestioneTavoli.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public Optional<JobOffer> getJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    public JobOffer createJobOffer(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer updateJobOffer(Long id, JobOffer jobOfferDetails) {
        Optional<JobOffer> jobOffer = jobOfferRepository.findById(id);
        if (jobOffer.isPresent()) {
            JobOffer existingJobOffer = jobOffer.get();
            existingJobOffer.setTitle(jobOfferDetails.getTitle());
            existingJobOffer.setDescription(jobOfferDetails.getDescription());
            existingJobOffer.setPostingDate(jobOfferDetails.getPostingDate());
            existingJobOffer.setEmploymentType(jobOfferDetails.getEmploymentType());
            existingJobOffer.setSalary(jobOfferDetails.getSalary());
            return jobOfferRepository.save(existingJobOffer);
        } else {
            return null;
        }
    }

    public void deleteJobOffer(Long id) {
        jobOfferRepository.deleteById(id);
    }
}