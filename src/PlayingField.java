public class PlayingField {

    private final int size;
    private final char[][] field;
    public static final char EMPTY_CELL = '•';

    public PlayingField(int size) {
        this.size = size;
        this.field = init(size);
    }

    public int getSize() {
        return size;
    }

    public char[][] getField() {
        char[][] tempArray = new char[size][size]; // deep copy of game field
        for (int i = 0; i < size; i++) {
            System.arraycopy(field[i], 0, tempArray[i], 0, size);
        }
        return tempArray;
    }

    private char[][] init(int size){
        char[][] result = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = EMPTY_CELL;
            }
        }
        return result;
    }

    public boolean isEmptyCell(int row, int column) {
        if (row < 0 || row >= size || column < 0 || column >= size) return false;
        return field[row][column] == EMPTY_CELL;
    }

    public boolean setSymbol(char symbol, int row, int column){
        if (isEmptyCell(row, column)) {
            field[row][column] = symbol;
            return true;
        }
        return false;
    }

    public boolean checkWin(char symbol){
        return checkAllRows(symbol) || checkAllColumn(symbol) || checkFillDiagonal(symbol) || checkFillBackDiagonal(symbol);
    }

    private boolean checkAllRows(char symbol) {
        for (int row = 0; row < field.length; row++) {
            if (checkFillRow(row, symbol)) return true;
        }
        return false;
    }

    private boolean checkFillRow(int row, char symbol) {
        for (int j = 0; j < field[row].length; j++) {
            if (field[row][j] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAllColumn(char symbol) {
        for (int column = 0; column < field.length; column++) {
            if (checkFillColumn(column, symbol)) return true;
        }
        return false;
    }

    private boolean checkFillColumn(int column, char symbol) {
        for (char[] chars : field) {
            if (chars[column] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFillDiagonal(char symbol) {
        for (int i = 0; i < size; i++) {
            if (field[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFillBackDiagonal(char symbol) {
        int right = size - 1;
        for (int i = 0; i < size; i++) {
            if (field[i][right - i] != symbol) {
                return false;
            }
        }
        return true;
    }

    public boolean isMapFull() {
        for (char[] chars : field) {
            for (char aChar : chars) {
                if (aChar == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }
}
