package ragnarok;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.Vector;

import chai_utils.Animations;
import chai_utils.CollisionDetection;
import chai_utils.Sprite;
import chai_utils.SpriteInfo;

public class Hero extends Unit {
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Constants
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	
	// hero媛� ��吏곸씪�븣(�궎�굹 留덉슦�뒪), �솕硫댁븞�뿉�꽌 �뼱�뼡 怨녠퉴吏� 媛��빞留� scroll�씠 �씪�뼱�굹�뒗吏�..
	public static final double SCROLL_BOUNDARY_NORTH_SCREEN_Y = Ragnarok.H / 4;
	public static final double SCROLL_BOUNDARY_SOUTH_SCREEN_Y = (Ragnarok.H / 4) * 3;
	public static final double SCROLL_BOUNDARY_WEST_SCREEN_X = Ragnarok.W / 4;
	public static final double SCROLL_BOUNDARY_EAST_SCREEN_X = (Ragnarok.W / 4) * 3;

//	public static final int NUM_LIVES = 1;

//	public static final String 01_FILENAME = "assets/sprites/02.png";

	public static final double START_X_ON_MAP = Ragnarok.MAP_W/2;
	public static final double START_Y_ON_MAP = Ragnarok.MAP_H/2;

	public static final double SPEED_X = 2.0;
	public static final double SPEED_Y = 3.0;
 
//	public static final double CLIMB_Y = 4.0;

//	public static final int JUMP_UP_COUNTER_MAX = 40;
//	public static final double JUMP_SPEED_Y = 8.0;
//	public static final double JUMP_SPEED_Y_MAX_WHEN_FALL = JUMP_SPEED_Y + 2.0;
//	public static final double JUMP_SPEED_Y_RATE = 1.1;

//	public static final int FEET_CENTER_WIDTH = 30;
//	public static final int FEET_CENTER_HEIGHT = 10;

//	public static final int HP = 100;
//	public static final int ATK = 10;
	
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Constants for Hero working
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
//	public static final char SHAPE = 'H';
//	public static final int ROW = (Board.ROW_SIZE/2);
//	public static final int COL = (Board.COL_SIZE/2);
	public static final int MAX_HP = 1000;
	public static final int HP = (MAX_HP-20);
	public static final int MAX_MP = 100;
	public static final int MP = MAX_MP;
	public static final int ATK = 100;
	public static final int DEF = 10;
	public static final int GOLD = 1000;
	public static final int EXP = 0;
	
	//-------------------------------------------------------------------------
	// Tarma #02: Fire
	//-------------------------------------------------------------------------
//    public static final int MAX_NUM_PISTOL_BULLETS = 30;
//
//    public static final String SWORD_01_FILENAME = "assets/sprites/sword.png";
//  
//    	public static final int BULLET_FRAMES = 9;
//    	public static final double BULLET_SPEED = 11;
//		public static final double BULLET_RATE = 0.75;
//		public static final int BULLET_Y_OFFSET = 51;
//		public static final int BULLET_X_OFFSET = 0;
//    public static final int PISTOL_FIRING_INTERVAL = 10;
//
//    	public static final double SWORD_START_X = 300;
//		public static final double SWORD_START_Y = GhoulsNGhosts.H/2;
//
//		public static final double SWORD_SPEED_X = 5.0;
//		public static final double SWORD_SPEED_Y = 3.0;
//
//	public static final double SWORD_MAX_DIST_X = Ragnarok.W / 3;
	    
	//-------------------------------------------------------------------------
    // Sprite
    //-------------------------------------------------------------------------
	public static final int NUM_ACTIONS = 3;
	
	public static final int ACTION_IDLE = 0;
	public static final int ACTION_WALKING = 1;
	public static final int PLAYER_ACTION_SHOOTING = 2;
//	public static final int PLAYER_ACTION_JUMPING = 2;
//	public static final int PLAYER_ACTION_SQUAT = 3;
//	public static final int PLAYER_ACTION_STAIRS = 4;
//	public static final int PLAYER_ACTION_KNOCKBACK = 6;
	
	public static final String[] SPRITE_ACTION_NAME = {
			"IDLE" ,
			"WALKING",
			"SHOOTING",
//			"JUMPING",
//			"SQUAT",
//			"STAIRS",
//			"KNOCKBACK"
	};
	public static final String[] SPRITE_FILENAME_PREFIX = {
			"assets/sprite/hero/fix_hero_01_front_",
			"assets/sprite/hero/whole_front_walking_hero_",
			"assets/sprite/hero/hero_attack_",			
//			"assets/sprites/morris/hero_jump_",
//			"assets/sprites/morris/hero_squat_",
//			"assets/sprites/morris/hero_stairs_",
//			"assets/sprites/morris/hero_shoot_",
//			"assets/sprites/morris/hero_knockback_"
	};
	public static final String[] SPRITE_FILENAME_POSTFIX = {
			".png",
			".png",
			".png",
//			".png",
//			".png",
//			".png",
//			".png"
	};
	public static final int[] SPRITE_NUM_SPRITES = {
			6,
			8,
			9,
//			1,
//			6,
//			1,
//			1
	};
	public static final int SPRITE_GAME_FPS = Ragnarok.GAME_FPS; 
	public static final int[] SPRITE_SPRITE_FPS = {
			10,
			10,
			10,
//			1,
//			3,
//			3,
//			3
	};
	public static final int[] SPRITE_START_IMAGE_INDEX = {
			0,
			0,
			0,
//			0,
//			0,
//			0,
//			0
	};

