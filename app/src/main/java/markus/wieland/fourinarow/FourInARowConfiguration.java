package markus.wieland.fourinarow;

import markus.wieland.games.game.GameConfiguration;

public class FourInARowConfiguration implements GameConfiguration {

    private final boolean singlePlayer;

    public FourInARowConfiguration(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }
}
