package it.gestione.tavoli.GestioneTavoli.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactFormDTO {

    @NotBlank
    @Size(max = 30)
    private String name;

    @Email
    @NotBlank(message = "Inserire l'email, non può essere vuota o mancante" )
    private String email;

    @NotBlank(message = "Il messaggio non può essere vuoto o mancante" )
    private String message;

}
