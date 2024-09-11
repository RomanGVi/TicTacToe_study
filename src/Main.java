import org.apache.log4j.Logger;
import view.ConsoleView;
import view.View;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static String titleMessage = "-= Игра крестики-нолики =-\n";
    private final static String strSelectSymbol = "Выберете каким символом вы будете играть\n";
    private final static String strInfoAboutStep = "=> Ход игрока %s... \n";
    private final static String strInfoWinUser = "Победил игрок - %s\n";
    private final static String strDeathDead = "Игровое поле заполнено. Продолжение игры невозможно\n";
    private static final PlayingField playingField = new PlayingField();
    private static String winner;
    private static Player player1, player2;
    private static List<Player> listPlayers;
    private static final Logger logger = Logger.getLogger(Main.class);
    private static View view;

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
        view.outputMessage(titleMessage);
        int selectUserChoice = getSelectUserChoice();
        switch (selectUserChoice) {
            case 1 -> {
                player1 = new Player(PlaySymbols.SYMBOL_0.getValue(), true);
                player2 = new Player(PlaySymbols.SYMBOL_X.getValue(), false);
            }
            case 2 -> {
                player1 = new Player(PlaySymbols.SYMBOL_X.getValue(), true);
                player2 = new Player(PlaySymbols.SYMBOL_0.getValue(), false);
            }
        }
        listPlayers = List.of(player1, player2);
        view.drawPlayingField(playingField.getPlayingField());
        playGame();
        view.outputMessage(winner);
    }

    private static void playGame() {
        do {
            for (Player player : listPlayers) {
                view.outputMessage(String.format(strInfoAboutStep, player.getName()));
                turnPlayer(playingField, player);
                view.drawPlayingField(playingField.getPlayingField());

                if (playingField.checkWin(player.getSymbol())) {
                    winner = String.format(strInfoWinUser, player.getName());
                    return;
                }
                if (playingField.isFull()) {
                    view.outputMessage(strDeathDead);
                    break;
                }
            }
        } while (true);
    }

    private static void turnPlayer(PlayingField playingField, Player player) {
        Random random = new Random();
        int selectRow, selectColumn;
        do {
            if (player.isHuman()) {
                view.outputMessage("Введите номер строки: ");
                selectRow = view.inputNumber() - 1;
                view.outputMessage("Введите номер столбца: ");
                selectColumn = view.inputNumber() - 1;
            } else {
                selectRow = random.nextInt(playingField.getSizeField());
                selectColumn = random.nextInt(playingField.getSizeField());
            }
            logger.info(
                    String.format(
                            "Log => %s Проба координат: [%d, %d]",
                            player.getName(), selectRow , selectColumn)
            );
            if (playingField.setSign(player.getSymbol(), selectRow, selectColumn)) {
                logger.info(
                        String.format(
                                "Log => %s Координаты приняты: [%d, %d]",
                                player.getName(), selectRow , selectColumn)
                );
                return;
            }
            if (player.isHuman()) {
                view.outputMessage("Введена не корректная позиция символа. Попробуйте снова.\n");
            }
        } while (true);
    }

    private static int getSelectUserChoice() {

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

    // метод
}