	public static final int[] SPRITE_OFFSET_X = {
			0,
			12,
			0,
//			0,
//			0,
//			0,
//			0
	};
	public static final int[] SPRITE_OFFSET_X_EAST = {
			0,
			40,
			0,
//			0,
//			0,
//			0,
//			0
	};
	public static final int[] SPRITE_OFFSET_Y = {
			0,
			17,
			0,
//			0,
//			0,
//			0,
//			0
	};

	public static final int MAX_NUM_DAGGERS = 10;

	//-------------------------------------------------------------------------
    // Sprite
    //-------------------------------------------------------------------------
	// standing animation용 sprite 사이즈
//	public final double IMAGE_W = widthSize;
	public static final double IMAGE_W = 49;
	public static final double IMAEG_H = 109; 
	public static final double HP_BAR_IMAGE_W = 55; 
	public static final double HP_BAR_IMAGE_H = 34; 
	public static final double HP_BAR_TOP_GAP = 0; 

	public static final double HP_BAR_BAR_IMAGE_START_X = 12; 
	public static final double HP_BAR_BAR_IMAGE_END_X = 47; 

	public static final String HP_BAR_FRAME_IMAGE_FILENAME = "assets/hp_bar/hpbar_frame.png";
	public static final String HP_BAR_BAR_IMAGE_FILENAME = "assets/hp_bar/hpbar_bar.png";
	public static final double HP_BAR_OFFSET_X = IMAGE_W / 2 - HP_BAR_IMAGE_W / 2;
	public static final double HP_BAR_OFFSET_Y = -(HP_BAR_IMAGE_H + HP_BAR_TOP_GAP); 
	
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Member Variables
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
//	public Daggar daggar;
    public Dagger [] daggerPool;
    public int maxNumDaggers;

	//-------------------------------------------------------------------------
	// Hero
	//-------------------------------------------------------------------------
//	int countJumps;
//
//	public boolean destroyed;
//    public int imageWidthForHP = calculateImageWidth();
    
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Member Functions
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------

	public Hero() {
		super();

		Animations anims = new Animations();
		for (int i = 0; i < NUM_ACTIONS; i++) {
			SpriteInfo hero01SpriteInfo =
				new SpriteInfo(this,
							   SPRITE_FILENAME_PREFIX[i],
							   SPRITE_FILENAME_POSTFIX[i],
							   SPRITE_NUM_SPRITES[i],
							   SPRITE_GAME_FPS,
							   SPRITE_SPRITE_FPS[i],
							   SPRITE_START_IMAGE_INDEX[i],
							   SPRITE_OFFSET_X[i],
							   SPRITE_OFFSET_X_EAST[i],
							   SPRITE_OFFSET_Y[i]);
			Sprite curSprite = new Sprite(hero01SpriteInfo);
			anims.add(SPRITE_ACTION_NAME[i], curSprite);
		}
		
		anims.setDefaultAction(SPRITE_ACTION_NAME[0]); // IDLE
		
		// create hp bar
		HPBar hpBar = new HPBar(this,
				HP_BAR_FRAME_IMAGE_FILENAME, HP_BAR_BAR_IMAGE_FILENAME,
				HP_BAR_OFFSET_X, HP_BAR_OFFSET_Y,
				HP_BAR_BAR_IMAGE_START_X, HP_BAR_BAR_IMAGE_END_X,
				HP, MAX_HP);
		
		super.init(anims, START_X_ON_MAP, START_Y_ON_MAP, Ragnarok.DIR_E, SPEED_X, SPEED_Y, HP, MAX_HP, hpBar);
		
		//-----------------------------------------------------------------
		// init weapons
		//-----------------------------------------------------------------
//    	daggar = new Daggar();
		maxNumDaggers = MAX_NUM_DAGGERS;
	    daggerPool = new Dagger [maxNumDaggers];
	    
	    for (int i = 0; i < maxNumDaggers; i++) {
	    		daggerPool[i] = new Dagger();
	    		daggerPool[i].destroyed = true; // 처음에는 꺼두어야 나중에 던질 때, 꺼져 있는 대거를 찾아서 던질 수 있게 된다.
	    }
	    
 		//---------------------------------------------------------------------
 		// update the position by action state
 		//---------------------------------------------------------------------
// 	    imageWidthForHP = calculateImageWidth();
// 	    hpBarOffsetX = (int) (imageWidthForHP / 2 - HP_BAR_IMAGE_W / 2);
	}
	
