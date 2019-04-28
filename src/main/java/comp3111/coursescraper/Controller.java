package comp3111.coursescraper;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import comp3111.coursescraper.Scraper.CourseSFQStruct;
import comp3111.coursescraper.Scraper.InstSFQScoreStruct;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@SuppressWarnings("restriction")
public class Controller {
    private static List<Course> scrapedCourse = new ArrayList<>();
    private static List<String> subjects = new ArrayList<>(); // List to store subjects searched by first-time All
    // Subject Search.
    private static List<Course> FilteredCourse = new ArrayList<>(); // remember to edit it after searching and
    // ALlsubjectSearching!
    private static List<Section> EnrolledSection = new ArrayList<>();

    private static void checkValidURL(final String url) throws FileNotFoundException {

	if (url.indexOf("w5.ab.ust.hk/wcq/cgi-bin") < 0)
	    throw new FileNotFoundException(url);

	if (!Controller.exists(url))
	    throw new FileNotFoundException(url);
    }

    private static boolean exists(final String URLName) {
	try {
	    HttpURLConnection.setFollowRedirects(false);
	    final HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
	    con.setRequestMethod("HEAD");
	    return con.getResponseCode() == HttpURLConnection.HTTP_OK;
	} catch (final Exception e) {
	    return false;
	}
    }

    public ObservableList<Section> data = FXCollections.observableArrayList();

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;
    
    @FXML
    private Label instructionText1;
    
    @FXML
    private Label instructionText2;
    
    @FXML
    private Label displayText1;
    
    @FXML
    private Label displayText2;
    
    @FXML
    private Label displayText3;
    
    @FXML
    private Label instructionText3;

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
    private CheckBox AM;

    @FXML
    private CheckBox PM;

    @FXML
    private CheckBox Mon;

    @FXML
    private CheckBox Tue;

    @FXML
    private CheckBox Wed;

    @FXML
    private CheckBox Thur;

    @FXML
    private CheckBox Fri;

    @FXML
    private CheckBox Sat;

    @FXML
    private Button SelectAll;

    @FXML
    private CheckBox CommonCore;

    @FXML
    private CheckBox NoExclusion;

    @FXML
    private CheckBox WithLabsorTutorial;

    @FXML
    private Tab tabList;

    @FXML
    private TableView<Section> ListTable;// change to section

    @FXML
    private TableColumn<Section, String> CourseCode;

    @FXML
    private TableColumn<Section, String> Section;

    @FXML
    private TableColumn<Section, String> CourseName;

    @FXML
    private TableColumn<Section, String> Instructor;

    @FXML
    private TableColumn<Section, CheckBox> Enroll;

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

    private final Scraper scraper = new Scraper();

