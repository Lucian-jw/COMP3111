package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;





public class Course {
	private static final int DEFAULT_MAX_SLOT = 20;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private String commoncore;
	private Slot [] slots;
	public  List<Section> sections;
	private int numSlots;
	

	public Course() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		sections=new ArrayList<Section>();
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
		
	}
	public void addSection(Section s){
		this.sections.add(s);
	}
	public Section getSection(int i){
		return sections.get(i);
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	public Slot getSlot(int i) {
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

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the numSlots
	 */
	public int getNumSlots() {
		return numSlots;
	}
	
	/*
	 * @return commoncore information
	 */
	public String getCommoncore(){
		return this.commoncore;
	}

	/**
	 * @param numSlots the numSlots to set
	 */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	/*
	 * @param commoncore to set commoncore
	 */
	
	public void setCommoncore(String commoncore){ 
		this.commoncore= commoncore;
	}
	
	public Boolean containsAM(){
		int i = 0;
		if (this.getNumSlots() == 0) {// If it does not have any slots
			return false;
		}
		while (!this.getSlot(i).isAM()) {
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsPM(){
		int i = 0;
		if (this.getNumSlots() == 0) {// If it does not have any slots
			return false;
		}
		while (!this.getSlot(i).isPM()) {
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsMon(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (!(this.getSlot(i).getDay() == 0)) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsTue(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (!(this.getSlot(i).getDay() == 1)) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsWed(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (!(this.getSlot(i).getDay() == 2)) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsThurs(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (!(this.getSlot(i).getDay() == 3)) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsFri(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (!(this.getSlot(i).getDay() == 4)) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsSat(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (!(this.getSlot(i).getDay() == 5)) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	public Boolean containsLab(){
		int i = 0;
		if (this.getNumSlots() == 0) { // If it does not have any slots
			return false;
		}
		while (this.getSlot(i).getSectionType() == null || (!(this.getSlot(i).getSectionType().startsWith("L"))
				&& !(this.getSlot(i).getSectionType().startsWith("T")))) { // 0 means Monday,1,2,3,4,5 means Tue....
			if (i == this.getNumSlots() - 1) {
				return false;
			}
			i++;
		}
		return true;
	}
	

}
