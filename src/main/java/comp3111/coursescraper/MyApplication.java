package comp3111.coursescraper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Entry point of the program.
 */
public class MyApplication extends Application {
    private static final String UI_FILE = "/ui.fxml";

    /**
     * Entry point of the program. No argument should be supplied
     *
     * @param args - not used.
     */
    public static void main(final String args[]) {
	Application.launch(args);
    }

    /**
     * Start the program.
     */
    @Override
    public void start(final Stage stage) throws Exception {
	final FXMLLoader loader = new FXMLLoader();
	loader.setLocation(getClass().getResource(MyApplication.UI_FILE));
	final VBox root = (VBox) loader.load();
	final Scene scene = new Scene(root);
	stage.setScene(scene);
	stage.setTitle("Course Scraper");
	stage.show();
    }

}
