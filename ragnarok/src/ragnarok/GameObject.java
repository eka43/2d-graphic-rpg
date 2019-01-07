package ragnarok;

import chai_utils.Animations;

public class GameObject {
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
	public double xOnMap;
	public double yOnMap;
	
	// for z-index
//	public double feetOffsetX;
//	public double feetOffsetY;
	
    public double prevMapX;
    public double prevMapY;
	
    public int dir;
    
    public int hpBarOffsetX;
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public GameObject() {
        init(0.0, 0.0, Ragnarok.DIR_E);
    }
    
    public GameObject(double xOnMap, double yOnMap, int dir) {
        init(xOnMap, yOnMap, dir);
    }

    public void init(double xOnMap, double yOnMap, int dir) {
        this.xOnMap = xOnMap;
        this.yOnMap = yOnMap;
        this.dir = dir;
    }
    
    public String toString() {
        return "xOnMap:" + xOnMap + " yOnMap:" + yOnMap + " dir:" + dir;
    }
}
