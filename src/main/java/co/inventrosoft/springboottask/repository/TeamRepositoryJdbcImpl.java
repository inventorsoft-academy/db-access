package co.inventrosoft.springboottask.repository;


import co.inventrosoft.springboottask.configuration.JdbcConfig;
import co.inventrosoft.springboottask.model.Team;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class TeamRepositoryJdbcImpl implements TeamRepository{
    private static final String INSERT_QUERY = "INSERT INTO teams (name, capitan, coach) VALUES (?, ?, ?)";
    private static final String UPDATE_BY_NAME_QUERY = "UPDATE teams SET capitan=?, coach=? WHERE name=?";
    private static final String IS_EXIST_QUERY = "SELECT EXISTS(SELECT name FROM teams WHERE name=?)";
    private final JdbcConfig jdbcConfig;

    public TeamRepositoryJdbcImpl(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
    }
    @Override
    public void save(List<Team> teams) {
        try (Connection connection = getConnection()) {
            for (Team team: teams) {
                if (isExist(team.getName())) {
                    // update if exist
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_NAME_QUERY);
                    preparedStatement.setString(3, team.getName());
                    preparedStatement.setString(1, team.getCapitan());
                    preparedStatement.setString(2, team.getCoach());

                    preparedStatement.executeUpdate();
                }
                else {
                    // insert if not exist
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
                    preparedStatement.setString(1, team.getName());
                    preparedStatement.setString(2, team.getCapitan());
                    preparedStatement.setString(3, team.getCoach());

                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Team team) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, team.getName());
            preparedStatement.setString(2, team.getCapitan());
            preparedStatement.setString(3, team.getCoach());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Team team) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_NAME_QUERY);
            preparedStatement.setString(3, team.getName());
            preparedStatement.setString(1, team.getCapitan());
            preparedStatement.setString(2, team.getCoach());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isExist(String teamName) {
        boolean isExists;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(IS_EXIST_QUERY);
            preparedStatement.setString(1, teamName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isExists = resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExists;
    }
}
