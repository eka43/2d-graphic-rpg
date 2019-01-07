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
import java.util.Timer;
import java.util.Vector;

import chai_utils.Animations;
import chai_utils.CollisionDetection;
import chai_utils.Sprite;
import chai_utils.SpriteInfo;

public abstract class Monster extends Unit {
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Constants
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Constants for Monsters working
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
//	public static final char MONSTER_SHAPE = 'm';
//	public static final int MAX_NUM_MONS = 10;

//	public static final int MONSTER_ROW = (Board.ROW_SIZE/2);
//	public static final int MONSTER_COL = (Board.COL_SIZE/2);
	
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Member Variables
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variables for Monsters working
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // 일단 throwable item이 몬스터에 맞았을 때, 기억해두고, 두번 이상 맞지 않도록 한다.
	// 여러방 맞아야 하는 부메랑을 일단 제외. 지나가는 아이만..
	public static final int PROJECTILES_HIT_ME_MAX = 10;
    public Vector<Weapon> projectilesHitMe;
	
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	// Member Functions
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------
	//-------------------------------------------------------------------------

	public Monster() {
		super();
		projectilesHitMe = new Vector<Weapon>();
	}
	
    @Override
	public void update() {
		super.update();
	}
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Functions for Monsters working
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
	//battle
//	public void interact(Unit unit) {
//		if (unit == null) {
//			System.out.println("interact(): error: unit == null");
//			System.exit(1);
//		}
//
//	    if (isDead()) {
//	        return;
//	    }
//
//		decHp(unit.getAtk()); //ADD hero hits mon
//
//		if (!isDead()) {
//			unit.decHp(atk);
//		}
//		else {
//			unit.incGold(gold);
//			unit.incExp(exp);
//			decGold(gold);
//			decGold(exp);
//		}
//	}

	public boolean hit(Weapon projectile) {
		// 한번 맞은 아이는 두번 맞지 않게 한다.
		if (projectilesHitMe.contains(projectile)) {
			return false;
		}

		if (hp - projectile.atk <= 0) {
			hp = 0;
			died = true;
		}
		else {
			hp -= projectile.atk;
		}
		
		// 일단 Queue방식으로 쌓이면 앞에것을 지우고, 뒤에 붙이는 식.
		if (projectilesHitMe.size() >= PROJECTILES_HIT_ME_MAX) {
			projectilesHitMe.removeElementAt(0);
		}
		
		projectilesHitMe.add(projectile);
		
		return true;
	}
}
