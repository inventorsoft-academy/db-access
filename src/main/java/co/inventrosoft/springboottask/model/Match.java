package co.inventrosoft.springboottask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    int id;
    //  1/round - name of round. e.g roundCode = 8 -> round name = 1/8
    //  roundCode == number of matches in current round
    //  if roundCode == 1 - this round is final
    int roundCode;

    //  for resolving to which match carry the winner
    //  order == sequence number of match in current round
    int order;

    Team firstTeam;
    Team secondTeam;

    boolean played;
    int firstTeamResult;
    int secondTeamResult;

    int tournamentId;

    public Match(int roundCode, int order, int tournamentId) {
        this.roundCode = roundCode;
        this.order = order;
        this.tournamentId = tournamentId;
    }

    @Override
    public String toString() {
        String str = "\n";
        str += this.roundCode != 1 ? "Round 1/" + this.roundCode : "Final!";

        if (this.played) {
            str += "\nScore: " + getScore();
        }
        str += "\nTeam 1: " + (firstTeam != null ? firstTeam + "\n" : "undefined\n");
        str += "Team 2: " + (secondTeam != null ? secondTeam + "\n" : "undefined\n");

        return str;
    }
    @JsonIgnore
    public String getFirstTeamName() {
        if (firstTeam != null) {
            return firstTeam.getName();
        }
        return null;
    }
    @JsonIgnore
    public String getSecondTeamName() {
        if (secondTeam != null) {
            return secondTeam.getName();
        }
        return null;
    }
    @JsonIgnore
    public String getScore() {
        String score = null;
        if (this.played) {
            score = this.firstTeamResult + ":" + this.secondTeamResult;
        }
        return score;
    }
    // lombok is broken here idk
    public boolean getPlayed() {
        return this.played;
    }

    @JsonIgnore
    public Team getWinner() {
        Team winner = null;
        if (this.played) {
            winner = (firstTeamResult > secondTeamResult) ? firstTeam : secondTeam;
        }
        return winner;
    }

    @JsonIgnore
    public void setTeamByOrder(int order, Team team) {
        if (order % 2 == 0) {
            firstTeam = team;
        }
        else {
            secondTeam = team;
        }
    }

    @JsonIgnore
    public boolean isFinal() {
        return this.roundCode == 1;
    }

}
