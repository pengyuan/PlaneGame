package org.impeng.util;

import org.impeng.game.R;
import org.impeng.sprite.Enemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CreateFactory {
	private static Bitmap bmp;
	
	private CreateFactory(){}
	
	public static Bitmap createBitmap(Context ctx,int id){
		bmp = BitmapFactory.decodeResource(ctx.getResources(),id);
		if(bmp == null){
			System.out.println("创建Bitmap对象失败");
		}
		return bmp;			
	}
	
	public static Bitmap createBitmap(Bitmap bmp,int px,int py,int w,int h){
		bmp = Bitmap.createBitmap(bmp, px, py, w, h);
		if(bmp == null){
			System.out.println("创建Bitmap对象失败");
		}
		return bmp;
	}
	
	public static Enemy createEnemy(Context ctx,int kind,int px,int py) {
		Enemy n = null;
		Bitmap bmp = null;
		switch(Config.stage){
			case 1:
				switch(kind) {
					case 0:
						bmp = CreateFactory.createBitmap(ctx, R.drawable.enemy1);
						n = new Enemy(bmp,57,46,px,py);
						n.setHp(5);
						n.setScore(200);
						break;
						
					case 1:
						bmp = CreateFactory.createBitmap(ctx, R.drawable.enemy2);
						n = new Enemy(bmp,24,32,px,py);
						n.setHp(2);
						n.setScore(50);
						break;
						
					case 2:
						bmp = CreateFactory.createBitmap(ctx, R.drawable.enemy3);
						n = new Enemy(bmp,78,40,px,py);
						n.setHp(3);
						n.setScore(100);
						break;
						
					case 3:
						bmp = CreateFactory.createBitmap(ctx, R.drawable.boss);
						n = new Enemy(bmp,114,86,px,py);
						n.setHp(50);
						n.setScore(5000);
						break;	
				}
				break;
			case 2:
				switch(kind) {
				case 0:
					bmp = CreateFactory.createBitmap(ctx, R.drawable.enemy4);
					n = new Enemy(bmp,56,56,px,py);
					n.setHp(3);
					n.setScore(200);
					break;
					
				case 1:
					bmp = CreateFactory.createBitmap(ctx, R.drawable.enemy6);
					n = new Enemy(bmp,56,45,px,py);
					n.setHp(5);
					n.setScore(80);
					break;
					
				case 2:
					bmp = CreateFactory.createBitmap(ctx, R.drawable.enemy7);
					n = new Enemy(bmp,19,22,px,py);
					n.setHp(1);
					n.setScore(50);
					break;	
					
				case 3:
					bmp = CreateFactory.createBitmap(ctx, R.drawable.boss2);
					n = new Enemy(bmp,203,111,px,py);
					n.setHp(50);
					n.setScore(8000);
					break;	
				}				
				break;
		}
		return n;
	}
}
