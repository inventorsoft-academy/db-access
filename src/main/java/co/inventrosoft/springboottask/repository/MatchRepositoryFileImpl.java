package co.inventrosoft.springboottask.repository;

import co.inventrosoft.springboottask.model.Match;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Qualifier
@Repository
public class MatchRepositoryFileImpl implements MatchRepository {
    private final static String matchesFile = "matches.json";
    private final ObjectMapper mapper;

    public MatchRepositoryFileImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public void save(List<Match> matches) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter(matchesFile, false)))) {
            mapper.writeValue(out, matches);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Match> findAll(int tournamentId) {
        List<Match> matches = null;
        try {
            matches = mapper.readValue(new File(matchesFile), new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    @Override
    public Match getByTeamNames(String firstTeamName, String secondTeamName, int tournamentId) {
        List<Match> matches = findAll(tournamentId);
        Match resultMatch = null;
        for (Match match: matches) {
            boolean checkIfTeamsNotNulls = match.getFirstTeam() != null && match.getSecondTeam() != null;
            if (checkIfTeamsNotNulls && match.getFirstTeam().getName().equals(firstTeamName)
                    && match.getSecondTeam().getName().equals(secondTeamName)) {
                resultMatch = match;
                break;
            }
        }
        return resultMatch;
    }

    @Override
    public Match getByRoundCodeAndOrder(int roundCode, int order, int tournamentId) {
        List<Match> matches = findAll(tournamentId);
        Match resultMatch = null;
        for (Match match: matches) {
            if (match.getRoundCode() == roundCode && match.getOrder() == order) {
                resultMatch = match;
                break;
            }
        }
        return resultMatch;
    }

    @Override
    public void update(Match match) {
        List<Match> matches = findAll(0);
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getRoundCode() == match.getRoundCode() && matches.get(i).getOrder() == match.getOrder()) {
                matches.set(i, match);
                save(matches);
                break;
            }
        }

    }


}
