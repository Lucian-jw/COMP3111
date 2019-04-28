package comp3111.coursescraper;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


@SuppressWarnings("restriction")
/**
 * This class implements the "section" which consists of Course
 * * @author JIANG WEI & Zhang LuoShu
 *
 */
public class Section {
    private SimpleStringProperty CourseCode;
    
    private SimpleStringProperty Section;
    
    private SimpleStringProperty Instructor;
    
    private SimpleStringProperty CourseName;
    
    private SimpleBooleanProperty EnrolledStatus;
    
    private final ArrayList<Slot> belongedSlots = new ArrayList<>();
    
    private ArrayList<Label> labels = new ArrayList<Label>();
    
    private ArrayList<Color> colors = new ArrayList<Color>();
    
    public void addColor(Color color) {
    	colors.add(color);
    }
    
    public int getNumColor() {
    	return colors.size();
    }
    
    public Color getColor(int i) {
    	return colors.get(i);
    }

    public void addSlot(final Slot s) {
    	belongedSlots.add(s);
    }

    @Override
    
    /**
     * This is a Section constructor
     * @return it will construct a new section contains all the original information
     */
    public Section clone() {
		final Section s = new Section();
		s.setCourseCode(getCourseCode());
		s.setCourseName(getCourseName());
		s.setInstructor(getInstructor());
		s.setSection(getSection());
		s.setEnrolledStatus(getEnrolledStatus());
		return s;
    }
    
    public int getNumLabels() {
    	return labels.size();
    }
    
    public void addLabel(Label label) {
    	labels.add(label);
    }
    
    public Label getLabel(int i) {
    	return labels.get(i);
    }
    
    /**
     * This function returns the CourseCode Property
     * @return the CourseCode property
     */
    public StringProperty CourseCodeProperty() {
    	return CourseCode;
    }
    
    /**
     * This function returns the CourseName Property
     * @return the CourseName property
     */
    public StringProperty CourseNameProperty() {
    	return CourseName;
    }
    
    /**
     * This function returns the EnrolledStatus Property
     * @return the EnrolledStatus property
     */
    public BooleanProperty EnrolledStatusProperty() {
    	return EnrolledStatus;
    }
     
    public boolean equals(final Section s) {
    	return getCourseCode().equals(s.getCourseCode()) && getSection().equals(s.getSection());
    }
    
    /**
     * This function returns the Course Code Value
     * @return the CourseCode Value
     */
    public String getCourseCode() {

    	return CourseCode.get();

    }
    
    /**
     * This function returns the Course Name Value
     * @return the CourseName Value
     */
    public String getCourseName() {

    	return CourseName.get();

    }
    
    /**
     * This function returns the EnrolledStatus Value
     * @return the EnrolledStatus Value
     */
    public Boolean getEnrolledStatus() {

    	return EnrolledStatus.get();

    }
    
    /**
     * This function returns the instructor Value
     * @return the instructor Value
     */
    public String getInstructor() {

    	return Instructor.get();

    }
   
    /**
     * This function returns the Section Value
     * @return the Section Value
     */
    public String getSection() {

    	return Section.get();

    }

    public Slot getSlot(final int i) {
    	return belongedSlots.get(i);
    }

    
    public int getSlotSize() {
    	return belongedSlots.size();
    }
    
    /**
     * This function returns the Instructor Property
     * @return the Instructor property
     */
    public StringProperty InstructorProperty() {
    	return Instructor;
    }
    
    /**
     * This function returns the Section Property
     * @return the Section property
     */
    public StringProperty SectionProperty() {
    	return Section;
    }
    
    /**
     * This function will set the Section with CourseCode
     * @param CCode a string value stands for the CourseCode
     */
    public void setCourseCode(final String CCode) {
    	CourseCode = new SimpleStringProperty(CCode);
    }
    
    /**
     * This function will set the Section with CourseName
     * @param CName a string value stands for the CourseName
     */
    public void setCourseName(final String CName) {
    	CourseName = new SimpleStringProperty(CName);
    }
    
    /**
     * This function will set the Section with Status
     * @param status  a boolean value stands for the status
     */
    public void setEnrolledStatus(final Boolean status) {
    	EnrolledStatus = new SimpleBooleanProperty(status);
    }
    
    /**
     * This function will set the Section with Instructor
     * @param Instructor a string value stands for the Instructor
     */
    public void setInstructor(final String Instructor) {
    	this.Instructor = new SimpleStringProperty(Instructor);
    }
    
    /**
     * This function will set the Section with section
     * @param section string value stands for the section information
     */
    public void setSection(final String section) {
    	Section = new SimpleStringProperty(section);
    }

}