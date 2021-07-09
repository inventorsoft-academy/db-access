package co.inventrosoft.springboottask.repository;


import co.inventrosoft.springboottask.model.Team;
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
public class TeamRepositoryFileImpl implements TeamRepository{
    private final static String teamsFile = "teams.json";
    private final ObjectMapper mapper;

    public TeamRepositoryFileImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private List<Team> findAll() {
        List<Team> teams = null;
        try {
            teams = mapper.readValue(new File(teamsFile), new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public void save(List<Team> teams) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter(teamsFile, false)))) {
            mapper.writeValue(out, teams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Team team) {
        List<Team> teams = findAll();
        teams.add(team);
        save(teams);
    }

    @Override
    public void update(Team team) {
        List<Team> teams = findAll();
        for (Team team1: teams) {
            if (team1.getName().equals(team.getName())) {
                team1.setCapitan(team.getCapitan());
                team1.setCoach(team.getCoach());
            }
        }
        save(teams);
    }




    @Override
    public boolean isExist(String teamName) {
        List<Team> teams = findAll();
        return teams.stream().anyMatch(team -> team.getName().equals(teamName));
    }
}
