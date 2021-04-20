package markus.wieland.fourinarow;

import markus.wieland.games.game.GameResult;
import markus.wieland.games.player.Player;

public class FourInARowGameResult implements GameResult {

    private final Player winner;

    public FourInARowGameResult(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
