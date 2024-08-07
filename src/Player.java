public class Player {

    static int count; // Номер игрока. Нужен ли он? Надо подумать
    private final String name;
    private final boolean isHuman;
    private char playerSymbol;

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

    public void setSymbol(char playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
