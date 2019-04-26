package comp3111.coursescraper;




import org.junit.Test;
import comp3111.coursescraper.Slot;
import comp3111.coursescraper.Course;
import static org.junit.Assert.*;


public class ItemTest {

	@Test
	public void testSetTitle() {
		Course i = new Course();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
	
	
	@Test
	public void testAMPM1(){
		Slot i=new Slot();
		i.setStart("08:00AM");
		i.setEnd("11:59AM");
		assertEquals(i.isAM(),true);
		assertEquals(i.isPM(),false);

	}
	@Test
	public void testAMPM2(){

		Slot j=new Slot();
		j.setStart("08:00AM");
		j.setEnd("12:00PM");
		assertEquals(j.isAM(),true);
		assertEquals(j.isPM(),true);
		
	}
	@Test
	public void testAMPM3(){

		Slot k=new Slot();

		k.setStart("12:00PM");
		k.setEnd("02:00PM");
		assertEquals(k.isAM(),false);
		assertEquals(k.isPM(),true);
	}
	@Test
	public void testSection(){
		Section i=new Section();
		i.setCourseCode("COMP1021");
		i.setSection("L1");
		i.setCourseName("Python");
		i.setInstructor("Gibson");
		i.setEnrolledStatus(false);
		assertEquals(i.getEnrolledStatus(),false);
		assertEquals(i.getCourseCode(),"COMP1021");
		assertEquals(i.getCourseName(),"Python");
		assertEquals(i.getInstructor(),"Gibson");
		assertEquals(i.getSection(),"L1");
	}
	@Test
	public void testCourseAMPM(){
		Slot i=new Slot();
		Slot j=new Slot();
		Slot k=new Slot();
		i.setStart("08:00AM");
		i.setEnd("10:00AM");
		j.setStart("09:00AM");
		j.setEnd("11:00AM");
		k.setStart("12:00PM");
		k.setEnd("04:00PM");
		Course a=new Course();
		a.addSlot(i);
		a.addSlot(j);
		assertEquals(a.containsAM(),true);
		assertEquals(a.containsPM(),false);
		Course b=new Course();
		b.addSlot(k);
		b.addSlot(j);
		assertEquals(b.containsAM(),true);
		assertEquals(b.containsPM(),true);
	}
	
	@Test
	public void testWeekday(){
		Slot i1=new Slot();
		Slot i2=new Slot();
		Slot i3=new Slot();
		Slot i4=new Slot();
		Slot i5=new Slot();
		Slot i6=new Slot();
		i1.setDay(0);
		i2.setDay(1);
		i3.setDay(2);
		i4.setDay(3);
		i5.setDay(4);
		i6.setDay(5);
		Course a=new Course();
		a.addSlot(i1);
		a.addSlot(i2);
		a.addSlot(i3);
		Course b=new Course();
		b.addSlot(i4);
		b.addSlot(i5);
		b.addSlot(i6);
		assertEquals(a.containsMon(),true);
		assertEquals(a.containsTue(),true);
		assertEquals(a.containsWed(),true);
		assertEquals(a.containsThurs(),false);
		assertEquals(a.containsFri(),false);
		assertEquals(a.containsSat(),false);
		assertEquals(b.containsMon(),false);
		assertEquals(b.containsTue(),false);
		assertEquals(b.containsWed(),false);
		assertEquals(b.containsThurs(),true);
		assertEquals(b.containsFri(),true);
		assertEquals(b.containsSat(),true);
		
	}
	@Test
	public void testType(){
		Slot i1=new Slot();
		Slot i2=new Slot();
		Slot i3=new Slot();
		Slot i4=new Slot();
		Slot i5=new Slot();
		Slot i6=new Slot();
		i1.setSectionType("L1");
		i2.setSectionType("L2");
		i3.setSectionType("T1");
		i4.setSectionType("R1");
		i5.setSectionType("LA1");
		i6.setSectionType("X1");
		Course a=new Course();
		a.addSlot(i1);
		a.addSlot(i2);
		a.addSlot(i3);
		Course b=new Course();
		b.addSlot(i4);
		b.addSlot(i5);
		b.addSlot(i6);
		assertEquals(a.containsLab(),true);
		assertEquals(b.containsLab(),true);
		
		
	}
	
	
	
	
	
}
