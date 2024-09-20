package it.gestione.tavoli.GestioneTavoli.controller;

import it.gestione.tavoli.GestioneTavoli.dto.PrenotazioneDTO;
import it.gestione.tavoli.GestioneTavoli.entity.Prenotazione;
import it.gestione.tavoli.GestioneTavoli.exception.BadRequestException;
import it.gestione.tavoli.GestioneTavoli.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/disponibilita")
    public ResponseEntity<List<Map<String, Object>>> getDisponibilita(@RequestParam LocalDate data) {
        try {
            List<Map<String, Object>> disponibilita = prenotazioneService.getDisponibilitaPerData(data);
            return ResponseEntity.ok(disponibilita);
        } catch (Exception e) {
            // Gestisci l'eccezione e restituisci un errore appropriato
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy) {
        return prenotazioneService.getPrenotazioni(page, size, sortBy);
    }

    @PostMapping
    public Prenotazione creaPrenotazione(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors()
                    .stream().map(e -> e.getDefaultMessage()).reduce("", (s, s2) -> s + " - " + s2));
        }

        return prenotazioneService.creaPrenotazione(prenotazioneDTO);
    }




}
