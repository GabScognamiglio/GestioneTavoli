package it.gestione.tavoli.GestioneTavoli.service;

import it.gestione.tavoli.GestioneTavoli.dto.PrenotazioneDTO;
import it.gestione.tavoli.GestioneTavoli.entity.Prenotazione;
import it.gestione.tavoli.GestioneTavoli.entity.Tavolo;
import it.gestione.tavoli.GestioneTavoli.exception.BadRequestException;
import it.gestione.tavoli.GestioneTavoli.exception.TavoloGiaPrenotatoException;
import it.gestione.tavoli.GestioneTavoli.exception.TavoloNotFoundException;
import it.gestione.tavoli.GestioneTavoli.repository.PrenotazioneRepository;
import it.gestione.tavoli.GestioneTavoli.repository.TavoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private TavoloRepository tavoloRepository;

    @Autowired
    private JavaMailSender emailSender;

    private static final List<LocalTime> ORARI_PERMESSI = Arrays.asList(
            LocalTime.of(13, 0),
            LocalTime.of(14, 30),
            LocalTime.of(18, 0),
            LocalTime.of(20, 0),
            LocalTime.of(21, 30)
    );

    public Page<Prenotazione> getPrenotazioni(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione creaPrenotazione(PrenotazioneDTO prenotazioneDTO){
        if (prenotazioneRepository.existsByTavoloIdAndDataAndOra(prenotazioneDTO.getTavoloId(), prenotazioneDTO.getData(), prenotazioneDTO.getOra())) {
            throw new TavoloGiaPrenotatoException("Il tavolo è già prenotato per questa data e ora");
        }

        Tavolo tavoloPrenotato = tavoloRepository.findById(prenotazioneDTO.getTavoloId()).orElseThrow(TavoloNotFoundException::new);

        if (prenotazioneDTO.getPersone() < tavoloPrenotato.getPostiMin() || prenotazioneDTO.getPersone() > tavoloPrenotato.getPostiMax()) {
            throw new BadRequestException("Il numero di persone deve essere compreso tra " + tavoloPrenotato.getPostiMin() + " e " + tavoloPrenotato.getPostiMax());
        }

        if (!ORARI_PERMESSI.contains(prenotazioneDTO.getOra())) {
            throw new BadRequestException("L'ora deve essere una delle seguenti: " + ORARI_PERMESSI);
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setTavolo(tavoloPrenotato);
        prenotazione.setData(prenotazioneDTO.getData());
        prenotazione.setOra(prenotazioneDTO.getOra());
        prenotazione.setPersone(prenotazioneDTO.getPersone());
        prenotazione.setNome(prenotazioneDTO.getNome());
        prenotazione.setEmail(prenotazioneDTO.getEmail());
        prenotazione.setNumeroTel(prenotazioneDTO.getNumeroTel());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(prenotazione.getEmail());
        message.setSubject("Conferma Prenotazione Gab's Ristorante");
        message.setText("Gentile " + prenotazione.getNome() + ",\n\n" +
                "La ringraziamo per aver scelto Gab's Ristorante.\n" +
                "Siamo lieti di confermare la Sua prenotazione con i seguenti dettagli:\n\n" +
                "Tavolo: " + prenotazione.getTavolo().getId() + "\n" +
                "Data: " + prenotazione.getData() + "\n" +
                "Ora: " + prenotazione.getOra() + "\n" +
                "Numero di persone: " + prenotazione.getPersone() + "\n\n" +
                "Se ha bisogno di modificare o cancellare la prenotazione, non esiti a contattarci.\n" +
                "Numero di telefono fornito: " + prenotazione.getNumeroTel() + "\n\n" +
                "A presto,\n" +
                "Gab's Ristorante");

        emailSender.send(message);

        return prenotazioneRepository.save(prenotazione);
    }


    public List<Map<String, Object>> getDisponibilitaPerData(LocalDate data) {
        List<LocalTime> orari = Arrays.asList(
                LocalTime.of(13, 0),
                LocalTime.of(14, 30),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
                LocalTime.of(21, 30)
        );

        List<Tavolo> tavoli = tavoloRepository.findAll();
        tavoli.sort(Comparator.comparing(Tavolo::getId));

        List<Map<String, Object>> disponibilitaTavoli = new ArrayList<>();

        for (Tavolo tavolo : tavoli) {
            List<Prenotazione> prenotazioni = prenotazioneRepository.findByTavoloIdAndData(tavolo.getId(), data);
            Map<String, Boolean> disponibilitaOraria = new HashMap<>();

            for (LocalTime ora : orari) {
                boolean isDisponibile = prenotazioni.stream()
                        .noneMatch(p -> p.getOra().equals(ora));
                disponibilitaOraria.put(ora.toString(), isDisponibile);
            }

            Map<String, Object> tavoloDisponibilita = new HashMap<>();
            tavoloDisponibilita.put("id", tavolo.getId());
            tavoloDisponibilita.put("disponibilita", disponibilitaOraria);

            disponibilitaTavoli.add(tavoloDisponibilita);
        }

        return disponibilitaTavoli;
    }



}
