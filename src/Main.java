import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static int SIZE_FIELD = 5;
    private final static char DOT_TIC = 'o';
    private final static char DOT_TAC = 'x';
    private final static char DOT_EMPTY = '•';
    private final static String titleMessage = "-= Игра крестики-нолики =-";

    private static char dotHuman;
    private static char dotAI;

    private static char[][] playingField;

    private static String winner;

    private static final Scanner scanner = new Scanner(System.in);

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
        int selectUserChoice = getSelectUserChoice();
        switch (selectUserChoice) {
            case 1 -> {
                dotHuman = DOT_TIC;
                dotAI = DOT_TAC;
            }
            case 2 -> {
                dotHuman = DOT_TAC;
                dotAI = DOT_TIC;
            }
        }
        drawMap(playingField);
        playGame();
        System.out.println(winner);
    }

    private static void playGame() {
        boolean flagHuman, flagAI;
        do {
            System.out.println("Ход пользователя ->");
            stepUser(playingField);
            drawMap(playingField);
            flagHuman = checkWin(playingField, dotHuman);
            if (flagHuman) {
                winner = "Победил человек";
                return;
            }
            System.out.println("Ход робота ->");
            stepAI(playingField);
            drawMap(playingField);
            flagAI = checkWin(playingField, dotAI);
            if (flagAI) {
                winner = "Победил робот";
                return;
            }

        } while (true);
    }

    private static char[][] initializePlayingField(int sizeField) {
        char[][] field = new char[sizeField][sizeField];
        for (int i = 0; i < sizeField; i++)  {
            for (int j = 0; j < sizeField; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
        return field;
    }

    private static boolean checkWin(char[][] array, char dot) {
        for (int i = 0; i < array.length; i++) {
            if (checkFillRow(array, i, dot)) {
                return true;
            }
        }

        for (int j = 0; j < array.length; j++) {
            if (checkFillColumn(array, j, dot)) {
                return true;
            }
        }

        if (checkFillDiagonal(array, dot) || checkFillBackDiagonal(array, dot)) {
            return true;
        }
        return false;
    }

    private static boolean checkFillRow(char[][] array, int row, char symbol) {
        for (int j = 0; j < array[row].length; j++) {
            if (array[row][j] != symbol) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkFillColumn(char[][] array, int column, char symbol) {
        for (int j = 0; j < array.length; j++) {
            if (array[j][column] != symbol) {
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

    private static void stepAI(char[][] field) {
        Random random = new Random();
        do {
            int cellRow = random.nextInt(field.length);
            int cellColumn = random.nextInt(field.length);
            if (isFieldIsEmpty(field, cellRow, cellColumn)) {
                field[cellRow][cellColumn] = dotAI;
                return;
            }
        } while (true);

    }

    private static boolean isMapFull(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void stepUser(char[][] field) {
        System.out.println("Введите номер строки:");
        int selectRow = Integer.parseInt(scanner.next()) - 1;
        System.out.println("Введите номер столбца:");
        int selectColumn = Integer.parseInt(scanner.next()) -1;
        if (isFieldIsEmpty(field, selectRow, selectColumn)) {
            field[selectRow][selectColumn] = dotHuman;
        }
    }

    private static boolean isFieldIsEmpty(char[][] field, int row, int column) {
        return  field[row][column] == DOT_EMPTY;
    }

    private static int getSelectUserChoice() {

        int selectUserChoice = 0;
        do {
            try {
                System.out.println("Выберите символ:\n\t1. o\n\t2. x");
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
    private static void drawMap(char[][] array) {
        System.out.printf("%3c", ' ');
        // Заголовок таблицы
        for (int i = 1; i<= SIZE_FIELD; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();
        for (int i = 0; i < SIZE_FIELD ; i++) {
            System.out.printf("%3d", i + 1);
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%3c", array[i][j]);
            }
            System.out.println();
        }
    }
}
