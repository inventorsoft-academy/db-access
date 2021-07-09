package co.inventrosoft.springboottask;

import co.inventrosoft.springboottask.model.Match;
import co.inventrosoft.springboottask.model.Team;
import co.inventrosoft.springboottask.repository.MatchRepositoryJdbcImpl;
import co.inventrosoft.springboottask.repository.TeamRepositoryJdbcImpl;
import co.inventrosoft.springboottask.repository.TournamentRepositoryJdbcImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SpringBootTask1ApplicationTests {

    @Autowired
    TeamRepositoryJdbcImpl teamRepository;

    @Autowired
    TournamentRepositoryJdbcImpl tournamentRepository;

    @Autowired
    MatchRepositoryJdbcImpl matchRepository;

    @Test
    void contextLoads() {
        int id = 3;
        ArrayList<Match> matches = new ArrayList<>();
        Match a = new Match();
        matches.add(a);

        a.setTournamentId(id);
        a.setRoundCode(2);
        a.setOrder(2);


        Team t1 = new Team("test2212", "das", "daa");
        Team t2 = new Team("test1212", "das", "daa");

        //a.setFirstTeam(t1);
        a.setSecondTeam(t2);
        //matchRepository.save(matches);

        Match m = matchRepository.getByRoundCodeAndOrder(2, 2, id);
        m.setFirstTeam(t1);
        matchRepository.update(m);

        //matchRepository.update(a);



    }

}
