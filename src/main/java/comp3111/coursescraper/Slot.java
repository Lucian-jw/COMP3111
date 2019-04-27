package comp3111.coursescraper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Slot {
    public static final String DAYS[] = { "Mo", "Tu", "We", "Th", "Fr", "Sa" };
    public static final Map<String, Integer> DAYS_MAP = new HashMap<>();
    static {
	for (int i = 0; i < Slot.DAYS.length; i++)
	    Slot.DAYS_MAP.put(Slot.DAYS[i], i);
    }
    private int day;
    private LocalTime start;
    private LocalTime end;
    private String venue;
    private String instructor;
    private String sectionType;

    private Section belongedSection;

    @Override
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
     * @return the day
     */
    public int getDay() {
	return day;
    }

    /**
     * @return the end
     */
    public LocalTime getEnd() {
	return end;
    }

    public int getEndHour() {
	return end.getHour();// s
    }

    public int getEndMinute() {
	return end.getMinute();
    }

    /*
     * return the ins
     */
    public String getinstructor() {
	return instructor;
    }

    public Section getSection() {
	return belongedSection.clone();
    }

    /*
     * return the type:L , LA,LT
     */
    public String getSectionType() {
	return sectionType;
    }

    /**
     * @return the start
     */
    public LocalTime getStart() {
	return start;
    }

    public int getStartHour() {
	return start.getHour();
    }

    public int getStartMinute() {
	return start.getMinute();
    }

    /**
     * @return the venue
     */
    public String getVenue() {
	return venue;
    }

    /*
     * determine whether this slot starts in A.M
     */
    public boolean isAM() {
	if (start != null)
	    return start.isBefore(LocalTime.NOON);
	return false;

    }

    /*
     * determine whether this slot ends at P.M
     */
    public boolean isPM() {
	if (end != null)
	    return end.isAfter(LocalTime.NOON) || end.equals(LocalTime.NOON);
	return false;

    }

    /**
     * @param day the day to set
     */
    public void setDay(final int day) {
	this.day = day;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(final String end) {
	this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    }

    /*
     * set the ins
     */
    public void setinstructor(final String ins) {
	instructor = ins;
    }

    public void setSection(final Section s) {
	belongedSection = s.clone();
    }

    /*
     * set the type:LA,L,LT
     */
    public void setSectionType(final String sectionType) {
	this.sectionType = sectionType;
    }

    /**
     * @param start the start to set
     */
    public void setStart(final String start) {
	this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(final String venue) {
	this.venue = venue;
    }

    @Override
    public String toString() {
	return Slot.DAYS[day] + start.toString() + "-" + end.toString() + ":" + venue;
    }

}