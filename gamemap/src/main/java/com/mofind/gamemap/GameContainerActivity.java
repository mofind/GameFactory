package com.mofind.gamemap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

public class GameContainerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        setContentView(new GameViewBg1(this, display.getWidth(), display.getHeight()));
    }


}
