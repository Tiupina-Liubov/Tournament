package src.blocks;

import src.enams.HandPosition;
import src.metods.Utility;
import src.objectClass.Participant;
import src.objectClass.Team;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    public static final Random RANDOM = new Random();

    public static <T extends Participant> Team<T> resultTournament(Map<Team<T>, Float> teams) {
        Map<Team<T>, Float> resultQualifyingStage = playAllWithAll(teams);
        Map<Team<T>, Float> resultSemifinalsGame = playAllWithAll(getTopLimitTeams(resultQualifyingStage, 5));//todo dopisat v Hangler mapy dla chranenija
        Map<Team<T>, Float> resultFinalGame = playAllWithAll(getTopLimitTeams(resultSemifinalsGame, 3));

        Handler.statisticMapTeamsPoints(resultFinalGame);
        Handler.statisticMapTeamsPoints(resultSemifinalsGame);
        Handler.statisticMapTeamsPoints(resultQualifyingStage);

        return getLastTeam(resultFinalGame);
    }

    public static <T extends Participant> Map<Team<T>, Float> playAllWithAll(Map<Team<T>, Float> teams) {

        for (Map.Entry<Team<T>, Float> entry1 : teams.entrySet()) {
            Team<T> team1 = entry1.getKey();
            Float points1 = entry1.getValue();

            for (Map.Entry<Team<T>, Float> entry2 : teams.entrySet()) {
                Team<T> team2 = entry2.getKey();
                Float points2 = entry2.getValue();

                if (team1 != null && team2 != null && !team1.equals(team2)) {
                    Team<T> winner = play(team1, team2);

                    if (winner == null) {
                        updatePoints(teams, team1, points1 + 0.5f);
                        updatePoints(teams, team2, points2 + 0.5f);
                        collectionStatisticalData(team1, team2, 0.5f, 0.5f);

                    } else if (winner.equals(team1)) {
                        oneCouch(teams, team1);
                        collectionStatisticalData(team1, team2, 1.0f, 0.0f);

                    } else {
                        oneCouch(teams, team2);
                        collectionStatisticalData(team1, team2, 0.0f, 1.0f);
                    }
                }
            }
        }
        Utility.sortBuPointsTeams(teams);
        return teams;
    }

    private static <T extends Participant> void collectionStatisticalData(Team<T> team1, Team<T> team2,
                                                                          float couchTeam1,
                                                                          float couchTeam2) {
        String a = coupleOfTeams(team1, team2);
        String b = coupleOfTeams(team2, team1);

        if (Handler.getListGamingStatistics().isEmpty()) {
            Handler.setListGamingStatistics(newStatisticMap(team1, team2, couchTeam1, couchTeam2));

        } else if (!Handler.getListGamingStatistics().containsKey(a) &&
                (!Handler.getListGamingStatistics().containsKey(b))) {

            Handler.setListGamingStatistics(newStatisticMap(team1, team2, couchTeam1, couchTeam2));

        } else {

            if (Handler.getListGamingStatistics().containsKey(a)) {
                addingResult(a, couchTeam1);
                addingResult(b, couchTeam2);
            }
        }
    }

    public static <T extends Participant> String coupleOfTeams(Team<T> team1, Team<T> team2) {
        return team1.getName() + " - " + team2.getName();
    }

    private static <T extends Participant> Map<String, List<Float>> newStatisticMap(Team<T> team1, Team<T> team2,
                                                                                    float couchTeam1,
                                                                                    float couchTeam2) {
        Map<String, List<Float>> newStatisticMap = new HashMap<>();

        List<Float> listTeam1 = new ArrayList<>();
        listTeam1.add(0, couchTeam1);

        List<Float> listTeam2 = new ArrayList<>();
        listTeam2.add(0, couchTeam2);

        newStatisticMap.put(coupleOfTeams(team1, team2), listTeam1);
        newStatisticMap.put(coupleOfTeams(team2, team1), listTeam2);

        return newStatisticMap;
    }

    private static void addingResult(String nameTeams, float couch) {

        for (Map.Entry<String, List<Float>> list : Handler.getListGamingStatistics().entrySet()) {

            if (list.getKey().equals(nameTeams)) {
                addCouchOfList(list.getValue(), couch);
                break;
            }
        }
    }

    private static void addCouchOfList(List<Float> list, float couch) {
        list.add(list.size(), couch);
    }

    private static <T extends Participant> Team<T> play(Team<T> team1, Team<T> team2) {
        int rnd = RANDOM.nextInt(3);
        int rnd1 = RANDOM.nextInt(3);

        String str1 = switch (rnd) {
            case 0 -> HandPosition.SCISSORS.name();
            case 1 -> HandPosition.PAPER.name();
            default -> HandPosition.STONE.name();
        };

        String str2 = switch (rnd1) {
            case 0 -> HandPosition.SCISSORS.name();
            case 1 -> HandPosition.PAPER.name();
            default -> HandPosition.STONE.name();
        };

        if (str1.equals(str2)) {
            return null;

        } else {

            if ((str1.equals(HandPosition.SCISSORS.name()) && str2.equals(HandPosition.PAPER.name())) ^ (str1
                    .equals(HandPosition.PAPER.name()) && str2.equals(HandPosition.STONE.name())) ^
                    (str1.equals(HandPosition.STONE.name()) && str2.equals(HandPosition.SCISSORS.name()))) {

                return team1;

            } else {
                return team2;
            }
        }
    }

    private static <T extends Participant> void updatePoints(Map<Team<T>, Float> teams, Team<T> team, float newPoints) {
        teams.put(team, newPoints);
    }

    private static <T extends Participant> void oneCouch(Map<Team<T>, Float> teamMap, Team<T> team) {

        for (Map.Entry<Team<T>, Float> m : teamMap.entrySet()) {

            if (m.getKey() != null && m.getKey().equals(team)) {
                m.setValue(m.getValue() + 1);
            }
        }
    }

    public static <T extends Participant> Map<Team<T>, Float> getTopLimitTeams(Map<Team<T>, Float> teams, int topLimit) {

        if (checkingElementsValues(teams, topLimit - 1, topLimit)) {
            return teams.keySet().stream()
                    .limit(topLimit)
                    .collect(Collectors.toMap(team -> team, team -> 0.0f));
        }

        Map<Team<T>, Float> top5Teams = teams.keySet().stream()
                .limit(topLimit - 1)
                .collect(Collectors.toMap(team -> team, team -> 0.0f));

        Team<T> winner = replay(getKey(teams, topLimit - 1), getKey(teams, topLimit));
        top5Teams.put(winner, 0.0f);

        return top5Teams;
    }

    private static <T extends Participant> Team<T> getKey(Map<Team<T>, Float> teams, int index) {
        List<Team<T>> teamList = teams.keySet().stream()
                .toList();

        return teamList.get(index);
    }

    private static <T extends Participant> boolean checkingElementsValues(Map<Team<T>, Float> teams, int indexFirst, int indexSecond) {
        List<Float> floats = teams.values().stream()
                .toList();

        return floats.get(indexFirst) > (floats.get(indexSecond));
    }


    private static <T extends Participant> Team<T> replay(Team<T> team, Team<T> team1) {
        Team<T> winner = play(team, team1);

        if (winner == null) {
            return replay(team, team1);
        }
        return winner;
    }


    private static <T extends Participant> Team<T> getLastTeam(Map<Team<T>, Float> teams) {

        if (checkingElementsValues(teams, 0, 1)) {
            return getKey(teams, 0);
        } else
            return replay(getKey(teams, 0), getKey(teams, 1));
    }
}


