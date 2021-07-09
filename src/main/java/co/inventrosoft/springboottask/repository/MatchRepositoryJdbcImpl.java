package co.inventrosoft.springboottask.repository;


import co.inventrosoft.springboottask.configuration.JdbcConfig;
import co.inventrosoft.springboottask.mapper.MatchMapper;
import co.inventrosoft.springboottask.model.Match;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class MatchRepositoryJdbcImpl implements MatchRepository{
    private static final String UPDATE_QUERY = "UPDATE matches SET tournament_id=?, " +
            "first_team_name=?, second_team_name=?, " +
            "first_team_result=?, second_team_result=?, " +
            "is_played=?, round_code=?, match_order=? " +
            "WHERE match_id=?";

    private static final String GET_QUERY = "SELECT " +
            "m.match_id, m.tournament_id, m.first_team_result, m.second_team_result, " +
            "m.is_played, m.round_code, m.match_order, " +
            "t1.name AS t1_name, t1.capitan AS t1_capitan, t1.coach AS t1_coach, " +
            "t2.name AS t2_name, t2.capitan AS t2_capitan, t2.coach AS t2_coach " +
            "FROM matches AS m " +
            "LEFT JOIN teams AS t1 ON m.first_team_name = t1.name " +
            "LEFT JOIN teams AS t2 ON m.second_team_name = t2.name " +
            "WHERE tournament_id=?  ";

    public static final String GET_BY_ROUND_CODE_AND_ORDER_QUERY = GET_QUERY +
            "AND round_code=? AND match_order=?";

    private static final String GET_BY_TEAM_NAMES_QUERY = GET_QUERY +
            "AND first_team_name=? AND second_team_name=?";

    private static final String INSERT_QUERY = "INSERT INTO matches " +
            "(tournament_id, first_team_name, second_team_name, first_team_result, " +
            "second_team_result, is_played, round_code, match_order) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?)";

    private final JdbcConfig jdbcConfig;

    public MatchRepositoryJdbcImpl(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
    }

    @Override
    public void save(List<Match> matches) {
        try (Connection connection = getConnection()) {
            for (Match match: matches) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
                preparedStatement.setInt(1, match.getTournamentId());
                preparedStatement.setString(2, match.getFirstTeamName());
                preparedStatement.setString(3, match.getSecondTeamName());
                preparedStatement.setInt(4, match.getFirstTeamResult());
                preparedStatement.setInt(5, match.getSecondTeamResult());
                preparedStatement.setBoolean(6, match.getPlayed());
                preparedStatement.setInt(7, match.getRoundCode());
                preparedStatement.setInt(8, match.getOrder());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Match> findAll(int tournamentId) {
        List<Match> matches = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_QUERY + "ORDER BY round_code DESC, match_order");
            preparedStatement.setInt(1, tournamentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                matches.add(MatchMapper.fromResultSetToMatch(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matches;
    }

    @Override
    public Match getByTeamNames(String firstTeamName, String secondTeamName, int tournamentId) {
        Match match = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_TEAM_NAMES_QUERY);
            preparedStatement.setString(2, firstTeamName);
            preparedStatement.setString(3, secondTeamName);
            preparedStatement.setInt(1, tournamentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                match = MatchMapper.fromResultSetToMatch(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return match;
    }

    @Override
    public Match getByRoundCodeAndOrder(int roundCode, int order, int tournamentId) {
        Match match = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ROUND_CODE_AND_ORDER_QUERY);
            preparedStatement.setInt(2, roundCode);
            preparedStatement.setInt(3, order);
            preparedStatement.setInt(1, tournamentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                match = MatchMapper.fromResultSetToMatch(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return match;
    }

    @Override
    public void update(Match match) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(9, match.getId());
            preparedStatement.setInt(1, match.getTournamentId());

            preparedStatement.setString(2, match.getFirstTeamName());
            preparedStatement.setString(3, match.getSecondTeamName());

            preparedStatement.setInt(4, match.getFirstTeamResult());
            preparedStatement.setInt(5, match.getSecondTeamResult());

            preparedStatement.setBoolean(6, match.getPlayed());
            preparedStatement.setInt(7, match.getRoundCode());
            preparedStatement.setInt(8, match.getOrder());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
