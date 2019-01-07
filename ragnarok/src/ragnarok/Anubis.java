package ragnarok;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import chai_utils.Animations;
import chai_utils.CollisionDetection;
import chai_utils.Sprite;
import chai_utils.SpriteInfo;

public class Anubis extends Monster {
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Constants
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	static Random r = new Random();
	
	public static final double SCROLL_BOUNDARY_NORTH_SCREEN_Y = Ragnarok.H / 4;
	public static final double SCROLL_BOUNDARY_SOUTH_SCREEN_Y = (Ragnarok.H / 4) * 3;
	public static final double SCROLL_BOUNDARY_WEST_SCREEN_X = Ragnarok.W / 4;
	public static final double SCROLL_BOUNDARY_EAST_SCREEN_X = (Ragnarok.W / 4) * 3;

	public static final double START_X_ON_MAP = Ragnarok.MAP_W/2.5;
	public static final double START_Y_ON_MAP = Ragnarok.MAP_H/2.5;

	public static final double SPEED_X = 2.0;
	public static final double SPEED_Y = 3.0;
	    
	public static final int MAX_HP = 1000;
	public static final int HP = MAX_HP;
	public static final int MAX_MP = 0;
	public static final int MP = MAX_MP;
	public static final int ATK = 5;
	public static final int DEF = 5;
	public static final int GOLD = 100;
	public static final int EXP = 1000;
	
	//-------------------------------------------------------------------------
    // Sprite
    //-------------------------------------------------------------------------
	public static final int NUM_ACTIONS = 4;
	
	public static final int ACTION_IDLE = 0;
	public static final int ACTION_WALKING = 1;
	public static final int ACTION_ATTACKING = 2;
	public static final int ACTION_DYING = 3;

	public static final String[] SPRITE_ACTION_NAME = {
			"IDLE" ,
			"WALKING",
			"ATTACKING",
			"DYING",
	};
	public static final String[] SPRITE_FILENAME_PREFIX = {
			"assets/sprite/monster/anubis/standing/anubis_standing_front_",
			"assets/sprite/monster/anubis/walking/anubis_standing_front_",
			"assets/sprite/monster/anubis/attacking/anubis_attacking_front_",
			"assets/sprite/monster/anubis/dying/anubis_dying_front_",
	};
	public static final String[] SPRITE_FILENAME_POSTFIX = {
			".png",
			".png",
			".png",
			".png",
	};
	public static final int[] SPRITE_NUM_SPRITES = {
			7,
			11,
			5,
			8,
	};
	public static final int SPRITE_GAME_FPS = Ragnarok.GAME_FPS; 
	public static final int[] SPRITE_SPRITE_FPS = {
			10,
			10,
			10,
			10,
	};
	public static final int[] SPRITE_START_IMAGE_INDEX = {
			0,
			0,
			0,
			0,
	};

	public static final int[] SPRITE_OFFSET_X = {
			0,
			12,
			0,
			0,
	};
	public static final int[] SPRITE_OFFSET_X_EAST = {
			0,
			0,
			0,
			0,
	};
	public static final int[] SPRITE_OFFSET_Y = {
			0,
			0,
			0,
			0,
	};

	//-------------------------------------------------------------------------
	// Game Mechanic
	//-------------------------------------------------------------------------
	public static final double DIST_TO_FOLLOW = 150.0;
	public static final double DIST_TO_ATTACK = 90.0;

	//-------------------------------------------------------------------------
    // Sprite
    //-------------------------------------------------------------------------
	// standing animation용 sprite 사이즈
	public static final double IMAGE_W = 114; 
	public static final double IMAEG_H = 167; 
	public static final double HP_BAR_IMAGE_W = 55; 
	public static final double HP_BAR_IMAGE_H = 34; 
	public static final double HP_BAR_TOP_GAP = 0; 

	public static final double HP_BAR_BAR_IMAGE_START_X = 12; 
	public static final double HP_BAR_BAR_IMAGE_END_X = 47; 

	public static final String HP_BAR_FRAME_IMAGE_FILENAME = "assets/hp_bar/hpbar_frame.png";
	public static final String HP_BAR_BAR_IMAGE_FILENAME = "assets/hp_bar/hpbar_bar.png";
	public static final double HP_BAR_OFFSET_X = IMAGE_W / 2 - IMAEG_H / 2; 
	public static final double HP_BAR_OFFSET_Y = -(HP_BAR_IMAGE_H + HP_BAR_TOP_GAP); 
	
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Member Variables
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Member Functions
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------

