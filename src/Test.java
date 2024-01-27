package src;

import src.blocks.Game;
import src.blocks.Generator;
import src.blocks.Handler;
import src.enams.Category;
import src.metods.Utility;
import src.objectClass.*;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<Team<Adult>, Float> mapAdult=Generator.genTeams(6,4, Adult.class);
        Map<Team<Teenager>, Float> mapTeenager=Generator.genTeams(6,4, Teenager.class);
        Map<Team<Pupil>, Float> mapPupil=Generator.genTeams(6,4, Pupil.class);

        Team<Pupil> winnerPupil= Game.resultTournament(mapPupil);
        Team<Adult> winnerAdult= Game.resultTournament(mapAdult);
        Team<Teenager> winnerTeenager= Game.resultTournament(mapTeenager);

        System.out.println(Handler.getTeamsMap());

        System.out.println("Найти команду с максимальными баллами: ");
        System.out.println(Utility.maxCouch(Handler.getTeamsMap()).getName());

        System.out.println("Подсчет общего количества баллов: ");
        System.out.println(Utility.totalNumberPoints(Handler.getTeamsMap()));

        System.out.println("Список команд без баллов: ");
        System.out.println();

        System.out.println("Средний возраст участников в каждой команде: ");
        System.out.println(Utility.averageAge(Handler.getTeamsMap()));

        System.out.println("Команды с определенной категорией участников: Вывести команды, где все участники " +
                "принадлежат к одной категории (например, только Adult).");
        Utility.conclusionParticipantsCategoryOfClass(Handler.getTeamsMap(),Adult.class);

        System.out.println("Команды с баллами выше среднего: ");
        System.out.println(Utility.aboveTheAverage(Handler.getTeamsMap()));

        System.out.println("Сортировка команд по баллам: ");
        System.out.println(Handler.getTeamsMap());
        Utility.sortBuPointsTeams(Handler.getTeamsMap());
        System.out.println("++++++++++++++++");
        System.out.println(Handler.getTeamsMap());

        System.out.println("Самая опытная команда: Определить команду с наибольшим суммарным возрастом участников.");
        System.out.println(Utility.greatestTotalAge(Handler.getTeamsMap()));

        System.out.println("Команды с участниками в определенном возрастном диапазоне: ");
        Utility.outputParticipantsCertainAgeRange(Handler.getTeamsMap(),17,35);





    }
}
