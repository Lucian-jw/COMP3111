package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements Course
 * 
 * @author JIANG WEI
 *
 */
public class Course {
    private static final int DEFAULT_MAX_SLOT = 20;

    private String title;
    private String description;
    private String exclusion;
    private String commoncore;
    private final Slot[] slots;

    /**
     * List to store sections
     */
    public List<Section> sections;
    private int numSlots;

    /**
     * Default constructor, initialize object variables.
     */
    public Course() {
	slots = new Slot[Course.DEFAULT_MAX_SLOT];
	sections = new ArrayList<>();
	for (int i = 0; i < Course.DEFAULT_MAX_SLOT; i++)
	    slots[i] = null;
	numSlots = 0;

    }

    /**
     * This function will return the size of the sections'List.
     * 
     * @return The section List's size
     * @author JIANG WEI
     */
    public int getNumSections() {
	return sections.size();
    }

    /**
     * This function will add a section into a Section List
     * 
     * @param s a Section
     * @author JIANG WEI
     */
    public void addSection(final Section s) {
	sections.add(s);
    }

    /**
     * Add a slot to this course
     * 
     * @param s Slot
     */
    public void addSlot(final Slot s) {
	if (numSlots >= Course.DEFAULT_MAX_SLOT)
	    return;
	slots[numSlots++] = s.clone();
    }

    /**
     * This function will determine whether this course has a AM SLOT
     * 
     * @return a boolean value that indicates if this course contains a AM slot
     * @author JIANG WEI
     */
    public Boolean containsAM() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!getSlot(i).isAM()) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a Friday SLOT
     * 
     * @return a boolean value that indicates if this course contains a Friday slot
     * @author JIANG WEI
     */
    public Boolean containsFri() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 4)) { // 0 means Monday,1,2,3,4,5 means Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a LAB or Tutorial SLOT
     * 
     * @return a boolean value that indicates if this course contains a LAB or
     *         Tutorial slot
     * @author JIANG WEI
     */
    public Boolean containsLab() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (getSlot(i).getSectionType() == null
		|| !getSlot(i).getSectionType().startsWith("LA") && !getSlot(i).getSectionType().startsWith("T")) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a Monday SLOT
     * 
     * @return a boolean value that indicates if this course contains a Monday slot
     * @author JIANG WEI
     */
    public Boolean containsMon() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 0)) { // 0 means Monday,1,2,3,4,5 means Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a PM SLOT
     * 
     * @return a boolean value that indicates if this course contains a PM slot
     * @author JIANG WEI
     */
    public Boolean containsPM() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!getSlot(i).isPM()) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a Saturday SLOT
     * 
     * @return a boolean value that indicates if this course contains a Saturday
     *         slot
     * @author JIANG WEI
     */
    public Boolean containsSat() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 5)) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a Thursday SLOT
     * 
     * @return a boolean value that indicates if this course contains a Thursday
     *         slot
     * @author JIANG WEI
     */
    public Boolean containsThurs() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 3)) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a Tuesday SLOT
     * 
     * @return a boolean value that indicates if this course contains a Tuesday slot
     * @author JIANG WEI
     */
    public Boolean containsTue() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 1)) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will determine whether this course has a Wednesday SLOT
     * 
     * @return a boolean value that indicates if this course contains a Wednesday
     *         slot
     * @author JIANG WEI
     */
    public Boolean containsWed() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 2)) {
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /**
     * This function will return common core information
     * 
     * @return a string value about common core information
     * @author JIANG WEI
     */
    public String getCommoncore() {
	return commoncore;
    }

    /**
     * This function will return the course Description information
     * 
     * @return a string value about Description information
     * @author JIANG WEI
     */
    public String getDescription() {
	return description;
    }

    /**
     * This function will return Description information
     * 
     * @return a string value about Description information
     * @author JIANG WEI
     */
    public String getExclusion() {
	return exclusion;
    }

    /**
     * This function will return total number of slots of this course
     * 
     * @return a integer value about number of slots
     * @author JIANG WEI
     */
    public int getNumSlots() {
	return numSlots;
    }

    /**
     * This function will return an assigned section in the Section list
     * 
     * @param i the position of a Section list
     * @return the section in position i
     * @author JIANG WEI
     */
    public Section getSection(final int i) {
	return sections.get(i);
    }

    /**
     * This function will return an assigned slot in the slot array
     * 
     * @param i the position of a slot array
     * @return the slot in position i
     */
    public Slot getSlot(final int i) {
	if (i >= 0 && i < numSlots)
	    return slots[i];
	return null;
    }

    /**
     * This function will return the course Title
     * 
     * @return the title of this course
     */
    public String getTitle() {
	return title;
    }

    /**
     * This function will set the common core information
     * 
     * @param commoncore a string value
     * @author JIANG WEI
     */
    public void setCommoncore(final String commoncore) {
	this.commoncore = commoncore;
    }

    /**
     * This function will set the course Description
     * 
     * @param description a string value about the course description
     * @author JIANG WEI
     */
    public void setDescription(final String description) {
	this.description = description;
    }

    /**
     * This function will set the exclusion information
     * 
     * @param exclusion a string value about the exclusion description
     * @author JIANG WEI
     */
    public void setExclusion(final String exclusion) {
	this.exclusion = exclusion;
    }

    /**
     * This function will set the number of Slots
     * 
     * @param numSlots a integer stands for total number of slots
     */
    public void setNumSlots(final int numSlots) {
	this.numSlots = numSlots;
    }

    /**
     * This function will set the title of Course
     * 
     * @param title a String value stands for the Course Title
     */
    public void setTitle(final String title) {
	this.title = title;
    }

}