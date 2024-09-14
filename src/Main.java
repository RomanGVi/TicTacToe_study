import model.PlaySymbols;
import model.Player;
import org.apache.log4j.Logger;
import view.ConsoleView;
import view.View;

import java.util.InputMismatchException;
import java.util.List;

public class Main {

    private final static String titleMessage = "-= Игра крестики-нолики =-\n";
    private final static String strSelectSymbol = "Выберете каким символом вы будете играть\n";
    private static final PlayingField playingField = new PlayingField();
    private static final Logger logger = Logger.getLogger(Main.class);
    private static View view;
    private static Game game;

    public static void main(String[] args) {

        // Алгоритм работы программы
        /*
            1. Приветственное слово. (Название игры)
            2. Выбор пользователя (чем будет играть, размер поля)
            3. Игра
                3.1 Рисуем поле
                3.2 Запрашиваем координаты у игрока
                3.3 Проверяем что по указанным координатам возможно поставить фишку
                3.4 Если это возможно ставим фишку
                3.5 Рисуем поле
                3.6 Проверяем на победу/ничья. Если победа ставим имя победителям и выходим на пункт 4
                3.7 Передаем ход второму игроку
            4. Конец игры. Результаты
         */
        view = new ConsoleView();
        game = new Game();
        view.outputMessage(titleMessage);
        Player player1 = new Player(true);
        Player player2 = new Player(false);
        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1 -> {
                player1.setSymbol(PlaySymbols.SYMBOL_0.getValue());
                player2.setSymbol(PlaySymbols.SYMBOL_X.getValue());
            }
            case 2 -> {
                player1.setSymbol(PlaySymbols.SYMBOL_X.getValue());
                player2.setSymbol(PlaySymbols.SYMBOL_0.getValue());
            }
        }
        List<Player> listPlayers = List.of(player1, player2);
        game.play(view, playingField, listPlayers);
    }

    private static int getUserChoice() {

        int selectUserChoice;
        do {
            try {
                String strInfoChoice = String.format("%s:\n1. %s\n2. %s\n", strSelectSymbol, PlaySymbols.SYMBOL_0.getValue(), PlaySymbols.SYMBOL_X.getValue());
                view.outputMessage(strInfoChoice);
                selectUserChoice = view.inputNumber();
                if (selectUserChoice == 1 || selectUserChoice == 2) {
                    return selectUserChoice;
                }
            } catch (InputMismatchException | NumberFormatException exception) {
                view.outputMessage("Введено не число. Введите 1 или 2.");
            }
        } while (true);
    }
}
