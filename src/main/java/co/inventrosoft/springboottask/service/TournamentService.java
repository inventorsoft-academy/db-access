package co.inventrosoft.springboottask.service;

import co.inventrosoft.springboottask.console.ConsoleParser;
import co.inventrosoft.springboottask.console.MatchResult;
import co.inventrosoft.springboottask.model.Match;
import co.inventrosoft.springboottask.model.Team;
import co.inventrosoft.springboottask.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentService {
    private final MatchService matchService;
    private final TeamService teamService;

    private final TournamentRepository tournamentRepository;

    private final ConsoleParser consoleParser;

    public TournamentService(MatchService matchService, TeamService teamService, ConsoleParser consoleParser, TournamentRepository tournamentRepository) {
        this.matchService = matchService;
        this.teamService = teamService;
        this.tournamentRepository = tournamentRepository;
        this.consoleParser = consoleParser;
    }

    /**
     * creates a tournament with matches,
     * where teams in first round stores teams from list, the rest stores null
     */
    public int createEmptyTournament(List<Team> teams) {
        int teamCount = teams.size();
        int roundCount = (int)(Math.log(teamCount) / Math.log(2)); // log base 2
        int id = tournamentRepository.create();
        ArrayList<Match> matches = new ArrayList<>();

        for (int round = 0; round < roundCount; round++) {

            // teamCount / (2 ^ (round+1))
            // this is code of round. e.g code == 2 -> round name = 1/2
            int numOfMatchesInRound = (int)(teamCount / (Math.pow(2, round+1)));

            for (int matchOrder = 0; matchOrder < numOfMatchesInRound; matchOrder++) {
                Match match = new Match(numOfMatchesInRound, matchOrder, id);
                // if first round set teams to matches
                if (round == 0) {
                    match.setFirstTeam(teams.get(matchOrder * 2));
                    match.setSecondTeam(teams.get(matchOrder * 2 + 1));
                }
                matches.add(match);
            }
        }
        matchService.saveAll(matches);
        return id;
    }
    public void start() throws IOException {
        // step 1: get teams
        List<Team> teams = consoleParser.getTeams();
        teamService.save(teams);
        Collections.shuffle(teams);

        // step 2: create tournament
        int tournamentId = createEmptyTournament(teams);
        Team winner = null;
        do {
            consoleParser.printTournament(matchService.getTournament(tournamentId));

            MatchResult matchResult = consoleParser.getResultOfMatch();
            if (!matchService.areTeamsInMatchResultExists(matchResult)) {
                consoleParser.printLine("Teams are wrong!");
                continue;
            }
            Match match = matchService.getMatchByResult(matchResult, tournamentId);
            if (match == null) {
                consoleParser.printLine("Match does not exist!");
                continue;
            }
            if (match.getPlayed()) {
                consoleParser.printLine("Match was played already!");
                continue;
            }
            // update match here
            matchService.updateMatchResults(matchResult, match);
            if (match.isFinal()) {
                winner = match.getWinner();
            }
        } while (winner == null);
        consoleParser.printWinner(winner);
        matchService.writeAllToCsv(tournamentId);

    }
}
