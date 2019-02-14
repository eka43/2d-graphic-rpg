package chai_utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ragnarok.GameObject;
import ragnarok.Ragnarok;
import ragnarok.Unit;

public class CircleCollider extends Collider {
	public GameObject owner;
	public BufferedImage image;
	public double topLeftX; 
	public double topLeftY; 
	public double btmRightX; 
	public double btmRightY;
	public double centerX;
	public double centerY;
	public double r;
	public double symmetryX;
	
	public CircleCollider() {
		this(null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	}

	public CircleCollider(GameObject owner, BufferedImage image, double topLeftX, double topLeftY, double btmRightX, double btmRightY, double centerX, double centerY, double r, double symmetryX) {
		this.owner = owner;
		this.image = image;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.btmRightX = btmRightX;
		this.btmRightY = btmRightY;
		this.centerX = centerX;
		this.centerY = centerY;
		this.r =r;
		this.symmetryX = symmetryX;
	}

	public static CircleCollider create(GameObject owner, BufferedImage image) {
		CircleCollider circleCollider = new CircleCollider();
		circleCollider.owner = owner;
		circleCollider.image = image;
		
		// find topLeftY
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					circleCollider.topLeftY = y;
				}
			}
		}

		// find btmRightY
		for (int y = image.getHeight()-1; y >= 0; y--) {
			for (int x = 0; x < image.getWidth(); x++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					circleCollider.btmRightY = y;
				}
			}
		}

		// find topLeftX
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					circleCollider.topLeftX = x;
				}
			}
		}

		// find btmRightX
		for (int x = image.getWidth()-1; x >= 0 ; x--) {
			for (int y = 0; y < image.getHeight(); y++) {
				int curAlpha = new Color(image.getRGB(x, y), true).getAlpha();
				if (curAlpha > 0) {
					circleCollider.btmRightX = x;
				}
			}
		}
		
		// find centerX
		circleCollider.centerX = owner.xOnMap + image.getWidth() / 2;
		
		// find centerY
		circleCollider.centerY = owner.yOnMap + image.getHeight() / 2;
		
		//compare the value that which one is bigger
		double r1 = Math.sqrt(Math.pow((circleCollider.topLeftX-circleCollider.centerX), 2) + Math.pow((circleCollider.topLeftY-circleCollider.centerY),2));
		double r2 = Math.sqrt(Math.pow((circleCollider.btmRightX-circleCollider.centerX), 2) + Math.pow((circleCollider.btmRightY-circleCollider.centerY),2));
		
		if (r1 > r2) {
			circleCollider.r = r1;
		} else {
			circleCollider.r = r2;
		}
		
		// get symmetryX
		circleCollider.symmetryX = (circleCollider.btmRightX + circleCollider.topLeftX) / 2;

		return circleCollider;
	}

	public void paint(Graphics g) {
		//double scrX = owner.xOnMap + Ragnarok.bgX + sprite.offsetX; 
		//double scrY = owner.yOnMap + Ragnarok.bgY + sprite.offsetY;
		
		double scrX = owner.xOnMap + Ragnarok.bgX;
		double scrY = owner.yOnMap + Ragnarok.bgY;

		if (owner.dir == Ragnarok.DIR_W) {
			//System.out.println("BoxCollider::paint() - dir is " + owner.dir);
			g.setColor(Color.pink);
	        g.drawOval((int)(scrX+(centerX-r)), (int)(scrY+(centerY-r)), (int)(2*r), (int)(2*r));
		} else {
			// 대칭점 공식	x1 + x2 / 2 = a // x2 = 2a - 1
			double flipLeftX = 2 * symmetryX - topLeftX;
			
			g.setColor(Color.CYAN);
	        g.drawOval((int)(scrX+(centerX-r)), (int)((scrY+(centerY-r))), (int)((2*r)-flipLeftX), (int)((2*r)));
	        //drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
		}
	}
	
	public boolean collideWith(Collider otherCollider) {
		if (otherCollider instanceof CircleCollider) {
			CircleCollider paraCircleCollider = (CircleCollider) otherCollider;
			
			double circle1CenterX = centerX;
			double circle1CenterY = centerY;
			double circle2CenterX = paraCircleCollider.centerX;
			double circle2CenterY = paraCircleCollider.centerY;
			
			// 각 circle의 중심 사이의 거리가 2r보다 작거나 같을때
			if(( Math.abs(circle1CenterX-circle2CenterX) <= 2*r)&&( Math.abs(circle1CenterY-circle2CenterY) <= 2*r)) {
				return true;
			}
			return false;
		}
		else if (otherCollider instanceof BoxCollider) {
			BoxCollider paraBoxCollider = (BoxCollider) otherCollider;
			
			double boxScrX = paraBoxCollider.owner.xOnMap + Ragnarok.bgX; 
			double boxScrY = paraBoxCollider.owner.yOnMap + Ragnarok.bgY;
			double circleScrX = owner.xOnMap + Ragnarok.bgX; 
			double circleScrY = owner.yOnMap + Ragnarok.bgY;
			double circleXonMap;
			double circleYonMap = centerX + Ragnarok.bgY; // 맵에서의 원의 중심점 좌표
			double boxFlipLeftX = paraBoxCollider.calculatePoint(paraBoxCollider.symmetryX, paraBoxCollider.topLeftX);
			double boxFlipRightX = paraBoxCollider.calculatePoint(paraBoxCollider.symmetryX, paraBoxCollider.btmRightX);
//			double circleFlipLeftX = 2 * circle.symmetryX - circle.topLeftX;
			
			// circle이 전부 box 안에 들어갔을때
			if (owner.dir == Ragnarok.DIR_W) {
				double BoxDistance  = boxScrX + paraBoxCollider.image.getWidth() - boxFlipRightX; // moster dir is East
				/*if((CircleDistance <= BoxDistance) && (circleScrY + centerY <= paraBoxCollider.topLeftY) 
					&& (circleScrY + centerY >= paraBoxCollider.btmRightY)) {
					return true;
				}*/
				
				//x좌표 detection : 원의 중심점과 사각형의 제일 가까운 변의 거리가 r보다 작을때 detect
				if((Math.abs((BoxDistance) - (circleScrX + centerX)) < r) && (circleScrY +r <= paraBoxCollider.topLeftY + boxScrY) 
						&& (circleScrY + r >= paraBoxCollider.btmRightY + boxScrY) ) { // 범위 계산 완료
					System.out.println("CircleCollider:: collideWith(): cir dir is west, box dir is east");
//					paraBoxCollider.owner.hp -= 10;
//					if (circleScrX + centerX + r == boxScrX) {
//					    paraBoxCollider.owner.processAttack();
//					}
					//Ragnarok.score++;
					return true; 
				}
			} else {//when weapon(circle collier) dir is east
				double BoxDistance  = boxScrX + paraBoxCollider.topLeftX; // moster dir is west

				if((Math.abs((BoxDistance) - (circleScrX + centerX)) < r) && (circleScrY +r <= paraBoxCollider.topLeftY + boxScrY) 
						&& (circleScrY + r >= paraBoxCollider.btmRightY + boxScrY) ) { // 범위 계산 완료
					System.out.println("CircleCollider:: collideWith(): cir dir is east, box dir is west");
//					paraBoxCollider. -= 10;
//	                if (circleScrX + centerX - r == boxScrX + paraBoxCollider.image.getWidth()) {
//	                    paraBoxCollider.owner.processAttack();
//	                }
	                //paraBoxCollider.owner.processAttack();
	                //Ragnarok.score++;
					return true;
				}				
			}
		}
		return false;
	}
}
