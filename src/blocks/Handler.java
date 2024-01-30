package src.blocks;

import src.objectClass.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

    public static void setMapTeenager(Map<Team<Teenager>, Float> mapTeenagers) {
        if(mapTeenager == null) {
            Handler.mapTeenager = mapTeenagers;
        }else {
            Handler.mapTeenager.putAll(mapTeenagers);
        }

    }

    public static Map<Team<Adult>, Float> getMapAdult() {
        return mapAdult;
    }

    public static void setMapAdult(Map<Team<Adult>, Float> mapAdults) {
        if (mapAdult == null) {
            Handler.mapAdult = mapAdults;
        }else{
            Handler.mapAdult.putAll(mapAdults);
        }
    }

    public static Map<Team<Pupil>, Float> getMapPupil() {
        return mapPupil;
    }

    public static void setMapPupil(Map<Team<Pupil>, Float> mapPupils) {
        if(mapPupil== null) {
            Handler.mapPupil = mapPupils;

        } else {
            Handler.mapPupil.putAll(mapPupils);
        }
    }

    public static Map<Team<Participant>, Float> getTeamsMap() {
        return teamsMap;
    }

    public static void setTeamsMap(Map<Team<Participant>, Float> teamsMaps) {

        if (teamsMap == null) {
           teamsMap =  teamsMaps;
        }else {

            for(Entry<Team<Participant>,Float>m: teamsMaps.entrySet()){

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

        for (Entry<Team<T>, Float> m : map.entrySet()){
            teamsMap.put((Team<Participant>) m.getKey(), m.getValue());
        }


        return teamsMap;
    }


    public static <T extends Participant> void statisticMapTeamsPoints(Map<Team<T>,Float> map){
        Handler.setTeamsMap(allTeamMap(map));
        setHandlerMapOfParameter(map);

    }

    public static <T extends Participant> void setHandlerMapOfParameter(Map<Team<T>, Float> teams) {
        if (!teams.isEmpty()) {
            Team<?> firstTeam = teams.keySet().iterator().next();

            if (!firstTeam.getParticipants().isEmpty()) {
                T firstParticipant = (T) firstTeam.getParticipants().get(0);
                System.out.println(firstParticipant);

                if (firstParticipant instanceof Adult) {
                    Handler.setMapAdult(teams.entrySet().stream()
                            .collect(Collectors.toMap(t -> (Team<Adult>) t.getKey(), Entry::getValue)));
                } else if (firstParticipant instanceof Pupil) {
                    Handler.setMapPupil(teams.entrySet().stream()
                            .collect(Collectors.toMap(t -> (Team<Pupil>) t.getKey(), Entry::getValue)));
                } else if (firstParticipant instanceof Teenager) {
                    Handler.setMapTeenager(teams.entrySet().stream()
                            .collect(Collectors.toMap(t -> (Team<Teenager>) t.getKey(), Entry::getValue)));
                }
            }
        }
    }
}


