package org.impeng.game;

import android.app.Activity;
import android.os.Bundle;

public class InitActivity extends Activity {
	public static InitActivity act;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        act = this;
    }
}
