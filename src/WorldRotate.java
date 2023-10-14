import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

public class WorldRotate extends Actor{
	
	public WorldRotate() {
		String path = getClass().getClassLoader().getResource("resources/WorldRotate.png").toString();
        
        DropShadow shadow = new DropShadow();
        this.setEffect(shadow);
        
        setImage(new Image(path));
	}
	
	@Override
	public void act(long now) {
		
	}
}
