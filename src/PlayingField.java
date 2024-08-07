import java.util.Arrays;

public class PlayingField {

    private final static char DOT_TIC = 'o';
    private final static char DOT_TAC = 'x';
    private final static char DOT_EMPTY = '•';
    private static char[][] playingField;

    public void init(int size) {
        playingField = new char[size][size];
        Arrays.stream(playingField).forEach(row -> Arrays.fill(row, DOT_EMPTY));
    }

    public static char[][] getPlayingField() {
        return playingField;
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

    public boolean isCellClear(int row, int column) {
        return playingField[column][row] != DOT_EMPTY;
    }

    public boolean setSign(char symbol, int row, int column) {
        if (isCellClear(row, column) || (row >= playingField.length) && (row < 0) && (column >= playingField.length) && (column < 0)) {
            playingField[row][column] = symbol;
            return true;
        }
        return false;
    }
}
