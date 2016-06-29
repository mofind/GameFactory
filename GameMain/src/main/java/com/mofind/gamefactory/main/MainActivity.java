package com.mofind.gamefactory.main;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mofind.gamefactory.GameContainerActivity;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, GameConfig.getOptions()));
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putInt("index", i);
                GameConfig.launcher(MainActivity.this, GameContainerActivity.class, b);
            }
        });
    }
}
