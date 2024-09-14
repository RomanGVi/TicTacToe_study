import model.Player;
import org.apache.log4j.Logger;
import view.View;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Game {

    private final static String strInfoAboutStep = "=> Ход игрока %s... \n";
    private final static String strInfoWinUser = "Победил игрок - %s\n";
    private final static String strDeathDead = "Игровое поле заполнено. Продолжение игры невозможно\n";
    private String strInfoGameOver;
    private static final Logger logger = Logger.getLogger(Game.class);

    public void play(View view, PlayingField playingField, List<Player> players){
        logger.info(
                String.format(
                        "Log => Начало игры =========\nИгроки: %s",
                        players.toString())
        );
        boolean flagEndGame = false;
        view.drawPlayingField(playingField.getPlayingField());
        do {
            for (Player player : players) {
                view.outputMessage(String.format(strInfoAboutStep, player.getName()));
                turnPlayer(view, playingField, player);
                view.drawPlayingField(playingField.getPlayingField());

                if (playingField.checkWin(player.getSymbol())) {
                    strInfoGameOver = String.format(strInfoWinUser, player.getName());
                    flagEndGame = true;
                } else if (playingField.isFull()) {
                    strInfoGameOver = strDeathDead;
                    flagEndGame = true;
                }
            }
        } while (!flagEndGame);
        view.outputMessage(strInfoGameOver);
    }

    private static void turnPlayer(View view, PlayingField playingField, Player player) {
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

    private static Point thinkingAICell(Player player, PlayingField playingField) {
        Random random = new Random();
        Point cell = new Point();
        int aiLevel = 1;
        switch (aiLevel) {
            case 1:
                cell.x = random.nextInt(playingField.getSizeField());
                cell.y = random.nextInt(playingField.getSizeField());
                break;
            case 2:
                // усложняем логику выбора ячейки для робота
                break;
        }
        return cell;
    }

}


