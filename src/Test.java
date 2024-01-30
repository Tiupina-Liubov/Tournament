package src;

import src.blocks.Game;
import src.blocks.Generator;
import src.blocks.Handler;
import src.metods.Utility;
import src.objectClass.*;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Test {
    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Map<Team<Adult>, Float> mapAdult = Generator.genTeams(6, 4, Adult.class);
        Map<Team<Teenager>, Float> mapTeenager = Generator.genTeams(6, 4, Teenager.class);
        Map<Team<Pupil>, Float> mapPupil = Generator.genTeams(6, 4, Pupil.class);

        Team<Pupil> winnerPupil = Game.resultTournament(mapPupil);
        Team<Adult> winnerAdult = Game.resultTournament(mapAdult);
        Team<Teenager> winnerTeenager = Game.resultTournament(mapTeenager);

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
        Utility.conclusionParticipantsCategoryOfClass(Handler.getTeamsMap(), Adult.class);

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
        System.out.println(Utility.outputParticipantsCertainAgeRange(Handler.getTeamsMap(), 20, 35));

        System.out.println("Имена участников по убыванию возраста ");
        Utility.showParticipantsDescendingOrderAge(Handler.getAllParticipants());

        System.out.println("Команды с победами над определенной командой: " +
                "Определить команды, которые выиграли у заданной команды");
        String teamName = Handler.getTeamsMap().keySet().stream()
                .limit(1)
                .map(Team::getName)
                .collect(Collectors.joining());

        System.out.println("Команды с победами над " + teamName + " :" + Utility.teamsWithWinsOverSpecificTeam(teamName));

        System.out.println("Вычислить средний балл для команд в каждой категории участников " +
                "(Adult, Teenager, Pupil)");
        Utility.showTeamAverageScores();

        System.out.println("Найти все пары команд, чьи участники имеют одинаковый суммарный возраст.");
        System.out.println(Utility.haveSameTotalAge(Handler.getTeamsMap()));

        System.out.println("Выявить команды, которые не имеют проигрышей ");
        System.out.println(Utility.undefeatedTeamList(Handler.getListGamingStatistics()));

        System.out.println("Найти команды, чьи баллы улучшались с каждой игрой.");
        System.out.println(Utility.findTeamsWhoseScoresImprovedEachGame(Handler.getListGamingStatistics()));

        System.out.println("Список команд без баллов:");
        System.out.println(Utility.getTeamsWithoutPoints(Handler.getListGamingStatistics()));

        System.out.println("Список команд, которые имели ничейные результаты с заданной командой - " + teamName);
        System.out.println(Utility.findListTeamsThatDrawWithGivenTeam(Handler.getListGamingStatistics(), teamName));

        System.out.println("Вывести результаты всех игр между двумя конкретными командами.");// todo bybod gut ubraty lischnee
        Team<Adult> adultTeam = Utility.getTeam(Handler.getMapAdult(), 4);
        Team<Adult> adultTeam1 = Utility.getTeam(Handler.getMapAdult(), 1);
        Utility.printResultOfAllGamesSpecificTeams(adultTeam, adultTeam1);

        System.out.println("Сравнить две команды по  баллам и среднему возрасту участников.");
        Utility.compareTwoTeamsPointsAndMiddleAge(adultTeam1.getName(), adultTeam.getName());

        System.out.println("Найти команды с наибольшим количеством ничьих результатов.");
        System.out.println(Utility.findTeamsWithMostTies(Handler.getListGamingStatistics(),
                Handler.getListGamingStatistics().size()));

        System.out.println("Определить команды с самой длинной последовательностью побед.");
        System.out.println(Utility.determineTeamsWithLongestWinningStreak(Handler.getListGamingStatistics()));

        System.out.println("Создать комплексный отчет, включающий средний возраст команды, общее количество баллов," +
                " наибольшую победную серию, и сравнение с другими командами.");
        for (Map.Entry<String, List<String>> map:Utility.createComprehensiveReport().entrySet()){
            System.out.println(map.getKey()+ "\n"+ map.getValue());
        }




    }
}