	public void update() {
//		System.out.println("-----------------------------------\n");
//		System.out.println("Current Action state is "+ actionState);
//		System.out.println("-----------------------------------\n");
		
		super.update();

//		if (died) {
//			return;
//		}	

        // 동작이 끝날때까지는 다른 동작을 할 수 없도록 하자.
		if (actionState == PLAYER_ACTION_SHOOTING) {
			if (anims.curSprite.curImageIndex < anims.curSprite.numSprites - 1) {
				return;
			}
			else {
				anims.curSprite.reset();
		        xOnMap += 20;
			}
		}
		
		double nextBgX = Ragnarok.bgX;
		double nextBgY = Ragnarok.bgY;
		double nextX = xOnMap + Ragnarok.bgX;
		double nextY = yOnMap + Ragnarok.bgY;
		double nextXOnMap = xOnMap;
		double nextYOnMap = yOnMap;
//		double nextMap01X = GhoulsNGhosts.bgX;
//		double nextMap01Y = GhoulsNGhosts.bgY;
		
//		double diffX = 0.0;
		
		if (!Ragnarok.wKeyPressed &&
			!Ragnarok.sKeyPressed &&
			!Ragnarok.dKeyPressed &&
			!Ragnarok.aKeyPressed) {
			if (actionState != ACTION_IDLE) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("IDLE");
			actionState = ACTION_IDLE;
		}

		// hero媛� ��吏곸씪�븣(�궎�굹 留덉슦�뒪), �솕硫댁븞�뿉�꽌 �뼱�뼡 怨녠퉴吏� 媛��빞留� scroll�씠 �씪�뼱�굹�뒗吏�..
//		public static final double SCROLL_BOUNDARY_NORTH_SCREEN_Y = Ragnarok.H / 4;
//		public static final double SCROLL_BOUNDARY_SOUTH_SCREEN_Y = Ragnarok.H / 4 * 3;
//		public static final double SCROLL_BOUNDARY_WEST_SCREEN_X = Ragnarok.W / 4;
//		public static final double SCROLL_BOUNDARY_EAST_SCREEN_X = Ragnarok.W / 4 * 3;
		
		if (Ragnarok.wKeyPressed) {
			// hero媛� ��吏곸씪�븣(�궎�굹 留덉슦�뒪), �솕硫댁븞�뿉�꽌 �뼱�뼡 怨녠퉴吏� 媛��빞留� scroll�씠 �씪�뼱�굹�뒗吏�..
			if (nextY - speedY < SCROLL_BOUNDARY_NORTH_SCREEN_Y) {
				nextBgY += speedY;
			}
			
			// �솕硫� scroll�씠 �씪�뼱�굹�뱺 留먮뱺, 臾댁“嫄� hero�쓽 �쐞移섎뒗 map�긽�뿉�꽌 諛붾�뚭쾶 �맂�떎.
			nextY -= speedY;
			nextYOnMap -= speedY;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}
		else if (Ragnarok.sKeyPressed) {
			//test
			System.out.println("SCROLL_BOUNDARY_SOUTH_SCREEN_Y: " + SCROLL_BOUNDARY_SOUTH_SCREEN_Y);
			System.out.println("nextY: " + nextY);
			System.out.println("nextBgY:" + nextBgY);
			System.out.println("speedY: " + speedY);
			
			// hero媛� ��吏곸씪�븣(�궎�굹 留덉슦�뒪), �솕硫댁븞�뿉�꽌 �뼱�뼡 怨녠퉴吏� 媛��빞留� scroll�씠 �씪�뼱�굹�뒗吏�..
			if (nextY + speedY > SCROLL_BOUNDARY_SOUTH_SCREEN_Y) {
				nextBgY -= speedY;
				
			}

			// �솕硫� scroll�씠 �씪�뼱�굹�뱺 留먮뱺, 臾댁“嫄� hero�쓽 �쐞移섎뒗 map�긽�뿉�꽌 諛붾�뚭쾶 �맂�떎.
			nextY += speedY;
			nextYOnMap += speedY;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}

		if (Ragnarok.dKeyPressed) {
			dir = Ragnarok.DIR_E;
			
			// hero媛� ��吏곸씪�븣(�궎�굹 留덉슦�뒪), �솕硫댁븞�뿉�꽌 �뼱�뼡 怨녠퉴吏� 媛��빞留� scroll�씠 �씪�뼱�굹�뒗吏�..
			if (nextX + speedX > SCROLL_BOUNDARY_EAST_SCREEN_X) {
				nextBgX -= speedX;
			}
			
			// �솕硫� scroll�씠 �씪�뼱�굹�뱺 留먮뱺, 臾댁“嫄� hero�쓽 �쐞移섎뒗 map�긽�뿉�꽌 諛붾�뚭쾶 �맂�떎.
			nextX += speedX;
			nextXOnMap += speedX;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}
		else if (Ragnarok.aKeyPressed) {
			dir = Ragnarok.DIR_W;

			// hero媛� ��吏곸씪�븣(�궎�굹 留덉슦�뒪), �솕硫댁븞�뿉�꽌 �뼱�뼡 怨녠퉴吏� 媛��빞留� scroll�씠 �씪�뼱�굹�뒗吏�..
			if (nextX - speedX < SCROLL_BOUNDARY_WEST_SCREEN_X) {
				nextBgX += speedX;
			}
			
			// �솕硫� scroll�씠 �씪�뼱�굹�뱺 留먮뱺, 臾댁“嫄� hero�쓽 �쐞移섎뒗 map�긽�뿉�꽌 諛붾�뚭쾶 �맂�떎.
			nextX -= speedX;
			nextXOnMap -= speedX;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}
		
 		if (!CollisionDetection.collideWithBackgroundMask(Ragnarok.bgMask, (int)nextBgX, (int)nextBgY, anims.curSprite.curImage, (int)nextX, (int)nextY)) {
 			Ragnarok.bgX = nextBgX;
 			Ragnarok.bgY = nextBgY;
 			
 			xOnMap = nextXOnMap;
 			yOnMap = nextYOnMap;
		}
// 		calculateImageWidth();
 		
 		//---------------------------------------------------------------------
 		// update the position by action state
 		//---------------------------------------------------------------------
// 	    imageWidthForHP = calculateImageWidth();
// 	    hpBarOffsetX = (int) (imageWidthForHP / 2 - HP_BAR_IMAGE_W / 2);
//		System.out.println("Hero::update(): owner.hpBarOffsetX = " + owner.hpBarOffsetX);
	}
	
