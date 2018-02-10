package tictactoe.model;

import tictactoe.aiplayer.MiniMaxAlgorithm;

/**
 * @author mario
 */
public final class Game {

    private final Player[] board = new Player[9];
    private Player currentPlayer = Player.NONE;
    private Player winner = Player.NONE;
    private boolean finished = false;

    public Game() {
        initializeGame(currentPlayer);
    }

    public Player[] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void initializeGame(Player player) {
        currentPlayer = player;
        winner = Player.NONE;
        finished = false;
        for (int i = 0; i < 9; i++) {
            board[i] = Player.NONE;
        }
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return finished;
    }

    public void move(Move move) {
        board[move.getIndex()] = currentPlayer;
        if (MiniMaxAlgorithm.wins(board, currentPlayer)) {
            winner = currentPlayer;
            finished = true;
        } else if (MiniMaxAlgorithm.getEmptyCells(board).isEmpty()) {
            finished = true; // It's a tie
        } else {
            currentPlayer = currentPlayer.getOponent();
        }
    }

}
