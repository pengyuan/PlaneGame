package org.impeng.sprite;

import android.graphics.Bitmap;

public class Bomb extends Sprite {

	public Bomb(Bitmap bmp, int w, int h) {
		super(bmp, w, h);
	}

	public Bomb(Bitmap bmp, int w, int h, int px, int py) {
		super(bmp, w, h, px, py);
	}
}