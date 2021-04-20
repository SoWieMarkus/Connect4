package markus.wieland.connect4;

import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.grid.GridGameState;
import markus.wieland.games.player.PlayerManager;

public class ConnectFourGameState extends GridGameState<ConnectFourGameStateField> {

    private final PlayerManager players;

    public ConnectFourGameState(SerializableMatrix<ConnectFourGameStateField> matrix, PlayerManager players) {
        super(matrix);
        this.players = players;
    }

    public PlayerManager getPlayers() {
        return players;
    }

    public int[][] convert() {
        int[][] board = new int[ConnectFourGameBoardView.SIZE_Y + 1][ConnectFourGameBoardView.SIZE_X];
        for (ConnectFourGameStateField field : matrix) {
            board[field.getCoordinate().getY()][field.getCoordinate().getX()] = field.getValue();
        }
        for (int x = 0; x < ConnectFourGameBoardView.SIZE_X; x++) {
            board[ConnectFourGameBoardView.SIZE_Y][x] = 2;
        }
        return board;
    }
}
