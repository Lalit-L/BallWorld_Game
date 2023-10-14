import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

public class PowerUp extends Actor{

	public PowerUp() {
		String path = getClass().getClassLoader().getResource("resources/ball.png").toString();
        
        DropShadow shadow = new DropShadow();
        this.setEffect(shadow);
        
        setImage(new Image(path));
	}
	
	@Override
	public void act(long now) {
		
	}
}
