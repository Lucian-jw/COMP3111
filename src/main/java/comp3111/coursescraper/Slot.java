package comp3111.coursescraper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class implements the object of Slot owned by Course and Section class
 *
 * @author JIANG WEI and ZHANG LUOSHU
 *
 */
public class Slot {

    /**
     * A static list that stores the names of the days
     */
    public static final String DAYS[] = { "Mo", "Tu", "We", "Th", "Fr", "Sa" };

    /**
     * A static map that maps from the index to the names of the days
     */
    public static final Map<String, Integer> DAYS_MAP = new HashMap<>();
    static {
	for (int i = 0; i < Slot.DAYS.length; i++) {
	    Slot.DAYS_MAP.put(Slot.DAYS[i], i);
	}
    }
    private int day;
    private LocalTime start;
    private LocalTime end;
    private String venue;
    private String instructor;
    private String sectionType;

    private Section belongedSection;

    @Override
    /**
     * This is a Slot clone() class method overwritten by the clone() method in
     * Object class
     *
     * @return it will construct a new Slot contains all the original information
     */
    public Slot clone() {
	final Slot s = new Slot();
	s.day = day;
	s.start = start;
	s.end = end;
	s.venue = venue;
	s.instructor = instructor;
	s.sectionType = sectionType;
	return s;
    }

    /**
     * this method gets the day
     * @return the day
     */
    public int getDay() {
	return day;
    }

    /**
     * this method gets the end
     * @return the end
     */
    public LocalTime getEnd() {
	return end;
    }

    /**
     * this method get the hour
     * @return the end hour
     */
    public int getEndHour() {
	return end.getHour();// s
    }

    /**
     * this method gets the end minute
     * @return the end minute
     */
    public int getEndMinute() {
	return end.getMinute();
    }

    /**
     * this method gets the instructor name
     * @return the instructor
     */
    public String getinstructor() {
	return instructor;
    }

    /**
     * this method gets the section
     * @return the belonged section of the current slot
     */
    public Section getSection() {
	return belongedSection.clone();
    }

    /**
     * this method gets the section type
     * @return the section type starting from L, LA, T
     */
    public String getSectionType() {
	return sectionType;
    }

    /**
     * this method get the start
     * @return the start time of the slot
     */
    public LocalTime getStart() {
	return start;
    }

    /**
     * this method get the start hour
     * @return the start hour of the slot
     */
    public int getStartHour() {
	return start.getHour();
    }

    /**
     * this method gets the start minute
     * @return the start minute of the slot
     */
    public int getStartMinute() {
	return start.getMinute();
    }

    /**
     * this method gets the venue
     * @return the venue
     */
    public String getVenue() {
	return venue;
    }

    /**
     * This function determines if this slot has AM quantum
     *
     * @return a boolean value indicates whether the time is AM
     * @author JIANG WEI
     */
    public boolean isAM() {
	if (start != null)
	    return start.isBefore(LocalTime.NOON);
	return false;

    }

    /**
     * This function determines if this slot has PM quantum
     *
     * @return a boolean value indicates whether the time is PM
     * @author JIANG WEI
     */
    public boolean isPM() {
	if (end != null)
	    return end.isAfter(LocalTime.NOON) || end.equals(LocalTime.NOON);
	return false;

    }

    /**
     * set the day
     * @param day the day to set
     */
    public void setDay(final int day) {
	this.day = day;
    }

    /**
     * set the end time
     * @param end the end to set
     */
    public void setEnd(final String end) {
	this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    }

    /**
     * set the ins
     *
     * @param ins the instructor name that needs to be set to the object
     */
    public void setinstructor(final String ins) {
	instructor = ins;
    }

    /**
     * set the section
     *
     * @param s the section that needs to be set to the object
     */
    public void setSection(final Section s) {
	belongedSection = s.clone();
    }

    /**
     * set the section type
     *
     * @param sectionType the section type that needs to be set to the object
     */
    public void setSectionType(final String sectionType) {
	this.sectionType = sectionType;
    }

    /**
     * set the start time
     * @param start the start to set
     */
    public void setStart(final String start) {
	this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    }

    /**
     * set the venue
     * @param venue the venue to set
     */
    public void setVenue(final String venue) {
	this.venue = venue;
    }

    @Override
    /**
     * this method change the object to string
     * @return a text version of the object
     */
    public String toString() {
	return Slot.DAYS[day] + start.toString() + "-" + end.toString() + ":" + venue;
    }

}