package markus.wieland.connect4;

import markus.wieland.games.game.GameConfiguration;

public class ConnectFourConfiguration implements GameConfiguration {

    private final boolean singlePlayer;

    public ConnectFourConfiguration(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }
}
