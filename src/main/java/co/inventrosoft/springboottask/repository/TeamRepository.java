package co.inventrosoft.springboottask.repository;

import co.inventrosoft.springboottask.model.Team;

import java.io.IOException;
import java.util.List;

public interface TeamRepository {
    void save(List<Team> teams);
    void add(Team team);
    void update(Team team);
    boolean isExist(String teamName);
}
