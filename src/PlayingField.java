import java.util.Arrays;

public class PlayingField {

    private final static int SIZE_FIELD = 3;
   private final static char DOT_EMPTY = ' ';
    private static char[][] playingField;

    public PlayingField() {
        this(SIZE_FIELD);
    }

    public PlayingField(int size) {
        this.init(size);
    }

    private void init(int size) {
        playingField = new char[size][size];
        Arrays.stream(playingField).forEach(row -> Arrays.fill(row, DOT_EMPTY));
    }

    public int getSizeField() {
        return playingField.length;
    }

    public char[][] getPlayingField() {
        return playingField;
    }

    public boolean isCellClear(int row, int column) {
        return playingField[row][column] == DOT_EMPTY;
    }

    public boolean setSign(char symbol, int row, int column) {
        if ((row >= 0 && (row < playingField.length))
                && ((column >= 0) && (column < playingField.length))) {
            if (isCellClear(row, column)) {
                playingField[row][column] = symbol;
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (char[] chars : playingField) {
            for (char aChar : chars) {
                if (aChar == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkFillOneRow(int row, char symbol) {
        for (int j = 0; j < playingField[row].length; j++) {
            if (playingField[row][j] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFillRows(char symbol) {
        for (int i = 0; i < playingField.length; i++) {
            if (checkFillOneRow(i, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFillColumns(char symbol) {
        for (int j = 0; j < playingField.length; j++) {
            if (checkFillOneColumn(j, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFillOneColumn(int column, char symbol) {
        for (char[] row : playingField) {
            if (row[column] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFillDiagonal(char symbol) {
        for (int i = 0; i < playingField.length; i++) {
            if (playingField[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFillBackDiagonal(char symbol) {
        int size = playingField.length;
        for (int i = 0; i < size; i++) {
            if (playingField[i][(size - i) - 1] != symbol) {
                return false;
            }
        }
        return true;
    }

    public boolean checkWin(char symbol) {
        return checkFillRows(symbol) || checkFillColumns(symbol)
                || checkFillDiagonal(symbol) || checkFillBackDiagonal(symbol);
    }
}
