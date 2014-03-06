package org.impeng.sprite;

import android.graphics.Bitmap;

public class Enemy extends Sprite {
	private int hp;
	private int time;
	private int power;
	private int moveStyle;
	private int score;

	public Enemy(Bitmap bmp, int w, int h,int px,int py) {
		super(bmp, w, h);
		this.x = px;
		this.y = py;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public int getMoveStyle() {
		return moveStyle;
	}

	public void setMoveStyle(int moveStyle) {
		this.moveStyle = moveStyle;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
