package comp3111.coursescraper;

 
import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class Slot {
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private String instructor;
	private String sectionType;
	private Section belongedSection;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}

	@Override
	public Slot clone() {
		Slot s = new Slot();
		s.day = this.day;
		s.start = this.start;
		s.end = this.end;
		s.venue = this.venue;
		s.instructor=this.instructor;
		s.sectionType=this.sectionType;
		return s;
	}
	
	public String toString() {
		return DAYS[day] + start.toString() + "-" + end.toString() + ":" + venue;
	}
	
	public void setSection(Section s) {
		this.belongedSection = s.clone();
	}
	
	public Section getSection() {
		return belongedSection.clone();
	}
	
	public int getStartHour() {
		return start.getHour();
	}
	public int getStartMinute() {
		return start.getMinute();
	}
	public int getEndHour() {
		return end.getHour();//s
	}
	public int getEndMinute() {
		return end.getMinute();
	}
	/**
	 * @return the start
	 */
	public LocalTime getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the end
	 */
	public LocalTime getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	/*
	 * set the ins
	 */
	public void setinstructor(String ins){
		this.instructor=ins;
	}
	
	/*
	 * return the ins
	 */
	public String getinstructor(){
		return instructor;
	}
	
	/*
	 * set the type:LA,L,LT
	 */
	public void setSectionType(String sectionType){
		this.sectionType=sectionType;
	}
	/*
	 * return the type:L , LA,LT
	 */
	public String getSectionType(){
		return sectionType;
	}
	/*
	 * determine whether this slot starts in A.M
	 */
	public boolean isAM (){
		if(this.start !=null)
			return this.start.isBefore(LocalTime.NOON);
		else{
			return false;
		}
		
		
	}
	/*
	 * determine whether this slot ends at P.M
	 */
	public boolean isPM (){
		if(this.end!=null)
			return this.end.isAfter(LocalTime.NOON)||this.end.equals(LocalTime.NOON);
		else{
			return false;
		}
		
		
	}
	

}