CREATE TABLE IF NOT EXISTS tournaments (
    tournament_id SERIAL PRIMARY KEY NOT NULL
);

CREATE TABLE IF NOT EXISTS teams (
    name VARCHAR(100) PRIMARY KEY UNIQUE NOT NULL,
    capitan VARCHAR(100) NOT NULL,
    coach VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS matches (
    match_id SERIAL PRIMARY KEY NOT NULL,
    tournament_id INT,
    first_team_name VARCHAR(100),
    second_team_name VARCHAR(100),

    first_team_result INT,
    second_team_result INT,
    is_played BOOLEAN,

    round_code INT,
    match_order INT,
    CONSTRAINT fk_tournament
        FOREIGN KEY(tournament_id)
            REFERENCES tournaments(tournament_id),

    CONSTRAINT fk_first_team
        FOREIGN KEY(first_team_name)
            REFERENCES teams(name),

    CONSTRAINT fk_second_team
        FOREIGN KEY(second_team_name)
            REFERENCES teams(name)



);