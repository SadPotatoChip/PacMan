package gameExe;

import java.util.Random;


/*** 
 * Contains general purpose methods
 ***/
public final class GPM {
	
	public final static int mapSize=19;
	
	public enum moveDir{
		UP,DOWN,LEFT,RIGHT;		
	}
	
	private GPM(){
		
	}
	
	/**
	 * @param original coordinate real value
	 * @param xy coordinate type ('x' or 'y')
	 * @return value converted into a grid coordinate
	 */
	public static int toGridCoord(double original, char xy){
		int tmp=0;
		if(xy=='x'){
			tmp=(int)((original-150)/30);
		}
		if(xy=='y'){
			tmp=(int)((original-50)/30);
		}
		if(tmp<0){
			System.out.println(tmp+" "+xy+" "+original);
			tmp=0;
		}
		return tmp;
	}
	
	public static double jumpToGrid(double original, char xy){
		double tmp=0;
		if(xy=='x'){
			tmp=(toGridCoord(original, 'x')*30+150+15);
		}
		if(xy=='y'){
			tmp=(toGridCoord(original, 'y')*30+50+15);
		}
		if(tmp<0){
			System.out.println(tmp+" "+xy+" "+original);
			tmp=0;
		}
		return tmp;
	}

	public static moveDir oppositeDirectionOf(moveDir direction){
		switch(direction){
		case RIGHT:
				return moveDir.LEFT;
		case LEFT:
			return moveDir.RIGHT;
		case DOWN: 
			return moveDir.UP;
		case UP: 
			return moveDir.DOWN;
		}
		System.out.println("GPM.oppositeDirectionOf unexpected error");
		return moveDir.DOWN;
	}

	/**
	 * Method checks the 3x3 square, taking the (x,y) as its center, for the pattern passed in the parameter.
	 * The pattern is an integer composed of 8 binary digits, each representing a certain position relative to the center.
	 * if the digit is 1 then the spot in WallGrid corresponding to it must be equal true for the function to return true and the opposite for the digit 0
	 * The digit 2 means its the position is irrelevant.
	 * 
	 * Pattern: 1 2 3
	 *          4 x 5
 	 *          6 7 8
 	 * 	
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param pattern the pattern to check for
	 * @param wallGrid map to which it is being applied  
	 * 
	 * @return
	 */
	public static boolean checkGridPattern(int x,int y,int pattern,boolean[][] wallGrid){
		if(pattern>322222222 || pattern<300000000){
			System.out.println("checkGridPattern error: invalid pattern '"+pattern+"'");
			return false;
		}
		if(x>1 && x<18 && y>1 && y<18){
			int[] pa=new int[8];
			for(int i=7;i>=0;i--){
				pa[i]=pattern%10;
				if(pa[i]>2){
					System.out.println("checkGridPattern error: invalid pattern '"+pattern+"'");
				}
				pattern/=10;			
			}
			
			if(pa[0]==1 && wallGrid[x-1][y-1]==false)
				return false;
			if(pa[0]==0 && wallGrid[x-1][y-1]==true)
				return false;		
			if(pa[1]==1 && wallGrid[x][y-1]==false)
				return false;
			if(pa[1]==0 && wallGrid[x][y-1]==true)
				return false;		
			if(pa[2]==1 && wallGrid[x+1][y-1]==false)
				return false;
			if(pa[2]==0 && wallGrid[x+1][y-1]==true)
				return false;
			
			if(pa[3]==1 && wallGrid[x-1][y]==false)
				return false;
			if(pa[3]==0 && wallGrid[x-1][y]==true)
				return false;
			if(pa[4]==1 && wallGrid[x+1][y]==false)
				return false;
			if(pa[4]==0 && wallGrid[x+1][y]==true)
				return false;
			
			if(pa[5]==1 && wallGrid[x-1][y+1]==false)
				return false;
			if(pa[5]==0 && wallGrid[x-1][y+1]==true)
				return false;		
			if(pa[6]==1 && wallGrid[x][y+1]==false)
				return false;
			if(pa[6]==0 && wallGrid[x][y+1]==true)
				return false;		
			if(pa[7]==1 && wallGrid[x+1][y+1]==false)
				return false;
			if(pa[7]==0 && wallGrid[x+1][y+1]==true)
				return false;		
		}else{
			return false;
		}
		
		return true;
	}
	
	public static int countAdjacentWalls(int i, int j, boolean[][] wallGrid){
		int n=0;
		try {
			if(i>1 && i<20 && j>1 && j<20){
				if(wallGrid[i][j-1])
					n++;
				if(wallGrid[i-1][j])
					n++;
				if(wallGrid[i+1][j])
					n++;
				if(wallGrid[i][j+1])
					n++;
			}else{
				return 99;
			}
		} catch (Exception e) {

		}
		return n;
	}

	public static boolean rollOdds(int chance){
		Random r=new Random();
		if(r.nextInt(100)<chance)
			return true;
		return false;
	}
}


















