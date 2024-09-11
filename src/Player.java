import java.awt.*;

public class Player {

    static int count; // Номер игрока. Нужен ли он? Надо подумать
    private final String name;
    private final boolean isHuman;
    private char playerSymbol;
    private Point fishka;

    public Player(String name, char symbol, boolean isHuman) {
        this.name = name;
        setSymbol(symbol);
        this.isHuman = isHuman;
    }


    public Player(char symbol, boolean isHuman) {
        this("Player" + ++count, symbol, isHuman);
    }

    public char getSymbol() {
        return playerSymbol;
    }

    public void setSymbol(char symbol) {
        playerSymbol = symbol;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
