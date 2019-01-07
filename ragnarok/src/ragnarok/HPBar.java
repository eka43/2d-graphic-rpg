package ragnarok;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HPBar {
	public GameObject owner;

	public BufferedImage frameImage;
	public BufferedImage barImage; // original
	
	public BufferedImage curBarImage; // 현재 hp/maxHp에 따라, startXOfBarImage와 endXOfBarImage사이를 비율로 계산해서, barImage로부터 이곳으로 copy해온다. 

	public double offsetX; // offset from owner xOnMap
	public double offsetY; // offset from owner yOnMap
	
	public double startXOfBarImage;
	public double endXOfBarImage;

	public double prevHp;

	public double hp;
	public double maxHp;
	
	public HPBar() {
		
	}

	public HPBar(GameObject owner,
				String frameImageFilename, String barImageFilename,
				double offsetX, double offsetY,
				double startXOfBarImage, double endXOfBarImage,
				double hp, double maxHp) {
		this.owner = owner;
		
		if (frameImageFilename != null) {
			try {
				frameImage = ImageIO.read(new File(frameImageFilename));
		   	} catch (IOException e) {
		   		System.out.println("Item::init(): error: failed to open image file: " + frameImageFilename);
		   		System.exit(1);
		   	}
		}

		if (barImageFilename != null) {
			try {
				barImage = ImageIO.read(new File(barImageFilename));
		   	} catch (IOException e) {
		   		System.out.println("Item::init(): error: failed to open image file: " + barImageFilename);
		   		System.exit(1);
		   	}
		}
		
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		this.startXOfBarImage = startXOfBarImage;
		this.endXOfBarImage = endXOfBarImage;

		prevHp = hp;
		this.hp = hp;
		this.maxHp = maxHp;
		
		processBarImageByHP();
	}

	public String toString() {
        return "";
    }

	void processBarImageByHP() {
		double ratio = hp / maxHp;
//		System.out.println("HPBar::processBarImageByHP(): maxHp = " + maxHp + " / hp = " + hp);
//		System.out.println("HPBar::processBarImageByHP(): ratio = " + ratio);
		
		int imageW = barImage.getWidth();
		int imageH = barImage.getHeight();
//		System.out.println("HPBar::processBarImageByHP(): imageW = " + imageW);
//		System.out.println("HPBar::processBarImageByHP(): imageH = " + imageH);

//		if (curBarImage == null) {
			curBarImage = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_ARGB);
			//Graphics2D g2 = curBarImage.createGraphics(); // 이것을 쓰면 이미지 위에 그림을 그릴 수 있다.
//		}

		double actualBarLength = endXOfBarImage - startXOfBarImage + 1;
//		System.out.println("HPBar::processBarImageByHP(): actualBarLength = " + actualBarLength);

		int curEndXByRatio = (int)(startXOfBarImage + actualBarLength * ratio);
//		System.out.println("HPBar::processBarImageByHP(): curEndXByRatio = " + curEndXByRatio);

		for (int i = 0; i < imageH; i++) { // i == y
			for (int j = (int)startXOfBarImage; j < curEndXByRatio; j++) { // j == x
				curBarImage.setRGB(j, i, barImage.getRGB(j, i));
			}
		}
	}

    public void paint(Graphics g) {
    		if (prevHp != hp) {
    			processBarImageByHP();
    		}
    	
//    		System.out.println("HPBar::paint(): owner.hpBarOffsetX = " + owner.hpBarOffsetX);
    		double scrX = owner.xOnMap + Ragnarok.bgX + owner.hpBarOffsetX;
    		double scrY = owner.yOnMap + Ragnarok.bgY + offsetY;

		g.drawImage(curBarImage, (int)scrX, (int)scrY, null);
		g.drawImage(frameImage, (int)scrX, (int)scrY, null);
    }
    
    public void update() {
        
    }
    
    public void updateHp(int hp) {
    		if (hp <= 0) {
    			hp = 0;
    		}
    		prevHp = this.hp;
    		this.hp = hp;
    }
}
