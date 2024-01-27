package src.blocks;

import com.github.javafaker.Faker;
import src.objectClass.*;

import java.util.*;

public class Generator {
    private static final Faker FAKER = new Faker();

    @Override
    public String toString() {
        return "Generator{}";
    }

    public static <T extends Participant> Map<Team<T>, Float> genTeams(int teams, int participants, Class<T> t) {
        Map<Team<T>, Float> mapTeams = new HashMap<>();

        if (teams <= 0 || participants <= 0) {
            System.out.println("Please enter the correct data");
            return mapTeams;
        }

        for (int i = 0; i < teams; i++) {
            List<T> participantsList = genParticipant(t, participants);
            mapTeams.put(newTeam(FAKER.team().name(), participantsList), 0.0f);
        }

        return mapTeams;
    }

    private static <T extends Participant> List<T> genParticipant(Class<T> t, int participants) {
        List<T> list = new ArrayList<>();

        while (participants > 0) {

            if (t.isAssignableFrom(Adult.class)) {
                list.add((T) new Adult(FAKER.name().fullName(), (int) (Math.random() * (18 + 1) + 18)));

            } else if (t.isAssignableFrom(Teenager.class)) {
                list.add((T) new Teenager(FAKER.name().fullName(), (int) (Math.random() * (8 + 1) + 10)));

            } else if (t.isAssignableFrom(Pupil.class)) {
                list.add((T) new Pupil(FAKER.name().fullName(), (int) (Math.random() * (3 + 1) + 6)));
            }
            participants--;
        }
        Handler.setAllParticipants(Collections.unmodifiableList(list));
        return list;
    }

    private static <T extends Participant> Team<T> newTeam(String name, List<T> participants) {
        return new Team<>(name, participants);
    }

    public static <T extends Participant> void printTeams(Map<Team<T>, Float> map) {

        if (!map.isEmpty()) {

            for (Team<T> team : map.keySet()) {
                System.out.println("Team Name: " + team);
            }
        } else {
            System.out.println("Set is empty");
        }
    }
}

