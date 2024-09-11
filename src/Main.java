import org.apache.log4j.Logger;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

//    private final static char DOT_ZERO = 'o';
//    private final static char DOT_CROSS = 'x';
    private final static String titleMessage = "-= Игра крестики-нолики =-";
    private final static String strSelectSymbol = "Выберете каким символом вы будете играть";
    private final static String strInfoAboutStep = "=> Ход игрока %s... \n";
    private final static String strInfoWinUser = "Победил игрок - %s";
    private final static String strDeathDead = "Игровое поле заполнено. Продолжение игры невозможно";
    private static final PlayingField playingField = new PlayingField();
    private static String winner;
    private static final Scanner scanner = new Scanner(System.in);
    private static Player player1, player2;
    private static List<Player> listPlayers;
    private static final Logger logger = Logger.getLogger(Main.class);

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

        System.out.println(titleMessage);

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
        drawPlayingField(playingField.getPlayingField());
        playGame();
        System.out.println(winner);
    }

    private static void playGame() {
        do {
            for (Player player : listPlayers) {
                System.out.printf(strInfoAboutStep, player.getName());
                turnPlayer(playingField, player);
                drawPlayingField(playingField.getPlayingField());

                if (playingField.checkWin(player.getSymbol())) {
                    winner = String.format(strInfoWinUser, player.getName());
                    return;
                }
                if (playingField.isFull()) {
                    System.out.println(strDeathDead);
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
                System.out.print("Введите номер строки: ");
                selectRow = Integer.parseInt(scanner.next()) - 1;
                System.out.print("Введите номер столбца: ");
                selectColumn = Integer.parseInt(scanner.next()) - 1;
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
                System.out.println("Введена не корректная позиция символа. Попробуйте снова.");
            }
        } while (true);
    }

    private static int getSelectUserChoice() {

        int selectUserChoice;
        do {
            try {
                System.out.printf("%s:\n1. %s\n2. %s\n", strSelectSymbol, PlaySymbols.SYMBOL_0.getValue(), PlaySymbols.SYMBOL_X.getValue());
                selectUserChoice = Integer.parseInt(scanner.next());
                if (selectUserChoice == 1 || selectUserChoice == 2) {
                    return selectUserChoice;
                }
            } catch (InputMismatchException | NumberFormatException exception) {
                System.out.println("Введено не число. Введите 1 или 2.");
            }
        } while (true);
    }

    // метод
    private static void drawPlayingField(char[][] array) {
        System.out.printf("%4c", ' ');
        // Заголовок таблицы
        for (int i = 1; i<= array.length; i++) {
            System.out.printf("%2d  ", i);
        }
        System.out.println();
        // Top-Line Field
        System.out.printf("%3c", ' ');
        System.out.print("┌");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                System.out.print("───");
            } else {
                System.out.print("───┬");
            }
        }
        System.out.println("┐");
        // Main part Field
        for (int i = 0; i < array.length ; i ++) {
            System.out.printf("%2d │", i + 1);
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%2c │", array[i][j]);
            }
            System.out.println();
            if (i == array.length - 1) {
                continue;
            }
            System.out.printf("%3c", ' ');
            System.out.print("├");
            for (int k = 0; k < array.length; k++) {
                if (k == array.length - 1) {
                    System.out.print("───");
                } else {
                    System.out.print("───┼");
                }
            }
            System.out.println("┤");
        }
        // Bottom line Field
        System.out.printf("%3c", ' ');
        System.out.print("└");
        for (int k = 0; k < array.length; k++) {
            if (k == array.length - 1) {
                System.out.print("───");
            } else {
                System.out.print("───┴");
            }
        }
        System.out.println("┘");
    }
}
