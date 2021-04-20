package markus.wieland.connect4;

import markus.wieland.games.ai.gridbased.GridGameAI;
import markus.wieland.games.ai.moverater.HighValueMoveRater;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.persistence.GameState;

public class ConnectFourAI extends GridGameAI {

    private final int opponent;

    public ConnectFourAI(int player, int opponent, Difficulty difficulty) {
        super(new HighValueMoveRater(), player, difficulty);
        this.opponent = opponent;
    }

    @Override
    protected int[][] getCurrentGameState(GameState s) {
        return ((ConnectFourGameState) s).convert();
    }

    @Override
    protected ConnectFourAIMove buildMove(int x, int y, int[][] grid) {
        return new ConnectFourAIMove(difficulty, player, opponent, x, y, grid);
    }
}
