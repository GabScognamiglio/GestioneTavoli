package it.gestione.tavoli.GestioneTavoli.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PrenotazioneDTO {
    @Min(1)
    @Max(15)
    private int tavoloId;

    @NotNull(message = "La data non può essere nulla")
    @FutureOrPresent(message = "La data deve essere oggi o nel futuro")
    private LocalDate data;

    @NotNull(message = "L'ora non può essere nulla")
    private LocalTime ora;

    @NotNull(message = "Il numero di persone non può essere nullo")
    @Min(value = 1, message = "Il numero di persone deve essere almeno 1")
    @Max(value = 10, message = "Il numero di persone non può superare 10")
    private int persone;

    @NotBlank(message = "Il nome non può essere nullo")
    private String nome;
    @Email
    private String email;
    @NotBlank(message = "Il telefono non può essere nullo")
    private String numeroTel;

}