	public Anubis() {
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
    		
    		// standing animation용 sprite 사이즈
//    		public static final double IMAGE_W = 114; 
//    		public static final double IMAEG_H = 167; 
//    		public static final double HP_BAR_IMAGE_W = 55; 
//    		public static final double HP_BAR_IMAGE_H = 34; 
//    		public static final double HP_BAR_TOP_GAP = 10; 
//
//    		public static final double HP_BAR_BAR_IMAGE_START_X = 12; 
//    		public static final double HP_BAR_BAR_IMAGE_END_X = 47; 
//
//    		public static final String HP_BAR_FRAME_IMAGE_FILENAME = "assets/hp_bar/hpbar_frame.png";
//    		public static final String HP_BAR_BAR_IMAGE_FILENAME = "assets/hp_bar/hpbar_frame.png";
//    		public static final double HP_BAR_OFFSET_X = IMAGE_W / 2 - HP_BAR_IMAGE_W / 2; 
//    		public static final double HP_BAR_OFFSET_Y = HP_BAR_IMAGE_H + HP_BAR_TOP_GAP; 
//    		public HPBar(GameObject owner,
//    				String frameImageFilename, String barImageFilename,
//    				double offsetX, double offsetY,
//    				double startXOfBarImage, double endXOfBarImage,
//    				double hp, double maxHp) {
    		// create hp bar
    		HPBar hpBar = new HPBar(this,
    				HP_BAR_FRAME_IMAGE_FILENAME, HP_BAR_BAR_IMAGE_FILENAME,
    				HP_BAR_OFFSET_X, HP_BAR_OFFSET_Y,
    				HP_BAR_BAR_IMAGE_START_X, HP_BAR_BAR_IMAGE_END_X,
    				HP, MAX_HP);
    		
    		super.init(anims, START_X_ON_MAP, START_Y_ON_MAP, Ragnarok.DIR_E, SPEED_X, SPEED_Y, HP, MAX_HP, hpBar);
	}
	
