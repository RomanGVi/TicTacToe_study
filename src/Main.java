import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class Main {

    private final static int SIZE_FIELD = 3;
    private final static char DOT_TIC = 'o';
    private final static char DOT_TAC = 'x';
    private final static String titleMessage = "-= Игра крестики-нолики =-\n";
    private final static String strWinner = "Победил игрок - %s\n";
    private final static String strDraw = "\tНичья!\n";
    private final static String strBusyCell = "Клетка занята. Выберите другую.\n";
    private final static String strInputRowNumber = "Введите номер строки: ";
    private final static String strInputColumnNumber = "Введите номер столбца: ";
    private static char dotHuman;
    private static char dotAI;
    private static String winner;

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
        View view = new ConsoleView();
        view.printMessage(titleMessage);
        PlayingField playingField = new PlayingField(SIZE_FIELD);
        int selectUserChoice = getSelectUserChoice(view);
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
        Player player1 = new Player(dotHuman, true);
        Player player2 = new Player(dotAI, false);
        boolean playerOneIsFirst = Math.random() < 0.5;
        List<Player> playerList = playerOneIsFirst ? List.of(player1, player2) : List.of(player2, player1);
        playGame(playerList, playingField, view);
        view.printMessage(winner);
    }

    private static void playGame(List<Player> playerList, PlayingField playingField, View view) {
        view.drawPlayingField(playingField.getField());
        do {
            for (Player player: playerList) {
                view.printMessage(String.format("Ход пользователя -> %s\n", player.getName()));
                turnGame(playingField, player, view);
                view.drawPlayingField(playingField.getField());
                if (playingField.checkWin(player.getSymbol())) {
                    winner = String.format(strWinner, player.getName());
                    return;
                }
                if (playingField.isMapFull()) {
                    winner = strDraw; // ничья
                    return;
                }
            }
        } while (true);
    }

    private static void turnGame(PlayingField field, Player player, View view){
        int selectRow;
        int selectColumn;
        do {
            if (player.isHuman()) {
                view.printMessage(strInputRowNumber);
                selectRow = view.getIntegerNumber() - 1;
                view.printMessage(strInputColumnNumber);
                selectColumn = view.getIntegerNumber() - 1;
            } else {
                Random random = new Random();
                selectRow = random.nextInt(field.getSize());
                selectColumn = random.nextInt(field.getSize());
            }
            if (field.setSymbol(player.getSymbol(), selectRow, selectColumn)) {
                return;
            } else if (player.isHuman()) {
                view.printMessage(strBusyCell);
            }
        } while (true);
    }

    private static int getSelectUserChoice(View view) {
        int selectUserChoice;
        do {
            try {
                view.printMessage("Выберите символ:\n\t1. o\n\t2. x\n");
                selectUserChoice = view.getIntegerNumber();
                if (selectUserChoice == 1 || selectUserChoice == 2) {
                    return selectUserChoice;
                }
            } catch (InputMismatchException | NumberFormatException exception) {
                view.printMessage("Введено не число. Введите 1 или 2.\n");
            }
        } while (true);
    }
}
