package ragnarok;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import chai_utils.CircleCollider;
import chai_utils.Sprite;

public class Item extends GameObject {
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Constants
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Variables
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------

    //-------------------------------------------------------------------------
    // for Shop/Inventory Panel
    //-------------------------------------------------------------------------
	public String name;
	public int price;
	public String ImgFilename;
	public String description;
	
    //-------------------------------------------------------------------------
    // for in-game movement/edible/pickable
    //-------------------------------------------------------------------------
	public BufferedImage image;

    public double speedX;
    public double speedY;
    
    public boolean destroyed;

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public Item() {
        init(null, 0.0, 0.0, Ragnarok.DIR_NONE, 0.0, 0.0, 0, null);
    }
    
    public Item(String imgFilename, int price, String description) {
        init(imgFilename, 0.0, 0.0, Ragnarok.DIR_NONE, 0.0, 0.0, price, description);
    }

    public Item(String imageFilename, double xOnMap, double yOnMap, int dir, double speedX, double speedY, int price, String description) {
        init(imageFilename, xOnMap, yOnMap, dir, speedX, speedY, price, description);
    }
    
    public Item(Item item) {
	    	this.name = item.name;
	    	this.description = item.description;
	    	this.destroyed = item.destroyed;
	    	this.dir = item.dir;
	    	this.hpBarOffsetX = item.dir;
	    	this.image = item.image;
	    	this.ImgFilename = item.ImgFilename;
	    	this.prevMapX = item.prevMapX;
	    	this.prevMapY = item.prevMapY;
	    	this.price = item.price;
	    	this.speedX = item.speedX;
	    	this.speedY = item.speedY;
	    	this.xOnMap = item.xOnMap;
	    	this.yOnMap = item.yOnMap;
    }

    public void init(String imgFilename, int price, String description) {
        init(imgFilename, 0.0, 0.0, Ragnarok.DIR_NONE, 0.0, 0.0, price, description);
    }

    public void init(String imgFilename, double xOnMap, double yOnMap, int dir, double speedX, double speedY, int price, String description) {
    		super.init(xOnMap, yOnMap, dir);

		if (imgFilename != null) {
			try {
				image = ImageIO.read(new File(imgFilename));
		   	} catch (IOException e) {
		   		System.out.println("Item::init(): error: failed to open image file: " + imgFilename);
		   		System.exit(1);
		   	}
		}
//		else {
//	   		System.out.println("Item::init(): error: imageFilename != null");
//	   		System.exit(1);
//		}

		// GameObject로 옮김.
//        this.xOnMap = xOnMap;
//        this.yOnMap = yOnMap;
//
//        this.dir = dir;
        
        this.speedX = speedX;
        this.speedY = speedY;
        
        destroyed = false;
        
        this.price = price;
        this.description = description;
    }
    
    public void reset() {
        xOnMap = -1;
        yOnMap = -1;

        dir = Ragnarok.DIR_NONE;
        
        destroyed = false;
    }
    
    @Override
    public String toString() {
        return "xOnMap:" + xOnMap + " yOnMap:" + yOnMap + " dir:" + dir + " speedX:" + speedX + " speedY:" + speedY;
    }
    
    public void paint(Graphics g) {
		if (!destroyed) {
			double scrX = xOnMap + Ragnarok.bgX; 
			double scrY = yOnMap + Ragnarok.bgY;
		
			g.drawImage(image, (int)scrX, (int)scrY, null);
		}
    }
    
    public void update() {
        
    }
}
