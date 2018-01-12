package mapStuff;

import java.util.Random;

import gameExe.GPM;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Map {
	public boolean wallGrid[][];
	
	public Circle foodGrid[][];
	public int foodRemaining;
	
	int nOfRandomWalls=30;
	
	public Map(){
		wallGrid=new boolean[GPM.mapSize][GPM.mapSize];
		for(int i=0;i<GPM.mapSize;i++){
			wallGrid[0][i]=true;
			wallGrid[18][i]=true;
			wallGrid[i][0]=true;
			wallGrid[i][18]=true;
		}		
		generateMap();
					
	}
	
	private void generateMap(){
		for(int i=0; i<nOfRandomWalls;i++)
			tossInRandom();
		for(int i=1;i<GPM.mapSize;i++){
			for(int j=1;j<GPM.mapSize;j++){
				if(!isInPresetGrid(i,j) && ((i%2==0) || (j%2==0))){
					if(GPM.checkGridPattern(i,j,301011010,wallGrid))
						wallGrid[i][j]=true;
					for(int x=0;x<3;x++){
						if(GPM.checkGridPattern(i,j,301022010,wallGrid))
							wallGrid[i][j]=true;
						if(GPM.checkGridPattern(i,j,302011020,wallGrid))
							wallGrid[i][j]=true;
					}
					if(GPM.countAdjacentWalls(i, j, wallGrid)<1 ){
						wallGrid[i][j]=true;
					}
				}
			}
		}
		//Second pass
		for(int i=1;i<GPM.mapSize;i++){
			for(int j=1;j<GPM.mapSize;j++){
				if(!isInPresetGrid(i,j)){
					if(GPM.checkGridPattern(i,j,300000000,wallGrid))
						if(GPM.rollOdds(50)){
							wallGrid[i][j-1]=true;
						}
					if(GPM.checkGridPattern(i,j,300000000,wallGrid))
						if(GPM.rollOdds(50)){
							wallGrid[i+1][j]=true;
						}
					if(GPM.checkGridPattern(i,j,300000000,wallGrid))
							wallGrid[i][j+1]=true;
					if(GPM.checkGridPattern(i,j,301012020,wallGrid))
						wallGrid[i][j]=true;
					if(GPM.checkGridPattern(i,j,301021020,wallGrid))
						wallGrid[i][j]=true;
				}
			}
		}
		generateFood();
		
	}
	
	private void tossInRandom(){
		int i,j;
		Random r=new Random();
		i=r.nextInt(17)+1;
		j=r.nextInt(17)+1;
		i-=i%2;
		j-=j%2;
		if(!isInPresetGrid(i, j))
			wallGrid[i][j]=true;
	}
	
	private boolean isInPresetGrid(int i, int j){
		if(i==1 && j==1)
			return true;
		if((i==7 && j==8) || (i==8 && j==8) || (i==9 && j==8) 
			|| (i==10 && j==8) 	|| (i==11 && j==8) 
			|| (i==12 && j==8)){
			wallGrid[i][j]=true;
			return true;
		}
		if((i==7 && j==9) || (i==12 && j==9)){
				wallGrid[i][j]=true;
				return true;
		}
		if((i==7 && j==10) || (i==8 && j==10) || (i==9 && j==10) 
			|| (i==10 && j==10)	|| (i==11 && j==10) 
			|| (i==12 && j==10)){
			wallGrid[i][j]=true;
			return true;
		}
		if((i==8 && j==9) || (i==9 && j==9) || (i==10 && j==9) || (i==11 && j==9) ){
				return true;
		}
		
		
		return false;
	}
	
	private void generateFood(){
		foodGrid=new Circle[GPM.mapSize][GPM.mapSize];
		foodRemaining=0;
	}

	
	public Group addMapPosition(int i,int j){
		Group mg=new Group();
		
		try {
			if(wallGrid[i][j]==true){
				Region tmp=new Region();
				//150+30*i,50+30*j,30,30
				tmp.setLayoutX(150+30*i);
				tmp.setLayoutY(50+30*j);
				tmp.setPrefSize(30, 30);
				int corner[]=new int[4];
				corner=checkCorners(i,j);				
				tmp.setStyle("-fx-background-color: gray; "
						+ "-fx-background-radius: "+corner[0]+" "+corner[1]+" "+corner[2]+" "+corner[3]+" ");
				mg.getChildren().add(tmp);
			}else if(!isInPresetGrid(i, j)){
				Circle curr;
				curr=new Circle(i*30+150+15,j*30+50+15,5);
				curr.setFill(Color.YELLOW);
				foodGrid[i][j]=curr;
				foodRemaining++;
				mg.getChildren().add(curr);
			}
		} catch (Exception e) {
			System.out.println("addMapPosition error! trying to add "+
		i+" "+j+" to grid");
		}
		return mg;
	}
	
	private int[] checkCorners(int i, int j){
		int corner[]=new int[4];
		if(GPM.checkGridPattern(i, j, 320202222, wallGrid)){
			corner[0]=8;
		}else{
			corner[0]=0;
		}
		if(GPM.checkGridPattern(i, j, 320220222, wallGrid)){
			corner[1]=8;
		}else{
			corner[1]=0;
		}
		if(GPM.checkGridPattern(i, j, 322220202, wallGrid)){
			corner[2]=8;
		}else{
			corner[2]=0;
		}
		if(GPM.checkGridPattern(i, j, 322202202, wallGrid)){
			corner[3]=8;
		}else{
			corner[3]=0;
		}

		return corner;
	}
	
	public String toString(){
		String s=new String("");
		for(int i=0;i<GPM.mapSize;i++){
			for(int j=0;j<GPM.mapSize;j++){
				if(wallGrid[j][i])
					s+="X";
				else
					s+=" ";
			}
			s+="\n";
		}		
		return s;
	}

}











