package src.blocks;

import src.objectClass.*;

import java.util.*;

public class Handler {
    private static Map<Team<Teenager>, Float> mapTeenager = new LinkedHashMap<>();
    private static Map<Team<Adult>, Float> mapAdult = new LinkedHashMap<>();
    private static Map<Team<Pupil>, Float> mapPupil = new LinkedHashMap<>();
    private static Map<Team<Participant>, Float> teamsMap = new LinkedHashMap<>();
    private static List<Participant> allParticipants = new ArrayList<>();
    private static Map<String, List<Float>> listGamingStatistics = new LinkedHashMap<>();

    public static Map<Team<Teenager>, Float> getMapTeenager() {
        return mapTeenager;
    }

    public static void setMapTeenager(Map<Team<Teenager>, Float> mapTeenager) {
        Handler.mapTeenager = mapTeenager;
    }

    public static Map<Team<Adult>, Float> getMapAdult() {
        return mapAdult;
    }

    public static void setMapAdult(Map<Team<Adult>, Float> mapAdult) {
        Handler.mapAdult = mapAdult;
    }

    public static Map<Team<Pupil>, Float> getMapPupil() {
        return mapPupil;
    }

    public static void setMapPupil(Map<Team<Pupil>, Float> mapPupil) {
        Handler.mapPupil = mapPupil;
    }

    public static Map<Team<Participant>, Float> getTeamsMap() {
        return teamsMap;
    }

    public static void setTeamsMap(Map<Team<Participant>, Float> teamsMaps) {

        if (teamsMap == null) {
           teamsMap =  teamsMaps;
        }else {

            for(Map.Entry<Team<Participant>,Float>m: teamsMaps.entrySet()){

                if(teamsMap.containsKey(m.getKey())){
                    teamsMap.put(m.getKey(),teamsMaps.get(m.getKey())+m.getValue());
                }else {
                    teamsMap.put(m.getKey(),m.getValue());
                }
            }
        }
    }

    public static List<Participant> getAllParticipants() {
        return allParticipants;
    }

    public static void setAllParticipants(List<Participant> allParticipant) {

        if (allParticipants == null) {
            allParticipants = allParticipant;
        } else {
            allParticipants.addAll(allParticipant);
        }
    }

    public static Map<String, List<Float>> getListGamingStatistics() {
        return listGamingStatistics;
    }

    public static void setListGamingStatistics(Map<String, List<Float>> listGamingStatistic) {

        if (listGamingStatistics == null) {
            listGamingStatistics = listGamingStatistic;
        } else {
            listGamingStatistics.putAll(listGamingStatistic);
        }
    }

    public static <T extends Participant> Map<Team<Participant>, Float> allTeamMap(Map<Team<T>, Float> map) {

        for (Map.Entry<Team<T>, Float> m : map.entrySet()) {
            teamsMap.put((Team<Participant>) m.getKey(), m.getValue());
        }

        return teamsMap;
    }

    public static <T extends Participant> void statisticMapTeamsPoints(Map<Team<T>,Float> map){
        Handler.setTeamsMap(allTeamMap(map));
    }

}
