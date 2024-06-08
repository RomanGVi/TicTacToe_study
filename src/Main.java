import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static int SIZE_FIELD = 3;
    private final static char DOT_TIC = 'o';
    private final static char DOT_TAC = 'x';
    private final static char DOT_EMPTY = '•';

    private static char dotHuman;
    private static char dotAI;

    private static char[][] field;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        System.out.println("Игра крестики-нолики");
        field = new char[SIZE_FIELD][SIZE_FIELD];
        initialazeField(field);
        int selectUserChoice = getSelectUserChoice();
        switch (selectUserChoice) {
            case 1:
                dotHuman = DOT_TIC;
                dotAI = DOT_TAC;
                break;
            case 2:
                dotHuman = DOT_TAC;
                dotAI = DOT_TIC;
        }
        drawMap(field);

        if (!isMapFull(field)) {
            stepUser(field);
            checkWin(field, dotHuman);
        }
        if (!isMapFull(field)) {
            stepAI(field);
            checkWin(field, dotAI);
        };
    }

    private static void checkWin(char[][] array, char dot) {
        for (int i = 0; i < array.length; i++) {
            if (checkFillRow(array, i, dot)) {
                //TODO
            }
        }
    }

    private static boolean checkFillRow(char[][] array, int row, char symbol) {

        for (int j = 0; j < array[row].length; j++) {
            if (array[row][j] == DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkFillColumn(char[][] array, int column, char symbol) {
        for (int j = 0; j < array.length; j++) {
            if (array[j][column] == DOT_EMPTY) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkFillDiagonal() {

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
        int selectRow = Integer.parseInt(scanner.next());
        System.out.println("Введите номер столбца:");
        int selectColumn = Integer.parseInt(scanner.next());
        if (isFieldIsEmpty(field, selectRow, selectColumn)) {
            field[selectRow][selectColumn] = dotHuman;
        }
    }

    private static boolean isFieldIsEmpty(char[][] field, int row, int column) {
        return  field[row][column] == DOT_EMPTY;
    }

    private static void initialazeField(char[][] field) {
        for (int i = 0; i < field.length; i++)  {
            Arrays.fill(field[i], DOT_EMPTY);
        }
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
                System.out.println("Введен не число. Введите 1 или 2.");
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
