package ragnarok;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import chai_utils.BoxCollider;

public class ShopPanel extends Canvas implements MouseListener, MouseMotionListener {
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Constants
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public static final String CANVAS_BG_IMAGE_FILENAME = "assets/inventory/shop_panel_02.png";
    
    public static final int CANVAS_BG_IMAGE_W = 277;
    //public static final int CANVAS_BG_IMAGE_H = 244;
    public static final int CANVAS_BG_IMAGE_H = 256;

    public static final int W = CANVAS_BG_IMAGE_W;
    public static final int H = CANVAS_BG_IMAGE_H;

    public static final int ROW_SIZE = 6;
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
    // Item List
    //-------------------------------------------------------------------------
	public static final String [] SELLING_ITEMS_SPRITES_FILENAMES = {
		"assets/sprite/item/hppotion.gif",
		"assets/sprite/item/redpotion.gif",
		"assets/sprite/item/mppotion.gif",
		"assets/sprite/item/bluepotion.gif",
		"assets/sprite/item/strawberry.gif",
		"assets/sprite/item/sandstorm.gif",
		"assets/sprite/item/zenyknife.gif",
		"assets/sprite/item/goldenarmor.gif",
		"assets/sprite/item/plate.gif"
	};
	
	public static final String [] SELLING_ITEMS_CLASS_NAME = {
		"ConsumableItem", //"HPPotion",
		"ConsumableItem", //"SuperHPPotion",
		"ConsumableItem", //"MPPotion",
		"ConsumableItem", //"SuperMPPotion",
		"ConsumableItem", //"Strawberry",
		"Weapon", //"Sandstorm",
		"Weapon", //"ZenyKnife",
		"Armor", //"GoldenArmor",
		"Armor", //"Plate"
	};

	// use name above for big image
//	public static final String [] SELLING_ITEMS_BIG_IMAGES_FILENAMES = {
//		"assets/sprite/item/hppotionbig.png",
//		"assets/sprite/item/redpotionbig.png",
//		"assets/sprite/item/mppotionbig.png",
//		"assets/sprite/item/bluepotionbig.png",
//		"assets/sprite/item/strawberrybig.png",
//		"assets/sprite/item/sandstormbig.png",
//		"assets/sprite/item/zenyknifebig.png",
//		"assets/sprite/item/goldenarmorbig.png",
//		"assets/sprite/item/platebig.png"
//	};
	
	public static final int [] SELLING_ITEMS_PRICE = {
		100,
		100,
		100,
		100,
		100,
		100,
		100,
		100,
		100,
	};
	
	public static final String [] SELLING_ITEMS_DESCRIPTION = {
		"This is an hp potion.",
		"This is an advanced hp potion.",
		"This is an mp potion.",
		"This is an advanced mp potion.",
		"This is an chocolate strawberry.",
		"This is an sandstorm knife.",
		"This is an zeny knife.",
		"This is an golden armor.",
		"This is an plate armor."
	};
	
    //-------------------------------------------------------------------------
    // buy button related
    //-------------------------------------------------------------------------
    public static final String BUY_BUTTON_FILENAME = "assets/inventory/buttonbuy_75perc.png";
    public static final int BUY_BUTTON_OFFSET_X = 97;
    public static final int BUY_BUTTON_OFFSET_Y = 236;

    public static final int BUY_BUTTON_ANIMATION_LEVEL = 3;
    public static final double BUY_BUTTON_ANIMATION_SIZE_PERC_PER_LEVEL = 10;
    public static final int BUY_BUTTON_ANIMATION_MAX_STEPS = 4;
    public static final int BUY_BUTTON_ANIMATION_INTERVAL_MAX_COUNT = 1000;
    
    public Vector<BufferedImage> buyButtonImages;
    public int buyButtonAnimationCurSteps;
    public int buyButtonAnimationCurIntervalCount;
    
    public static final int MIN_BUTTON_IMAGE_W_START = 97;
    public static final int MAX_BUTTON_IMAGE_W_END = 176;
    public static final int MIN_BUTTON_IMAGE_H_START = 236;
    public static final int MAX_BUTTON_IMAGE_H_END = 254;
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Variables
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    public BufferedImage bgImage;
    
    public int mouseX;
    public int mouseY;
    
    public JButton btnBuy;
    
