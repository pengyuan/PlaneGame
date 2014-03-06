package org.impeng.game;

import android.app.Activity;
import android.os.Bundle;

public class PlaneGame extends Activity {
	public static PlaneGame act;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
//        View v = this.findViewById(R.id.surfaceview);
//        v.invalidate();
        act = this;
    }
}