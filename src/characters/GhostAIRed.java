package characters;

import gameExe.Exe;
import gameExe.GPM;
import javafx.scene.shape.Rectangle;

public class GhostAIRed extends GhostAI {
	private double initialY;
	
	private double distanceSinceCheck=0;
	
	Rectangle shape;
	
	private boolean isActivating=true; 
	private boolean isActive=false;
	
	private final double activationMS=1f;
	private final double moveSpeed=2f;
	public static final int redIdleTime=7;
	public static final int redChaseTime=20;
	
	public GhostAIRed(double x, double y, Rectangle shape) {
		this.x=x;
		this.y=y;
		this.shape=shape;
		
		initialY=y;
		mode=ghostMode.IDLE;
	}
	
	public void move() {
		if(isActivating){
			if(y<=initialY-60){
				isActivating=false;
				isActive=true;
			}else{
				y-=activationMS;
			}
		}else if(isActive){
			distanceSinceCheck+=moveSpeed;
			if(distanceSinceCheck>30f)
				System.out.println("error in speed check sync for RED, disntance snince check: "+  distanceSinceCheck);
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
		
		shape.setX(x);
		shape.setY(y);
	}

	void check(){
		gameTime++;
		if(gameTime-lastModeSwtichTime==redIdleTime && mode==ghostMode.IDLE){
			lastModeSwtichTime=gameTime;
			mode=ghostMode.CHASE;
			direction=GPM.oppositeDirectionOf(direction);
			System.out.println("Mode switch to CHASE");
		}else if(gameTime-lastModeSwtichTime==redChaseTime && mode==ghostMode.CHASE){
			lastModeSwtichTime=gameTime;
			mode=ghostMode.IDLE;
			direction=GPM.oppositeDirectionOf(direction);
			System.out.println("Mode switch to IDLE");
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
	
	void checkMode(){
		switch(mode){
		case CHASE:
			targetX=Exe.player.x;
			targetY=Exe.player.y;
			break;
		case IDLE:
			targetX=GPM.mapSize*30+150;
			targetY=0;
			break;
		case SPOOKED:
			//TODO
			break;
	}
}
	
	
	
}


















