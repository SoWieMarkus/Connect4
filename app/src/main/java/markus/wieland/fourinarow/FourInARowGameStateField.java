package markus.wieland.fourinarow;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.game.GameBoardField;
import markus.wieland.games.game.grid.GridGameStateField;

public class FourInARowGameStateField extends GridGameStateField {

    private final int value;

    public FourInARowGameStateField(Coordinate coordinate, int value) {
        super(coordinate);
        this.value = value;
    }

    public FourInARowGameStateField(Coordinate coordinate) {
        super(coordinate);
        this.value = GameBoardField.FREE;
    }

    public int getValue() {
        return value;
    }


}
