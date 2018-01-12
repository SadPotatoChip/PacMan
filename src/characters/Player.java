package characters;

import gameExe.Exe;
import gameExe.GPM;
import gameExe.GPM.moveDir;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {
	public double x,y;
	public Circle shape;
	public Circle eye;	//obsolete
	
	
	double hitboxTLx,hitboxTLy,hitboxBRx,hitboxBRy;
	
	private float collisionBuffer=13f;
	private float hitboxleevay=8f;
	
	private float moveSpeed=2f;
	static public moveDir direction;
	private moveDir previousFrameDirection;
	private boolean isMoving;
	
	public boolean isTurningInCurrentFrame=false;
	
	
	public Player(double x, double y) {		
		this.x=x;
		this.y=y;
		shape=new Circle(x,y,15f);
		shape.setFill(Color.YELLOW);
		
		setHitbox();
		
		isMoving=true;
		direction=moveDir.DOWN;
		previousFrameDirection=direction;
	}
	
	public void playerMove(){
		if(checkHitboxCollision()){
			if(isTurningInCurrentFrame && previousFrameDirection!=direction &&
					 previousFrameDirection!=GPM.oppositeDirectionOf(direction) && isMoving){
				
				x=GPM.jumpToGrid(x, 'x');
				y=GPM.jumpToGrid(y, 'y');
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
			isMoving=true;
		}else{
			isMoving=false;
		}
		shape.setCenterX(x);
		shape.setCenterY(y);
		setHitbox();
		isTurningInCurrentFrame=false;
		previousFrameDirection=direction;
	}
	
	/***
	 * Checks if if the player hitbox collides with walls in front of it (based of current move direction)
	 * @return true if the player can move.
	 */
	
	private boolean checkHitboxCollision(){		
		//System.out.println(hitboxTLx+" "+hitboxTLy+" "+hitboxBRx+" "+hitboxBRy);
		switch(direction){
		case RIGHT:
			if((Exe.map.wallGrid[GPM.toGridCoord(x-collisionBuffer-2, 'x')+1]
					[GPM.toGridCoord(hitboxTLy, 'y')]) || 
					(Exe.map.wallGrid[GPM.toGridCoord(x-collisionBuffer-2, 'x')+1]
							[GPM.toGridCoord(hitboxBRy, 'y')]))
				return false;
			break;
		case LEFT:
			if((Exe.map.wallGrid[GPM.toGridCoord(x-collisionBuffer-3, 'x')]
					[GPM.toGridCoord(hitboxTLy, 'y')]) || 
					(Exe.map.wallGrid[GPM.toGridCoord(x-collisionBuffer-3, 'x')]
							[GPM.toGridCoord(hitboxBRy, 'y')]))
				return false;
			break;
		case DOWN: 
			if((Exe.map.wallGrid[GPM.toGridCoord(hitboxTLx,'x')]
					[GPM.toGridCoord(y-collisionBuffer-1, 'y')+1])|| 
					(Exe.map.wallGrid[GPM.toGridCoord(hitboxBRx,'x')]
							[GPM.toGridCoord(y-collisionBuffer-1, 'y')+1]))
				return false;
			break;
		case UP: 
			if((Exe.map.wallGrid[GPM.toGridCoord(hitboxTLx,'x')]
					[GPM.toGridCoord(y-collisionBuffer-3, 'y')])|| 
					(Exe.map.wallGrid[GPM.toGridCoord(hitboxBRx,'x')]
							[GPM.toGridCoord(y-collisionBuffer-3, 'y')]))
				return false;
			break;
		}
		return true;
	}

	private void setHitbox(){
		hitboxTLx=x-hitboxleevay;
		hitboxTLy=y-hitboxleevay;
		hitboxBRx=x+hitboxleevay;
		hitboxBRy=y+hitboxleevay;
	}
	
}
