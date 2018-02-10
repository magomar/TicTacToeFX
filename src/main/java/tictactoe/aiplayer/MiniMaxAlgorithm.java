package tictactoe.aiplayer;

import tictactoe.model.Move;
import tictactoe.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mario
 */
public class MiniMaxAlgorithm {

    public static List<Integer> getEmptyCells(Player[] board) {
        ArrayList<Integer> emptyCells = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            if (board[i] == Player.NONE) {
                emptyCells.add(i);
            }
        }
        return emptyCells;
    }

    public static boolean isEndState(Player[] board, Player lastPlayer) {
        return (getEmptyCells(board).isEmpty()
                || wins(board, lastPlayer));
    }

    public static boolean wins(Player[] board, Player player) {
        if (player == Player.NONE) {
            throw new IllegalArgumentException("Player should be HUMAN or COMPUTER");
        }
        return (board[0] == player && board[1] == player && board[2] == player)
                || (board[3] == player && board[4] == player && board[5] == player)
                || (board[6] == player && board[7] == player && board[8] == player)
                || (board[0] == player && board[3] == player && board[6] == player)
                || (board[1] == player && board[4] == player && board[7] == player)
                || (board[2] == player && board[5] == player && board[8] == player)
                || (board[0] == player && board[4] == player && board[8] == player)
                || (board[2] == player && board[4] == player && board[6] == player);
    }

    public static Move minimax(Player[] newBoard, Player player) {
        List<Integer> availSpots = getEmptyCells(newBoard);
        // checks for the terminal states such as win, lose, and tie 
        //and returning a value accordingly
        if (wins(newBoard, Player.HUMAN)) {
            return new Move(-1, -10); //Lose
        } else if (wins(newBoard, Player.COMPUTER)) {
            return new Move(-1, 10); //Win
        } else if (availSpots.isEmpty()) {
            return new Move(-1, 0); //Tie
        }
        List<Move> moves = new ArrayList<>(availSpots.size());
        for (Integer spot : availSpots) {
            Move move = new Move(spot);
            newBoard[spot] = player;
            move.setScore(minimax(newBoard, player.getOponent()).getScore());
            moves.add(move);
            newBoard[spot] = Player.NONE;
        }
        Move bestMove = null;
        if (player == Player.COMPUTER) {
            int bestScore = -10000;
            for (Move move : moves) {
                if (move.getScore() > bestScore) {
                    bestMove = move;
                    bestScore = move.getScore();
                }
            }
        } else {
            int bestScore = 10000;
            for (Move move : moves) {
                if (move.getScore() < bestScore) {
                    bestMove = move;
                    bestScore = move.getScore();
                }
            }
        }
        return bestMove;
    }
}
