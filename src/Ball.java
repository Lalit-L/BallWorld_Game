import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.net.URISyntaxException;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Ball extends Actor{
    private double dx, dy;
    private boolean hit;
    AnimationTimer t;
    Boolean rotate;
    private int power;

    public Ball(){
        this(5, 5);
        rotate = new Boolean(false);
        power = 1;
    }

    public Ball(double dx, double dy){
        String path = getClass().getClassLoader().getResource("resources/ball.png").toString();
        setImage(new Image(path));
        this.dx = dx;
        this.dy = dy;
        rotate = new Boolean(false);
        power = 1;
    }

    public int getPower() {
    	return power;
    }
    
    public void setPower(int p) {
    	power = p;
    }
    
    public Boolean rotate() {
    	return rotate;
    }
    
    public void setRotate() {
    	rotate = false;
    }
    
    @Override
    public void act(long now) {
        move(dx, dy);
        //Checking world boundaries
        if(getX() < 0){
            setX(0);
            dx = -dx;
        }else if(getX() + getWidth() > getWorld().getWidth()){
            setX(getWorld().getWidth() - getWidth());
            dx = -dx;
        }
        if(getY() < 0){
            setY(0);
            dy = -dy;
            //Setting score for hitting bottom
            /*BallWorld b = (BallWorld)getWorld();
            Score s = b.getScore();
            int curScore = s.getScore();
            s.setScore(curScore - 1000);*/
            
        }else if(getY() + getHeight() > getWorld().getHeight()){
            setY(getWorld().getHeight() - getHeight());
            dy = -dy;
            	
            BallWorld b = (BallWorld)getWorld();
            Score s = b.getScore();
            int curScore = s.getScore();
            s.setScore(curScore - 700);
            
        }
        //Paddle bouncing
        Paddle p = getOneIntersectingObject(Paddle.class);
        if(p == null) {
        	hit = false;
        }
        if(!hit && p != null) {
        	if(p != null && p.getDirection() == 0) {
            	dy = -dy;
            }
            else if(p != null && (getX() >= p.getX() + p.getWidth() / 3 && getX() <= p.getX() + (2 * p.getWidth()) / 3)) {
            	dy = -dy;
            }
            else if(p != null && getX() <= (p.getX() + p.getWidth() / 3) && p.getDirection() == -1) {
            	dx = -1 * Math.abs(dx);
            	dy = -1 * Math.abs(dy);
            }
            else if(p != null && getX() >= (p.getX() + (2 * p.getWidth() / 3)) && p.getDirection() == 1) {
            	dx = Math.abs(dx);
            	dy = -1 * Math.abs(dy);
            }
            else if(p != null && getX() == p.getX()) {
            	dy = -0.5 * dy;
            }
            else if(p != null && getX() == p.getX() + p.getWidth()) {
            	dy = -0.5 * dy;
            }
            else if(p != null){
            	dy = -dy;
            }
        	hit = true;
        }
        
        PowerUp pu = getOneIntersectingObject(PowerUp.class);
        if(pu != null){
        	Media longMusic;
			try {
				longMusic = new Media(getClass().getClassLoader().getResource("resources/Coin01.mp3").toURI().toString());
				MediaPlayer musicPlayer =  new MediaPlayer(longMusic);
	    		musicPlayer.setCycleCount(1);
	    		musicPlayer.play();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            getWorld().remove(pu);
            power = 2;
            new java.util.Timer().schedule(
            		new java.util.TimerTask() {
						@Override
						public void run() {
							power = 1;
						}
            		},
            		5000
            );
        }
        
        WorldRotate wr = getOneIntersectingObject(WorldRotate.class);
        if(wr != null){
        	Media longMusic;
			try {
				longMusic = new Media(getClass().getClassLoader().getResource("resources/Coin01.mp3").toURI().toString());
				MediaPlayer musicPlayer =  new MediaPlayer(longMusic);
	    		musicPlayer.setCycleCount(1);
	    		musicPlayer.play();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            getWorld().remove(wr);
            rotate = true;
        }
        
        
        SpeedUp sp = getOneIntersectingObject(SpeedUp.class);
        if(sp != null){
        	
        	Media longMusic;
			try {
				longMusic = new Media(getClass().getClassLoader().getResource("resources/Coin01.mp3").toURI().toString());
				MediaPlayer musicPlayer =  new MediaPlayer(longMusic);
	    		musicPlayer.setCycleCount(1);
	    		musicPlayer.play();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	double placeDx = dx;
        	double placeDy = dy;
        	dx = dx * 1.5;
            dy = dy * 1.5;
            getWorld().remove(sp);
            new java.util.Timer().schedule(
            		new java.util.TimerTask() {
						@Override
						public void run() {
							dx = placeDx;
							dy = placeDy;
						}
            		},
            		3000
            );
        }
        //if(p != null) dy = -dy;
        //Brick bouncing
        Brick b = getOneIntersectingObject(Brick.class);
        if(b != null){
            if(getX() >= b.getX() && getX() <= b.getX() + b.getWidth()){
                dy = -dy;
            }else if(getY() >= b.getY() && getY() <= b.getY() + b.getHeight()){
                dx = -dx;
            }else{
                dy = -dy;
                dx = -dx;
            }
            getWorld().remove(b);
            
            
            
            BallWorld bworld = (BallWorld)getWorld();
            Score s = bworld.getScore();
            int curScore = s.getScore();
            s.setScore(curScore + 100);
        }
        StrongBrick br = getOneIntersectingObject(StrongBrick.class);
        if(br != null){
            if(getX() >= br.getX() && getX() <= br.getX() + br.getWidth()){
                dy = -dy;
            }else if(getY() >= br.getY() && getY() <= br.getY() + br.getHeight()){
                dx = -dx;
            }else{
                dy = -dy;
                dx = -dx;
            }
            br.subHealth(power);
            if (br.getHealth() < 0) {
            	getWorld().remove(br);
	            BallWorld bworld = (BallWorld)getWorld();
	            Score s = bworld.getScore();
	            int curScore = s.getScore();
	            s.setScore(curScore + 150);
            }
        }
    }
}