    public Vector<Item> items;

//    public static Item itemPickedUp;
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Instance Methods
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	public ShopPanel() {
//		this.setLayout(null);      // set the layout manager

//		btnBuy = new JButton("BUY");
//		btnBuy.setBounds(100, 100, 100, 50);
//        this.add(btnBuy, new Integer(0), 0);
//        btnBuy.setVisible(true);
		
	    try {
	    		bgImage = ImageIO.read(new File(CANVAS_BG_IMAGE_FILENAME));
	   	} catch (IOException e) {
	   		System.out.println("ShopPanel::ShopPanel(): error: failed to open image file: " + CANVAS_BG_IMAGE_FILENAME);
	   	    return;
	   	}
	    
	    //-----------------------------------------------------------------
	    // buy button related
	    //-----------------------------------------------------------------
	    buyButtonImages = new Vector<BufferedImage>();

	    BufferedImage originalBuyButtonImage = null;
	    
	    try {
	    		originalBuyButtonImage = ImageIO.read(new File(BUY_BUTTON_FILENAME));
	   	} catch (IOException e) {
	   		System.out.println("ShopPanel::ShopPanel(): error: failed to open image file: " + BUY_BUTTON_FILENAME);
	   	    return;
	   	}
		buyButtonImages.add(originalBuyButtonImage);

	    for (int i = 1; i < BUY_BUTTON_ANIMATION_LEVEL; i++) {
//		    BufferedImage curBuyButtonImage = null;

	    		double curPerc = (100.0 - (i * BUY_BUTTON_ANIMATION_SIZE_PERC_PER_LEVEL))/100.0;
	    		double curWidth = curPerc * originalBuyButtonImage.getWidth();
	    		double curHeight = curPerc * originalBuyButtonImage.getHeight();
	    	
		    BufferedImage curBuyButtonImage = new BufferedImage((int)curWidth, (int)curHeight, originalBuyButtonImage.getType());
		    Graphics2D g = curBuyButtonImage.createGraphics();
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g.drawImage(originalBuyButtonImage, 0, 0, (int)curWidth, (int)curHeight, 0, 0, originalBuyButtonImage.getWidth(),
		    		originalBuyButtonImage.getHeight(), null);
		    g.dispose();
		    
	    		buyButtonImages.add(curBuyButtonImage);
	    }
	    
	    buyButtonAnimationCurSteps = BUY_BUTTON_ANIMATION_MAX_STEPS;
	    buyButtonAnimationCurIntervalCount = BUY_BUTTON_ANIMATION_INTERVAL_MAX_COUNT;
	    
//	    public static final String BUY_BUTTON_FILENAME = "assets/inventory/buttonbuy_75perc.png";
//	    public static final int BUY_BUTTON_OFFSET_X = 236;
//	    public static final int BUY_BUTTON_OFFSET_Y = 97;
//
//	    public static final int BUY_BUTTON_ANIMATION_LEVEL = 3;
//	    public static final double BUY_BUTTON_ANIMATION_SIZE_PERC_PER_LEVEL = 10;
//	    public Vector<BufferedImage> buyButtonImages;
	    	
	    //-----------------------------------------------------------------
	    // load items from server(for now, from constants)
	    //-----------------------------------------------------------------
	    items = new Vector<Item>();
	    for (int i = 0; i < SELLING_ITEMS_SPRITES_FILENAMES.length; i++) {
		    	Item newItem = null;
		    		
		    	try {
		    		newItem = (Item)Class.forName("ragnarok."+SELLING_ITEMS_CLASS_NAME[i]).newInstance();
		    	}
		    	catch (ClassNotFoundException e) {
		    		System.out.println("ShopPanel::ShopPanel(): ClassNotFoundException: " + SELLING_ITEMS_CLASS_NAME[i]);
		   			System.exit(1);
		   		}
		   		catch (IllegalAccessException e) {
		   			System.out.println("ShopPanel::ShopPanel(): IllegalAccessException: " + SELLING_ITEMS_CLASS_NAME[i]);
	    			System.exit(1);
	    		}
		    	catch (InstantiationException e) {
		   			System.out.println("ShopPanel::ShopPanel(): InstantiationException: " + SELLING_ITEMS_CLASS_NAME[i]);
		   			System.exit(1);
		   		}
		   		
	    		newItem.init(SELLING_ITEMS_SPRITES_FILENAMES[i], SELLING_ITEMS_PRICE[i], SELLING_ITEMS_DESCRIPTION[i]);
    		
//	    		if (SELLING_ITEMS_CLASS_NAME[i].equals("ConsumableItem")) {
//	    			newItem = new ConsumableItem(SELLING_ITEMS_SPRITES_FILENAMES[i], SELLING_ITEMS_PRICE[i], SELLING_ITEMS_DESCRIPTION[i]);
//	    		}
//	    		else if (SELLING_ITEMS_CLASS_NAME[i].equals("Weapon")) {
//	    			newItem = new Weapon(SELLING_ITEMS_SPRITES_FILENAMES[i], SELLING_ITEMS_PRICE[i], SELLING_ITEMS_DESCRIPTION[i]);
//	    		}
//	    		else if (SELLING_ITEMS_CLASS_NAME[i].equals("Armor")) {
//	    			newItem = new Armor(SELLING_ITEMS_SPRITES_FILENAMES[i], SELLING_ITEMS_PRICE[i], SELLING_ITEMS_DESCRIPTION[i]);
//	    		}
	    	
	   		items.add(newItem);
	   	}
	   	
//	   	itemPickedUp = null;
	   	
	   	//tried to put Jbutton
// 		ImageIcon buttonImage = new ImageIcon("assets/inventory/button.jpg");
	   	
//   	btnBuy = new JButton("buy", buttonImage);
//	   	btnBuy.setMnemonic(KeyEvent.VK_S);
	   	}
	
//	@Override
	public void paint(Graphics g) {
		g.drawImage(bgImage, 0, 0, null);
		//System.out.println("ShopPanel::paint():");

	    // anim�씠 �씪�뼱�굹怨� �엳�뒗 寃쎌슦..
	    if (buyButtonAnimationCurSteps < BUY_BUTTON_ANIMATION_MAX_STEPS) {
	    		if (buyButtonAnimationCurIntervalCount <= 0) {
//	    			if (buyButtonAnimationCurSteps < buyButtonImages.size() - 1) {
//	    				g.drawImage(buyButtonImages.get(buyButtonAnimationCurSteps+1), BUY_BUTTON_OFFSET_X, BUY_BUTTON_OFFSET_Y, null);
//	    			}
//	    			else {
//	    				g.drawImage(buyButtonImages.get(buyButtonImages.size() - buyButtonAnimationCurSteps), BUY_BUTTON_OFFSET_X, BUY_BUTTON_OFFSET_Y, null);
//	    			}
	    			
//		    		g.drawImage(buyButtonImages.get(0), BUY_BUTTON_OFFSET_X+(buyButtonAnimationCurSteps * 10), BUY_BUTTON_OFFSET_Y, null);

	    			buyButtonAnimationCurIntervalCount = BUY_BUTTON_ANIMATION_INTERVAL_MAX_COUNT;
	    		}
    			if (buyButtonAnimationCurSteps < buyButtonImages.size() - 1) {
    				g.drawImage(buyButtonImages.get(buyButtonAnimationCurSteps+1), BUY_BUTTON_OFFSET_X+5, BUY_BUTTON_OFFSET_Y, null);
    			}
    			else {
    				g.drawImage(buyButtonImages.get(buyButtonImages.size() - buyButtonAnimationCurSteps), BUY_BUTTON_OFFSET_X, BUY_BUTTON_OFFSET_Y, null);
    			}
//	    		g.drawImage(buyButtonImages.get(0), BUY_BUTTON_OFFSET_X+(buyButtonAnimationCurSteps * 10), BUY_BUTTON_OFFSET_Y, null);
	    		buyButtonAnimationCurIntervalCount--;
	    		
	    		buyButtonAnimationCurSteps++;
	    }
	    // anim�씠 �씪�뼱�굹吏� �븡�쓣 寃쎌슦.
	    else {
	    		g.drawImage(buyButtonImages.get(0), BUY_BUTTON_OFFSET_X, BUY_BUTTON_OFFSET_Y, null);
	    }
		
		if (items.size() > MAX_NUM_ITEMS) {
			System.out.println("ShopPanel::paint(): error - if (items.size() > MAX_NUM_ITEMS) {");
			System.exit(1);
		}

		for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
			int rowIndex = itemIndex / COL_SIZE;
			int colIndex = itemIndex % COL_SIZE;
			
			//  offset from top-left of panel
			int offsetX = TOP_LEFT_START_X + colIndex * (SLOT_WIDTH + HORZ_GAP) + (SLOT_WIDTH - ITEM_IMAGE_WIDTH) / 2;
			int offsetY = TOP_LEFT_START_Y + rowIndex * (SLOT_HEIGHT + VERT_GAP) + (SLOT_HEIGHT - ITEM_IMAGE_HEIGHT) / 2;
			
			g.drawImage(items.get(itemIndex).image, offsetX, offsetY, null);
		}
		
