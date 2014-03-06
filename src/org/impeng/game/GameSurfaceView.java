package org.impeng.game;

import java.util.ArrayList;

import org.impeng.sprite.Bomb;
import org.impeng.sprite.Bullet;
import org.impeng.sprite.Enemy;
import org.impeng.sprite.Gift;
import org.impeng.sprite.Plane;
import org.impeng.util.BulletPool;
import org.impeng.util.Config;
import org.impeng.util.CreateFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {
	Context ctx;
	final int PLAY = 0;
	final int GAME_OVER = 1;
	final int PASS = 2;
	int game_state;
	SurfaceHolder sHolder;
	boolean isRunning;
	Plane player;
	Bitmap plane;
	Bitmap clipBmp0,clipBmp1;
	Bitmap[] bmpArray;
	Bitmap background_bmp;
	Bitmap bullet_bmp;
	Bitmap bullet_bmp2;
	Bitmap bomb_bmp;
	Bitmap bomb_bmp2;
	Bitmap game_over;
	Bitmap you_win;
	Bitmap hp_block;
	Bitmap hp_block2;
	Bitmap life_bmp;
	Bitmap gift1;
	Bitmap gift2;
	Bitmap gift3;
	BulletPool pool;
	BulletPool enemy_pool;
	ArrayList<Enemy> enemyArray = new ArrayList<Enemy>();
	ArrayList<Bomb> bombArray = new ArrayList<Bomb>();
	ArrayList<Gift> giftArray = new ArrayList<Gift>();
	int life;
	int bg_Y1,bg_Y2;
	int time;
	int timeRow;
	int bg_move;
	boolean up,down,left,right;
	Canvas c;
	Paint p;
	int player_x, player_y;
	boolean god_time;
	int index;
	int score;
	int flag;
	int flag_life = 0;
	int flag_hp = 0;
	int flag_bullet = 0;
	
	public GameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		sHolder = this.getHolder();
		sHolder.addCallback(this);
		this.setFocusable(true);
		plane = CreateFactory.createBitmap(ctx, R.drawable.plane);
		bullet_bmp = CreateFactory.createBitmap(ctx, R.drawable.bullet);
		bullet_bmp2 = CreateFactory.createBitmap(ctx, R.drawable.bullet2);
		bomb_bmp = CreateFactory.createBitmap(ctx, R.drawable.explosion);
		bomb_bmp2 = CreateFactory.createBitmap(ctx, R.drawable.bomb);
		game_over = CreateFactory.createBitmap(this.getContext(), R.drawable.game_over);
		you_win = CreateFactory.createBitmap(ctx, R.drawable.you_win);
		hp_block = CreateFactory.createBitmap(this.getContext(), R.drawable.hp);
		hp_block2 = CreateFactory.createBitmap(ctx, R.drawable.hp2);
		life_bmp = CreateFactory.createBitmap(this.getContext(), R.drawable.life);
		gift1 = CreateFactory.createBitmap(this.getContext(), R.drawable.gift1);
		gift2 = CreateFactory.createBitmap(this.getContext(), R.drawable.gift2);
		gift3 = CreateFactory.createBitmap(this.getContext(), R.drawable.gift3);
		p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(13);
		god_time = false;
		life = 3;
		this.init();
	}
	
	public void init(){
		switch(Config.stage){
			case 1:
				background_bmp = CreateFactory.createBitmap(ctx, R.drawable.background);
				break;
			case 2:
				background_bmp = CreateFactory.createBitmap(ctx, R.drawable.background2);
				break;
		}
		time = 0;
		index = 0;
		score = 0;
		bg_move = 10;
		timeRow = 0;
		bg_Y1 = -480;
		bg_Y2 = 0;
		isRunning = true;
		flag = 1;
		flag_life = 0;
		flag_hp = 0;
		flag_bullet = 0;
		player = new Plane(plane,32,32);
		player.setFrame(1);
		player.setX(144);
		player.setY(450);
		player.setPower(1);
		player.setHp(15);
		player.setMovable(true);
		enemyArray.clear();
		bombArray.clear();
		pool = new BulletPool(100,bullet_bmp,18,16);
		enemy_pool = new BulletPool(100,bullet_bmp2,12,12);
		game_state = PLAY;
	}
	
	public void run() {
		while(isRunning) {
			control();
			model();
			draw();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
	}

	public void draw() {
		c = sHolder.lockCanvas();
		c.drawColor(0xffe6e6e6);
		c.drawBitmap(background_bmp, 0, bg_Y1, null);
		c.drawBitmap(background_bmp, 0, bg_Y2, null);
		if(god_time){
			if(index%2==1){
				player.draw(c,p);	
			}
			index++;
		}
		else{
			player.draw(c,p);				
		}
		controlBullet(c,p);
		controlBullet2(c,p);
		controlEnemy(c,p);
		controlBomb(c,p);
		controlGift(c,p);
		for(int i=0; i<life;i++){
			c.drawBitmap(life_bmp, 10+25*i,10, null);
		}
		p.setColor(0x77ffffff);
		p.setTextSize(13);
		c.drawText("HP:", 100,25, p);
		int hp = player.getHp();
		if(hp>5){
			for(int i = 0;i<player.getHp();i++){
				c.drawBitmap(hp_block, 125+5*i,15, null);
			}	
		}else {
			for(int i = 0;i<player.getHp();i++){
				c.drawBitmap(hp_block2, 125+5*i,15, null);
			}	
		}
		c.drawText("SCORE: ", 220, 25, p);
		p.setColor(0xff0099ff);
		c.drawText("" + score, 270, 25, p);	
		if(game_state==GAME_OVER){
			player.setMovable(false);
			p.setColor(Color.WHITE);
			p.setTextSize(40);
			p.setAlpha(200);
			c.drawBitmap(game_over, 0, 0, null);
				c.drawText(""+score, 100, 280, p);			

		}
		if(game_state==PASS){
			player.setMovable(false);
			p.setColor(Color.WHITE);
			p.setTextSize(40);
			p.setAlpha(200);
			c.drawBitmap(you_win, 0, 0, null);
			c.drawText(""+score, 100, 280, p);
			flag++;
			if(flag==20){
				Config.stage = 2;
				init();
				game_state = PLAY;
			}
		}
		sHolder.unlockCanvasAndPost(c);		
	}
	
	public void model() {
		bg_Y1 += bg_move;
		bg_Y2 += bg_move;
		if(bg_Y1>= 480) {
			bg_Y1 = -480;
		}
		if(bg_Y2>= 480) {
			bg_Y2 = -480;
		}
		createEnemy();
		checkCollision();
		removeEnemy();
		if(player.getY()<440){
			god_time = false;
		}
		if(god_time){
			player.move(0, -2);
		}
		
		if(player.getHp()<=0){
			if(life>0){
				life--;
				if(game_state!=GAME_OVER){
					bombArray.add(new Bomb(bomb_bmp,56,39,player_x-5,player_y-10));
				}
				player.setHp(15);
				player.setX(144);
				player.setY(500);
				god_time = true;
			}else{
				game_state = GAME_OVER;
			}
		}
	}
	
	public void control(){
		player_x = player.getX();
		player_y = player.getY();
		if(player_x<-8){
			left = false;
		}else if(player_x>288){
			right = false;
		}else if(player_y<0){
			up = false;
		}else if(player_y>448){
			down = false;
		}
		if(up){
			player.move(0,-6);
		}
		if(down){
			player.move(0,6);
		}
		if(left){
			player.move(-6,0);
		}
		if(right){
			player.move(6,0);
		}
	}
	
	public void createEnemy() {
		switch(Config.stage){
			case 1:
				time++;
				if(timeRow<Config.npcTable1.length&&time == Config.npcTable1[timeRow][0]) {
					int[] temp = Config.npcTable1[timeRow];
					for(int i = 1; i < temp.length; i+=6) {
						int kind = temp[i];
						int px = temp[i+1];
						int py = temp[i+2];
						int bullet_time = temp[i+3];
						int power = temp[i+4];
						int move = temp[i+5];
						Enemy n = CreateFactory.createEnemy(ctx, kind, px, py);
						n.setTime(bullet_time);
						n.setPower(power);
						n.setMoveStyle(move);
						enemyArray.add(n);
					}
					timeRow++;
				}
				break;
			case 2:
				time++;
				if(timeRow<Config.npcTable2.length&&time == Config.npcTable2[timeRow][0]) {
					int[] temp = Config.npcTable2[timeRow];
					for(int i = 1; i < temp.length; i+=6) {
						int kind = temp[i];
						int px = temp[i+1];
						int py = temp[i+2];
						int bullet_time = temp[i+3];
						int power = temp[i+4];
						int move = temp[i+5];
						Enemy n = CreateFactory.createEnemy(ctx, kind, px, py);
						n.setTime(bullet_time);
						n.setPower(power);
						n.setMoveStyle(move);
						enemyArray.add(n);
					}
					timeRow++;
				}
				break;			
		}
	}
	
	public void checkCollision() {
		Bullet b = null;
		Enemy n = null;
		Gift g = null;
		for(int i = 0; i < pool.bulletList.size(); i++)	{ //player×Óµ¯ÓëenemyµÄÅö×²¼ì²â
			b = pool.bulletList.get(i);
			for(int j = 0; j < enemyArray.size(); j++) {
				n = enemyArray.get(j);
				if(b.isCollision(n)) {
					n.setHp(n.getHp()-1);
					pool.pop(b);
					if(n.getHp()>1){
						bombArray.add(new Bomb(bomb_bmp2,10,10,b.getX(),b.getY()));
					}
					break;
				}
			}
		}
		for(int i = 0; i < enemyArray.size(); i++){  //playerÓëenemyµÄÅö×²¼ì²â
			n = enemyArray.get(i);
			if(player.isCollision(n)) {
				if(!god_time&&game_state!=GAME_OVER){
					player.setHp(player.getHp()-n.getHp()/2);
					n.setHp(n.getHp()-1);					
				}
				removeEnemy();
				break;
			}
		}
		for(int i = 0; i < enemy_pool.bulletList.size(); i++)	{ //playerÓëenemyµÄ×Óµ¯Åö×²¼ì²â
			b = enemy_pool.bulletList.get(i);
			if(player.isCollision(b)) {
				if(!god_time){
					player.setHp(player.getHp()-2);
				}
				enemy_pool.pop(b);	
				break;
			}
		}
		for(int i = 0; i < giftArray.size(); i++)	{ //playerÓëenemyµÄ×Óµ¯Åö×²¼ì²â
			g = giftArray.get(i);
			if(player.isCollision(g)) {
				giftArray.remove(g);
				switch(g.getType()){
					case 1:
						life += (life==3)?0:1;
						break;
					case 2:
						player.setHp(15);
						break;
					case 3:
						player.setPower(3);
						break;
				}
				break;
			}
		}
	}
	
	public void removeEnemy() {
		Enemy n = null;
		for(int i = 0; i < enemyArray.size(); i++) {
			n = enemyArray.get(i);
			if(n.getHp() <= 0 || n.getY()>500) {
				bombArray.add(new Bomb(bomb_bmp,56,39,n.getX(),n.getY()));
				enemyArray.remove(n);
				if(n.getHp()<=0){
					score += n.getScore();
				}
				if(score/3000>flag_life){
					flag_life++;
					Gift gift = new Gift(gift1,11,9,n.getX(),n.getY());
					gift.setType(1);
					gift.setKind((int)Math.random()*4);
					giftArray.add(gift);
				}else if(score/1000>flag_hp){
					flag_hp++;
					Gift gift = new Gift(gift2,18,9,n.getX(),n.getY());
					gift.setType(2);
					gift.setKind((int)Math.random()*4);
					giftArray.add(gift);
				}
				else if(score/500>flag_bullet){
					flag_bullet++;
					Gift gift = new Gift(gift3,18,9,n.getX(),n.getY());
					gift.setType(3);
					gift.setKind((int)Math.random()*4);
					giftArray.add(gift);
				}
				if(n.getMoveStyle() == 0&&enemyArray.isEmpty()==true){
					game_state = PASS;
				}
			}				
		}
	}
	
	public void controlBomb(Canvas c, Paint p) {
		Bomb n = null;
		for(int i = 0; i < bombArray.size(); i++) {
			n = bombArray.get(i);
			n.draw(c, p);
			n.next();
			if(n.getFrame() == 0) {
				bombArray.remove(n);
			}
		}
	}

	public void greatBomb(){ //´óÕÐ
		for(int i=0; i<enemyArray.size(); i++){
			Enemy enemy = enemyArray.get(i);
			bombArray.add(new Bomb(bomb_bmp,56,39,enemy.getX(),enemy.getY()));
			enemy.setHp(enemy.getHp()-10);
		}
		Canvas c = sHolder.lockCanvas();
		c.drawARGB(50, 255, 255, 255);
		sHolder.unlockCanvasAndPost(c);
	}
	
	int row = 0;
	int state = 0;
	
	public void controlEnemy(Canvas c,Paint p) {
		Enemy n = null;
		for(int i = 0; i < enemyArray.size(); i++) {
			n = enemyArray.get(i);
			n.draw(c, p);
			n.next();
			int x = n.getMoveStyle();
			if(x == 0){
				bg_move = 1;
				if(state == 0){
					n.move(6, 0);
					if(n.getX()>200){
						for(int j = 0; j < n.getPower(); j++) {
							enemy_pool.push();
							Bullet bullet = enemy_pool.bulletList.get(enemy_pool.bulletList.size()-1);
							bullet.setKind(j);
							bullet.setPosition(n.getX()+10, n.getY()+5);
						}
						state = (int)(Math.random()*4);
					}
				}
				if(state == 1){
					n.move(0, 6);
					if(n.getY()>200){
						for(int j = 0; j < n.getPower(); j++) {
							enemy_pool.push();
							Bullet bullet = enemy_pool.bulletList.get(enemy_pool.bulletList.size()-1);
							bullet.setKind(j);
							bullet.setPosition(n.getX()+10, n.getY()+5);
						}
						state = (int)(Math.random()*4);
					}
				}
				if(state == 2){
					n.move(-6, 0);
					if(n.getX()<10){
						for(int j = 0; j < n.getPower(); j++) {
							enemy_pool.push();
							Bullet bullet = enemy_pool.bulletList.get(enemy_pool.bulletList.size()-1);
							bullet.setKind(j);
							bullet.setPosition(n.getX()+10, n.getY()+5);
						}
						state = (int)(Math.random()*4);
					}
				}
				if(state == 3){
					n.move(0, -6);
					if(n.getY()<30){
						for(int j = 0; j < n.getPower(); j++) {
							enemy_pool.push();
							Bullet bullet = enemy_pool.bulletList.get(enemy_pool.bulletList.size()-1);
							bullet.setKind(j);
							bullet.setPosition(n.getX()+10, n.getY()+5);
						}
						state = (int)(Math.random()*4);
					}
				}
			}else{
				n.move(x,6);				
			}
		}
	}
	
	public void controlBullet(Canvas c,Paint p){
		Bullet b = null;
		for(int i = 0; i < pool.bulletList.size(); i++){
			b = pool.bulletList.get(i);
			b.draw(c, p);
			b.moveBullet();
			if(b.getY() < -20){
				pool.pop(b);
			}
		}
	}
	
	public void controlGift(Canvas c,Paint p){
		Gift g = null;
		for(int i = 0; i < giftArray.size(); i++){
			g = giftArray.get(i);
			g.draw(c, p);
			g.moveGift();
			g.next();
			if(g.getY() > 500){
				giftArray.remove(g);
			}
		}		
	}
	
	public void controlBullet2(Canvas c,Paint p){
		Bullet b = null;
		for(int i = 0; i < enemy_pool.bulletList.size(); i++){
			b = enemy_pool.bulletList.get(i);
			b.draw(c, p);
			b.enemyBullet();
			if(b.getY() > 500){
				enemy_pool.pop(b);
			}
		}
		for(Enemy enemy:enemyArray){
			if(time == enemy.getTime()){
				for(int j = 0; j < enemy.getPower(); j++) {
					enemy_pool.push();
					Bullet bullet = enemy_pool.bulletList.get(enemy_pool.bulletList.size()-1);
					bullet.setKind(j);
					bullet.setPosition(enemy.getX()+10, enemy.getY()+5);
				}
			}
		}
	}	
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode) {
			case 19:
				up = false;
				break;
			case 20:
				down = false;
				break;
			case 21:
				left = false;
				break;
			case 22:
				right = false;
				break;
		}
		player.setFrame(1);
		return super.onKeyUp(keyCode, event);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode) {
			case 19:
				up = true;
				break;
			case 20:
				down = true;
				break;
			case 21:
				left = true;
				player.setFrame(2);
				break;
			case 22:
				right = true;
				player.setFrame(0);
				break;
			case 62:	
			case 23:
				for(int i = 0; i < player.getPower(); i++) {
					pool.push();
					Bullet b = pool.bulletList.get(pool.bulletList.size()-1);
					b.setKind(i);
					b.setPosition(player.getX()+10, player.getY()-5);
				}
				break;
			case 35:
				greatBomb();
				break;
			case 66: //Enter¼ü
				if(game_state == GAME_OVER){
					life = 4;
					this.init();
				}
				break;
		}			
		return super.onKeyDown(keyCode, event);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		isRunning = true;
		Thread th = new Thread(this);
		th.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		isRunning = false;
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
}
