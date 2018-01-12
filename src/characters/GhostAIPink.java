package characters;

import gameExe.Exe;
import gameExe.GPM;
import javafx.scene.shape.Rectangle;

public class GhostAIPink extends GhostAI {
	private double initialY;
	
	private double distanceSinceCheck=0;
	
	Rectangle shape;
	
	private boolean isActivating=true; 
	private boolean isActive=false;
	
	private final int activationTime=20*30;
	private int activationCountdown;
	
	
	private final double activationMS=1f;
	private final double moveSpeed=2f;
	private int pinkIdleTime=7;
	private int pinkChaseTime=20;
	
	public GhostAIPink(double x, double y, Rectangle shape) {
		this.x=x;
		this.y=y;
		this.shape=shape;
		
		activationCountdown=activationTime;
		initialY=y;
		mode=ghostMode.CHASE;
	}
	
	@Override
	public void move() {
		if(isActive){
			if(isActivating){
				if(y<=initialY-60){
					isActivating=false;
				}else{
					y-=activationMS;
				}
			}else{
				distanceSinceCheck+=moveSpeed;
				if(distanceSinceCheck>30f)
					System.out.println("error in speed check sync for PINK, disntance snince check: "+  distanceSinceCheck);
				if(distanceSinceCheck>=30f){
					distanceSinceCheck=0;
					check();
				}
				
				switch(direction){
				case RIGHT:
						x+=moveSpeed;
					break;
				case LEFT:
						x-=moveSpeed;
					break;
				case DOWN: 
						y+=moveSpeed;
					break;
				case UP: 
						y-=moveSpeed;
				}
				
			}
		}else{
			if(activationCountdown<=0){
				isActive=true;
			}
			activationCountdown--;
		}
		shape.setX(x);
		shape.setY(y);
		
	}
	
	
	void check() {
		gameTime++;
		if(gameTime-lastModeSwtichTime==pinkIdleTime && mode==ghostMode.IDLE){
			lastModeSwtichTime=gameTime;
			mode=ghostMode.CHASE;
			direction=GPM.oppositeDirectionOf(direction);
			
		}else if(gameTime-lastModeSwtichTime==pinkChaseTime && mode==ghostMode.CHASE){
			lastModeSwtichTime=gameTime;
			mode=ghostMode.IDLE;
			direction=GPM.oppositeDirectionOf(direction);
			
		}
		
		checkMode();
		
		int numOfWallsAdjacent=GPM.countAdjacentWalls(GPM.toGridCoord(x, 'x'), 
				GPM.toGridCoord(y, 'y'), Exe.map.wallGrid);
		
		if(numOfWallsAdjacent>=3){
			direction=GPM.oppositeDirectionOf(direction);
		}else{
			direction=pickWayToTarget(numOfWallsAdjacent);
		}
		
	}

	@Override
	void checkMode() {
		switch(mode){
		case CHASE:
			calculateTarget();
			break;
		case IDLE:
			targetX=0;
			targetY=GPM.mapSize*30+50;;
			break;
		case SPOOKED:
			//TODO
			break;
		}
	}

	private void calculateTarget(){
		targetX=Exe.player.x;
		targetY=Exe.player.y;
		
		switch(Player.direction){
		case UP:
			targetY-=4*30;
			break;
		case DOWN:
			targetY+=4*30;
			break;
		case LEFT:
			targetX-=4*30;
			break;
		case RIGHT:
			targetX+=4*30;
			break;			
		}
	}

}
