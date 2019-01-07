package ragnarok;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class ConsumableItem extends Item {
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
    public int atk;
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public ConsumableItem() {
        init(null, 0.0, 0.0, Ragnarok.DIR_NONE, 0.0, 0.0, 0, null);
    }
    
    public ConsumableItem(String imgFilename, int price, String description) {
        init(imgFilename, 0.0, 0.0, Ragnarok.DIR_NONE, 0.0, 0.0, price, description);
    }

    public ConsumableItem(String imageFilename, double xOnMap, double yOnMap, int dir, double speedX, double speedY, int atk, int price, String description) {
        init(imageFilename, xOnMap, yOnMap, dir, speedX, speedY, atk, price, description);
    }
    
    public void init(String imageFilename, double xOnMap, double yOnMap, int dir, double speedX, double speedY, int atk, int price, String description) {
    		super.init(imageFilename, xOnMap, yOnMap, dir, speedX, speedY, price, description);
    		this.atk = atk;
    }
    
    @Override
    public void paint(Graphics g) {
    		super.paint(g);
    }
    
    @Override
	public void update() {
    		super.update();
    }
}
