package markus.wieland.connect4;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.game.GameBoardField;
import markus.wieland.games.game.grid.GridGameStateField;

public class ConnectFourGameStateField extends GridGameStateField {

    private final int value;

    public ConnectFourGameStateField(Coordinate coordinate, int value) {
        super(coordinate);
        this.value = value;
    }

    public ConnectFourGameStateField(Coordinate coordinate) {
        super(coordinate);
        this.value = GameBoardField.FREE;
    }

    public int getValue() {
        return value;
    }


}
