package com.mofind.gamefactory;

import android.app.Activity;
import android.os.Bundle;

public class GameContainerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }


}
