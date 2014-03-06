package org.impeng.sprite;

import android.graphics.Bitmap;

public class Bullet extends Sprite {
	boolean isAlive;
	int kind;
	int speed = 14;
	
	public Bullet(Bitmap bmp, int w, int h) {
		super(bmp, w, h);
		isAlive = true;
	} 
	
	public void moveBullet() {
		switch(kind) {
			case 0:
				this.move(0, -speed);
				break;
			case 1:
				this.move(-3, -speed);
				break;
			case 2:
				this.move(3, -speed);
				break;
			case 3:
				this.move(-6, -speed);
				break;
			case 4:
				this.move(6, -speed);
				break;
		}
	}
	
	public void enemyBullet() {
		switch(kind) {
			case 0:
				this.move(0, speed);
				break;
			case 1:
				this.move(-2, speed);
				break;
			case 2:
				this.move(-4, speed);
				break;
			case 3:
				this.move(-6, speed);
				break;
			case 4:
				this.move(-8, speed);
				break;
			case 5:
				this.move(2, speed);
				break;
			case 6:
				this.move(4, speed);
				break;
			case 7:
				this.move(5, speed);
				break;
			case 8:
				this.move(8, speed);
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
}