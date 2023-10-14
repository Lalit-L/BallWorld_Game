import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text {
	private int score;
	
	public Score() {
		score = 0;
		setFont(new Font(20));
		this.setFill(Color.WHITE);
		updateDisplay();
	}
	
	private void updateDisplay() {
		setText("" + score);
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score = s;
		updateDisplay();
	}
}
