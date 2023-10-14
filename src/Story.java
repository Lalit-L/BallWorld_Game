import javafx.scene.effect.DropShadow;
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

public class Story extends BorderPane{
	public Story() {
		BackgroundImage myBI= new BackgroundImage(new Image("resources/black.JPG",500,500,false,true),
		BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
		
		Text story = new Text();
		DropShadow shadow = new DropShadow();
        story.setEffect(shadow);
		story.setText("It's the year 2XXX, and the Human Civilization has conquered the \nMilky"
				+ "Way Galaxy. Humans have brought forth prosperity to all \nlife-bearing"
				+ "worlds, while also bringing new life to those barren of it.\n"
				+ "But, peace could not be maintained forever. The Brickstians, an\n"
				+ "Alien race from the Andromeda Galaxy, arrived at the Milky Way.\n"
				+ "They saw the way the humans protected the galaxy as foolish, and\n"
				+ "deicded to take it for themselves. You, a captain of the 24th\n"
				+ "cavalry, is one of the last standing fighter on the front lines\n."
				+ "In order to protect the last of the evacuating people of the\n"
				+ "planet Earth, you must fight off the Brickstian Spacecrafts\n\n"
				+ "Good luck, Soldier \nGodspeed");
		story.setFont(Font.font("Helvetica", FontPosture.ITALIC, 17));
		story.setFill(Color.WHITE);
		this.setCenter(story);
	}
}
