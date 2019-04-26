package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import comp3111.coursescraper.Scraper.CourseSFQStruct;
import comp3111.coursescraper.Scraper.InstSFQScoreStruct;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import javafx.util.Callback;

public class Controller {
	private static List<Course> scrapedCourse = new ArrayList<Course>();
	private static List<String> subjects = new ArrayList<String>(); // List to store subjects searched by first-time All
	// Subject Search.
	private static List<Course> FilteredCourse = new ArrayList<Course>(); // remember to edit it after searching and
	// ALlsubjectSearching!
	private static List<Section> EnrolledSection = new ArrayList<Section>();
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
	private TableView<Section> ListTable; // change to section

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

	private Scraper scraper = new Scraper();

	@FXML
	void AM_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void CommonCore_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void Fri_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void Mon_Action(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void NoExlusion_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void PM_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void Sat_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void Thur_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void Tue_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void Wed_Selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void WithLabsorTutorial_selection(ActionEvent event) {
		select(); // Once you click the checkbox, it will select
	}

	@FXML
	void SelectAll_Action(ActionEvent event) {

		if (SelectAll.getText() != "De-select All") { // If it is "Select-All, change to "De" and change all the
			// statuses to selected, do the selection.
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
			SelectAll.setText("Select All"); // If it is "DeSelect-All,change to"Se" and change all the statuses to
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

	final Task<Void> allSSThread = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			for (int i = 0; i < subjects.size(); i++) {
				updateProgress(i + 1, subjects.size());
				String cur = subjects.get(i);
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), cur);
				scrapedCourse.addAll(v);
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + cur + " is done");
			}
			return null;
		}
	};

	@FXML
	void allSubjectSearch() {
		if (subjects.isEmpty()) {
			subjects.addAll(scraper.scrapeSubjects(textfieldURL.getText(), textfieldTerm.getText()));
			textAreaConsole.setText(
					textAreaConsole.getText() + "\n" + "Total Number of Categories/Code Prefix: " + subjects.size());
		} else {
			scrapedCourse.clear();
			progressbar.progressProperty().bind(allSSThread.progressProperty());
			final Thread thread = new Thread(allSSThread, "AllSS-thread");
			thread.setDaemon(true);
			thread.start();
		}
	}

	@FXML
	void findInstructorSfq() {
		List<InstSFQScoreStruct> out = scraper.scrapeInstSFQ(textfieldSfqUrl.getText());
		for (int i = 0; i < out.size(); i++) {
			List<String> curScore = out.get(i).score;
			float total = 0;
			for (int j = 0; j < curScore.size(); j++) {
				total += Float.parseFloat(curScore.get(j));
			}
			total = total / curScore.size();
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Instructor: " + out.get(i).name + "\n"
					+ "SFQ Score: " + total + "\n" + "\n");
		}
	}

	@FXML
	void findSfqEnrollCourse() {
		List<CourseSFQStruct> out = scraper.scrapeCourseSFQ(textfieldSfqUrl.getText(), EnrolledSection);
		for (int i = 0; i < out.size(); i++)
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Section: " + out.get(i).section.getCourseCode()
					+ ' ' + out.get(i).section.getSection() + "\n" + "SFQ Score: " + out.get(i).score + "\n" + "\n");
	}

	@FXML
	void search() {
		scrapedCourse.clear();
		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), textfieldSubject.getText());

		for (Course c : v) {
			String newline = c.getTitle() + "\n";
			for (int i = 0; i < c.getNumSlots(); i++) {
				Slot t = c.getSlot(i);
				newline += "Slot " + i + ":" + t + "\n";
			}
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
		}

		// Add a random block on Saturday
		AnchorPane ap = (AnchorPane) tabTimetable.getContent();
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

		/*
		 * edit the tablecolumn after the search @Brother Liang implement it also in
		 * ALLSbujectSearch;
		 */
		scrapedCourse.addAll(v);
		List();
	}

	void select() { // the console will display the corresponding courses under the restriction,by
		// the way, Why do you read this,uh?
		textAreaConsole.setText(null);
		List<Course> v = new ArrayList<Course>(); // edit it to be a AllSubjectSearch course or normal search list!
		v.addAll(scrapedCourse);
		List<Course> found = new ArrayList<Course>();
		for (Course c : v) {
			if (AM.isSelected()) {
				
				if(!c.containsAM()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (PM.isSelected()) {
				
				if(!c.containsPM()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (Mon.isSelected()) {
				
				if(!c.containsMon()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (Tue.isSelected()) {
				
				if(!c.containsTue()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (Wed.isSelected()) {
				
				if(!c.containsWed()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (Thur.isSelected()) {
				
				if(!c.containsThurs()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (Fri.isSelected()) {
				
				if(!c.containsFri()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (Sat.isSelected()) {// hope god bless these poor guys :D
				
				if(!c.containsSat()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
			if (CommonCore.isSelected()) {
				if (c.getCommoncore() == "null") {// If it is not ccc,WHY DO I enroll it 2333?
					found.add(c);
					continue;
				}
			}
			if (NoExclusion.isSelected()) {
				if (c.getExclusion() != "null") {// How come a course without any exclusion?
					found.add(c);
					continue;
				}
			}
			if (WithLabsorTutorial.isSelected()) {
				
				if(!c.containsLab()){
					found.add(c);
				}
				if (found.contains(c)) {
					continue;
				}
			}
		}
		v.removeAll(found);// found is the union that doesn't satisfy any of requirement ,remove all of
		// them,now V is what I want.
		FilteredCourse.clear();
		FilteredCourse.addAll(v);

		for (Course c : v) {
			String newline = c.getTitle() + "\n";
			for (int i = 0; i < c.getNumSlots(); i++) {
				Slot t = c.getSlot(i);
				newline += "Slot " + i + ":" + t + "\n";
			}
			if (textAreaConsole.getText() == null) {
				textAreaConsole.setText('\n' + newline);// WTF? get Null WILL be "NULL"????
			} else {
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}
		List();

	}

	void List() {
		// TODO: Add a logic to disable the button buttonSfqEnrollCourse when no course
		// in enrolledSection
		CourseCode.setCellValueFactory(cellData -> cellData.getValue().CourseCodeProperty());
		Section.setCellValueFactory(cellData -> cellData.getValue().SectionProperty());
		CourseName.setCellValueFactory(cellData -> cellData.getValue().CourseNameProperty());
		Instructor.setCellValueFactory(cellData -> cellData.getValue().InstructorProperty());
		Enroll.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Section, CheckBox>, ObservableValue<CheckBox>>() {

					@Override
					public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Section, CheckBox> arg0) {
						Section sec = arg0.getValue();

						CheckBox checkBox = new CheckBox();

						checkBox.selectedProperty().setValue(sec.getEnrolledStatus());
						checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val,
									Boolean new_val) {
								sec.setEnrolledStatus((new_val));
								if (sec.getEnrolledStatus() == true && !EnrolledSection.contains(sec)) {
									EnrolledSection.add(sec);
								}
								if (sec.getEnrolledStatus() == false && EnrolledSection.contains(sec)) {
									EnrolledSection.remove(sec);
								}
								textAreaConsole.clear();
								String newline = "The following sections are enrolled:" + "\n";
								for (Section s : EnrolledSection) {
									newline += s.getCourseCode() + " " + s.getSection() + " " + s.getCourseName() + " "
											+ s.getInstructor() + " \n";
								}
								if (textAreaConsole.getText() == null) {
									textAreaConsole.setText('\n' + newline);// WTF? get Null WILL be "NULL"????
								} else {
									textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
								}
							}
						});

						return new SimpleObjectProperty<CheckBox>(checkBox);
					}
				});
		if (FilteredCourse.isEmpty()) {
			data.clear();
			for (Course c : scrapedCourse) {
				data.addAll(c.sections);
			}
			ListTable.setItems(data);
		} else {
			data.clear();
			for (Course c : FilteredCourse) {
				data.addAll(c.sections);
			}
			ListTable.setItems(data);
		}
	}

}
