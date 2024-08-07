import java.util.*;

public class Main {

    private final static int SIZE_FIELD = 3;
    private final static char DOT_TIC = 'o';
    private final static char DOT_TAC = 'x';
    private final static char DOT_EMPTY = '•';
    private final static String titleMessage = "-= Игра крестики-нолики =-";
    private final static String strSelectSymbol = "Выберете каким символом вы будете играть";
    private final static String strInfoAboutStep = "=> Ход игрока %s... \n";
    private final static String strDeathDead = "Игровое поле заполнено. Продолжение игры невозможно";
    private static char[][] playingField;
    private static String winner;
    private static final Scanner scanner = new Scanner(System.in);
    private static Player player1, player2;
    private static List<Player> listPlayers;

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
        playingField = initializePlayingField(SIZE_FIELD);
        PlayingField field = new PlayingField();
        field.init(SIZE_FIELD);
        int selectUserChoice = getSelectUserChoice();
        switch (selectUserChoice) {
            case 1 -> {
                player1 = new Player(DOT_TIC, true);
                player2 = new Player(DOT_TAC, false);
            }
            case 2 -> {
                player1 = new Player(DOT_TAC, true);
                player2 = new Player(DOT_TIC, false);
            }
        }
        listPlayers = List.of(player1, player2);
        drawPlayingField(playingField);
        playGame();
        System.out.println(winner);
    }

    private static void playGame() {
        boolean flagHuman, flagAI;
        do {
//            for (Player player: listPlayers) {
//                System.out.printf("Ход игрока %s -> ", player.getName());
//                stepUser(playingField);
//                drawPlayingField(playingField);
//                flagHuman = checkWin(playingField, player.getSymbol());
//                if (flagHuman) {
//                    winner = "Победил игрок " + player.getName();
//                    return;
//                }
//
//                if (checkDeadHeat(playingField)) {
//                    System.out.println("Игровое поле заполнено. Продолжение игры невозможно");
//                    break;
//                }
//            }
            System.out.printf(strInfoAboutStep, player1.getName());
            stepUser(playingField);
            drawPlayingField(playingField);
            flagHuman = checkWin(playingField, player1.getSymbol());
            if (flagHuman) {
                winner = String.format("Победил игрок - %s", player1.getName());
                return;
            }

            if (isPlayingFieldFull(playingField)) {
                System.out.println(strDeathDead);
                break;
            }

            System.out.printf(strInfoAboutStep, player2.getName());
            stepAI(playingField);
            drawPlayingField(playingField);
            flagAI = checkWin(playingField, player2.getSymbol());
            if (flagAI) {
                winner = String.format("Победил игрок - %s", player1.getName());
                return;
            }
            if (isPlayingFieldFull(playingField)) {
                System.out.println(strDeathDead);
                break;
            }

        } while (true);
    }

    private static char[][] initializePlayingField(int sizeField) {
        char[][] field = new char[sizeField][sizeField];
        Arrays.stream(field).forEach(row -> Arrays.fill(row, DOT_EMPTY));
        return field;
    }

    private static boolean checkWin(char[][] array, char dot) {

        return checkFillRows(array, dot) || checkFillColumns(array, dot) || checkFillDiagonal(array, dot) || checkFillBackDiagonal(array, dot);
    }

    private static boolean checkFillColumns(char[][] array, char dot) {
        for (int j = 0; j < array.length; j++) {
            if (checkFillOneColumn(array, j, dot)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkFillOneColumn(char[][] array, int column, char symbol) {
        for (char[] row : array) {
            if (row[column] != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkFillRows(char[][] array, char dot) {
        for (int i = 0; i < array.length; i++) {
            if (checkFillOneRow(array, i, dot)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkFillOneRow(char[][] array, int row, char symbol) {
        for (int j = 0; j < array[row].length; j++) {
            if (array[row][j] != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkFillDiagonal(char[][] array, char symbol) {
        for (int i = 0; i < array.length; i++) {
            if (array[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkFillBackDiagonal(char[][] array, char symbol) {
        for (int i = 0; i < array.length; i++) {
            if (array[i][(array.length - i) - 1] != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPlayingFieldFull(char[][] array) {
        for (char[] chars : array) {
            for (char aChar : chars) {
                if (aChar == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void stepUser(char[][] field) {
        System.out.print("Введите номер строки: ");
        int selectRow = Integer.parseInt(scanner.next()) - 1;
        System.out.print("Введите номер столбца: ");
        int selectColumn = Integer.parseInt(scanner.next()) -1;
        if (isFieldIsEmpty(field, selectRow, selectColumn)) {
            field[selectRow][selectColumn] = player1.getSymbol();
        }
    }

    private static void stepAI(char[][] field) {
        Random random = new Random();
        do {
            int cellRow = random.nextInt(field.length);
            int cellColumn = random.nextInt(field.length);
            if (isFieldIsEmpty(field, cellRow, cellColumn)) {
                field[cellRow][cellColumn] = player2.getSymbol();
                return;
            }
        } while (true);
    }

    private static boolean isFieldIsEmpty(char[][] field, int row, int column) {
        return  field[row][column] == DOT_EMPTY;
    }

    private static int getSelectUserChoice() {

        int selectUserChoice;
        do {
            try {
                System.out.printf("%s:\n1. %s\n2. %s\n", strSelectSymbol, DOT_TIC, DOT_TAC);
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
        System.out.printf("%3c", ' ');
        for (int i = 0; i < array.length + 1; i++) {
            System.out.print("───");
        }
        System.out.println();
        for (int i = 0; i < array.length ; i ++) {
            System.out.printf("%2d │", i + 1);
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%2c │", array[i][j]);
            }
            System.out.println();
            System.out.printf("%3c", ' ');
            for (int j = 0; j < array.length +1; j++) {
                System.out.print("───");
            }
            System.out.println();
        }
    }
}
