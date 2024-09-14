package model;

import java.awt.*;
import java.util.HashMap;

public class Player {

    static int count; // Номер игрока. Нужен ли он? Надо подумать
    private final String name;
    private final boolean isHuman;
    private char playerSymbol;
    private final HashMap<Point, Integer> historyMap;


    public Player(String name, char symbol, boolean isHuman) {
        this.name = name;
        setSymbol(symbol);
        this.isHuman = isHuman;
        this.historyMap = new HashMap<>();
    }

    public Player(char symbol, boolean isHuman) {
        this("model.Player" + ++count, symbol, isHuman);
    }

    public Player(boolean isHuman) {
        this(PlaySymbols.SYMBOL_UNDEFINE.getValue(), isHuman);
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

    public HashMap<Point, Integer> getHistoryMap() {
        return historyMap;
    }

    public void saveTurn(Point cell) {
        if (historyMap != null) {
            historyMap.put(cell, 1);
        }
    }
}
