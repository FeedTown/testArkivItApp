import javax.swing.JFrame;

public class FileChooserTestMain {

	  public static void main(String[] args) {
		    run(new FilerChooserTest(), 250, 110);
		  }

		  public static void run(JFrame frame, int width, int height) {
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(width, height);
		    frame.setVisible(true);
		  }

}
