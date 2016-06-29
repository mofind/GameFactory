package com.mofind.gamefactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

import com.mofind.gamefactory.main.GameConfig;

public class GameContainerActivity extends Activity {

    private GameViewBg1 mGameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        mGameView = GameConfig.buildGameView(this, display.getWidth(), display.getHeight(), getIntent().getBundleExtra("extra").getInt("index"));
        setContentView(mGameView);
    }


}
