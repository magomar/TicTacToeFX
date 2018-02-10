package tictactoe;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tictactoe.aiplayer.MiniMaxAlgorithm;
import tictactoe.model.Game;
import tictactoe.model.Move;
import tictactoe.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ViewController implements Initializable {

    private final StackPane[][] cellPane = new StackPane[3][3];
    private final IntegerProperty markerSize = new SimpleIntegerProperty();
    @FXML
    private GridPane boardGrid;
    @FXML
    private Button startButton;
    @FXML
    private RadioButton human;
    @FXML
    private ToggleGroup firstPlayerSelection;
    @FXML
    private RadioButton computer;
    private Game game;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StackPane pane = new StackPane();
                cellPane[i][j] = pane;
                pane.setOnMouseClicked(this::cellClicked);
                boardGrid.add(pane, i, j);
            }
        }
        NumberBinding markerWidth = Bindings.divide(boardGrid.widthProperty(), 7);
        NumberBinding markerHeight = Bindings.divide(boardGrid.heightProperty(), 7);
        markerSize.bind(Bindings.min(markerWidth, markerHeight));
        markerSize.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() != oldValue.intValue()) {
                updateBoard();
            }
        });
    }

    private void cellClicked(MouseEvent event) {
        if (game.getCurrentPlayer() != Player.HUMAN) {
            return;
        }
        StackPane node = (StackPane) event.getSource();
        int i = GridPane.getColumnIndex(node);
        int j = GridPane.getRowIndex(node);
        int cellIndex = i * 3 + j;
        Player[] board = game.getBoard();
        if (board[cellIndex] == Player.NONE) {
            game.move(new Move(cellIndex));
            updateBoard();
            if (!checkEndOfGame()) {
                playComputer();
            }
        }

    }

    @FXML
    private void start(ActionEvent event) {
        startButton.setDisable(true);
        Player currentPlayer = (firstPlayerSelection.getSelectedToggle() == computer
                ? Player.COMPUTER : Player.HUMAN);
        game.initializeGame(currentPlayer);
        updateBoard();
        if (currentPlayer == Player.COMPUTER) {
            playComputer();
        }
    }

    private void playComputer() {
        Player[] board = game.getBoard();
        Move move = MiniMaxAlgorithm.minimax(board, Player.COMPUTER);
        game.move(move);
        updateBoard();
        checkEndOfGame();
    }

    private boolean checkEndOfGame() {
        if (game.isFinished()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("GAME OVER");
            alert.setHeaderText(null);
            String message;
            switch (game.getWinner()) {
                case HUMAN:
                    message = "You win";
                    break;
                case COMPUTER:
                    message = "Computer wins";
                    break;
                default:
                    message = "This is a tie";
            }
            startButton.setDisable(false);
            alert.setContentText(message);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            alert.showAndWait();
            startButton.setText("Try again");
            return true;
        } else {
            return false;
        }
    }

    private void updateBoard() {
        Player[] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Player cell = board[i * 3 + j];
                switch (cell) {
                    case NONE:
                        cellPane[i][j].getChildren().clear();
                        break;
                    case HUMAN:
                        cellPane[i][j].getChildren().add(new Circle(markerSize.doubleValue(), Color.BLUE));
                        break;
                    case COMPUTER:
                        cellPane[i][j].getChildren().add(new Circle(markerSize.doubleValue(), Color.RED));
                        break;
                }
            }
        }
    }

}
