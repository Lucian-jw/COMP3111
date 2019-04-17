package comp3111.coursescraper;


import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.Random;

import comp3111.coursescraper.Scraper.InstSFQScoreStruct;

import java.util.List;
public class Controller {

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    private Scraper scraper = new Scraper();
    
    private static List<String> subjects; // List to store subjects searched by first-time All Subject Search.
    
    private static List<List<Course>> allSubjects;
    
    private static List<Section> courses;
    
    @FXML
    void allSubjectSearch() {
    	if (subjects.isEmpty()) {
    		subjects = this.scraper.scrapeSubjects(this.textfieldURL.getText(), this.textfieldTerm.getText());
    		this.textAreaConsole.setText(this.textAreaConsole.getText() + "\n" + "Total Number of Categories/Code Prefix: " + subjects.size());
    	}
    	else {
    		for (String cur : subjects) {
    			List<Course> v = this.scraper.scrape(this.textfieldURL.getText(), this.textfieldTerm.getText(),this.textfieldSubject.getText());
    			allSubjects.add(v);
    			this.textAreaConsole.setText(this.textAreaConsole.getText() + "\n" + cur + " is done");
    			this.progressbar.setProgress((subjects.indexOf(cur) + 1) / subjects.size());
    		}
    	}
    }

    @FXML
    void findInstructorSfq() {
    	
    	List<InstSFQScoreStruct> out = scraper.scrapeInstSFQ(textfieldSfqUrl.getText());
    	
    	for (int i = 0; i < out.size() - 1; i++) {
    		List<String> curScore = out.get(i).score;
    		float total = 0;
    		for (int j = 0; j < curScore.size() - 1; j++) {
    			total += Float.parseFloat(curScore.get(j));
    		}
    		total = total / curScore.size();
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Instructor: " + out.get(i).name + "\n" + "SFQ Score: " + total + "\n" + "\n");
    	}
    	
    }

    @FXML
    void findSfqEnrollCourse() {
    	if (subjects.isEmpty())
    		this.buttonSfqEnrollCourse.setDisable(true);
    	else
    		this.buttonSfqEnrollCourse.setDisable(false);
    }

    @FXML
    void search() {
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	for (Course c : v) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			newline += "Slot " + i + ":" + t + "\n";
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    	
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;

    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    	
    	
    	
    }

}
