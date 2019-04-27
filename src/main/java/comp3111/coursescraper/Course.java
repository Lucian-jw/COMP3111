package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private static final int DEFAULT_MAX_SLOT = 20;

    private String title;
    private String description;
    private String exclusion;
    private String commoncore;
    private final Slot[] slots;
    public List<Section> sections;
    private int numSlots;

    public Course() {
	slots = new Slot[Course.DEFAULT_MAX_SLOT];
	sections = new ArrayList<>();
	for (int i = 0; i < Course.DEFAULT_MAX_SLOT; i++)
	    slots[i] = null;
	numSlots = 0;

    }
    
    public int getNumSections() {
    	return sections.size();
    }

    public void addSection(final Section s) {
	sections.add(s);
    }

    public void addSlot(final Slot s) {
	if (numSlots >= Course.DEFAULT_MAX_SLOT)
	    return;
	slots[numSlots++] = s.clone();
    }

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

    public Boolean containsLab() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (getSlot(i).getSectionType() == null
		|| !getSlot(i).getSectionType().startsWith("L") && !getSlot(i).getSectionType().startsWith("T")) { // 0
														   // means
														   // Monday,1,2,3,4,5
														   // means
														   // Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

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

    public Boolean containsSat() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 5)) { // 0 means Monday,1,2,3,4,5 means Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    public Boolean containsThurs() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 3)) { // 0 means Monday,1,2,3,4,5 means Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    public Boolean containsTue() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 1)) { // 0 means Monday,1,2,3,4,5 means Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    public Boolean containsWed() {
	int i = 0;
	if (getNumSlots() == 0)
	    return false;
	while (!(getSlot(i).getDay() == 2)) { // 0 means Monday,1,2,3,4,5 means Tue....
	    if (i == getNumSlots() - 1)
		return false;
	    i++;
	}
	return true;
    }

    /*
     * @return commoncore information
     */
    public String getCommoncore() {
	return commoncore;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @return the exclusion
     */
    public String getExclusion() {
	return exclusion;
    }

    /**
     * @return the numSlots
     */
    public int getNumSlots() {
	return numSlots;
    }

    public Section getSection(final int i) {
	return sections.get(i);
    }

    public Slot getSlot(final int i) {
	if (i >= 0 && i < numSlots)
	    return slots[i];
	return null;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    public void setCommoncore(final String commoncore) {
	this.commoncore = commoncore;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
	this.description = description;
    }

    /**
     * @param exclusion the exclusion to set
     */
    public void setExclusion(final String exclusion) {
	this.exclusion = exclusion;
    }

    /**
     * @param numSlots the numSlots to set
     */
    public void setNumSlots(final int numSlots) {
	this.numSlots = numSlots;
    }

    /*
     * @param commoncore to set commoncore
     */
    /**
     * @param title the title to set
     */
    public void setTitle(final String title) {
	this.title = title;

    }

}