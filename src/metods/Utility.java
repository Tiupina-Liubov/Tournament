package src.metods;

import src.blocks.Game;
import src.blocks.Handler;
import src.enams.Category;
import src.objectClass.Participant;
import src.objectClass.Team;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Utility {
    public static final Random RANDOM = new Random();


    public static <T extends Participant> Team<T> maxCouch(Map<Team<T>, Float> teamMap) {
        float num = 0;
        Team<T> team = new Team<>(null, null);

        for (Map.Entry<Team<T>, Float> m : teamMap.entrySet()) {
            if (num < m.getValue()) {
                num = m.getValue();
                team = m.getKey();
            }
        }

        return team;
    }

    public static <T extends Participant> float totalNumberPoints(Map<Team<T>, Float> teamMap) {
        float sum = 0.0f;
        Collection<Float> values = teamMap.values();

        for (Float value : values) {
            sum += value;
        }

        return sum;
    }

    public static <T extends Participant> Set<String> listOfTeams(Map<Team<T>, Float> teamMap) {
        HashSet<Team<T>> setTeams = new HashSet<>(teamMap.keySet());
        HashSet<String> setNameTeams = new HashSet<>();

        for (Team<T> set : setTeams) {
            setNameTeams.add(set.getName());
        }

        return setNameTeams;
    }

    public static <T extends Participant> Map<String, Float> averageAge(Map<Team<T>, Float> teamMap) {
        HashSet<Team<T>> setTeams = new HashSet<>(teamMap.keySet());
        Map<String, Float> averageAgeTeams = new HashMap<>();

        for (Team<T> set : setTeams) {
            float averageAge = 0.0f;
            List<T> participants = set.getParticipants();

            for (T participnt : participants) {
                averageAge += participnt.getAge();
            }

            averageAge /= participants.size();
            averageAgeTeams.put(set.getName(), averageAge);
        }
        return averageAgeTeams;
    }



    public static float averageValue(Collection<Float> list) {
        return list.stream()
                .reduce(Float::sum)
                .map(f -> f / list.size())
                .orElse(0.0f);

    }

    public static <T extends Participant> Map<Team<T>, Float> aboveTheAverage(Map<Team<T>, Float> mapParticipant) {
        float average = averageValue(mapParticipant.values());

        Map<Team<T>, Float> aboveAverage = new HashMap<>();

        for (Map.Entry<Team<T>, Float> m : mapParticipant.entrySet()) {

            if (m.getValue() > average) {
                aboveAverage.put(m.getKey(), m.getValue());
            }
        }

        return aboveAverage;
    }

    public static <T extends Participant> void sortBuPointsTeams(Map<Team<T>, Float> mapParticipant) {
        Comparator<Map.Entry<Team<T>, Float>> comparator = Map.Entry.comparingByValue();

        List<Map.Entry<Team<T>, Float>> list = new ArrayList<>(mapParticipant.entrySet());
        list.sort(comparator.reversed());

        mapParticipant.clear();


        for (Map.Entry<Team<T>, Float> entry : list) {
            mapParticipant.put(entry.getKey(), entry.getValue());
        }
    }

    public static Participant youngestPlayer(Map<Team<Participant>, Float> map) {
        Participant young = null;
        int temp = Integer.MAX_VALUE;

        for (Team<Participant> team : map.keySet()) {

            for (Participant participant : team.getParticipants()) {

                if (participant.getAge() < temp) {
                    temp = participant.getAge();
                    young = participant;
                }
            }
        }
        return young;
    }

    static <T extends Participant> Team<T> experiencedTeam(Map<Team<Participant>, Float> map) {
        int tempAge = 0;
        Team<T> experiencedTeam = null;

        for (Team<Participant> team : map.keySet()) {
            int temp = 0;

            for (Participant participant : team.getParticipants()) {
                temp += participant.getAge();
            }

            if (tempAge < temp) {
                tempAge = temp;
                experiencedTeam = (Team<T>) team;
            }
        }

        return experiencedTeam;
    }

    public static <T extends Participant> void conclusionParticipantsCategoryOfClass(Map<Team<Participant>, Float> map, Class<T> tClass) {

        for (Team<Participant> team : map.keySet()) {

            if (team.getParticipants().get(0).getClass().isNestmateOf(tClass)) {
                System.out.println(team.getName());
            }
        }
    }

    public static List<Team<Participant>> outputParticipantsCertainAgeRange(Map<Team<Participant>, Float> map, int bottom, int upper) {
        List<Team<Participant>> teams = new ArrayList<>();

        for (Team<Participant> team : map.keySet()) {

            if (allNumbersThisRange(team, bottom, upper)) {
                teams.add(team);
            }
        }
        return teams;
    }

    static boolean allNumbersThisRange(Team<Participant> team, int bottom, int upper) {
        boolean b = false;

        for (Participant participant : team.getParticipants()) {

            if (bottom <= participant.getAge() && participant.getAge() <= upper) {
                b = true;

            } else {
                return false;
            }
        }

        return b;
    }

    public static void showParticipantsDescendingOrderAge(List<Participant> participantList) {
        Comparator<Participant> participantComparator = Participant::compareTo;
        participantList.sort(participantComparator);

        for (Participant participant : participantList) {
            System.out.println(participant);//participant.getName();
        }
    }

    public static <T extends Participant> Team<Participant> greatestTotalAge(Map<Team<Participant>, Float> map) {
        int maxSumAge = 0;
        Team<Participant> temp = null;

        for (Team<Participant> team : map.keySet()) {

            if (maxSumAge < getSumAge(team.getParticipants())) {
                maxSumAge = getSumAge(team.getParticipants());
                temp = team;
            }
        }

        return temp;
    }

    private static int getSumAge(List<Participant> team) {
        int sum = 0;

        for (Participant participant : team) {
            sum += participant.getAge();
        }

        return sum;
    }

    public static Map<Map<String, String>, Integer> haveSameTotalAge(Map<Team<Participant>, Float> map) {
        Map<Map<String, String>, Integer> teamsOfSameTotalAge = new HashMap<>();
        Map<Team<Participant>, Integer> mapTeamTotalAge = determiningAmountOfAge(map);

        for (Map.Entry<Team<Participant>, Integer> m : mapTeamTotalAge.entrySet()) {

            for (Map.Entry<Team<Participant>, Integer> cloneM : mapTeamTotalAge.entrySet()) {

                if ((!(m.getKey() == cloneM.getKey())) && (getSumAge(m.getKey()
                        .getParticipants()) == (getSumAge(cloneM.getKey().getParticipants())))) {

                    teamsOfSameTotalAge.put(addStringToMap(m.getKey().getName(),
                            cloneM.getKey().getName()), getSumAge(m.getKey().getParticipants()));
                }
            }
        }

        return teamsOfSameTotalAge;
    }

    private static Map<String, String> addStringToMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);

        return map;
    }

    private static Map<Team<Participant>, Integer> determiningAmountOfAge(Map<Team<Participant>, Float> map) {
        Map<Team<Participant>, Integer> mapTeamTotalAge = new HashMap<>();

        for (Team<Participant> team : map.keySet()) {
            mapTeamTotalAge.put(team, getSumAge(team.getParticipants()));
        }

        return mapTeamTotalAge;
    }

    public static void showTeamAverageScores() {
        System.out.println("Average scores " + Category.TEENAGER.name() +
                " = " + averageValue(Handler.getMapTeenager().values()));

        System.out.println("Average scores " + Category.PUPIL.name() +
                " = " + averageValue(Handler.getMapPupil().values()));

        System.out.println("Average scores " + Category.ADULT.name() +
                " = " + averageValue(Handler.getMapAdult().values()));
    }

    public static Set<String> undefeatedTeamList(Map<String, List<Float>> map) {
        Set<String> setTeams = new HashSet<>();

        for (Map.Entry<String, List<Float>> m : map.entrySet()) {

            if (!m.getValue().contains(0.0f)) {
                setTeams.add(m.getKey().split(" - ")[0]);
            }
        }

        return setTeams;
    }

    private static int showLunchQuantity(List<Float> floatList) {

        if (floatList.isEmpty()) {
            System.out.println("This team has not played yet.");
            return 0;
        }

        int lunchQuantity = 0;

        for (Float aFloat : floatList) {

            if (aFloat == 1) {
                lunchQuantity += 1;
            }
        }

        return lunchQuantity;
    }

