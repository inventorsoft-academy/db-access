package co.inventrosoft.springboottask.repository;


import co.inventrosoft.springboottask.configuration.JdbcConfig;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


@Primary
@Repository
public class TournamentRepositoryJdbcImpl implements TournamentRepository {
    private static final String CREATE_QUERY = "INSERT INTO tournaments DEFAULT VALUES RETURNING tournament_id";

    private final JdbcConfig jdbcConfig;

    public TournamentRepositoryJdbcImpl(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
    }
    @Override
    public int create() {
        int tournamentId;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(CREATE_QUERY);
            resultSet.next();
            tournamentId = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tournamentId;
    }
}
