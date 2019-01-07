package ragnarok;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class InventoryPanel extends Canvas implements MouseListener, MouseMotionListener {
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Constants
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public static final String CANVAS_BG_IMAGE_FILENAME = "assets/inventory/inventory_panel_01.png";
	
    public static final int CANVAS_BG_IMAGE_W = 277;
    public static final int CANVAS_BG_IMAGE_H = 93;

    public static final int W = CANVAS_BG_IMAGE_W;
    public static final int H = CANVAS_BG_IMAGE_H;

    public static final int ROW_SIZE = 2;
    public static final int COL_SIZE = 7;

    public static final int MAX_NUM_ITEMS = ROW_SIZE * COL_SIZE;

    //-------------------------------------------------------------------------
    // to draw items on panel
    //-------------------------------------------------------------------------
    public static final int TOP_LEFT_START_X = 12;
    public static final int TOP_LEFT_START_Y = 12;
    
    public static final int SLOT_WIDTH = 31;
    public static final int SLOT_HEIGHT = 30;

    public static final int HORZ_GAP = 6;
    public static final int VERT_GAP = 8;

    public static final int ITEM_IMAGE_WIDTH = 24;
    public static final int ITEM_IMAGE_HEIGHT = 24;
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Variables
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public BufferedImage bgImage;
    
    public int mouseX;
    public int mouseY;
    
//    public JButton btnBuy;
    
//    public static Vector<Item> inventory;
//    public Item droppedItems;
    
    public static Item[][] items;
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	public InventoryPanel() {
		try {
	   		bgImage = ImageIO.read(new File(CANVAS_BG_IMAGE_FILENAME));
		} catch (IOException e) {
			System.out.println("InventoryPanel::InventoryPanel(): error: failed to open image file: " + CANVAS_BG_IMAGE_FILENAME);
		}

		items = new Item[ROW_SIZE][COL_SIZE];
		
		for (int i = 0; i < ROW_SIZE; i++) {
			for (int j = 0; j < COL_SIZE; j++) {
				items[i][j] = null;
			}
		}
	}

	//@Override
	public void paint(Graphics g) {
		g.drawImage(bgImage, 0, 0, null);
		
		for (int i = 0; i < ROW_SIZE; i++) {
			for (int j = 0; j < COL_SIZE; j++) {
				if (items[i][j] != null) {
					//  offset from top-left of panel
					int offsetX = TOP_LEFT_START_X + j * (SLOT_WIDTH + HORZ_GAP) + (SLOT_WIDTH - ITEM_IMAGE_WIDTH) / 2;
					int offsetY = TOP_LEFT_START_Y + i * (SLOT_HEIGHT + VERT_GAP) + (SLOT_HEIGHT - ITEM_IMAGE_HEIGHT) / 2;

					g.drawImage(items[i][j].image, offsetX, offsetY, null);
				}
			}
		}
		
		if (Ragnarok.itemPickup != null) {
			g.drawImage(Ragnarok.itemPickup.image, mouseX, mouseY, null);
		}
	}
	
	public static void reactBuy(int index) {
		for (int i = 0; i < ROW_SIZE; i++) {
			for (int j = 0; j < COL_SIZE; j++) {
				if (items[i][j] == null) {
					items[i][j] = Ragnarok.itemPickup;
					return;
				}
			}
		}
	}
	
//	public void paint(int row, int col) {
//		
//		Graphics g2 = (Graphics) bufferStrategy.getDrawGraphics();
//		
//		int offsetX = TOP_LEFT_START_X + col * (SLOT_WIDTH + HORZ_GAP) + (SLOT_WIDTH - ITEM_IMAGE_WIDTH) / 2;
//		int offsetY = TOP_LEFT_START_Y + row * (SLOT_HEIGHT + VERT_GAP) + (SLOT_HEIGHT - ITEM_IMAGE_HEIGHT) / 2;
//		
//		g.drawImage(ShopPanel.itemPickedUp.image, offsetX, offsetY, null);
//	}
	
	//==================================================================================================== Mouse listener methods
    //==================================================================================================== Mouse listener methods
    //==================================================================================================== Mouse listener methods
    // Mouse listener methods 
    //==================================================================================================== Mouse listener methods
    //==================================================================================================== Mouse listener methods
    //==================================================================================================== Mouse listener methods
    
	@Override
	public void mouseDragged(MouseEvent e) {
		this.setLocation(getX() - mouseX + e.getPoint().x, getY() - mouseY + e.getPoint().y);
        Ragnarok.gamePanel.requestFocus();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
//        System.out.println("ShopPanel::mouseMoved(): mouseX = " + mouseX + " / mouseY = " + mouseY);
        
//        setBounds(mouseX, mouseY, ShopPanel.W, ShopPanel.H);
        if (Ragnarok.itemPickup != null) {
	    	Ragnarok.mouseX = mouseX + getX();
	    	Ragnarok.mouseY = mouseY + getY();
	    	
		    Ragnarok.shopPanel.mouseX = Ragnarok.mouseX - Ragnarok.shopPanel.getX();
	   		Ragnarok.shopPanel.mouseY = Ragnarok.mouseY - Ragnarok.shopPanel.getY();
	   		
		    Ragnarok.gamePanel.mypaint();
	        Ragnarok.shopPanel.repaint();
	        
    		repaint();
        }
        Ragnarok.gamePanel.requestFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
//		Item droppedItems = new Item(ShopPanel.itemPickedUp);
//		this.droppedItems = ShopPanel.itemPickedUp;
//		inventory.add(droppedItems);
//		System.out.println("InventoryPanel::mouseClicked(): ShopPanel " + ShopPanel.itemPickedUp.name);
//		System.out.println("InventoryPanel::mouseClicked(): I just coppied " + droppedItems.name);
		
//		Item newDroppedItem = null;
//		newDroppedItem.init(ShopPanel.itemPickedUp.ImgFilename, ShopPanel.itemPickedUp.xOnMap, ShopPanel.itemPickedUp.yOnMap, 
//				ShopPanel.itemPickedUp.dir, ShopPanel.itemPickedUp.speedX, ShopPanel.itemPickedUp.speedY, 
//				ShopPanel.itemPickedUp.price, ShopPanel.itemPickedUp.description);
		
//		droppedItems.add(ShopPanel.itemPickedUp);
		
//		System.out.println("InventoryPanel::mouseClicked(): droppedItems " + droppedItems );
		
//		paint(ShopPanel.itemPickedUp.image, row, col, null);
        repaint();
        Ragnarok.gamePanel.requestFocus();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int col = mouseX;
		int row = mouseY;
		
		//tempX = (tempX - TOP_LEFT_START_X) / HORZ_GAP;
		//tempY = (tempY - TOP_LEFT_START_Y) / VERT_GAP;
		
		col = col - TOP_LEFT_START_X;
		col = col / (SLOT_WIDTH + HORZ_GAP);
        
		row = row - TOP_LEFT_START_Y;
		row = row / (SLOT_HEIGHT + VERT_GAP);
		
		System.out.println("InventoryPanel::mouseClicked(): x is = " + col + " y is " + row);
		
        mouseX = e.getX();
        mouseY = e.getY();
        
        int tempX = mouseX;
        tempX = tempX - TOP_LEFT_START_X;
        tempX = tempX / (SLOT_WIDTH + HORZ_GAP);
        
        int tempY = mouseY;
        tempY = tempY - TOP_LEFT_START_Y;
        tempY = tempY / (SLOT_HEIGHT + VERT_GAP);
        
        int itemIndex = (tempY * COL_SIZE) + tempX;
        
        System.out.println("InventoryPanel::mousePressed(): itemIndex = " + itemIndex);
        
        if ((Ragnarok.itemPickup != null) && items[row][col] == null) {
	        
	        items[tempY][tempX] = new Item(Ragnarok.itemPickup);
	        
	        // process purchasing/checkout
	        //???????????????????????????????????????????????????????????????????????????????????/
	        
	        Ragnarok.itemPickup = null;
	        Ragnarok.itemPickupPanel = Ragnarok.PANEL_NONE;
	        Ragnarok.itemPickupRow = -1;
	        Ragnarok.itemPickupCol = -1;
        } else {
        	System.out.println("~Cannot pose item at here!~");
        }
        
        repaint();
        Ragnarok.gamePanel.requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
        repaint();
        Ragnarok.gamePanel.requestFocus();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		Ragnarok.gamePanel.requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Ragnarok.gamePanel.requestFocus();
	}
}
