import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

public class Brick extends Actor{

	int x;
	int y;
    /*public Brick(){
        String path = getClass().getClassLoader().getResource("resources/brick.png").toString();
        
        DropShadow shadow = new DropShadow();
        this.setEffect(shadow);
        
        setImage(new Image(path));
    }*/
    
    public Brick(int xc, int yc){
        String path = getClass().getClassLoader().getResource("resources/brick.png").toString();
        x = xc;
        y = yc;
        setImage(new Image(path));
    }

    public int returnX() {
    	return x;
    }
    
    public int returnY() {
    	return y;
    }
    
    @Override
    public void act(long now) {

    }
}