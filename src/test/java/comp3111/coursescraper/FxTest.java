package comp3111.coursescraper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxTest extends ApplicationTest {

    private Scene s;

    @Override
    public void start(final Stage stage) throws Exception {
	final FXMLLoader loader = new FXMLLoader();
	loader.setLocation(getClass().getResource("/ui.fxml"));
	final VBox root = (VBox) loader.load();
	final Scene scene = new Scene(root);
	stage.setScene(scene);
	stage.setTitle("Course Scraper");
	stage.show();
	s = scene;
    }

    @Test
    public void testButton() {
	clickOn("#tabSfq");
	final Button b = (Button) s.lookup("#buttonSfqEnrollCourse");
	sleep(1000);
	assertTrue(b.isDisabled());
    }

    @Test
    public void testSelect() {
	clickOn("#buttonSearch");
	clickOn("#tabFilter");
	sleep(1000);
	clickOn("#SelectAll");
	sleep(1000);
	final TextArea a = (TextArea) s.lookup("#textAreaConsole");
	assertTrue(a.getText() == null);
	clickOn("#SelectAll");
	sleep(1000);
	clickOn("#WithLabsorTutorial");
	clickOn("#WithLabsorTutorial");
	clickOn("#CommonCore");
	clickOn("#CommonCore");
	clickOn("#NoExclusion");
	clickOn("#NoExclusion");
    }

    @Test
    public void testselectall() {
	clickOn("#tabFilter");
	final Button a = (Button) s.lookup("#SelectAll");
	sleep(1000);
	clickOn(a);
	assertEquals(a.getText(), "De-select All");
	clickOn(a);
	assertEquals(a.getText(), "Select All");
    }
}