		if (Ragnarok.itemPickup != null) {
			g.drawImage(Ragnarok.itemPickup.image, mouseX, mouseY, null);
		}
	}
	
//	public void tossItem(Item item) {
//		InventoryPanel.inventory.add(itemPickedUp);
//
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
//        mouseX = e.getX();
//        mouseY = e.getY();
//        System.out.println("ShopPanel::mouseMoved(): mouseX = " + mouseX + " / mouseY = " + mouseY);

//        setBounds(mouseX, mouseY, ShopPanel.W, ShopPanel.H);
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
	
	    		Ragnarok.inventoryPanel.mouseX = Ragnarok.mouseX - Ragnarok.inventoryPanel.getX();
	    		Ragnarok.inventoryPanel.mouseY = Ragnarok.mouseY - Ragnarok.inventoryPanel.getY();
	
	    		Ragnarok.gamePanel.mypaint();
	    		Ragnarok.inventoryPanel.repaint();
	        
        		repaint();
        }
        
        Ragnarok.gamePanel.requestFocus();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Ragnarok.gamePanel.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        
        // calculate/get itemIndex from mouseX, mouseY
//		for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
//			int rowIndex = itemIndex / COL_SIZE;
//			int colIndex = itemIndex % COL_SIZE;
//			
//			//  offset from top-left of panel
//			int offsetX = TOP_LEFT_START_X + colIndex * (SLOT_WIDTH + HORZ_GAP) + (SLOT_WIDTH - ITEM_IMAGE_WIDTH) / 2;
//			int offsetY = TOP_LEFT_START_Y + rowIndex * (SLOT_HEIGHT + VERT_GAP) + (SLOT_HEIGHT - ITEM_IMAGE_HEIGHT) / 2;
//			
//			g.drawImage(items.get(itemIndex).image, offsetX, offsetY, null);
//		}
        int tempX = mouseX;
        tempX = tempX - TOP_LEFT_START_X;
        tempX = tempX / (SLOT_WIDTH + HORZ_GAP);
        
        int tempY = mouseY;
        tempY = tempY - TOP_LEFT_START_Y;
        tempY = tempY / (SLOT_HEIGHT + VERT_GAP);
        
        int itemIndex = (tempY * COL_SIZE) + tempX;
        
        System.out.println("ShopPanel::mousePressed(): itemIndex = " + itemIndex);
        
        if (itemIndex < items.size() && items.get(itemIndex) != null) {
	        Ragnarok.itemPickup = items.get(itemIndex);
	        Ragnarok.itemPickupPanel = Ragnarok.PANEL_SHOP;
	        Ragnarok.itemPickupRow = tempY;
	        Ragnarok.itemPickupCol = tempX;
        }
        
        //System.out.println("ShopPanel::mousePressed(): itempickedup name = " + itemPickedUp.image);
        //System.out.println("itemPickedUp :" + itemPickedUp.name);
        