//    static Map<String, Integer> showLongestWinningStreak() {
//        Map<String, Integer> showLongestWinningStreakMap = new HashMap<>();
//
//        for (Map.Entry<Map<String, String>, List<Float>> map : Handler.getListGamingStatistics().entrySet()) {
//
//            for (Map.Entry<String, String> m : map.getKey().entrySet()) {
//                showLongestWinningStreakMap.put(m.getKey(), showLunchQuantity(map.getValue()));
//                break;
//            }
//        }
//
//        return showLongestWinningStreakMap;
//    }

    public static List<String> teamsWithWinsOverSpecificTeam(String teamName) {
        return Handler.getListGamingStatistics().entrySet().stream()
                .filter(entry -> entry.getKey().split(" - ")[0].equals(teamName) && entry.getValue().contains(0.0f))
                .map(entry -> entry.getKey().split(" - ")[1])
                .toList();

    }

    //Найти команды, чьи баллы улучшались с каждой игрой.
    //Find teams whose scores improved with each game.
    public static Set<String> findTeamsWhoseScoresImprovedEachGame(Map<String, List<Float>> map) {
        return map.entrySet().stream()
                .filter(entry -> (entry.getValue().stream()
                        .filter(f -> f > 0.0f).count()) == entry.getValue().size())
                .map(entry -> entry.getKey().split(" - ")[0])
                .collect(Collectors.toSet());
    }

    //Список команд без баллов:
    //List of teams without points:
    public static Set<String> getTeamsWithoutPoints(Map<String, List<Float>> map) {
        return map.entrySet().stream()
                .filter(entry -> (entry.getValue().stream()
                        .filter(f -> f < 0.5f).count()) == entry.getValue().size())
                .map(entry -> entry.getKey().split(" - ")[0])
                .collect(Collectors.toSet());
    }

    //Список команд, которые имели ничейные результаты с заданной командой.
    //List of teams that had a draw with a given team.
    public static Set<String> findListTeamsThatDrawWithGivenTeam(Map<String, List<Float>> map,
                                                                 String teamName) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().split(" - ")[0].contains(teamName))
                .filter(entry -> entry.getValue().contains(0.0f))
                .map(entry -> entry.getKey().split(" - ")[1])
                .collect(Collectors.toSet());
    }

    //Вывести результаты всех игр между двумя конкретными командами.
    //Print the results of all games between two specific teams.
    public static void printResultOfAllGamesSpecificTeams(Team<? extends Participant> team1,
                                                          Team<? extends Participant> team2) {
        String teamsNames = Game.coupleOfTeams(team1, team2);
        String teamsNames1 = Game.coupleOfTeams(team2, team1);

        if (Handler.getListGamingStatistics().containsKey(teamsNames)) {
            Handler.getListGamingStatistics().entrySet().stream()
                    .filter(entry -> entry.getKey().equals(teamsNames) || entry.getKey().equals(teamsNames1))
                    .forEach(entry -> System.out.println(entry.getKey() + "\n" + entry.getValue()));

        } else {
            System.err.println("These teams did not play each other ");
        }
    }

    //
    public static <T extends Participant> Team<T> getTeam(Map<Team<T>, Float> map, int index) {
        List<Team<T>> list = map.keySet().stream()
                .toList();

        if (index > map.size() - 1) {
            System.err.println("There is no element with this index, I return random");
            return list.get(RANDOM.nextInt(map.size() - 1));

        } else {
            return list.get(index);
        }
    }

    //Сравнить две команды по  баллам и среднему возрасту участников.
    //Compare two teams in average points and middle age participants
    public static void compareTwoTeamsPointsAndMiddleAge(String teamName,
                                                         String teamName1) {
        Map<Map<Team<Participant>, Double>, Float> teamList = Handler.getTeamsMap().entrySet().stream()
                .filter(teamEntry -> teamEntry.getKey().getName().equals(teamName) ||
                        teamEntry.getKey().getName().equals(teamName1))
                .collect(Collectors.toMap(
                        teamEntry -> {
                            Team<Participant> team = teamEntry.getKey();
                            Double averageAge = team.getParticipants().stream()
                                    .collect(Collectors.averagingDouble(Participant::getAge));
                            return Map.of(team, averageAge);
                        },
                        Map.Entry::getValue));

        for (Map.Entry<Map<Team<Participant>, Double>, Float> entry : teamList.entrySet()) {
            System.out.println("Team: " + entry.getKey().keySet().iterator().next().getName() +
                    ", Average Age: " + entry.getKey().values().iterator().next() +
                    ", Float Value: " + entry.getValue());
        }
    }

    //Найти команды с наибольшим количеством ничьих результатов.
    public static List<String> findTeamsWithMostTies(Map<String, List<Float>> map, int numberOfTeams) {
        return map.entrySet().stream()
                .map(entry ->
                        entry.getKey().split(" - ")[0] + " = "
                                + entry.getValue().stream()
                                .filter(f -> f == 0.5)
                                .count())
                .distinct()
                .sorted((str1, str2) -> {
                    int count1 = Integer.parseInt(str1.split(" = ")[1]);
                    int count2 = Integer.parseInt(str2.split(" = ")[1]);
                    return Integer.compare(count2, count1);
                })
                .limit(numberOfTeams)
                .toList();
    }

    //Определить команды с самой длинной последовательностью побед.
    public static List<String> determineTeamsWithLongestWinningStreak(Map<String, List<Float>> map) {
        return map.entrySet().stream()
                .map(entry -> {
                    String teamName = entry.getKey().split(" - ")[0];
                    int maxStreak = 0;
                    int currentStreak = 0;

                    for (Float result : entry.getValue()) {
                        if (result == 1) {
                            currentStreak++;
                            maxStreak = Math.max(maxStreak, currentStreak);
                        } else {
                            currentStreak = 0;
                        }
                    }
                    return teamName + " = " + maxStreak;
                })
                .distinct()
                .sorted((str1, str2) -> {
                    int streak1 = Integer.parseInt(str1.split(" = ")[1]);
                    int streak2 = Integer.parseInt(str2.split(" = ")[1]);
                    return Integer.compare(streak2, streak1);
                })
                .toList();
    }

    //Создать комплексный отчет, включающий средний возраст команды,
    // общее количество баллов, наибольшую победную серию, и сравнение с другими командами.
    public static Map<String,List<String>> createComprehensiveReport(){
       return  Handler.getTeamsMap().entrySet().stream()
                .collect(Collectors.toMap(teamFloatEntry -> teamFloatEntry.getKey().getName(),
                        teamFloatEntry -> new ArrayList<>(List.of("Average age of the team = "+averageAgeTeam(
                                teamFloatEntry.getKey().getParticipants()),"Total points = "+teamFloatEntry.getValue(),
                                "Longest winning streak = "+longestWinningStreak(Handler.getListGamingStatistics(),
                                        teamFloatEntry.getKey().getName())))));


    }

    private static int averageAgeTeam(List<Participant> list){
        return list.stream()
                .map(Participant::getAge)
                .reduce((a,b)->(a+b)/list.size())
                .orElse(0);

    }

    public static int longestWinningStreak(Map<String,List<Float>> map, String teamName){
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().split(" - ")[0].equals(teamName))
                .map(entry -> determineTeamsWithLongestWinningStreak( entry.getValue()))
                .findFirst()
                .orElse(0);



    }

        public static int determineTeamsWithLongestWinningStreak(List<Float> list) {
            int maxStreak = 0;
            int currentStreak = 0;

            for (Float result : list) {
                if (result == 1) {
                    currentStreak++;
                    maxStreak = Math.max(maxStreak, currentStreak);
                } else {
                    currentStreak = 0;
                }
            }

            return maxStreak;
        }

}