    @Override
	public void update() {
		super.update();
		
//		hp = 1;
//        hpBar.updateHp(1);
        
		if (died) {
			if (actionState != ACTION_DYING) {
				anims.curSprite.reset();
				anims.setCurrentAction("DYING");
				actionState = ACTION_DYING;	
			}
			else {
				if (anims.curSprite.curImageIndex < anims.curSprite.numSprites - 1) {
					return;
				}
				else {
					visible = false;
				}
			}
			return;
		}	

		// Hero���뒗 �떎瑜닿쾶 bgX�� bgY瑜� 諛붽씀吏��뒗 �븡�뒗�떎.
//		double nextBgX = Ragnarok.bgX;
//		double nextBgY = Ragnarok.bgY;
		double nextX = xOnMap + Ragnarok.bgX;
		double nextY = yOnMap + Ragnarok.bgY;
		double nextXOnMap = xOnMap;
		double nextYOnMap = yOnMap;
//		double nextMap01X = GhoulsNGhosts.bgX;
//		double nextMap01Y = GhoulsNGhosts.bgY;
		
//		double diffX = 0.0;
		
		// Enemy�뿉�꽌�뒗 keyboard濡� 諛⑺뼢�쓣 Access�븯�뒗 寃껋씠 �븘�땲�떎.
		// Anubis留뚯쓽 ��吏곸엫�쓣 �뵒�옄�씤�빐蹂댁옄.
		// ��吏곸엫 �븿嫄곕━�벉 #1: 諛�
		//     hero媛� �엳�뒗 履쎌쑝濡� 諛⑺뼢�쓣 �듉�떎.
		//     Issue: feetOffsetX瑜� �븘吏� �벐吏� �븡怨�, bitmap�쓽 top-left�쓽 x瑜� �벐湲� �븣臾몄뿉, �븘吏� 吏��굹媛�吏��룄 �븡�븯�뒗�뜲..
		//            諛⑺뼢�쓣 �듉�떎. �듅�엳 hero01�씠 �쇊履쎌뿉�꽌 �삤瑜몄そ�쑝濡� 媛� �븣, 諛섎��뒗 吏��굹媛붾뒗�뜲�룄 �븘吏� 諛⑺뼢�쓣 諛붽씀吏� �븡�뒗 �쁽�긽.
		if (xOnMap > Ragnarok.hero01.xOnMap) {
			dir = Ragnarok.DIR_W;
		}
		else  {
			dir = Ragnarok.DIR_E;
		}
		
		// �긽�븯濡� hero媛� �듅�젙 嫄곕━ �씠�긽 �뼥�뼱吏�硫� �뵲�씪媛��룄濡�..
		double distY = Math.abs(yOnMap - Ragnarok.hero01.yOnMap);
		if (yOnMap > Ragnarok.hero01.yOnMap && distY > DIST_TO_FOLLOW) { // to north
			nextY -= speedY;
			nextYOnMap -= speedY;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}
		else if (yOnMap < Ragnarok.hero01.yOnMap && distY > DIST_TO_FOLLOW) {
			nextY += speedY;
			nextYOnMap += speedY;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}
		// 醫뚯슦濡� hero媛� �듅�젙 嫄곕━ �씠�긽 �뼥�뼱吏�硫� �뵲�씪媛��룄濡�..
		double distX = Math.abs(xOnMap - Ragnarok.hero01.xOnMap);
		if (xOnMap > Ragnarok.hero01.xOnMap && distX > DIST_TO_FOLLOW) {
			dir = Ragnarok.DIR_W;
			
			nextX -= speedX;
			nextXOnMap -= speedX;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;	
		}
		else if (xOnMap < Ragnarok.hero01.xOnMap && distX > DIST_TO_FOLLOW) {
			dir = Ragnarok.DIR_E;
			
			nextX += speedX;
			nextXOnMap += speedX;
			
			if (actionState != ACTION_WALKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("WALKING");
			actionState = ACTION_WALKING;
		}
		
		if(distX < DIST_TO_ATTACK && distY < DIST_TO_ATTACK) {
			if (actionState != ACTION_ATTACKING) {
				anims.curSprite.reset();
			}
			anims.setCurrentAction("ATTACKING");
			actionState = ACTION_ATTACKING;
		}

 		if (!CollisionDetection.collideWithBackgroundMask(Ragnarok.bgMask, (int)Ragnarok.bgX, (int)Ragnarok.bgY, anims.curSprite.curImage, (int)nextX, (int)nextY)) {
			xOnMap = nextXOnMap;
			yOnMap = nextYOnMap;
 		}
		
//		if (!Ragnarok.wKeyPressed &&
//			!Ragnarok.sKeyPressed &&
//			!Ragnarok.dKeyPressed &&
//			!Ragnarok.aKeyPressed) {
//			if (actionState != ACTION_IDLE) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("IDLE");
//			actionState = ACTION_IDLE;
//		}
//
//		// hero占�? ??吏곸씪?占쏙옙(?占쏙옙?占쏙옙 留덉슦?占쏙옙), ?占쏙옙硫댁븞?占쏙옙?占쏙옙 ?占쏙옙?占쏙옙 怨녠퉴占�? 占�??占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙占�?..
////		public static final double SCROLL_BOUNDARY_NORTH_SCREEN_Y = Ragnarok.H / 4;
////		public static final double SCROLL_BOUNDARY_SOUTH_SCREEN_Y = Ragnarok.H / 4 * 3;
////		public static final double SCROLL_BOUNDARY_WEST_SCREEN_X = Ragnarok.W / 4;
////		public static final double SCROLL_BOUNDARY_EAST_SCREEN_X = Ragnarok.W / 4 * 3;
//		
//		if (Ragnarok.wKeyPressed) {
//			// hero占�? ??吏곸씪?占쏙옙(?占쏙옙?占쏙옙 留덉슦?占쏙옙), ?占쏙옙硫댁븞?占쏙옙?占쏙옙 ?占쏙옙?占쏙옙 怨녠퉴占�? 占�??占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙占�?..
//			if (nextY - speedY < SCROLL_BOUNDARY_NORTH_SCREEN_Y) {
//				nextBgY += speedY;
//			}
//			
//			// ?占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙 留먮뱺, 臾댁“占�? hero?占쏙옙 ?占쏙옙移섎뒗 map?占쏙옙?占쏙옙?占쏙옙 諛뷂옙?占쎄쾶 ?占쏙옙?占쏙옙.
//			nextY -= speedY;
//			nextYOnMap -= speedY;
//			
//			if (actionState != ACTION_WALKING) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("WALKING");
//			actionState = ACTION_WALKING;
//		}
//		else if (Ragnarok.sKeyPressed) {
//			//test
//			System.out.println("SCROLL_BOUNDARY_SOUTH_SCREEN_Y: " + SCROLL_BOUNDARY_SOUTH_SCREEN_Y);
//			System.out.println("nextY: " + nextY);
//			System.out.println("nextBgY:" + nextBgY);
//			System.out.println("speedY: " + speedY);
//			
//			// hero占�? ??吏곸씪?占쏙옙(?占쏙옙?占쏙옙 留덉슦?占쏙옙), ?占쏙옙硫댁븞?占쏙옙?占쏙옙 ?占쏙옙?占쏙옙 怨녠퉴占�? 占�??占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙占�?..
//			if (nextY + speedY > SCROLL_BOUNDARY_SOUTH_SCREEN_Y) {
//				nextBgY -= speedY;
//				
//			}
//
//			// ?占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙 留먮뱺, 臾댁“占�? hero?占쏙옙 ?占쏙옙移섎뒗 map?占쏙옙?占쏙옙?占쏙옙 諛뷂옙?占쎄쾶 ?占쏙옙?占쏙옙.
//			nextY += speedY;
//			nextYOnMap += speedY;
//			
//			if (actionState != ACTION_WALKING) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("WALKING");
//			actionState = ACTION_WALKING;
//		}
//
//		if (Ragnarok.dKeyPressed) {
//			dir = DIR_E;
//			
//			// hero占�? ??吏곸씪?占쏙옙(?占쏙옙?占쏙옙 留덉슦?占쏙옙), ?占쏙옙硫댁븞?占쏙옙?占쏙옙 ?占쏙옙?占쏙옙 怨녠퉴占�? 占�??占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙占�?..
//			if (nextX + speedX > SCROLL_BOUNDARY_EAST_SCREEN_X) {
//				nextBgX -= speedX;
//			}
//			
//			// ?占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙 留먮뱺, 臾댁“占�? hero?占쏙옙 ?占쏙옙移섎뒗 map?占쏙옙?占쏙옙?占쏙옙 諛뷂옙?占쎄쾶 ?占쏙옙?占쏙옙.
//			nextX += speedX;
//			nextXOnMap += speedX;
//			
//			if (actionState != ACTION_WALKING) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("WALKING");
//			actionState = ACTION_WALKING;
//		}
//		else if (Ragnarok.aKeyPressed) {
//			dir = DIR_W;
//
//			// hero占�? ??吏곸씪?占쏙옙(?占쏙옙?占쏙옙 留덉슦?占쏙옙), ?占쏙옙硫댁븞?占쏙옙?占쏙옙 ?占쏙옙?占쏙옙 怨녠퉴占�? 占�??占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙占�?..
//			if (nextX - speedX < SCROLL_BOUNDARY_WEST_SCREEN_X) {
//				nextBgX += speedX;
//			}
//			
//			// ?占쏙옙占�? scroll?占쏙옙 ?占쏙옙?占쏙옙?占쏙옙?占쏙옙 留먮뱺, 臾댁“占�? hero?占쏙옙 ?占쏙옙移섎뒗 map?占쏙옙?占쏙옙?占쏙옙 諛뷂옙?占쎄쾶 ?占쏙옙?占쏙옙.
//			nextX -= speedX;
//			nextXOnMap -= speedX;
//			
//			if (actionState != ACTION_WALKING) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("WALKING");
//			actionState = ACTION_WALKING;
//		}
//		
// 		if (!CollisionDetection.collideWithBackgroundMask(Ragnarok.bgMask, (int)nextBgX, (int)nextBgY, anims.curSprite.curImage, (int)nextX, (int)nextY)) {
// 			Ragnarok.bgX = nextBgX;
// 			Ragnarok.bgY = nextBgY;
// 			
// 			xOnMap = nextXOnMap;
// 			yOnMap = nextYOnMap;
//		}
 
//      System.out.println("Anubis.update()::HP = " + Anubis.HP);
	}
    
//    public void dying() {
//		if (died) {
//			if (actionState != ACTION_DYING) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("DYING");
//			actionState = ACTION_DYING;	
//		}	
//    }
    
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
