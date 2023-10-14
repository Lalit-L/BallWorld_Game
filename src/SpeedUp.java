import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

public class SpeedUp extends Actor{

	public SpeedUp() {
		String path = getClass().getClassLoader().getResource("resources/SpeedUp.png").toString();
        
        DropShadow shadow = new DropShadow();
        this.setEffect(shadow);
        
        setImage(new Image(path));
	}
	
	@Override
	public void act(long now) {
		
	}

}
