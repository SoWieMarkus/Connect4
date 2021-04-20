package markus.wieland.fourinarow;

import markus.wieland.games.ai.gridbased.GridGameAI;
import markus.wieland.games.ai.moverater.HighValueMoveRater;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.persistence.GameState;

public class FourInARowAI extends GridGameAI {

    private final int opponent;

    public FourInARowAI(int player, int opponent, Difficulty difficulty) {
        super(new HighValueMoveRater(), player, difficulty);
        this.opponent = opponent;
    }

    @Override
    protected int[][] getCurrentGameState(GameState s) {
        return ((FourInARowGameState) s).convert();
    }

    @Override
    protected FourInARowAIMove buildMove(int x, int y, int[][] grid) {
        return new FourInARowAIMove(difficulty, player, opponent, x, y, grid);
    }
}
