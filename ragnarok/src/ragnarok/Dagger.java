package ragnarok;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import chai_utils.BoxCollider;
import chai_utils.CircleCollider;
import chai_utils.Collider;
import chai_utils.CollisionDetection;
import chai_utils.Sprite;
import chai_utils.SpriteInfo;

public class Dagger extends Weapon {
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	// Constants
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	public static final int DEFAULT_DAGGER_INDEX = 0;
	
	public static final String [] DAGGER_IMAGE_FILENAMES = {
		"assets/sprite/weapon/daggar1.png",
		"assets/sprite/weapon/daggar2.png"
	};

	public static final double [] START_X_ON_MAP = {
		-1,
		-1
	};
	
	public static final double [] START_Y_ON_MAP = {
		-1,
		-1
	};

	public static final double [] SPEED_X = {
		5.0,
		5.0
	};
	
	public static final double [] SPEED_Y = {
		0.0,
		0.0
	};

	public static final double [] ROTATE_SPEED_IN_DEGREE = {
		30.0,
		30.0
	};

	public static final double MAX_DISTANCE = 200.0;

	public static final int ATK = 10;

	public static final int PRICE = 100;
	public static final String DESCRIPTION = "Dagger";

	//-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Variables
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	int indexOfDagger; // type of dagger
	double curDegree;
	double rotateSpeedInDegree;
	double curDist; // 현재까지 날라간 거리
	
	public Collider collider; //  dagger는 일단 circle collider를 쓰기로..

	//-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	public Dagger() {
		this(DEFAULT_DAGGER_INDEX);
	}

	public Dagger(int indexOfDagger) {
		this.indexOfDagger = indexOfDagger;
		super.init(DAGGER_IMAGE_FILENAMES[indexOfDagger],
				   START_X_ON_MAP[indexOfDagger],
				   START_Y_ON_MAP[indexOfDagger],
				   Ragnarok.DIR_NONE,
				   SPEED_X[indexOfDagger],
				   SPEED_Y[indexOfDagger],
				   ATK,
				   PRICE,
				   DESCRIPTION);
		
		curDegree = 0;
		rotateSpeedInDegree = ROTATE_SPEED_IN_DEGREE[indexOfDagger];
		curDist = 0.0;
		
		collider = CircleCollider.create(this, image);
		
        atk = 10;
	}

    @Override
	public void reset() {
		super.reset();
		
		curDegree = 0;
		rotateSpeedInDegree = ROTATE_SPEED_IN_DEGREE[indexOfDagger];
		curDist = 0.0;
	}	
	
    @Override
    public void paint(Graphics g) {
		if (destroyed) {
			return;
		}
		
		double scrX = xOnMap + Ragnarok.bgX; 
		double scrY = yOnMap + Ragnarok.bgY;
		
        // create the transform, note that the transformations happen
        // in reversed order (so check them backwards)
        AffineTransform at = new AffineTransform();

        // 4. translate it to the center of the component
//        at.translate(image.getWidth() / 2, image.getHeight() / 2);
        at.translate((int)scrX + image.getWidth() / 2,  (int)scrY + image.getHeight() / 2);

        // 3. do the actual rotation
//        at.rotate(Math.PI/4);
        at.rotate((-curDegree / 180.0) * Math.PI);
        
        if (dir == Ragnarok.DIR_E) {
        	curDegree -= rotateSpeedInDegree;
        }
        else {
        	curDegree += rotateSpeedInDegree;
        }

        // 2. just a scale because this image is big
//        at.scale(0.5, 0.5);

        // 1. translate the object so that you rotate it around the 
        //    center (easier :))
        at.translate(-image.getWidth()/2, -image.getHeight()/2);
//        at.translate((int)scrX + -image.getWidth()/2,  (int)scrY - image.getHeight()/2);

        // draw the image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
   
//		g.drawImage(image, (int)scrX, (int)scrY, null);
        
		collider.paint(g);
    }
    
    @Override
	public void update() {
		if (destroyed) {
			return;
		}

		super.update();
		
		//System.out.println("Daggar::update(): " + this);
		
		if (dir == Ragnarok.DIR_W) {
			xOnMap -= speedX;
		}
		else  {
			xOnMap += speedX;
		}
		curDist += speedX; // 방향성이 없으므로, 절대값이라 간주하고 더한다.
		
		if (curDist >= MAX_DISTANCE) {
			destroyed = true;
		}
		
//		double nextX = xOnMap + Ragnarok.bgX;
//		double nextY = yOnMap + Ragnarok.bgY;
//		double nextXOnMap = xOnMap;
//		double nextYOnMap = yOnMap;
//		double nextMap01X = GhoulsNGhosts.bgX;
//		double nextMap01Y = GhoulsNGhosts.bgY;
		
//		double diffX = 0.0;
		

		
//		double distX = Math.abs(xOnMap - Ragnarok.hero01.xOnMap);
//		if (xOnMap > Ragnarok.hero01.xOnMap) {
//			dir = DIR_W;
//			
//			nextX -= speedX;
//			nextXOnMap -= speedX;
//			
//			if (actionState != DAGGAR1) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("DAGGAR1");
//			actionState = DAGGAR1;
//		}
//		else if (xOnMap < Ragnarok.hero01.xOnMap) {
//			dir = DIR_E;
//			
//			nextX += speedX;
//			nextXOnMap += speedX;
//			
//			if (actionState != DAGGAR1) {
//				anims.curSprite.reset();
//			}
//			anims.setCurrentAction("DAGGAR1");
//			actionState = DAGGAR1;
//		}
//		
// 		if (!CollisionDetection.collideWithBackgroundMask(Ragnarok.bgMask, (int)Ragnarok.bgX, (int)Ragnarok.bgY, anims.curSprite.curImage, (int)nextX, (int)nextY)) {
//			xOnMap = nextXOnMap;
//			yOnMap = nextYOnMap;
// 		}
    }
}