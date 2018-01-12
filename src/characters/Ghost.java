package characters;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ghost {
	public double x,y;
	public Rectangle shape;
	public gColor ghostColor;
	public GhostAI ai;
	
	private final float ghostShapeXBuffer=5f;
	private final float ghostShapeYBuffer=2.5f;
	
	
	public enum gColor{
		RED,PINK,ORANGE,CYAN;
	}
	
	public Ghost(gColor ghostColor){
		this.ghostColor=ghostColor;
		shape=new Rectangle(20,25);
		switch(ghostColor){
		case RED:
			x=8*30+150+ghostShapeXBuffer;
			y=9*30+50+ghostShapeYBuffer;
			shape.setFill(Color.RED);
			ai=new GhostAIRed(x,y,shape);
			break;
		case PINK:
			x=9*30+150+ghostShapeXBuffer;
			y=9*30+50+ghostShapeYBuffer;
			shape.setFill(Color.PINK);
			ai=new GhostAIPink(x,y,shape);
			break;
		case ORANGE:
			x=10*30+150+ghostShapeXBuffer;
			y=9*30+50+ghostShapeYBuffer;
			shape.setFill(Color.ORANGE);
			//ai=new GhostAIRed(x,y,shape);
			break;
		case CYAN:
			x=11*30+150+ghostShapeXBuffer;
			y=9*30+50+ghostShapeYBuffer;
			shape.setFill(Color.CYAN);
			//ai=new GhostAIRed(x,y,shape);
			
		}
		shape.setX(x);
		shape.setY(y);

	}
	
	public Group getGhostGraphic(){
		Group tmp=new Group();
		tmp.getChildren().add(shape);
		return tmp;
	}
	
	
	
	
	
	
	
}
