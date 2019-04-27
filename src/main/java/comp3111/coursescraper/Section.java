package comp3111.coursescraper;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;

public class Section {
    private SimpleStringProperty CourseCode;
    private SimpleStringProperty Section;
    private SimpleStringProperty Instructor;
    private SimpleStringProperty CourseName;
    private SimpleBooleanProperty EnrolledStatus;
    private final ArrayList<Slot> belongedSlots = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<Label>();

    public void addSlot(final Slot s) {
	belongedSlots.add(s);
    }

    @Override
    public Section clone() {
	final Section s = new Section();
	s.setCourseCode(getCourseCode());
	s.setCourseName(getCourseName());
	s.setInstructor(getInstructor());
	s.setSection(getSection());
	s.setEnrolledStatus(getEnrolledStatus());
	return s;
    }

    public StringProperty CourseCodeProperty() {
	return CourseCode;
    }

    public StringProperty CourseNameProperty() {
	return CourseName;
    }

    public BooleanProperty EnrolledStatusProperty() {
	return EnrolledStatus;
    }

    public boolean equals(final Section s) {
	return getCourseCode().equals(s.getCourseCode()) && getSection().equals(s.getSection());
    }

    public String getCourseCode() {

	return CourseCode.get();

    }

    public String getCourseName() {

	return CourseName.get();

    }

    public Boolean getEnrolledStatus() {

	return EnrolledStatus.get();

    }

    public String getInstructor() {

	return Instructor.get();

    }

    public String getSection() {

	return Section.get();

    }

    public Slot getSlot(final int i) {
	return belongedSlots.get(i);
    }

    public int getSlotSize() {
	return belongedSlots.size();
    }

    public StringProperty InstructorProperty() {
	return Instructor;
    }

    public StringProperty SectionProperty() {
	return Section;
    }

    public void setCourseCode(final String CCode) {
	CourseCode = new SimpleStringProperty(CCode);
    }

    public void setCourseName(final String CName) {
	CourseName = new SimpleStringProperty(CName);
    }

    public void setEnrolledStatus(final Boolean status) {
	EnrolledStatus = new SimpleBooleanProperty(status);
    }

    public void setInstructor(final String Instructor) {
	this.Instructor = new SimpleStringProperty(Instructor);
    }

    public void setSection(final String section) {
	Section = new SimpleStringProperty(section);
    }

}