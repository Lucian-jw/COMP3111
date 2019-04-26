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
    void AM_Selection() {
	select(); // once you click the checkbox, it will select
    }

    @FXML
    void CommonCore_Selection() {
	select();// once you click the checkbox, it will select
    }

    private void displayToTimetable(final Section section) {
	// Generate color from the list.
	Color c;
	final Random random = new Random();
	c = Color.rgb(54 + random.nextInt(202), 54 + random.nextInt(202), 54 + random.nextInt(202));

	// Get the slot information of the section.
	for (int i = 0; i < section.getSlotSize(); i++) {
	    // Display the content to the timetable.
	    final AnchorPane ap = (AnchorPane) tabTimetable.getContent();

	    // Y on 9 AM: 48
	    // Y on 20: 270
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
		content = section.getCourseCode() + " (" + section.getSection() + ")";

	    final Label courseLabel = new Label(content);
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

	    ap.getChildren().addAll(courseLabel);
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
		    + "SFQ Score: " + total + "\n" + "\n");
	}
    }

    @FXML
    void findSfqEnrollCourse() {
	final List<CourseSFQStruct> out = scraper.scrapeCourseSFQ(textfieldSfqUrl.getText(),
		Controller.EnrolledSection);
	for (int i = 0; i < out.size(); i++)
	    textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Section: " + out.get(i).section.getCourseCode()
		    + ' ' + out.get(i).section.getSection() + "\n" + "SFQ Score: " + out.get(i).score + "\n" + "\n");
    }

    @FXML
    void Fri_Selection() {
	select();// once you click the checkbox, it will select
    }

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
		if (sec.getEnrolledStatus() == false && Controller.EnrolledSection.contains(sec))
		    Controller.EnrolledSection.remove(sec);
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
    void Mon_Action() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    void NoExlusion_Selection() {
	select();
    }// once you click the checkbox, it will select

    @FXML
    void PM_Selection() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    void Sat_Selection() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    void search() {
	try {
	    checkValidURL(
		    textfieldURL.getText() + "/" + textfieldTerm.getText() + "/subject/" + textfieldSubject.getText());
	    final List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
		    textfieldSubject.getText());

	    for (final Course c : v) {
		String newline = c.getTitle() + "\n";
		for (int i = 0; i < c.getNumSlots(); i++) {
		    final Slot t = c.getSlot(i);
		    newline += "Slot " + i + ":" + t + "\n";
		}
		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
	    }

	    /*
	     * edit the tablecolumn after the search @Brother Liang implement it also in
	     * ALLSbujectSearch;
	     */
	    Controller.scrapedCourse.addAll(v);
	    List();
	} catch (final FileNotFoundException e) {
	    String consoleComponent = "Invalid URL for " + e.getMessage();
	    consoleComponent += ". Please input a valid HKUST URL.";

	    textAreaConsole.setText(consoleComponent);
	}
    }

    void select() {// the console will display the corresponding courses under the restriction,by
		   // the way, Why do you read this,uh?
	textAreaConsole.setText(null);
	final List<Course> v = new ArrayList<>(); // edit it to be a AllSubjectSearch course or normal search list!
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
	v.removeAll(found);// found is the union that doesn't satisfy any of requirement ,remove all of
	// them,now V is what I want.
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
    void SelectAll_Action() {

	if (SelectAll.getText() != "De-select All") {// If it is "Select-All,change to"De" and change all the statuses
						     // to selected,do the selection.
	    System.out.println("hi");
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
	    SelectAll.setText("Select All");// If it is "DeSelect-All,change to"Se" and change all the statuses to
					    // selected,do the selection.
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
    void Thur_Selection() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    void Tue_Selection() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    void Wed_Selection() {
	select();// once you click the checkbox, it will select
    }

    @FXML
    void WithLabsorTutorial_selection() {
	select();// once you click the checkbox, it will select
    }

}
