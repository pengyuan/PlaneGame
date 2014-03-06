package org.impeng.game;
import org.impeng.util.CreateFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Splash extends SurfaceView implements Runnable ,SurfaceHolder.Callback {
	SurfaceHolder sHolder;
	Context ctx;
	boolean isRunning;
	Bitmap splash;
	Bitmap background;
	Bitmap help;
	Bitmap choose;
	Intent intent;
	int gameState;  //×´Ì¬Çý¶¯
	public static final int STARTGAME = 1;
	public static final int HELP = 2;
	public static final int OVER = 3;
	public static final int MENU = 0;
	public static boolean a = false;
	int alpha = 255;
	int cy = 210;
	
	public Splash(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		sHolder = this.getHolder();
		sHolder.addCallback(this);
		splash = CreateFactory.createBitmap(this.getContext(), R.drawable.splash);
		help = CreateFactory.createBitmap(this.getContext(), R.drawable.help);
		background = CreateFactory.createBitmap(this.getContext(), R.drawable.background);
		choose = CreateFactory.createBitmap(this.getContext(), R.drawable.choose);
		gameState = MENU;
    	intent = new Intent();
		intent.setClass(ctx, PlaneGame.class);
		this.setFocusable(true);
	}

	public void draw() {
		Canvas c = sHolder.lockCanvas();
		Paint p = new Paint();
		c.drawColor(0xffe8e8e8);
		c.drawBitmap(splash, 0, 0, p);
		switch(gameState) {
			case STARTGAME:
				c.drawColor(0xffffffff);
				c.drawBitmap(background, 0, 0, null);
				p.setColor(0x333333);
				p.setAlpha(alpha);
				c.drawRect(new Rect(0,0,320,480), p);
				alpha -= 10;
	        	if(alpha<20) {
	        		isRunning = false;
	        		ctx.startActivity(intent);
	    			InitActivity.act.finish();
	        	}
				break;
			case HELP:
				c.drawColor(0xffe8e8e8);
				c.drawBitmap(help, 0, 0, null);
				break;
			case OVER:
				InitActivity.act.finish();
				break;
			case MENU:
				drawMenu(c,p);
				break;
		}
        sHolder.unlockCanvasAndPost(c);
	}	
	
	public void drawMenu(Canvas c, Paint p) {
		p.setColor(0xff0099ff);
		p.setTextSize(16);
		p.setColor(0xff4477bd);
		p.setTextSize(14);
		c.drawBitmap(choose, 114, cy+1, p);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode) {
		case 19:
			if(cy<=210)
				break;
			else{
				cy -= 29;
				break;
			}
		case 20:
			if(cy>=267)
				break;
			else {
				cy += 29;
				break;
			}
		case 66:
		case 23:
			if(gameState == HELP){
				gameState = MENU;
			}
			else{
				switch(cy) {
					case 210:
						gameState = STARTGAME;
						break;
					case 239:
						gameState = HELP;
						break;
					case 268:
						gameState = OVER;
						break;
				}				
			}
			break;		
		}
		return true;
	}

	public void run() {
		while(isRunning) {
			draw();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

	public void surfaceCreated(SurfaceHolder holder) {
		isRunning = true;
		Thread th = new Thread(this);
		th.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		isRunning = false;
	}
}
