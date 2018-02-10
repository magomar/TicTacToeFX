package tictactoe.model;

/**
 * @author mario
 */
public class Move {

    private int index;
    private int score;

    public Move(int index) {
        this.index = index;
    }

    public Move(int index, int score) {
        this.index = index;
        this.score = score;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
