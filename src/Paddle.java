import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor{
    private double dx;
    private int direction;

    public Paddle(){
        this(5);
    }

    public Paddle(double dx){
        String path = getClass().getClassLoader().getResource("resources/paddle.png").toString();
        setImage(new Image(path));
        this.dx = dx;
    }

    @Override
    public void act(long now) {
        if(getWorld().isKeyDown(KeyCode.LEFT)){
        	direction = -1;
            setX(getX() - dx);
        }else if(getWorld().isKeyDown(KeyCode.RIGHT)){
        	direction = 1;
            setX(getX() + dx);
        }else {
        	direction = 0;
        }
    }
    
    public int getDirection() {
    	return direction;
    }
    
    public void setDirection(int x) {
    	direction = x;
    }

    public double dx(){
        return dx;
    }
}
