package markus.wieland.connect4;

import android.os.Bundle;
import android.view.WindowManager;

import markus.wieland.games.GameActivity;
import markus.wieland.games.game.GameConfiguration;
import markus.wieland.games.game.Highscore;
import markus.wieland.games.persistence.GameGenerator;
import markus.wieland.games.persistence.GameSaver;
import markus.wieland.games.screen.view.EndScreenView;
import markus.wieland.games.screen.view.StartScreenView;

public class MainActivity extends GameActivity<ConnectFourConfiguration, Highscore, ConnectFourGameState, ConnectFourGameResult, ConnectConnectFour> {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected StartScreenView initializeStartScreen() {
        return findViewById(R.id.four_in_a_row_start_screen);
    }

    @Override
    protected EndScreenView initializeEndScreen() {
        return findViewById(R.id.four_in_a_row_end_screen);
    }

    @Override
    protected GameGenerator<ConnectFourGameState> initializeGenerator(GameConfiguration configuration) {
        return new ConnectFourGenerator(configuration, this);
    }

    @Override
    protected GameSaver<ConnectFourGameState, Highscore> initializeGameSaver() {
        return null;
    }

    @Override
    protected void initializeGame(ConnectFourGameState connectFourGameStateFields) {
        game = new ConnectConnectFour(this, connectFourGameStateFields, findViewById(R.id.four_in_a_row_game_board));
        game.start();
    }
}