package markus.wieland.fourinarow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Random;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.game.GameBoardField;
import markus.wieland.games.game.grid.GridGameBoardFieldView;
import markus.wieland.games.game.view.GameStateField;

public class FourInARowGameBoardFieldView extends androidx.appcompat.widget.AppCompatImageView implements GridGameBoardFieldView, View.OnClickListener {

    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    private Coordinate coordinate;
    private int value;

    public FourInARowGameBoardFieldView(@NonNull Context context) {
        this(context, null);
    }

    public FourInARowGameBoardFieldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FourInARowGameBoardFieldView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    private Drawable getDrawable(int value) {
        switch (value) {
            case PLAYER_1:
                return ContextCompat.getDrawable(getContext(), R.drawable.player_1);
            case PLAYER_2:
                return ContextCompat.getDrawable(getContext(), R.drawable.player_2);
            default:
                return null;
        }
    }

    public void highlight(int number) {
        setImageDrawable(getDrawable(number));
    }

    public void update() {
        setImageDrawable(getDrawable(value));
        setY(getYOffset());
        animate().translationY(0)
                .setDuration(1000)
                .setInterpolator(new BounceInterpolator())
                .start();
    }

    private float getYOffset() {
        return -1 * (getCoordinate().getY() + 1) * getHeight() + (float) getHeight();
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void load(GameStateField stateField) {
        FourInARowGameStateField fourInARowGameStateField = (FourInARowGameStateField) stateField;
        this.coordinate = fourInARowGameStateField.getCoordinate();
        this.value = fourInARowGameStateField.getValue();
    }

    @Override
    public FourInARowGameStateField getGameStateField() {
        return new FourInARowGameStateField(getCoordinate(), value);
    }

    @Override
    public void onClick(View view) {
        setValue(new Random().nextInt(3) - 1);
        update();
    }

    public boolean isEmpty() {
        return value == GameBoardField.FREE;
    }
}
