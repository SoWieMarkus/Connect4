package markus.wieland.connect4;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.elements.Line;
import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.GameResult;
import markus.wieland.games.game.grid.GridGameBoardView;
import markus.wieland.games.persistence.GameState;

public class ConnectFourGameBoardView extends GridGameBoardView<ConnectFourGameBoardFieldView> {

    public static final int SIZE_X = 7;
    public static final int SIZE_Y = 6;

    private ProgressBar progressBarPlayer1;
    private ProgressBar progressBarPlayer2;

    public ConnectFourGameBoardView(@NonNull Context context) {
        super(context);
    }

    public ConnectFourGameBoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ConnectFourGameBoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSizeX() {
        return SIZE_X;
    }

    @Override
    protected int getSizeY() {
        return SIZE_Y;
    }

    private ConnectFourGameBoardInteractionListener getGameBoardInteractionListener() {
        return (ConnectFourGameBoardInteractionListener) gameBoardInteractionListener;
    }

    @Override
    protected void initializeLines() {
        //vertical
        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < 4; x++) {
                Line line = new Line();
                addCoordinateToLine(x, y, line);
                addCoordinateToLine(x + 1, y, line);
                addCoordinateToLine(x + 2, y, line);
                addCoordinateToLine(x + 3, y, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x + 1, y + 1, line);
                addCoordinateToLine(x + 2, y + 1, line);
                addCoordinateToLine(x + 3, y + 1, line);
                lines.add(line);
            }
        }

        //horizontal
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                Line line = new Line();
                addCoordinateToLine(x, y, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x, y + 2, line);
                addCoordinateToLine(x, y + 3, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x, y + 1, line);
                lines.add(line);
            }
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 4; x++) {
                Line line = new Line();
                addCoordinateToLine(x, y, line);
                addCoordinateToLine(x + 1, y + 1, line);
                addCoordinateToLine(x + 2, y + 2, line);
                addCoordinateToLine(x + 3, y + 3, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x + 1, y + 2, line);
                addCoordinateToLine(x + 2, y + 3, line);
                addCoordinateToLine(x + 3, y + 4, line);
                lines.add(line);
            }
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 3; x < SIZE_X; x++) {
                Line line = new Line();
                addCoordinateToLine(x, y, line);
                addCoordinateToLine(x - 1, y + 1, line);
                addCoordinateToLine(x - 2, y + 2, line);
                addCoordinateToLine(x - 3, y + 3, line);
                addCoordinateToLine(x, y + 1, line);
                addCoordinateToLine(x - 1, y + 2, line);
                addCoordinateToLine(x - 2, y + 3, line);
                addCoordinateToLine(x - 3, y + 4, line);
                lines.add(line);
            }
        }
    }

    public void showLines() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for (Line line : lines) {
                        int number = 0;
                        for (int i = 0; i < 8; i++) {
                            if (i >= 4) number = 1;
                            Coordinate coordinate = line.getCoordinate(i);
                            if (coordinate.getY() == SIZE_Y) {
                                continue;
                            }
                            int finalNumber = number;
                            ((AppCompatActivity) getContext()).runOnUiThread(() -> matrix.get(coordinate).highlight(finalNumber));

                        }


                        sleep(1000);

                        ((AppCompatActivity) getContext()).runOnUiThread(() -> clear());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void clear() {
        for (ConnectFourGameBoardFieldView view : matrix) {
            view.highlight(-1);
        }
    }

    public void performClick(int column, int player) {
        int y = SIZE_Y - 1;
        while (!get(new Coordinate(column, y)).isEmpty()) {
            y--;
        }
        ConnectFourGameBoardFieldView field = get(new Coordinate(column, y));
        field.setValue(player);
        field.update();
    }

    private void addCoordinateToLine(int x, int y, Line line) {
        if (y < 0) {
            y = getSizeY();
        }
        line.add(new Coordinate(x, y));
    }

    public ConnectFourGameBoardFieldView get(Coordinate coordinate) {
        return matrix.get(coordinate);
    }

    public void switchPlayer(int currentPlayer) {
        progressBarPlayer1.setVisibility(currentPlayer == ConnectFourGameBoardFieldView.PLAYER_1 ? VISIBLE : GONE);
        progressBarPlayer2.setVisibility(currentPlayer == ConnectFourGameBoardFieldView.PLAYER_2 ? VISIBLE : GONE);
    }

    @Override
    protected void initializeFields() {

        progressBarPlayer1 = findViewById(R.id.layout_game_player_1_progress_bar);
        progressBarPlayer2 = findViewById(R.id.layout_game_player_2_progress_bar);


        for (int x = 0; x < SIZE_X; x++) {
            int finalX = x;
            findViewById(getResources()
                    .getIdentifier("fiar_row_" + x, "id", getContext()
                            .getPackageName()))
                    .setOnClickListener(v -> getGameBoardInteractionListener().clickColumn(finalX));
        }
    }

    @Override
    protected GameResult checkGameForFinish() {
        return null;
    }

    public boolean isCompleted() {
        for (ConnectFourGameBoardFieldView view : matrix) {
            if (view.isEmpty()) return false;
        }
        return true;
    }

    public SerializableMatrix<ConnectFourGameStateField> getGameState() {
        SerializableMatrix<ConnectFourGameStateField> fields = new SerializableMatrix<>(SIZE_X, SIZE_Y);
        for (ConnectFourGameBoardFieldView view : matrix) {
            fields.set(view.getCoordinate(), view.getGameStateField());
        }
        return fields;
    }

    @Override
    protected void loadGameState(GameState gameState) {

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                matrix.set(x, y, findViewById(getResources().getIdentifier("fiar_" + y + "" + x, "id", getContext().getPackageName())));
            }
        }

        ConnectFourGameState connectFourGameState = (ConnectFourGameState) gameState;
        for (ConnectFourGameStateField field : connectFourGameState) {

            matrix.get(field.getCoordinate()).load(field);
        }

    }


}
