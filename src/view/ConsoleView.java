package view;

import java.util.Scanner;

public class ConsoleView implements View {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public int inputNumber() {
        return Integer.parseInt(scanner.next());
    }

    public void outputMessage(String message){
        System.out.print(message);
    }
    public void drawPlayingField(char[][] array) {
        System.out.printf("%4c", ' ');
        // Заголовок таблицы
        for (int i = 1; i<= array.length; i++) {
            System.out.printf("%2d  ", i);
        }
        System.out.println();
        drawBorderLineField(array.length, "┌", "───┬", "┐");
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
            drawBorderLineField(array.length, "├", "───┼", "┤");
        }
        drawBorderLineField(array.length, "└", "───┴", "┘");
    }

    private static void drawBorderLineField(int arrayLength, String firstStr, String middleStr, String endStr) {
        // Top-Line Field
        System.out.printf("%3c", ' ');
        System.out.print(firstStr);
        for (int i = 0; i < arrayLength; i++) {
            if (i == arrayLength - 1) {
                System.out.print("───");
            } else {
                System.out.print(middleStr);
            }
        }
        System.out.println(endStr);
    }
}
