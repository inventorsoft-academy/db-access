package co.inventrosoft.springboottask.service;

import co.inventrosoft.springboottask.model.Team;
import co.inventrosoft.springboottask.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void save(List<Team> teams) throws IOException {
        teamRepository.save(teams);
    }

    public boolean isTeamExist(String teamName) {
        return teamRepository.isExist(teamName);
    }
}
