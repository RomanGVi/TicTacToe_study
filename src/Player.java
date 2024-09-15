public class Player {

    private static int count = 0;
    private final String name;
    private final char symbol;
    private final boolean isHuman;


    public Player(char symbol, boolean isHuman) {
        this(String.format("Player%d", ++count), symbol, isHuman);
    }

    public Player(String name, char symbol, boolean isHuman) {
        this.name = name;
        this.symbol = symbol;
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
