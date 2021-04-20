package markus.wieland.connect4;

import markus.wieland.games.game.GameResult;
import markus.wieland.games.player.Player;

public class ConnectFourGameResult implements GameResult {

    private final Player winner;

    public ConnectFourGameResult(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
