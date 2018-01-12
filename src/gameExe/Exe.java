package gameExe;


import java.util.Timer;
import java.util.TimerTask;

import characters.Ghost;
import characters.Player;
import gameExe.GPM.moveDir;
import characters.Ghost.gColor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mapStuff.Map;

public class Exe extends Application{
	public int tickSpeed=30;
	public int setupSpeed=10;
	
	Timer gameSetup;
	Timer gameLoop;
	
	Stage ps;
	
	Scene initialGameScene;
	Group allGraphics;
	Rectangle playgroundBackground;
	
	public static Map map;
	
	KeyboardListenerStuff kls;
	public static Player player;
	
	public Ghost[] ghost;
	
	public boolean gameSetupCompleted=false;
	private int mapGenI=0,mapGenY=0;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		ps=primaryStage;
		primaryStage=stageSetup(primaryStage);		
		gameSetup();		
	}
	
	Stage stageSetup(Stage primaryStage){	
		primaryStage.setMinWidth(900);
		primaryStage.setMinHeight(700);
		primaryStage.setResizable(false);	
		primaryStage.setScene(setupScene());
		primaryStage.show();
		return primaryStage;
	}
	
	Scene setupScene(){
		ghost= new Ghost[4];
		map=new Map();
		kls=new KeyboardListenerStuff();
		allGraphics=setupGraphics(allGraphics);
		initialGameScene= new Scene(allGraphics ,900,700);	
		kls.setupKeyboardListener(initialGameScene);
		return initialGameScene;
	}
	
	Group setupGraphics(Group allGraphics){
		allGraphics=new Group();
		
		Button resetGame=new Button("resetGame");
		resetGame.setOnAction(smth->{
			mapGenI=0;
			mapGenY=0;
			gameSetupCompleted=false;
			gameLoop.cancel();
			gameLoop.purge();
			ps.setScene(setupScene());
			gameSetup();
		});		
		
		playgroundBackground=new Rectangle(150,50,570,570);
		allGraphics.getChildren().addAll(playgroundBackground,resetGame);
		
		return allGraphics;
	}
	
	
	void gameSetup(){
		gameSetup=new Timer();
		gameSetup.schedule(new TimerTask() {
            public void run() {
            	if(!gameSetupCompleted){
            		addWallToGraphics();
            	}else{
            		player=new Player(180+15,80+15);
            		ghost[0]=new Ghost(gColor.RED);
            		ghost[1]=new Ghost(gColor.PINK);
            		ghost[2]=new Ghost(gColor.ORANGE);
            		ghost[3]=new Ghost(gColor.CYAN);            		
            		
            		Platform.runLater(new Runnable(){
            			public void run(){
                    		for(int i=0;i<ghost.length;i++){
                    			allGraphics.getChildren().add(ghost[i].getGhostGraphic());
                    		}
                    		allGraphics.getChildren().add(player.shape);
            				player.shape.toFront();
            			}
            		});
            		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            		gameLoop();
            		gameSetup.cancel();
            		gameSetup.purge();
            	}
            }
		}, 0, 10);
	}
	
	void gameLoop(){
		gameLoop=new Timer();
		gameLoop.schedule(new TimerTask() {
            public void run() {
	            if(checkInput())
	            	player.isTurningInCurrentFrame=true;
	            player.playerMove();
	            eat();
	            for(int i=0;i<4;i++){
	            	if(ghost[i].ai!=null)
	            		ghost[i].ai.move();
            	}
            }
		}, 0, tickSpeed);	           
	}
	
	boolean checkInput(){
		if(kls.isPressed('w')){
			Player.direction=moveDir.UP;
			return true;
		}
		if(kls.isPressed('a')){
			Player.direction=moveDir.LEFT;
			return true;
		}
		if(kls.isPressed('s')){
			Player.direction=moveDir.DOWN;
			return true;
		}
		if(kls.isPressed('d')){
			Player.direction=moveDir.RIGHT;
			return true;
		}
		return false;
	}
	
	private void eat(){
		Circle curr=map.foodGrid[GPM.toGridCoord(player.x, 'x')][GPM.toGridCoord(player.y, 'y')];
		if(curr!=null){
			if(curr.isVisible()){
				curr.setVisible(false);
				map.foodRemaining--;
				//System.out.println(--map.foodRemaining);
			}
		}
	}
	
	private void addWallToGraphics(){
		Platform.runLater(new Runnable(){
			public void run(){
				allGraphics.getChildren().add(map.addMapPosition(mapGenI,mapGenY));
				incrementMapGenIandY();
			}
		});
	}
	
	
	/**
	 * Obsolete
	 */
	private void incrementMapGenIandY(){
		mapGenY++;
		if(mapGenY>18){
			mapGenY=0;
			mapGenI++;
		}
		if(mapGenI>18){
			//tickSpeed=30;
			gameSetupCompleted=true;
		}
	}
	
}














