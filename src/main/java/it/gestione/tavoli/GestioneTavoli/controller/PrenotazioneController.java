package it.gestione.tavoli.GestioneTavoli.controller;

import it.gestione.tavoli.GestioneTavoli.dto.PrenotazioneDTO;
import it.gestione.tavoli.GestioneTavoli.entity.Prenotazione;
import it.gestione.tavoli.GestioneTavoli.exception.BadRequestException;
import it.gestione.tavoli.GestioneTavoli.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Map<String, Object>> getDisponibilita(@RequestParam LocalDate data) {
        return prenotazioneService.getDisponibilitaPerData(data);
    }

    @PostMapping
    public Prenotazione creaPrenotazione (@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors()
                    .stream().map(e -> e.getDefaultMessage()).reduce("", (s, s2) -> s + " - " + s2));
        }

        return prenotazioneService.creaPrenotazione(prenotazioneDTO);
    }


}
