package markus.wieland.connect4;

import java.util.List;

import markus.wieland.games.ai.gridbased.GridGameAIMove;
import markus.wieland.games.ai.pattern.PatternMatcher;
import markus.wieland.games.ai.pattern.PatternMatchingLine;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameBoardField;

public class ConnectFourAIMove extends GridGameAIMove {

    private final int opponent;

    public ConnectFourAIMove(Difficulty difficulty, int player, int opponent, int x, int y, int[][] grid) {
        super(difficulty, player, x, y, grid);
        this.opponent = opponent;
    }

    @Override
    public boolean isLegal() {
        if (grid[y][x] != GameBoardField.FREE) return false;
        if (y == 6) return true;
        return (grid[y + 1][x] != GameBoardField.FREE);
    }

    @Override
    public void executeMove() {
        grid[y][x] = player;
    }

    @Override
    protected long easyMove() {
        return random.nextInt(1000);
    }

    @Override
    protected long mediumMove() {
        List<PatternMatchingLine> lines = getPatternMatchingLines();
        int win = PatternMatcher.getInstance().getAmountOfMatchingPatterns(4, player, lines);
        int amountTwoInARow = PatternMatcher.getInstance().getAmountOfMatchingPatterns(2, player, lines);
        int amountThreeInARow = PatternMatcher.getInstance().getAmountOfMatchingPatterns(3, player, lines);
        return win * 100000 + (long) amountTwoInARow * 100 + (long) amountThreeInARow * 500;
    }

    @Override
    public List<PatternMatchingLine> getPatternMatchingLines() {
        return super.getPatternMatchingLines();
    }

    @Override
    protected long hardMove() {
        List<PatternMatchingLine> lines = getPatternMatchingLines();
        int win = PatternMatcher.getInstance().getAmountOfMatchingPatterns(4, player, lines);
        int amountTwoInARow = PatternMatcher.getInstance().getAmountOfMatchingPatterns(2, player, lines);
        int amountThreeInARow = PatternMatcher.getInstance().getAmountOfMatchingPatterns(3, player, lines);

        int amountTwoInARowOfOpponent = PatternMatcher.getInstance().getAmountOfMatchingPatterns(2, opponent, lines);
        int amountThreeInARowOfOpponent = PatternMatcher.getInstance().getAmountOfMatchingPatterns(3, opponent, lines);

        return win * 100000 + (long) (amountTwoInARow - (2 * amountTwoInARowOfOpponent)) * 100 + (long) (amountThreeInARow - (2 * amountThreeInARowOfOpponent)) * 500;
    }
}