//        	tossItem(itemPickedUp);
//    		System.out.println("itemPickedUp :" + itemPickedUp.name);
//        }
        
        // 踰붿쐞 �븞�뿉 �뱾�뼱�삤硫�..
        //??????????????????????????/??????????????????????????????????????????????
        System.out.println("ShopPanel::mouseMoved(): mouseX = " + mouseX + " / mouseY = " + mouseY);
        if((mouseX >= MIN_BUTTON_IMAGE_W_START) && (mouseX <= MAX_BUTTON_IMAGE_W_END)){
        	if ((mouseY >= MIN_BUTTON_IMAGE_H_START) && (mouseY <= MAX_BUTTON_IMAGE_H_END)) {
         		buyButtonAnimationCurSteps = 0;
         		buyButtonAnimationCurIntervalCount = BUY_BUTTON_ANIMATION_INTERVAL_MAX_COUNT;
         		
         		System.out.println("ShopPanel::~Button Clicked!~");
         		Ragnarok.itemPickupIndex = itemIndex;
         		InventoryPanel.reactBuy(Ragnarok.itemPickupIndex);
         		
    	        Ragnarok.itemPickup = null;
    	        Ragnarok.itemPickupPanel = Ragnarok.PANEL_NONE;
    	        Ragnarok.itemPickupRow = -1;
    	        Ragnarok.itemPickupCol = -1;

        	}
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
