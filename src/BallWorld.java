import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * Date: 5/2/21
 * Period: 3rd Ferrante
 * Name: Lalit Lakamsani, Raeed Bourai, Gilford Ting (Team 12)
 * Time spent: ~ 1 hr
 *
 * This lab was very cool and epic. Everything mostly went smoothly; we ran into one issue where we added the Ball
 * object to the BorderPane instead of the World but after a little debugging with the handle() method in our
 * anonymous AnimationTimer class we figured it out and then got the ball to bounce around. Looking in the API was
 * annoying but we got the methods we needed after carefully reading through the documentation. We also added default
 * constants that we could use in our Game driver that allowed us to change values easily when we were testing our
 * final application.
 *
 * Overall, this lab was a lot of fun and we all enjoyed working with each other. Raeed was able to utilize his
 * expansive knowledge of derivatives in order to successfully take over Oracle HQ and become Java reincarnate.
 */

public class BallWorld extends World{

	String name;
	Score score;
	boolean nextLvl;
	public BallWorld() {
		name = "";
		nextLvl = false;
		score = new Score();
		score.setX(230);
		score.setY(0);
		getChildren().add(score);
		
		BackgroundImage myBI= new BackgroundImage(new Image("resources/Space.JPG",500,500,false,true),
    	BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    	BackgroundSize.DEFAULT);
    	this.setBackground(new Background(myBI));
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public Score getScore() {
		return score;
	}
	
	public boolean lvlChange() {
		return nextLvl;
	}
	
    @Override
    protected void act(long now) {
    	if (name == "world") { 
    		if (this.getBricks(Brick.class).size() == 0) {
    			nextLvl = true;
    		}
    	}
    	else if (name == "level2") {
    		if (this.getBricks(Brick.class).size() == 0 && this.getStrong(StrongBrick.class).size() == 0) {
    			nextLvl = true;
    		}
    	}
    	else {
    		if (this.getStrong(StrongBrick.class).size() == 0) {
    			nextLvl = true;
    		}
    	}
    }
}
