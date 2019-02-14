package chai_utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ragnarok.GameObject;
import ragnarok.Ragnarok;
import ragnarok.Unit;

public class BoxCollider extends Collider {
    public GameObject owner;
	public BufferedImage image;
	public double topLeftX; 
	public double topLeftY; 
	public double btmRightX; 
	public double btmRightY;
	public double symmetryX;
	public double centerX;
	public double centerY;
   
	public BoxCollider() {
		this(null, null, 0.0, 0.0, 0.0, 0.0, 0.0);
	}

	public BoxCollider(GameObject owner, BufferedImage image, double topLeftX, double topLeftY, double btmRightX, double btmRightY, double symmetryX) {
		this.owner = owner;
		this.image = image;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.btmRightX = btmRightX;
		this.btmRightY = btmRightY;
		this.symmetryX = symmetryX;
	}

	// static펑션을 독립적인 펑션이라고 생각하자. 따라서 절대 static이 아닌 member variable을 직접 access해서는 안 된다. 할 수도 없다.
	public static BoxCollider create(GameObject owner, BufferedImage image) {
		BoxCollider boxCollider = new BoxCollider();
		boxCollider.owner = owner;
		boxCollider.image = image;
		
		// 앨거리듬:
		//    - 맨위부터 내려오면서 alpha값이 투명하지 않은 pixel이 나오면, topLeftY
		//    - 이런식으로 top에서 아래로, bottom에서 위로, left에서 right으로, right에서 left로 가면서 위치를 발견한다.
		
		// find topLeftY
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					boxCollider.topLeftY = y;
				}
			}
		}

		// find btmRightY
		for (int y = image.getHeight()-1; y >= 0; y--) {
			for (int x = 0; x < image.getWidth(); x++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					boxCollider.btmRightY = y;
				}
			}
		}

		// find topLeftX
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					boxCollider.topLeftX = x;
				}
			}
		}

		// find btmRightX
		for (int x = image.getWidth()-1; x >= 0 ; x--) {
			for (int y = 0; y < image.getHeight(); y++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					boxCollider.btmRightX = x;
				}
			}
		}
		
		// get symmetryX
		boxCollider.symmetryX = (boxCollider.btmRightX + boxCollider.topLeftX) / 2;
		
		return boxCollider;
	}
	
	// 대칭점 공식	x1 + x2 / 2 = a // x2 = 2a - 1
	public double calculatePoint(double symmetryX, double pointX) {
		double getPoint = 2 * symmetryX - pointX;
		
		return getPoint; 
	}
	
	public void paint(Graphics g) {
//		double scrX = owner.xOnMap + Ragnarok.bgX + sprite.offsetX; 
//		double scrY = owner.yOnMap + Ragnarok.bgY + sprite.offsetY;
		double scrX = owner.xOnMap + Ragnarok.bgX; 
		double scrY = owner.yOnMap + Ragnarok.bgY;
/*		double width1 = image.getWidth() - topLeftX;
		double width2 = image.getWidth() - btmRightX;
		double width3 = image.getWidth();
*/		

		if (owner.dir == Ragnarok.DIR_W) {
			//System.out.println("BoxCollider::paint() - dir is " + owner.dir);
		    g.setColor(Color.green);
			g.drawLine((int)(scrX + topLeftX), (int)(scrY + topLeftY), (int)(scrX + btmRightX), (int)(scrY + topLeftY)); // top line
			g.drawLine((int)(scrX + topLeftX), (int)(scrY + btmRightY), (int)(scrX + btmRightX), (int)(scrY + btmRightY)); // bottom line
			g.drawLine((int)(scrX + topLeftX), (int)(scrY + topLeftY), (int)(scrX + topLeftX), (int)(scrY + btmRightY)); // left line
			g.drawLine((int)(scrX + btmRightX), (int)(scrY + topLeftY), (int)(scrX + btmRightX), (int)(scrY + btmRightY)); // right line
		} else {
			
/*			System.out.println("scrX is " + scrX);
			System.out.println("image.getWidth() is " + owner.getClass() + image.getWidth());
			System.out.println("btmRightX is " + btmRightX);
			System.out.println("topLeftX is " + topLeftX);
			System.out.println("width1 is " + width1); */
			
			double flipLeftX = calculatePoint(symmetryX, topLeftX);
			double flipRightX = calculatePoint(symmetryX, btmRightX); 
			
			g.setColor(Color.yellow);
			g.drawLine((int)(scrX + (image.getWidth()-flipLeftX)), (int)(scrY + topLeftY), (int)(scrX + (image.getWidth()-flipRightX)), (int)(scrY + topLeftY)); // top line
			g.drawLine((int)(scrX + (image.getWidth()-flipLeftX)), (int)(scrY + btmRightY), (int)(scrX + (image.getWidth()-flipRightX)), (int)(scrY + btmRightY)); // bottom line
			
			g.drawLine((int)(scrX + (image.getWidth()-flipLeftX)), (int)(scrY + topLeftY), (int)(scrX + (image.getWidth()-flipLeftX)), (int)(scrY + btmRightY)); // left line
			g.drawLine((int)(scrX + (image.getWidth()-flipRightX)), (int)(scrY + topLeftY), (int)(scrX + (image.getWidth() - flipRightX)), (int)(scrY + btmRightY)); // right line
		}
	}
	
	public boolean collideWith(Collider otherCollider) {
		if (otherCollider instanceof CircleCollider) {
			CircleCollider paraCircleCollider = (CircleCollider) otherCollider;
			
			double boxScrX = owner.xOnMap + Ragnarok.bgX; 
			double boxScrY = owner.yOnMap + Ragnarok.bgY;
			double circleScrX = paraCircleCollider.owner.xOnMap + Ragnarok.bgX; 
			double circleScrY = paraCircleCollider.owner.yOnMap + Ragnarok.bgY;
			double boxFlipLeftX = calculatePoint(symmetryX, topLeftX);
			double boxFlipRightX = calculatePoint(symmetryX, btmRightX);
//			double circleFlipLeftX = 2 * circle.symmetryX - circle.topLeftX;
			
			// circle이 전부 box 안에 들어갔을때
			if (owner.dir == Ragnarok.DIR_W) {	//box dir is west
				double BoxDistance  = boxScrX + topLeftX; 
				double CircleDistance = circleScrX + paraCircleCollider.centerX - paraCircleCollider.r; // weapon is east
				
				if((CircleDistance <= BoxDistance) && (circleScrY + paraCircleCollider.centerY <= topLeftY) 
					&& (circleScrY + paraCircleCollider.centerY >= btmRightY)) {
					return true;
				}
			} else { //box dir is east

				double BoxDistance  = boxScrX + image.getWidth() - boxFlipRightX; 
				double CircleDistance = circleScrX + paraCircleCollider.centerX + paraCircleCollider.r; // weapon is west
				
				if((CircleDistance <= BoxDistance) && (circleScrY + paraCircleCollider.centerY <= topLeftY) 
					&& (circleScrY + paraCircleCollider.centerY >= btmRightY)) {
					return true;
				}	
			}
		}
		else if (otherCollider instanceof BoxCollider) {
			BoxCollider paraBoxCollider = (BoxCollider) otherCollider;
			
			double boxScrX = owner.xOnMap + Ragnarok.bgX; 
			double boxScrY = owner.yOnMap + Ragnarok.bgY;
			double paraScrX = paraBoxCollider.owner.xOnMap + Ragnarok.bgX; 
			double paraScrY = paraBoxCollider.owner.yOnMap + Ragnarok.bgY;
			double boxFlipRightX = calculatePoint(symmetryX, btmRightX);
			double halfWidth = image.getWidth() / 2;
//			double halfHeight = image.getHeight() / 2;
			
			double paraboxRightTopY = paraScrY;
			double paraboxBottomY = paraScrX + paraBoxCollider.image.getHeight();
			double paraboxFlipRightX = calculatePoint(paraBoxCollider.symmetryX, paraBoxCollider.btmRightX);
//			double paraboxRightX = paraScrX + paraBoxCollider.image.getWidth() - paraboxFlipRightX;
			double boxReftTopY = boxScrY;
			double boxReftBottomY = boxScrY + image.getHeight();
			double halfXofBox = paraScrX + halfWidth;
			
			double boxEastX = boxScrX + image.getWidth() - boxFlipRightX; 
			double halfXofParaBox = paraScrX + (paraBoxCollider.image.getWidth() / 2);
					
			// y 좌표의 일부가 큰쪽의 어느부분에라던지 걸쳐져 있고, 총 너비의 반 이상이 상대 쪽으로 넘어가면 collide
			if (owner.dir == Ragnarok.DIR_W) {	//box dir is west
				if((((boxReftTopY >= paraboxBottomY)&&(boxReftTopY <= paraboxRightTopY)) ||
					((boxReftBottomY <= paraboxRightTopY)&&(boxReftBottomY>=paraboxBottomY)))
					&& (paraboxFlipRightX >= halfXofBox)){ //para box is east
					System.out.println("Collide!!!!");
					return true;
			} else { //box dir is east
				if((((boxReftTopY >= paraboxBottomY)&&(boxReftTopY <= paraboxRightTopY)) ||
					((boxReftBottomY <= paraboxRightTopY)&&(boxReftBottomY>=paraboxBottomY)))
					&& (boxEastX >= halfXofParaBox)) {
					System.out.println("Collide!!!!");
					return true;
				}
			}
		}
		return false;
	}
		return false;
}
	}
