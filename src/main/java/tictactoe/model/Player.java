package tictactoe.model;

/**
 * @author mario
 */
public enum Player {
    NONE,
    HUMAN,
    COMPUTER;

    static {
        HUMAN.oponent = COMPUTER;
        COMPUTER.oponent = HUMAN;
        NONE.oponent = NONE;
    }

    private Player oponent;

    public Player getOponent() {
        return oponent;
    }

}
