package src.metods;

import src.blocks.Handler;
import src.enams.Category;
import src.objectClass.Participant;
import src.objectClass.Team;

import java.util.*;

public class Utility {


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
        float average = 0.0f;

        for (Float i : list) {
            average += i;
        }

        return average /= list.size();
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

    static void showParticipantsDescendingOrderAge(List<Participant> participantList) {
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

    static Map<Map<String, String>, Integer> haveSameTotalAge(Map<Team<Participant>, Float> map) {
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

    static void showTeamAverageScores() {
        System.out.println("Average scores " + Category.TEENAGER.name() +
                " = " + averageValue(Handler.getMapTeenager().values()));

        System.out.println("Average scores " + Category.PUPIL.name() +
                " = " + averageValue(Handler.getMapPupil().values()));

        System.out.println("Average scores " + Category.ADULT.name() +
                " = " + averageValue(Handler.getMapAdult().values()));
    }

//    static <T extends Participant> void outputOfGameResults(Team<T> team1, Team<T> team2) {
//        Map<String, String> stringMap = Game.newMapNameTeamsPlay(team1, team2);
//        Map<String, String> stringMap1 = Game.newMapNameTeamsPlay(team2, team1);
//
//        List<Float> listTeam1 = new ArrayList<>();
//        List<Float> listTeam2 = new ArrayList<>();
//
//        if (!Handler.getListGamingStatistics().containsKey(stringMap)) {
//            System.out.println("These teams didn't play");
//
//        } else {
//
//            for (Map.Entry<Map<String, String>, List<Float>> map : Handler.getListGamingStatistics().entrySet()) {
//                Map<String, String> mapStr = map.getKey();
//
//                if (stringMap.equals(mapStr)) {
//                    System.out.println(stringMap + "1");
//                    listTeam1.addAll(map.getValue());
//                }
//
//                if (stringMap1.equals(mapStr)) {
//                    System.out.println(stringMap1 + "2");
//                    listTeam2.addAll(map.getValue());
//                }
//            }
//            System.out.println(stringMap1.entrySet());
//
//            for (int i = 0; i < listTeam1.size(); i++) {
//                System.out.println(listTeam1.get(i) + " : " + listTeam2.get(i));
//            }
//        }
//    }

    static Set<String> undefeatedTeamList(Map<Map<String, String>, List<Float>> map) {
        Set<String> setTeams = new HashSet<>();

        for (Map.Entry<Map<String, String>, List<Float>> m : map.entrySet()) {

            if (!m.getValue().contains(0.0f)) {

                for (Map.Entry<String, String> strMap : m.getKey().entrySet()) {
                    setTeams.add(strMap.getKey());
                    setTeams.add(strMap.getValue());
                }
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

}