package markus.wieland.fourinarow;

import markus.wieland.games.game.view.GameBoardInteractionListener;

public interface FourInARowGameBoardInteractionListener extends GameBoardInteractionListener {

    void clickColumn(int column);

}
