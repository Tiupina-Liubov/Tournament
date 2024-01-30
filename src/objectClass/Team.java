package src.objectClass;

import src.enams.Category;

import java.util.ArrayList;
import java.util.List;

public class Team<T extends Participant> {
    private String name;
    private List<T> participants = new ArrayList<>();
    private Category category;

    public Team(String name, List<T> participants) {
        this.name = name;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getParticipants() {
        return participants;
    }

    public void setParticipants(List<T> participants) {
        this.participants = participants;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "\nTeam{" +
                "name='" + name + '\'' +
                ", participants=" + participants +
                '}';
    }

    public void addNewParticipant(T newParticipant) {
        participants.add(newParticipant);
    }
}