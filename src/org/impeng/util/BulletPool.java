package org.impeng.util;

import java.util.ArrayList;
import org.impeng.sprite.Bullet;
import android.graphics.Bitmap;

public class BulletPool {
	public Bullet[] bulletArray;
	public ArrayList<Bullet> bulletList;
	
	public BulletPool(int len,Bitmap bullet_bmp,int w,int h) {
		bulletArray = new Bullet[len];
		for(int i = 0; i < bulletArray.length; i++) {
			bulletArray[i] = new Bullet(bullet_bmp,w,h);
		}
		bulletList = new ArrayList<Bullet>();
	}
	
	public void push() {
		Bullet b = null;
		for(int i = 0; i < bulletArray.length; i++) {
			b = bulletArray[i];
			if(b.isAlive()) {
				bulletList.add(b);
				b.setState(false);
				break;
			}
		}
	}
	
	public void pop(Bullet b) {
		b.setState(true);
		bulletList.remove(b);
	}
}
