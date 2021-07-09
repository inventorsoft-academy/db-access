package co.inventrosoft.springboottask.mapper;

import co.inventrosoft.springboottask.model.Match;
import co.inventrosoft.springboottask.model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MatchMapper {
    public static Match fromResultSetToMatch(ResultSet resultSet) throws SQLException {
        Match match = new Match();

        match.setId(resultSet.getInt("match_id"));
        match.setTournamentId(resultSet.getInt("tournament_id"));

        String firstTeamName = resultSet.getString("t1_name");
        if (firstTeamName != null) {
            match.setFirstTeam(
                new Team(firstTeamName, resultSet.getString("t1_capitan"), resultSet.getString("t1_coach"))
            );
        }
        String secondTeamName = resultSet.getString("t2_name");
        if (secondTeamName != null) {
            match.setSecondTeam(
                new Team(secondTeamName, resultSet.getString("t2_capitan"), resultSet.getString("t2_coach"))
            );
        }
        match.setFirstTeamResult(resultSet.getInt("first_team_result"));
        match.setSecondTeamResult(resultSet.getInt("second_team_result"));
        match.setPlayed(resultSet.getBoolean("is_played"));
        match.setRoundCode(resultSet.getInt("round_code"));
        match.setOrder(resultSet.getInt("match_order"));

        return match;
    }
}
