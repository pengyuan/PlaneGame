package org.impeng.sprite;

import android.graphics.Bitmap;

public class Gift extends Sprite {
	boolean isAlive;
	int kind;
	int type;
	int speed = 8;
	
	public Gift(Bitmap bmp, int w, int h,int px,int py) {
		super(bmp, w, h);
		this.x = px;
		this.y = py;
	}

	public void moveGift() {
		switch(kind) {
			case 0:
				this.move(0, speed);
				break;
			case 1:
				this.move(-3, speed);
				break;
			case 2:
				this.move(3, speed);
				break;
			case 3:
				this.move(-6, speed);
				break;
			case 4:
				this.move(6, speed);
				break;
		}
	}
	
	public void setPosition(int m,int n) { //子弹位置随飞机位置改变，所以要随时设置
		x = m;
		y = n;
	}
	
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setState(boolean state){
		this.isAlive = state;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
