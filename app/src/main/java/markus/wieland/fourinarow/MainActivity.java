package markus.wieland.fourinarow;

import android.os.Bundle;
import android.view.WindowManager;

import markus.wieland.games.GameActivity;
import markus.wieland.games.game.GameConfiguration;
import markus.wieland.games.game.Highscore;
import markus.wieland.games.persistence.GameGenerator;
import markus.wieland.games.persistence.GameSaver;
import markus.wieland.games.screen.view.EndScreenView;
import markus.wieland.games.screen.view.StartScreenView;

public class MainActivity extends GameActivity<FourInARowConfiguration, Highscore, FourInARowGameState, FourInARowGameResult, FourInARow> {

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
    protected GameGenerator<FourInARowGameState> initializeGenerator(GameConfiguration configuration) {
        return new FourInARowGenerator(configuration, this);
    }

    @Override
    protected GameSaver<FourInARowGameState, Highscore> initializeGameSaver() {
        return null;
    }

    @Override
    protected void initializeGame(FourInARowGameState fourInARowGameStateFields) {
        game = new FourInARow(this, fourInARowGameStateFields, findViewById(R.id.four_in_a_row_game_board));
        game.start();
    }
}