	public Dagger getDaggerFromDaggerPool() {
	    for (int i = 0; i < maxNumDaggers; i++) {
		    	if (daggerPool[i].destroyed) { // if (daggerPool[i].destroyed == true) { <-- 절대  boolean을 비교하지 말자.
		    		return daggerPool[i];
		    	}
	    }
	    return null;
	}	
	
	public void shoot(double mouseX, double mouseY) {
        System.out.println("Hero::shoot(): mouseX = " + mouseX + " / mouseY = " + mouseY);

        hp -= 100;
        hpBar.updateHp(hp);
        
        // 동작이 끝날때까지는 다른 동작을 할 수 없도록 하자.
		if (actionState == PLAYER_ACTION_SHOOTING) {
			return;
		}
		
		Dagger dagger = getDaggerFromDaggerPool();
		// available한 dagger가 없을 경우는 그냥 쏘지 말자.
		if (dagger == null) {
			return;
		}
		
		dagger.reset();
        
        double mouseXOnMap = mouseX - Ragnarok.bgX;
        double mouseYOnMap = mouseY - Ragnarok.bgY;
        
        xOnMap -= 20;
        
		if (xOnMap > mouseXOnMap) {
			dir = Ragnarok.DIR_W;
			
			if (actionState != PLAYER_ACTION_SHOOTING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("SHOOTING");
			actionState = PLAYER_ACTION_SHOOTING;
			//Dagger dagger = new Dagger();
			dagger.xOnMap = xOnMap - 10; // 왼쪽으로 놓는다.
			dagger.yOnMap = yOnMap + 50;
			dagger.dir = dir;
			
			//for(int i = 0; i< 600; i++) {
			//	daggar.xOnMap--;
			//	Ragnarok.projectiles.add(daggar);
			//}
		}
		else  {
			dir = Ragnarok.DIR_E;
			
			if (actionState != PLAYER_ACTION_SHOOTING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("SHOOTING");
			actionState = PLAYER_ACTION_SHOOTING;
			//Dagger daggar = new Dagger();
			dagger.xOnMap = xOnMap + 75; // 오쪽으로 놓는다.
			dagger.yOnMap = yOnMap + 50;
			dagger.dir = dir;
		}
		
		Ragnarok.projectiles.add(dagger);
	}
	
	@Override
	public int calculateImageWidth() {
		if (actionState == 0) {
//    			System.out.println("Hero::getImageWidth is IDLE");
    			return 49;
		} else if (actionState == 1) {
//    			System.out.println("Hero::getImageWidth is WALKING");
    			return 48;
		} else if (actionState == 2) { //NUM_ACTIONS==2
//    			System.out.println("Hero::getImageWidth is SHOTTING");
    			return 86;
		}
		return 0;
	}
}
