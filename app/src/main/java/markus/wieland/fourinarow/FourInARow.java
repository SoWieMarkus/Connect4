package markus.wieland.fourinarow;

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

public class FourInARow extends MultiPlayerGame<FourInARowGameState, FourInARowGameResult> implements FourInARowGameBoardInteractionListener {

    private final FourInARowGameBoardView fourInARowGameBoardView;

    private boolean aiMove;

    public FourInARow(GameEventListener<FourInARowGameResult> gameEventListener, FourInARowGameState gameState, FourInARowGameBoardView fourInARowGameBoardView) {
        super(gameEventListener);
        this.playerManager = gameState.getPlayers();
        this.playerManager.shuffleOrder();
        this.fourInARowGameBoardView = fourInARowGameBoardView;
        this.fourInARowGameBoardView.setGameBoardInteractionListener(this);
        this.fourInARowGameBoardView.loadGameState(gameState);
        aiMove = false;

        PatternMatcher.getInstance().initialize(4, fourInARowGameBoardView.getLines(), GameBoardField.FREE);
        PatternMatcher.getInstance().append(4, PatternType.BLOCKED);

        nextPlayer();
    }

    @Override
    public FourInARowGameState getGameState() {
        return new FourInARowGameState(fourInARowGameBoardView.getGameState(), playerManager);
    }

    @Override
    public FourInARowGameResult getResult() {
        FourInARowGameState gameState = getGameState();
        FourInARowAIMove ticTacToeAIMove = new FourInARowAIMove(
                Difficulty.HARD, FourInARowGameBoardFieldView.PLAYER_1, FourInARowGameBoardFieldView.PLAYER_2, 0, 0,
                gameState.convert());

        List<PatternMatchingLine> patternMatchingLines = ticTacToeAIMove.getPatternMatchingLines();

        if (checkWin(FourInARowGameBoardFieldView.PLAYER_1, patternMatchingLines))
            return new FourInARowGameResult(playerManager.getPlayer(FourInARowGameBoardFieldView.PLAYER_1));
        if (checkWin(FourInARowGameBoardFieldView.PLAYER_2, patternMatchingLines))
            return new FourInARowGameResult(playerManager.getPlayer(FourInARowGameBoardFieldView.PLAYER_2));
        if (fourInARowGameBoardView.isCompleted())
            return new FourInARowGameResult(null);
        return null;
    }

    @Override
    protected void performAIMove(AIMove aiMove) {
        FourInARowAIMove fourInARowAIMove = (FourInARowAIMove) aiMove;
        performClick(fourInARowAIMove.getX());
        fourInARowGameBoardView.setEnabled(true);
    }

    private boolean checkWin(int playerValue, List<PatternMatchingLine> patternMatchingLines) {
        return PatternMatcher.getInstance().getAmountOfMatchingPatterns(4, playerValue, patternMatchingLines) >= 1;
    }

    @Override
    public void nextPlayer() {
        Player player = playerManager.next();
        if (player.hasAI()) {
            aiMove = true;
            fourInARowGameBoardView.setEnabled(false);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> performAIMove(player.move(getGameState())), 500);

            return;
        }
        aiMove = false;
        fourInARowGameBoardView.setEnabled(true);
    }

    private void performClick(int column) {
        fourInARowGameBoardView.performClick(column, playerManager.getCurrentPlayer().getValue());
        FourInARowGameResult gameResult = getResult();
        if (gameResult != null) {
            finish(gameResult);
            return;
        }
        nextPlayer();
    }

    @Override
    public void clickColumn(int column) {
        if (!aiMove && fourInARowGameBoardView.get(new Coordinate(column, 0)).isEmpty())
            performClick(column);
    }
}
