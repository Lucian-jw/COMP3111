package comp3111.coursescraper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.*;


public class Section {
	private SimpleStringProperty CourseCode;
    private SimpleStringProperty Section;
    private SimpleStringProperty Instructor;
    private SimpleStringProperty CourseName;
    private SimpleBooleanProperty  EnrolledStatus;
    ArrayList<Slot> slots = new ArrayList<Slot>();
   
    @Override
	public Section clone() {
		Section s = new Section();
		s.setCourseCode(this.getCourseCode());
		s.setCourseName(this.getCourseName());
		s.setInstructor(this.getInstructor());
		s.setSection(this.getSection());
		s.setEnrolledStatus(this.getEnrolledStatus());
		return s;
	}
    
    public boolean equals(Section s) {
    	return getCourseCode().equals(s.getCourseCode()) && getSection().equals(s.getSection());
    }
    
    public String getCourseCode() {
        return CourseCode.get();
    }
    
    public String getSection() {
        return Section.get();
    }
    
    public String getInstructor() {
        return Instructor.get();
    }
    
    public String getCourseName() {
        return CourseName.get();
    }
    
    public Boolean getEnrolledStatus(){
    	return EnrolledStatus.get();
    }
    
    public void setCourseCode(String CCode){
		this.CourseCode=new SimpleStringProperty(CCode);
	}
    
    public void setCourseName(String CName){
    	this.CourseName=new SimpleStringProperty(CName);
	}

	public void setInstructor(String Instructor){
		this.Instructor=new SimpleStringProperty(Instructor);
	}
	
	public void setSection(String section){
		this.Section=new SimpleStringProperty(section);
	}
	
	public void setEnrolledStatus(Boolean status){
    	this.EnrolledStatus=new SimpleBooleanProperty(status);
	}
	
	public StringProperty CourseNameProperty() {
        return CourseName;
    }
	public StringProperty CourseCodeProperty() {
        return CourseCode;
    }
	public StringProperty InstructorProperty() {
        return Instructor;
    }
	public StringProperty SectionProperty() {
        return Section;
    }
	public BooleanProperty EnrolledStatusProperty(){
		return EnrolledStatus;
	}
	
}
