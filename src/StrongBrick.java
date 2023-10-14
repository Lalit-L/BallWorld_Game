import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

public class StrongBrick extends Actor{
	int health = 1;
	int x;
	int y;
	
	/*public StrongBrick() {
		String path = getClass().getClassLoader().getResource("resources/brick2.png").toString();
        
        DropShadow shadow = new DropShadow();
        this.setEffect(shadow);
        
        setImage(new Image(path));
	}*/
	
	public StrongBrick(int xc, int yc) {
		String path = getClass().getClassLoader().getResource("resources/brick2.png").toString();
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
	
	public void setHealth(int h) {
		health = h;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void subHealth(int i) {
		health = health-i;
	}

}
