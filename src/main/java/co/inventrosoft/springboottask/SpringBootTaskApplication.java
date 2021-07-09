package co.inventrosoft.springboottask;


import co.inventrosoft.springboottask.service.TournamentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootTaskApplication implements CommandLineRunner {
    private final TournamentService tournamentService;

    public SpringBootTaskApplication(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public void run(String... args) throws Exception {
        tournamentService.start();
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTaskApplication.class, args);
    }

}
