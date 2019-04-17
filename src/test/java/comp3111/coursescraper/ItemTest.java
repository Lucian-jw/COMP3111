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
	public void testType(){
		
	}
	
	
	
}
