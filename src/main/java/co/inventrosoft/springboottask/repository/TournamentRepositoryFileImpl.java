package co.inventrosoft.springboottask.repository;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier
@Repository
public class TournamentRepositoryFileImpl implements TournamentRepository {
    @Override
    public int create() {
        return 0;
    }
}
