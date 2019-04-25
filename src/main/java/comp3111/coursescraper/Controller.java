package comp3111.coursescraper;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
public class Controller {
	private static List<Course> ScrapedCourse =new ArrayList<Course>();
	private static List<Course> FilteredCourse= new ArrayList<Course>();//remember to edit it after searching and ALlsubjectSearching!
	private static List<Section>EnrolledSection= new ArrayList<Section>();
	public ObservableList<Section> data=FXCollections.observableArrayList();
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
    private TableView<Section> ListTable;//change to section
    
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
    	select(); //once you click the checkbox, it will select
    }

    @FXML
    void CommonCore_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void Fri_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void Mon_Action(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void NoExlusion_Selection(ActionEvent event) {
    	select();
    }//once you click the checkbox, it will select

    @FXML
    void PM_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void Sat_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }


    @FXML
    void Thur_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void Tue_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void Wed_Selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }

    @FXML
    void WithLabsorTutorial_selection(ActionEvent event) {
    	select();//once you click the checkbox, it will select
    }
    
    @FXML
    void SelectAll_Action(ActionEvent event) {
    	
    	if(SelectAll.getText()!="De-select All"){//If it is "Select-All,change to"De" and change all the statuses to selected,do the selection.
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
    
    	}
    	else{
	    	SelectAll.setText("Select All");//If it is "DeSelect-All,change to"Se" and change all the statuses to selected,do the selection.
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
    void allSubjectSearch() {
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    	textAreaConsole.setText(textAreaConsole.getText()+"\n"+textfieldSfqUrl.getText());
    }

    @FXML
    void findSfqEnrollCourse() {

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
    	
    	
    	/*
    	 * edit the tablecolumn after the search @Brother Liang implement it also in ALLSbujectSearch;
    	 */
    	ScrapedCourse.addAll(v);
    	List();
    }
    
    
    void select(){//the console will display the corresponding courses under the restriction,by the way, Why do you read this,uh? 
    	textAreaConsole.setText(null);
    	List<Course> v =new ArrayList<Course>();//edit it to be a AllSubjectSearch course or normal search list!
    	v.addAll(ScrapedCourse);
    	List<Course> found=new ArrayList<Course>();
    	for(Course c:v){
    		if(AM.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!c.getSlot(i).isAM()){
    				if(i==c.getNumSlots()-1){
    					found.add(c);//find the course not satisfy them 
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(PM.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!c.getSlot(i).isPM()){
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(Mon.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!(c.getSlot(i).getDay()==0)){//0 means Monday,1,2,3,4,5 means Tue....
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(Tue.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!(c.getSlot(i).getDay()==1)){
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(Wed.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!(c.getSlot(i).getDay()==2)){
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(Thur.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!(c.getSlot(i).getDay()==3)){
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(Fri.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!(c.getSlot(i).getDay()==4)){
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(Sat.isSelected()){//hope god bless these poor guys :D
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(!(c.getSlot(i).getDay()==5)){
    				if(i==c.getNumSlots()-1){
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}
    		if(CommonCore.isSelected()){
    			if(c.getCommoncore()=="null"){//If it is not ccc,WHY DO I enroll it 2333?
    				found.add(c);
    				continue;
    			}
    		}
    		if(NoExclusion.isSelected()){
    			if(c.getExclusion()!="null"){//How come a course without any exclusion?
    				found.add(c);
    				continue;
    			}
    		}
    		if(WithLabsorTutorial.isSelected()){
    			int i=0;
    			if(c.getNumSlots()==0){//If it does not have any slots
    				found.add(c);
    				continue;
    			}
    			while(c.getSlot(i).getSectionType()==null||(!(c.getSlot(i).getSectionType().startsWith("L"))&&!(c.getSlot(i).getSectionType().startsWith("T")))){
    				if(i==c.getNumSlots()-1){ //either it is null or  not(it starts with 'L' or it starts with 't' )
    					found.add(c);
    					break;
    				}
    				i++;
    			}
    			if(found.contains(c)){
    				continue;
    			}
    		}	
    	}
       	v.removeAll(found);//found is the union that doesn't satisfy any of requirement ,remove all of them,now V is what I want.
       	FilteredCourse.clear();
       	FilteredCourse.addAll(v);
       	
    	for (Course c : v) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			newline += "Slot " + i + ":" + t + "\n";
    		}
    		if(textAreaConsole.getText()==null){
    			textAreaConsole.setText('\n'+newline);//WTF? get Null WILL be "NULL"????
    		}
    		else{
    			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		}
    		
    	}
    	List();
    	
    	
    }
    void List(){
    	CourseCode.setCellValueFactory(cellData -> cellData.getValue().CourseCodeProperty());
    	Section.setCellValueFactory(cellData -> cellData.getValue().SectionProperty());
		CourseName.setCellValueFactory(cellData -> cellData.getValue().CourseNameProperty());
		Instructor.setCellValueFactory(cellData -> cellData.getValue().InstructorProperty());
		Enroll.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Section, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Section, CheckBox> arg0) {
                Section sec = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(sec.getEnrolledStatus());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
	                        sec.setEnrolledStatus((new_val));
	                        if(sec.getEnrolledStatus()==true &&  !EnrolledSection.contains(sec)){
	                        	EnrolledSection.add(sec);
	                        }
	                        if(sec.getEnrolledStatus()==false&& EnrolledSection.contains(sec)){
	                        	EnrolledSection.remove(sec);
	                        }
	                        textAreaConsole.clear();
	                        String newline = "The following sections are enrolled:" + "\n";
	                        for(Section s:EnrolledSection){
	                    		newline += s.getCourseCode()+" "+s.getSection()+" "+s.getCourseName()+" "+s.getInstructor()+" \n";
	                    	}
	                        if(textAreaConsole.getText()==null){
	                    		textAreaConsole.setText('\n'+newline);//WTF? get Null WILL be "NULL"????
	                    	}
	                    	else{
	                    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
	                    	}
                		}
                    });
                
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
		});
		if(FilteredCourse.isEmpty()){
    		data.clear();
    		for(Course c:ScrapedCourse){
    			data.addAll(c.sections);
    		}
    		ListTable.setItems(data);
    	}
		else{
			data.clear();
	    	for(Course c:FilteredCourse){
				data.addAll(c.sections);
			}
	    	ListTable.setItems(data);
		}	
    }
    
   

}