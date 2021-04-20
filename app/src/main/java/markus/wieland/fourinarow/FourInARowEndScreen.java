package markus.wieland.fourinarow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import markus.wieland.games.player.Player;
import markus.wieland.games.screen.view.EndScreenView;

public class FourInARowEndScreen extends EndScreenView {

    private TextView tvGameResultMessage;

    public FourInARowEndScreen(@NonNull Context context) {
        super(context);
    }

    public FourInARowEndScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FourInARowEndScreen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onNewGameResult() {
        if (gameResult == null) return;
        if (!(gameResult instanceof FourInARowGameResult)) throw new IllegalArgumentException();
        FourInARowGameResult ticTacToeGameResult = (FourInARowGameResult) gameResult;

        Player winner = ticTacToeGameResult.getWinner();
        if (winner == null) {
            tvGameResultMessage.setText(R.string.fiar_draw);
            setBackgroundColor(getContext().getColor(R.color.start));
            return;
        }
        String winnerMessage = winner.getPlayerName() + " " + getContext().getString(R.string.fiar_wins);
        tvGameResultMessage.setText(winnerMessage);

        if (winner.hasAI()) {
            setBackgroundColor(getContext().getColor(R.color.lose));
            return;
        }

        setBackgroundColor(getContext().getColor(R.color.win));
    }

    @Override
    protected void onBuild() {
        findViewById(R.id.activity_fiar_end_screen_back).setOnClickListener(v -> close(false));
        findViewById(R.id.activity_fiar_end_screen_again).setOnClickListener(v -> close(true));
        tvGameResultMessage = findViewById(R.id.activity_fiar_end_screen_text);
    }
}
