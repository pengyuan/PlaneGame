package org.impeng.sprite;

import android.graphics.Bitmap;

public class Plane extends Sprite{
	private int hp;
	private int power;
	private boolean movable;
	
	public Plane(Bitmap bmp,int w,int h) {
		super(bmp,w,h);
	}
	
	public Plane(Bitmap bmp,int w,int h,int px,int py) {
		this(bmp,w,h);
		this.x = px;
		this.y = py;
	}
	
	public void move(int m, int n) {
		if(this.getMovable()){
			super.move(m, n);
		}
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public boolean getMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}
}




















