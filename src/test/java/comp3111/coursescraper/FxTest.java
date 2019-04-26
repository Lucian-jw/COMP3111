/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}

	
	@Test

	public void testButton() {
		clickOn("#tabSfq");
		clickOn("#buttonInstructorSfq");
		Button b = (Button)s.lookup("#buttonInstructorSfq");
		sleep(1000);
		assertTrue(b.isDisabled());
	}
	
	@Test
	public void testselectall(){
		clickOn("#tabFilter");
		Button a=(Button)s.lookup("#SelectAll");
		sleep(1000);
		clickOn(a);
		assertEquals(a.getText(),"De-select All");	
		clickOn(a);
		assertEquals(a.getText(),"Select All");	
	}
	
	@Test
	public void testSelect(){
		clickOn("#buttonSearch");
		clickOn("#tabFilter");
		sleep(1000);
		clickOn("#SelectAll");
		sleep(1000);
		TextArea a=(TextArea)s.lookup("#textAreaConsole");
		assertTrue((a.getText()==null));
		clickOn("#SelectAll");
		sleep(1000);
		clickOn("#WithLabsorTutorial");
		clickOn("#WithLabsorTutorial");
		clickOn("#CommonCore");
		clickOn("#CommonCore");
		clickOn("#NoExclusion");
		clickOn("#NoExclusion");
	}
}
