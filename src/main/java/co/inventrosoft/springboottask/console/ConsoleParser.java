package co.inventrosoft.springboottask.console;

import co.inventrosoft.springboottask.model.Match;
import co.inventrosoft.springboottask.model.Team;
import co.inventrosoft.springboottask.service.TeamService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConsoleParser {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final TeamService teamService;
    public ConsoleParser(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<Team> getTeams() throws IOException {
        ArrayList<Team> teams = new ArrayList<>();
        int teamCount = getTeamCount();
        for (int i = 0; i < teamCount; i++) {
            while(true) {
                Team team = getTeam();
                // check if this team in list
                boolean isTeamNotInTeamList = teams.stream().noneMatch(teami -> teami.getName().equals(team.getName()));

                if (isTeamNotInTeamList) {
                    teams.add(team);
                    break;
                }
                System.out.println("Team with name \"" + team.getName() + "\" already exists");
            }
        }

        return teams;
    }
    public int getTeamCount() throws IOException {
        int teamCount = 0;
        boolean valid = false;
        do {
            System.out.print("Enter number of teams: ");
            try {
                teamCount = Integer.parseInt(reader.readLine());

                // check if power of 2
                double log2 = Math.log(teamCount) / Math.log(2); // log base 2
                boolean isPowerOfTwo = (int)Math.ceil(log2) == (int)Math.floor(log2);

                if (teamCount < 4 || !isPowerOfTwo) {
                    System.out.println("Number of teams should be at least 4 and power of 2!");
                }
                else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please, use only digits!");
            }
        }
        while (!valid);
        return teamCount;
    }

    /**
     * parses data from console by format {name} {capitan} {coach}
     * creates new team if team with this name does not exist
     * @return created team
     */
    public Team getTeam() throws IOException {
        String[] teamData;
        boolean valid = false;

        do {
            System.out.println("Enter a team by format (team fields can't have spaces!):\n{name} {capitan} {coach}");
            System.out.println("Every field should contain more than 2 letters");
            teamData = reader.readLine().split(" ");

            if (teamData.length == 3 && teamData[0].length() > 2 && teamData[1].length() > 2 && teamData[2].length() > 2) {
                valid = true;
            }
            else {
                System.out.println("Please, follow the format!");
            }
        }
        while (!valid);
        System.out.println("Team with name \"" + teamData[0] + "\" was added.\n");
        return new Team(teamData[0], teamData[1], teamData[2]);
    }

    /**
     * parses result of match from console by format {team1 name} vs {team2 name} {score}
     * score format: {result of team1}:{result of team2}
     * @return map, with keys: firstTeamName, secondTeamName, score
     */
    public MatchResult getResultOfMatch() throws IOException {
        MatchResult matchResult;
        String scoreRegex = "^(\\d+):(\\d+)$";
        do {
            System.out.println("Enter a result of match by format:");
            System.out.println("{team1 name} vs {team2 name} {team1 result}:{team2 result}");
            String[] rawResult = reader.readLine().split(" ");

            if (rawResult.length != 4 || !rawResult[1].equals("vs") || !rawResult[3].matches(scoreRegex)) {
                System.out.println("Please, follow the format!");
                continue;
            }

            String firstTeam = rawResult[0];
            String secondTeam = rawResult[2];
            String[] score = rawResult[3].split(":");
            int firstTeamResult = Integer.parseInt(score[0]);
            int secondTeamResult = Integer.parseInt(score[1]);

            if (firstTeam.length() < 3 || secondTeam.length() < 3) {
                System.out.println("Team names should have more than 2 symbols!");
                continue;
            }
            if (firstTeam.equals(secondTeam)) {
                System.out.println("Team names should be different!");
                continue;
            }
            if (firstTeamResult == secondTeamResult) {
                System.out.println("There should be no draw!");
                continue;
            }
            matchResult = new MatchResult(firstTeam, secondTeam, firstTeamResult, secondTeamResult);
            break;
        }
        while (true);
        return matchResult;
    }

    public void printTournament(List<Match> matches) {
        for(Match match: matches) {
            System.out.println(match);
        }
        System.out.println();
    }

    public void printLine(String text) {
        System.out.println(text);
    }
    public void printWinner(Team team) {
        System.out.println("Winner:\n" + team);
    }
}