    final Task<Void> allSSThread = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
		    for (int i = 0; i < Controller.subjects.size(); i++) {
			updateProgress(i + 1, Controller.subjects.size());
			final String cur = Controller.subjects.get(i);
			final List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), cur);
			Controller.scrapedCourse.addAll(v);
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + cur + " is done");
		    }
		    return null;
		}
    };

    @FXML
    void allSubjectSearch() {
	if (Controller.subjects.isEmpty()) {
	    Controller.subjects.addAll(scraper.scrapeSubjects(textfieldURL.getText(), textfieldTerm.getText()));
	    textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Total Number of Categories/Code Prefix: "
		    + Controller.subjects.size());
	} else {
	    Controller.scrapedCourse.clear();
	    progressbar.progressProperty().bind(allSSThread.progressProperty());
	    final Thread thread = new Thread(allSSThread, "AllSS-thread");
	    thread.setDaemon(true);
	    thread.start();
	}
    }

    @FXML
    /**
     * This function is will call the filter function once the Am checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void AM_Selection() {
	select(); 
    }

    @FXML
    /**
     * This function is will call the filter function once the CommonCore checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void CommonCore_Selection() {
	select();
    }

    private void displayToTimetable(final Section section) {
		// Generate color from the list.

    	Color c;
    	final Random random = new Random();
		  c = Color.rgb(54 + random.nextInt(202), 54 + random.nextInt(202), 54 + random.nextInt(202));
		  while (!checkApplicableColor(c)) {
			  c = Color.rgb(54 + random.nextInt(202), 54 + random.nextInt(202), 54 + random.nextInt(202));
		  }
		section.addColor(c);
		// Get the slot information of the section.
		for (int i = 0; i < section.getSlotSize(); i++) {
		    // Display the content to the timetable.
		    final AnchorPane ap = (AnchorPane) tabTimetable.getContent();
	
		    // Y on 9 AM: 49
		    // Y for one hour: 20
		    final LocalTime startTime = section.getSlot(i).getStart();
		    final LocalTime endTime = section.getSlot(i).getEnd();
		    final double startHour = startTime.getHour();
		    final double startMinute = startTime.getMinute();
		    final double endHour = endTime.getHour();
		    final double endMinute = endTime.getMinute();
		    final double timeStart = startHour + startMinute / 60;
		    final double timeEnd = endHour + endMinute / 60;
		    final double start = 49 + (timeStart - 9) * 20;
		    final double duration = (timeEnd - timeStart) * 20;
		    String content = section.getCourseCode() + "\n" + section.getSection();
		    if (timeEnd - timeStart <= 1.2)
			  content = section.getCourseCode() + "(" + section.getSection() + ")";

	
		    Label courseLabel = new Label(content);
		    courseLabel.setOpacity(0.5);
		    courseLabel.setFont(new Font("Ariel", 12));
		    courseLabel.setContentDisplay(ContentDisplay.TOP);
		    courseLabel.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
		    courseLabel.setLayoutX(section.getSlot(i).getDay() * 100 + 101);
		    courseLabel.setLayoutY(start);
		    courseLabel.setMinWidth(100.0);
		    courseLabel.setMaxWidth(100.0);
		    courseLabel.setMinHeight(duration);
		    courseLabel.setMaxHeight(duration);
		    section.addLabel(courseLabel);
		    ap.getChildren().addAll(courseLabel);
		}
    }
   
    private boolean checkApplicableColor(Color color) {
    	for (Section section: EnrolledSection) {
    		for (int i = 0; i < section.getNumColor(); i++) {
    			if (checkSimilarColor(color, section.getColor(i))) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    private boolean checkSimilarColor(Color c1, Color c2) {
    	double redDiffSquare = (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed());
    	double greenDiffSquare = (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen());
    	double blueDiffSquare = (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
    	return (java.lang.Math.sqrt(redDiffSquare + greenDiffSquare + blueDiffSquare) < 0.17);
    }
    
    private void removeFromTimetable(Section section) {
    	for(int i = 0; i < section.getNumLabels(); i++) {
    		final AnchorPane ap = (AnchorPane) tabTimetable.getContent();
    		ap.getChildren().remove(section.getLabel(i));
    	}
    }

    @FXML
    void findInstructorSfq() {
	final List<InstSFQScoreStruct> out = scraper.scrapeInstSFQ(textfieldSfqUrl.getText());
	for (int i = 0; i < out.size(); i++) {
	    final List<String> curScore = out.get(i).score;
	    float total = 0;
	    for (int j = 0; j < curScore.size(); j++)
		total += Float.parseFloat(curScore.get(j));
	    total = total / curScore.size();
	    textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Instructor: " + out.get(i).name + "\n"
		    + "SFQ Score: " + total + "\n");
	}

    }

    @FXML
    void findSfqEnrollCourse() {
	final List<CourseSFQStruct> out = scraper.scrapeCourseSFQ(textfieldSfqUrl.getText(), EnrolledSection);
	for (int i = 0; i < out.size(); i++) {
	    final List<String> curScore = out.get(i).score;
	    float total = 0;
	    for (int j = 0; j < curScore.size(); j++)
		total += Float.parseFloat(curScore.get(j));
	    total = total / curScore.size();
	    textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Course: " + out.get(i).courseCode + "\n"
		    + "SFQ Score: " + total + "\n");
	}
    }

    @FXML
    /**
     * This function is will call the filter function once the Fri checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void Fri_Selection() {
	select();

    }
    /**
     * This function implements the table column
     * It will use the scraped course information to fill up different columns with different information
     * @param no param needed
     * @return no value returned
     * @see the tablecolumn will be filled and checkbox will be generated
     * @author JIANG WEI
     */
    void List() {
		CourseCode.setCellValueFactory(cellData -> cellData.getValue().CourseCodeProperty());
		Section.setCellValueFactory(cellData -> cellData.getValue().SectionProperty());
		CourseName.setCellValueFactory(cellData -> cellData.getValue().CourseNameProperty());
		Instructor.setCellValueFactory(cellData -> cellData.getValue().InstructorProperty());
		Enroll.setCellValueFactory(arg0 -> {
		    final Section sec = arg0.getValue();
	
		    final CheckBox checkBox = new CheckBox();
	
		    checkBox.selectedProperty().setValue(sec.getEnrolledStatus());
		    checkBox.selectedProperty().addListener((ChangeListener<Boolean>) (ov, old_val, new_val) -> {
			sec.setEnrolledStatus(new_val);
			if (sec.getEnrolledStatus() == true && !Controller.EnrolledSection.contains(sec)) {
			    Controller.EnrolledSection.add(sec);
			    displayToTimetable(sec);
			}
			if (sec.getEnrolledStatus() == false && Controller.EnrolledSection.contains(sec)) {
			    Controller.EnrolledSection.remove(sec);
				removeFromTimetable(sec);
			}
			textAreaConsole.clear();
			String newline = "The following sections are enrolled:" + "\n";
			for (final Section s : Controller.EnrolledSection)
			    newline += s.getCourseCode() + " " + s.getSection() + " " + s.getCourseName() + " "
				    + s.getInstructor() + " \n";
			if (textAreaConsole.getText() == null)
			    textAreaConsole.setText('\n' + newline);// WTF? get Null WILL be "NULL"????
			else
			    textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
		    });
	
		    return new SimpleObjectProperty<>(checkBox);
		});
		if (Controller.FilteredCourse.isEmpty()) {
		    data.clear();
		    for (final Course c : Controller.scrapedCourse)
			data.addAll(c.sections);
		    ListTable.setItems(data);
		} else {
		    data.clear();
		    for (final Course c : Controller.FilteredCourse)
			data.addAll(c.sections);
		    ListTable.setItems(data);
		}
    }

    @FXML
    /**
     * This function is will call the filter function once the Monday checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void Mon_Action() {
	select();
    }

    @FXML
    /**
     * This function is will call the filter function once the NoExclusion parameters is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void NoExlusion_Selection() {
	select();
    }

    @FXML
    /**
     * This function is will call the filter function once the PM checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void PM_Selection() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    /**
     * This function is will call the filter function once the Sat checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void Sat_Selection() {
	select();// once you click the checkbox, it will select
    }
    
    private boolean checkInRange(Section s) {
    	for (int i = 0; i < s.getSlotSize(); i++) {
    		LocalTime start = s.getSlot(i).getStart();
    		LocalTime end = s.getSlot(i).getEnd();
    		double startMinute = start.getMinute();
    		double endMinute = end.getMinute();
    		double startTime = start.getHour() + (startMinute / 60);
    		double endTime = end.getHour() + (endMinute / 60);
    		final double threePlusTen = 15.1666666666666666666666666667;
    		if ((startTime < threePlusTen) && (endTime > threePlusTen) && s.getSlot(i).getDay() == 1) {
    			return true;
    		}
    	}
    	return false;
    }

    @FXML
    void search() {
		try {
		    checkValidURL(
			    textfieldURL.getText() + "/" + textfieldTerm.getText() + "/subject/" + textfieldSubject.getText());
		    final List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
			    textfieldSubject.getText());
		    
		    Integer numSection = 0;
		    Integer numCourse = 0;
		    ArrayList<String> instructors = new ArrayList<String>();
		    ArrayList<String> instructorsWithAssignment = new ArrayList<String>();
		    textAreaConsole.setText("");
		    for (final Course c : v) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSections(); i++) {
					if (!instructors.contains(c.getSection(i).getInstructor())) {
						instructors.add(c.getSection(i).getInstructor());
					}
					if (checkInRange(c.getSection(i))) {
						if (!instructorsWithAssignment.contains(c.getSection(i).getInstructor())) {
							instructorsWithAssignment.add(c.getSection(i).getInstructor());
						}
					}
					for (int j = 0; j < c.getSection(i).getSlotSize(); j++) {
						final Slot t = c.getSection(i).getSlot(j);
					    newline += c.getSection(i).getSection() + ": " + t + "\n";
					}
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
				numSection += c.getNumSections();
				numCourse++;
		    }
		    String addLine = "Total number of section(s): " + numSection.toString() + "\n\n";
		    addLine += ("Total number of course(s): " + numCourse.toString() + "\n\n");
		    addLine += ("Instrctuors who has teaching assignment this term but does not need to teach at Tu 3:10pm:\n");
		    
		    textAreaConsole.setText(textAreaConsole.getText() + "\n" + addLine);
		    instructors.removeAll(instructorsWithAssignment);
		    for(String s: instructors) {
		    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + s);
		    }
	
		    /*
		     * edit the tablecolumn after the search @Brother Liang implement it also in
		     * ALLSbujectSearch;
		     */
		    Controller.scrapedCourse = new ArrayList<Course>();
		    Controller.scrapedCourse.addAll(v);
		    List();
		} 
		catch (final FileNotFoundException e) {
		    String consoleComponent = "Invalid URL for " + e.getMessage();
		    consoleComponent += ". Please input a valid HKUST URL.";
	
		    textAreaConsole.setText(consoleComponent);
		    instructionText1.setText("* Cannot find the valid URL from HKUST class schedule and quota for");
		    instructionText2.setText("* " + e.getMessage());
		    instructionText3.setText("* Some instructions provided below.");
		    displayText1.setText("You need to provide a valid URL from HKUST class schedule and quota.");
		    displayText2.setText("You need to provide a valid time period.");
		    displayText3.setText("You need to provide a valid subject.");
		}
    }
    /**
     * This function will be called when either checkbox status is changed
     * This function will filter all the satisfied courses based on the requirements 
     * This function eventually will print the satisfied courses information in the console
     * @param no parameters needed
     * @return no returned
     * @see The console will show the filtered information
     * @author JIANG WEI
     */
    void select() {
		textAreaConsole.setText(null);
		final List<Course> v = new ArrayList<>(); 
		v.addAll(Controller.scrapedCourse);
		final List<Course> found = new ArrayList<>();
		for (final Course c : v) {
		    if (AM.isSelected()) {
	
			if (!c.containsAM())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (PM.isSelected()) {
	
			if (!c.containsPM())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (Mon.isSelected()) {
	
			if (!c.containsMon())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (Tue.isSelected()) {
	
			if (!c.containsTue())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (Wed.isSelected()) {
	
			if (!c.containsWed())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (Thur.isSelected()) {
	
			if (!c.containsThurs())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (Fri.isSelected()) {
	
			if (!c.containsFri())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (Sat.isSelected()) {// hope god bless these poor guys :D
	
			if (!c.containsSat())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		    if (CommonCore.isSelected())
			if (c.getCommoncore() == "null") {// If it is not ccc,WHY DO I enroll it 2333?
			    found.add(c);
			    continue;
			}
		    if (NoExclusion.isSelected())
			if (c.getExclusion() != "null") {// How come a course without any exclusion?
			    found.add(c);
			    continue;
			}
		    if (WithLabsorTutorial.isSelected()) {
	
			if (!c.containsLab())
			    found.add(c);
			if (found.contains(c))
			    continue;
		    }
		}
		v.removeAll(found);
		Controller.FilteredCourse.clear();
		Controller.FilteredCourse.addAll(v);
	
		for (final Course c : v) {
		    String newline = c.getTitle() + "\n";
		    for (int i = 0; i < c.getNumSlots(); i++) {
			final Slot t = c.getSlot(i);
			newline += "Slot " + i + ":" + t + "\n";
		    }
		    if (textAreaConsole.getText() == null)
			textAreaConsole.setText('\n' + newline);// WTF? get Null WILL be "NULL"????
		    else
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
	
		}
		List();
    }
    
    @FXML
    /**
     * This function call the filter function once the Select-All or De-select is clicked
     * The button text will be changed to "De-select all" from "Select All" or vice versa
     * All checkbox will be checked or not checked
     * the filter function will be called
     * @param no parameters needed
     * @return no returned
     * @see All checkbox status will be changed and the button text changes
     * @author JIANG WEI
     **/
    void SelectAll_Action() {

	if (SelectAll.getText() != "De-select All") {
	    SelectAll.setText("De-select All");
	    AM.setSelected(true);
	    PM.setSelected(true);
	    Mon.setSelected(true);
	    Tue.setSelected(true);
	    Wed.setSelected(true);
	    Thur.setSelected(true);
	    Fri.setSelected(true);
	    Sat.setSelected(true);
	    CommonCore.setSelected(true);
	    NoExclusion.setSelected(true);
	    WithLabsorTutorial.setSelected(true);

	} else {
	    SelectAll.setText("Select All");
	    AM.setSelected(false);
	    PM.setSelected(false);
	    Mon.setSelected(false);
	    Tue.setSelected(false);
	    Wed.setSelected(false);
	    Thur.setSelected(false);
	    Fri.setSelected(false);
	    Sat.setSelected(false);
	    CommonCore.setSelected(false);
	    NoExclusion.setSelected(false);
	    WithLabsorTutorial.setSelected(false);
	}
	select();
    }

    @FXML
    /**
     * This function is will call the filter function once the Thursday checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void Thur_Selection() {
	select();
    }

    @FXML
    /**
     * This function is will call the filter function once the Tuesday checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void Tue_Selection() {
	select();
    }

    @FXML
    /**
     * This function is will call the filter function once the Wednesday checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void Wed_Selection() {
	select();
    }

    @FXML
    /**
     * This function is will call the filter function once the WithLabsorTutorials checkbox is clicked
     * @param no parameters needed
     * @return no returned
     * @see no output
     * @author JIANG WEI
     */
    void WithLabsorTutorial_selection() {
    	
	select();
    }

}