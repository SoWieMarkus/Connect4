package markus.wieland.connect4;

import markus.wieland.games.game.view.GameBoardInteractionListener;

public interface ConnectFourGameBoardInteractionListener extends GameBoardInteractionListener {

    void clickColumn(int column);

}
