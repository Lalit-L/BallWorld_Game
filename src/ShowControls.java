import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class ShowControls extends BorderPane{
	public ShowControls() {
		BackgroundImage myBI= new BackgroundImage(new Image("resources/black.JPG",500,500,false,true),
		BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
				
		Text controls = new Text();
		controls.setText("The controls are as follows: \n\nUse the Arrow Keys or the Mouse Cursor"
				+ " to move \nthe paddle.\nHitting the power ball enables you to \nincrease damage"
				+ " by a factor of 2\n\n"
				+ "Do be warned: \nThe Yellow bricks require multiple hits to be\ndestroyed\n"
				+ "The yellow ball increases the ball's speed\n"
				+ "Warning - hitting too many speed balls leads to\n"
				+ "hyperdrive (constant increased speed)\n"
				+ "There is no ability to save in the final level\n"
				+ "There appears to be a purple ball, and we aren't \nquite sure as to what it does");
		controls.setFont(Font.font("Helvetica", FontPosture.ITALIC, 22));
		this.setCenter(controls);
		controls.setFill(Color.WHITE);
	}
}
