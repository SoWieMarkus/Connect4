package markus.wieland.connect4;

import android.content.Context;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameConfiguration;
import markus.wieland.games.persistence.GameGenerator;
import markus.wieland.games.player.Player;
import markus.wieland.games.player.PlayerManager;

public class ConnectFourGenerator extends GameGenerator<ConnectFourGameState> {

    private final Context context;

    public ConnectFourGenerator(GameConfiguration configuration, Context context) {
        super(configuration);
        this.context = context;
    }

    @Override
    public ConnectFourConfiguration getConfiguration() {
        return (ConnectFourConfiguration) super.getConfiguration();
    }

    @Override
    public ConnectFourGameState generate() {
        PlayerManager players = new PlayerManager();
        SerializableMatrix<ConnectFourGameStateField> fields = new SerializableMatrix<>(ConnectFourGameBoardView.SIZE_X, ConnectFourGameBoardView.SIZE_Y);

        for (int x = 0; x < ConnectFourGameBoardView.SIZE_X; x++) {
            for (int y = 0; y < ConnectFourGameBoardView.SIZE_Y; y++) {
                fields.set(new Coordinate(x, y), new ConnectFourGameStateField(new Coordinate(x, y)));
            }
        }

        players.register(new Player(null, ConnectFourGameBoardFieldView.PLAYER_1, context.getString(R.string.fiar_player_1)));
        players.register(new Player(getConfiguration().isSinglePlayer()
                ? new ConnectFourAI(ConnectFourGameBoardFieldView.PLAYER_2, ConnectFourGameBoardFieldView.PLAYER_1, Difficulty.HARD)
                : null, ConnectFourGameBoardFieldView.PLAYER_2, getConfiguration().isSinglePlayer() ? context.getString(R.string.fiar_bot) : context.getString(R.string.fiar_player_2)));

        return new ConnectFourGameState(fields, players);
    }
}
