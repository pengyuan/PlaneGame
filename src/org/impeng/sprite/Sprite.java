package org.impeng.sprite;

import org.impeng.util.CreateFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprite {
	Bitmap[] bmpArray;
	int frame;
	int x,y;
	int width,height;  //×ÊÔ´Í¼Æ¬    ÇÐ¸îÖµ (¿í)  ÇÐ¸îÖµ (¸ß)

	public Sprite(Bitmap bmp,int w,int h) {
		int col = bmp.getWidth() / w;
		int row = bmp.getHeight() / h;
		width = w;
		height = h;
		int len = row * col;
		bmpArray = new Bitmap[len];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				bmpArray[i*col+j] = CreateFactory.createBitmap(bmp, j*w, i*h, w, h);
				bmpArray[i*col+j] = Bitmap.createBitmap(bmp, j*w, i*h, w, h);
			}
		}
	}
	
	public Sprite(Bitmap bmp,int w,int h,int px,int py) {
		this(bmp,w,h);
		this.x = px;
		this.y = py;
	}
	
	public void move(int m,int n) {
		x += m;
		y += n;
	}
	
	public void draw(Canvas c,Paint p) {
		c.drawBitmap(bmpArray[frame], x, y, null);
	}
	
	public void next() {
		frame = (++frame) % bmpArray.length;
	}
	
	public boolean isCollision(Sprite p) {   //¾ØÐÎÅö×²¼ì²â
		if(p.x < this.x + this.width && p.x + p.width > this.x && p.y < this.y + this.height && p.y + p.height > this.y) {
			return true;
		}
		return false;
	}
	
	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}