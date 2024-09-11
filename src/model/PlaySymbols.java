package model;

public enum PlaySymbols {

    SYMBOL_X('X'),
    SYMBOL_0('O'),
    SYMBOL_UNDEFINE('■');
    private final char value;

    PlaySymbols(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
