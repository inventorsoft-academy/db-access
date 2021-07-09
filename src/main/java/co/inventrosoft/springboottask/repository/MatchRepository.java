package co.inventrosoft.springboottask.repository;

import co.inventrosoft.springboottask.model.Match;
import co.inventrosoft.springboottask.model.Team;

import java.io.IOException;
import java.util.List;

public interface MatchRepository {
    List<Match> findAll(int tournamentId);
    Match getByTeamNames(String firstTeam, String secondTeam, int tournamentId);
    Match getByRoundCodeAndOrder(int roundCode, int order, int tournamentId);

    void save(List<Match> matches);
    void update(Match match);
}
