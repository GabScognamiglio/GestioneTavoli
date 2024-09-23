package it.gestione.tavoli.GestioneTavoli;

import it.gestione.tavoli.GestioneTavoli.entity.Tavolo;
import it.gestione.tavoli.GestioneTavoli.repository.TavoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TavoloInitializer implements CommandLineRunner {

    @Autowired
    private TavoloRepository tavoloRepository;


    @Override
    public void run(String... args) throws Exception {
        if (tavoloRepository.count() == 0) {
            // Tavoli 1-3 (6-10 posti)
            tavoloRepository.save(new Tavolo(1, 6, 10));
            tavoloRepository.save(new Tavolo(2, 6, 10));
            tavoloRepository.save(new Tavolo(3, 6, 10));

            // Tavoli 4-9 (4-6 posti)
            for (int i = 4; i <= 9; i++) {
                tavoloRepository.save(new Tavolo(i, 3, 6));
            }

            // Tavoli 10-15 (1-2 posti)
            for (int i = 10; i <= 15; i++) {
                tavoloRepository.save(new Tavolo(i, 1, 2));
            }
        }
    }
}
