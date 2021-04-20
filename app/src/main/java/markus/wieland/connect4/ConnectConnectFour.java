package markus.wieland.connect4;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import markus.wieland.games.ai.AIMove;
import markus.wieland.games.ai.pattern.PatternMatcher;
import markus.wieland.games.ai.pattern.PatternMatchingLine;
import markus.wieland.games.ai.pattern.PatternType;
import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameBoardField;
import markus.wieland.games.game.GameEventListener;
import markus.wieland.games.game.MultiPlayerGame;
import markus.wieland.games.player.Player;

public class ConnectConnectFour extends MultiPlayerGame<ConnectFourGameState, ConnectFourGameResult> implements ConnectFourGameBoardInteractionListener {

    private final ConnectFourGameBoardView connectFourGameBoardView;

    private boolean aiMove;

    public ConnectConnectFour(GameEventListener<ConnectFourGameResult> gameEventListener, ConnectFourGameState gameState, ConnectFourGameBoardView connectFourGameBoardView) {
        super(gameEventListener);
        this.playerManager = gameState.getPlayers();
        this.playerManager.shuffleOrder();
        this.connectFourGameBoardView = connectFourGameBoardView;
        this.connectFourGameBoardView.setGameBoardInteractionListener(this);
        this.connectFourGameBoardView.loadGameState(gameState);
        aiMove = false;

        PatternMatcher.getInstance().initialize(4, connectFourGameBoardView.getLines(), GameBoardField.FREE);
        PatternMatcher.getInstance().append(4, PatternType.BLOCKED);

        nextPlayer();
    }

    @Override
    public ConnectFourGameState getGameState() {
        return new ConnectFourGameState(connectFourGameBoardView.getGameState(), playerManager);
    }

    @Override
    public ConnectFourGameResult getResult() {
        ConnectFourGameState gameState = getGameState();
        ConnectFourAIMove ticTacToeAIMove = new ConnectFourAIMove(
                Difficulty.HARD, ConnectFourGameBoardFieldView.PLAYER_1, ConnectFourGameBoardFieldView.PLAYER_2, 0, 0,
                gameState.convert());

        List<PatternMatchingLine> patternMatchingLines = ticTacToeAIMove.getPatternMatchingLines();

        if (checkWin(ConnectFourGameBoardFieldView.PLAYER_1, patternMatchingLines))
            return new ConnectFourGameResult(playerManager.getPlayer(ConnectFourGameBoardFieldView.PLAYER_1));
        if (checkWin(ConnectFourGameBoardFieldView.PLAYER_2, patternMatchingLines))
            return new ConnectFourGameResult(playerManager.getPlayer(ConnectFourGameBoardFieldView.PLAYER_2));
        if (connectFourGameBoardView.isCompleted())
            return new ConnectFourGameResult(null);
        return null;
    }

    @Override
    protected void performAIMove(AIMove aiMove) {
        ConnectFourAIMove connectFourAIMove = (ConnectFourAIMove) aiMove;
        performClick(connectFourAIMove.getX());
        connectFourGameBoardView.setEnabled(true);
    }

    private boolean checkWin(int playerValue, List<PatternMatchingLine> patternMatchingLines) {
        return PatternMatcher.getInstance().getAmountOfMatchingPatterns(4, playerValue, patternMatchingLines) >= 1;
    }

    @Override
    public void nextPlayer() {
        Player player = playerManager.next();
        connectFourGameBoardView.switchPlayer(player.getValue());
        if (player.hasAI()) {
            aiMove = true;
            connectFourGameBoardView.setEnabled(false);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> performAIMove(player.move(getGameState())), 700);

            return;
        }
        aiMove = false;
        connectFourGameBoardView.setEnabled(true);
    }

    private void performClick(int column) {
        connectFourGameBoardView.performClick(column, playerManager.getCurrentPlayer().getValue());
        ConnectFourGameResult gameResult = getResult();
        if (gameResult != null) {
            finish(gameResult);
            return;
        }
        nextPlayer();
    }

    @Override
    public void clickColumn(int column) {
        if (!aiMove && connectFourGameBoardView.get(new Coordinate(column, 0)).isEmpty())
            performClick(column);
    }
}
