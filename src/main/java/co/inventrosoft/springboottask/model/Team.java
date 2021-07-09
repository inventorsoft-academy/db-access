package co.inventrosoft.springboottask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    int id;
    String name;
    String capitan;
    String coach;

    public Team(String name, String capitan, String coach) {
        this.name = name;
        this.capitan = capitan;
        this.coach = coach;
    }

    @Override
    public String toString() {
        return this.name + ". Capitan - " + this.capitan + ", coach - " + this.coach;
    }
}
