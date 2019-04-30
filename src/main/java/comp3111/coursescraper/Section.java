
package comp3111.coursescraper;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This class implements the "section" which consists of Course
 *
 * @author JIANG WEI and ZHANG LUOSHU
 *
 */
public class Section {
    private SimpleStringProperty CourseCode;

    private SimpleStringProperty Section;

    private SimpleStringProperty Instructor;

    private SimpleStringProperty CourseName;

    private SimpleBooleanProperty EnrolledStatus;

    private final ArrayList<Slot> belongedSlots = new ArrayList<>();

    private ArrayList<Label> labels = new ArrayList<>();

    private ArrayList<Color> colors = new ArrayList<>();

    /**
     * This is a class method to get a list of instructors in case there are
     * multiple instructors for one section.
     *
     * @param color the color that needs to be added to the list stored in the
     *              object
     */
    public void addColor(Color color) {
	colors.add(color);
    }

    /**
     * This is a class method to get the number of colors stored in the list.
     *
     * @return the number of colors stored in the list
     */
    public int getNumColor() {
	return colors.size();
    }

    /**
     * This is a class method to get the color according to the index provided in
     * the parameter.
     *
     * @param i the index used to retrieve the color
     * @return the color extracted according to the index
     */
    public Color getColor(int i) {
	return colors.get(i);
    }

    /**
     * This is a class method to add a slot to the object of Section.
     *
     * @param s the Slot object that needs to be added to the slot list stored in
     *          the object.
     */
    public void addSlot(final Slot s) {
	belongedSlots.add(s);
    }

    @Override

    /**
     * This is a Section constructor
     *
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

    /**
     * This is a class method to get a list of instructors in case there are
     * multiple instructors for one section.
     *
     * @return a list of instructor names for one section.
     */
    public ArrayList<String> getInstructorNames() {
	ArrayList<String> instructorNames = new ArrayList<>();
	if (getInstructor().contains("\n")) {
	    String[] instructorNamesMultiple = getInstructor().split("\n");
	    for (String element : instructorNamesMultiple) {
		instructorNames.add(element);
	    }
	} else {
	    instructorNames.add(getInstructor());
	}
	return instructorNames;
    }

    /**
     * This class method will add a label to the label list stored in the object.
     *
     * @return the number of labels stored in the object
     */
    public int getNumLabels() {
	return labels.size();
    }

    /**
     * This class method will add a label to the label list stored in the object.
     *
     * @param label the label that needs to be added to the label list stored in the
     *              object
     */
    public void addLabel(Label label) {
	labels.add(label);
    }

    /**
     * This class method will get the label from the list stored in the object.
     *
     * @param i a number of index in a list of labels
     * @return the desired label according to the index
     */
    public Label getLabel(int i) {
	return labels.get(i);
    }

    /**
     * This function returns the CourseCode Property
     *
     * @return the CourseCode property
     */
    public StringProperty CourseCodeProperty() {
	return CourseCode;
    }

    /**
     * This function returns the CourseName Property
     *
     * @return the CourseName property
     */
    public StringProperty CourseNameProperty() {
	return CourseName;
    }

    /**
     * This function returns the EnrolledStatus Property
     *
     * @return the EnrolledStatus property
     */
    public BooleanProperty EnrolledStatusProperty() {
	return EnrolledStatus;
    }

    /**
     * This class method will check whether the object have the same content with
     * the given Section object.
     *
     * @param s the given object that needs to be checked by this class method.
     * @return whether the passed object have the same content with this Section
     *         object.
     */
    @Override
    public boolean equals(Object s) {
	if (!(s instanceof Section))
	    return false;
	Section section = (Section) s;
	return getCourseCode().equals(section.getCourseCode()) && getSection().equals(section.getSection());
    }

    /**
     * This function returns the Course Code Value
     *
     * @return the CourseCode Value
     */
    public String getCourseCode() {

	return CourseCode.get();

    }

    /**
     * This function returns the Course Name Value
     *
     * @return the CourseName Value
     */
    public String getCourseName() {

	return CourseName.get();

    }

    /**
     * This function returns the EnrolledStatus Value
     *
     * @return the EnrolledStatus Value
     */
    public Boolean getEnrolledStatus() {

	return EnrolledStatus.get();

    }

    /**
     * This function returns the instructor Value
     *
     * @return the instructor Value
     */
    public String getInstructor() {

	return Instructor.get();

    }

    /**
     * This function returns the Section Value
     *
     * @return the Section Value
     */
    public String getSection() {

	return Section.get();

    }

    /**
     * This class method will get the slot object according to the index.
     *
     * @param i the given index that is needed to retrieve the object
     * @return the retrieved slot according to the index
     */
    public Slot getSlot(final int i) {
	return belongedSlots.get(i);
    }

    /**
     * This function returns the number of slots in the section object
     *
     * @return the number of slots in the section object
     */
    public int getSlotSize() {
	return belongedSlots.size();
    }

    /**
     * This function returns the Instructor Property
     *
     * @return the Instructor property
     */
    public StringProperty InstructorProperty() {
	return Instructor;
    }

    /**
     * This function returns the Section Property
     *
     * @return the Section property
     */
    public StringProperty SectionProperty() {
	return Section;
    }

    /**
     * This function will set the Section with CourseCode
     *
     * @param CCode a string value stands for the CourseCode
     */
    public void setCourseCode(final String CCode) {
	CourseCode = new SimpleStringProperty(CCode);
    }

    /**
     * This function will set the Section with CourseName
     *
     * @param CName a string value stands for the CourseName
     */
    public void setCourseName(final String CName) {
	CourseName = new SimpleStringProperty(CName);
    }

    /**
     * This function will set the Section with Status
     *
     * @param status a boolean value stands for the status
     */
    public void setEnrolledStatus(final Boolean status) {
	EnrolledStatus = new SimpleBooleanProperty(status);
    }

    /**
     * This function will set the Section with Instructor
     *
     * @param Instructor a string value stands for the Instructor
     */
    public void setInstructor(final String Instructor) {
	this.Instructor = new SimpleStringProperty(Instructor);
    }

    /**
     * This function will set the Section with section
     *
     * @param section string value stands for the section information
     */
    public void setSection(final String section) {
	Section = new SimpleStringProperty(section);
    }

}