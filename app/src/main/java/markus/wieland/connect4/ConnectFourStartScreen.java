package markus.wieland.connect4;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import markus.wieland.games.screen.view.StartScreenView;

public class ConnectFourStartScreen extends StartScreenView {

    private boolean isSinglePlayer;

    public ConnectFourStartScreen(@NonNull Context context) {
        super(context);
    }

    public ConnectFourStartScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ConnectFourStartScreen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ConnectFourConfiguration getConfiguration() {
        return new ConnectFourConfiguration(isSinglePlayer);
    }

    @Override
    protected void onBuild() {
        setBackgroundColor(getContext().getColor(R.color.start));
        findViewById(R.id.activity_connect_4_start_screen_multiplayer).setOnClickListener(v -> setSinglePlayer(false));
        findViewById(R.id.activity_connect_4_start_single_player).setOnClickListener(v -> setSinglePlayer(true));
    }

    private void setSinglePlayer(boolean isSinglePlayer) {
        this.isSinglePlayer = isSinglePlayer;
        close();
    }
}
