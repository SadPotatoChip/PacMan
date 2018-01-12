package characters;

import gameExe.Exe;
import gameExe.GPM;
import gameExe.GPM.moveDir;

public abstract class GhostAI {
	double x,y;
	double targetX,targetY;
	moveDir direction=moveDir.RIGHT;
	
	ghostMode mode;
	
	static int gameTime=1;
	static int lastModeSwtichTime=0;
	
	
	public enum ghostMode{
		IDLE,CHASE,SPOOKED;
	}
	
	public abstract void move();
	
	moveDir pickWayToTarget(int numOfWallsAdjacent){
		double currMinDistance=2000f;
		moveDir currMinDistanceDirection=moveDir.UP;
		if(GPM.oppositeDirectionOf(direction)!=moveDir.RIGHT &&
				distanceToTargetFrom(x+30,y)<currMinDistance &&
				!Exe.map.wallGrid[GPM.toGridCoord(x+30, 'x')][GPM.toGridCoord(y, 'y')]){
			currMinDistance=distanceToTargetFrom(x+30,y);
			currMinDistanceDirection=moveDir.RIGHT;
		}
		if(GPM.oppositeDirectionOf(direction)!=moveDir.DOWN &&
				distanceToTargetFrom(x,y+30)<currMinDistance &&
				!Exe.map.wallGrid[GPM.toGridCoord(x, 'x')][GPM.toGridCoord(y+30, 'y')]){
			currMinDistance=distanceToTargetFrom(x,y+30);
			currMinDistanceDirection=moveDir.DOWN;
		}
		if(GPM.oppositeDirectionOf(direction)!=moveDir.LEFT &&
				distanceToTargetFrom(x-30,y)<currMinDistance &&
				!Exe.map.wallGrid[GPM.toGridCoord(x-30, 'x')][GPM.toGridCoord(y, 'y')]){
			currMinDistance=distanceToTargetFrom(x-30,y);
			currMinDistanceDirection=moveDir.LEFT;
		}
		if(GPM.oppositeDirectionOf(direction)!=moveDir.UP &&
				distanceToTargetFrom(x,y-30)<currMinDistance &&
				!Exe.map.wallGrid[GPM.toGridCoord(x, 'x')][GPM.toGridCoord(y-30, 'y')]){
			currMinDistance=distanceToTargetFrom(x,y-30);
			currMinDistanceDirection=moveDir.UP;
		}
		return currMinDistanceDirection;
	}
	
	
	double distanceToTargetFrom(double x, double y){
		return Math.sqrt(Math.pow(x-targetX, 2) + Math.pow(y-targetY, 2));
	}
	
	abstract void check();
	
	abstract void checkMode();

	
}
