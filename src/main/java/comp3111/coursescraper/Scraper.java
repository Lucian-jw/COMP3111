package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper {
    private WebClient client;

    public Scraper() {
	client = new WebClient();
	client.getOptions().setCssEnabled(false);
	client.getOptions().setJavaScriptEnabled(false);
    }

    private static final boolean isNullScore(String s) {
	if (s.equals("-"))
	    return true;
	else
	    return false;
    }

    private void addSlot(Section section, HtmlElement e, Course c, boolean secondRow, String ins, String sectionType) {
	String times[] = e.getChildNodes().get(secondRow ? 0 : 3).asText().split(" ");
	String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();
	if (times[0].equals("TBA"))
	    return;
	for (int j = 0; j < times[0].length(); j += 2) {
	    String code = times[0].substring(j, j + 2);
	    if (Slot.DAYS_MAP.get(code) == null)
		break;
	    Slot s = new Slot();
	    s.setDay(Slot.DAYS_MAP.get(code));
	    s.setStart(times[1]);
	    s.setEnd(times[3]);
	    s.setVenue(venue);
	    s.setinstructor(ins);
	    s.setSectionType(sectionType);
	    if (s.getSectionType().startsWith("L") || s.getSectionType().startsWith("T"))
		c.addSlot(s);
	    section.addSlot(s);
	}
    }

    public class InstSFQScoreStruct {
	public String name;
	public List<String> score = new ArrayList<String>();
    }

    public class CourseSFQStruct {
	public Section section;
	public String score;
    }

    public List<CourseSFQStruct> scrapeCourseSFQ(String baseurl, final List<Section> sections) {
	try {
	    HtmlPage page = this.client.getPage(baseurl);
	    List<CourseSFQStruct> courseScoreList = new ArrayList<CourseSFQStruct>();
	    for (Section curSection : sections) {
		String courseCode = curSection.getCourseCode();
		String courseSub = courseCode.substring(0, 4);
		String courseNum = courseCode.substring(5, 9);
		String XPathIn = ".//b[@id='" + courseSub + "']";
		HtmlElement header = page.getFirstByXPath(XPathIn);
		HtmlElement table = (HtmlElement) header.getNextSibling().getNextSibling();
		HtmlElement tableRow = table.getFirstByXPath(".//tr[td[contains(text(),'" + courseNum + "')]]");
		HtmlElement curRow = (HtmlElement) tableRow.getNextElementSibling();
		while (true) {
		    List<?> tableEntries = curRow.getByXPath(".//td");
		    HtmlElement testSectionAttr = (HtmlElement) tableEntries.get(1);
		    String testSection = testSectionAttr.asText().trim();
		    String curSectionCode = curSection.getSection().trim();
		    if (testSection.equals(curSectionCode)) {
			HtmlElement sectScore = (HtmlElement) tableEntries.get(3);
			String scoreRaw = sectScore.asText();
			String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
			if (!isNullScore(scoreProc)) {
			    CourseSFQStruct out = new CourseSFQStruct();
			    out.section = curSection;
			    out.score = scoreProc;
			    courseScoreList.add(out);
			}
			break;
		    }
		    curRow = (HtmlElement) curRow.getNextElementSibling();
		    if (curRow == null)
			break;
		}
	    }
	    return courseScoreList;
	} catch (Exception e) {
	    System.out.println(e);
	}
	return null;
    }

    public List<InstSFQScoreStruct> scrapeInstSFQ(String baseurl) {
	try {
	    HtmlPage page = this.client.getPage(baseurl);
	    List<?> tables = page.getByXPath(".//table[@border='1']");
	    List<InstSFQScoreStruct> instScoreList = new ArrayList<InstSFQScoreStruct>();
	    for (int i = 0; i < tables.size(); i++) {
		HtmlElement table = (HtmlElement) tables.get(i);
		List<?> tableRows = table.getByXPath(".//tr");
		for (int j = 0; j < tableRows.size(); j++) {
		    HtmlElement tableRow = (HtmlElement) tableRows.get(j);
		    List<?> tableEntries = tableRow.getByXPath(".//td");
		    if (tableEntries.size() != 8)
			continue;
		    HtmlElement tableEntryTest = (HtmlElement) tableEntries.get(2);
		    String testString = tableEntryTest.asText().trim();
		    if (testString.matches("[A-Z][\\s\\S]+")) {
			boolean isFound = false;
			for (int k = 0; k < instScoreList.size(); k++) {
			    if (instScoreList.get(k).name.equals(testString)) {
				HtmlElement scoreElement = (HtmlElement) tableEntries.get(4);
				String scoreRaw = scoreElement.asText();
				String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
				if (!isNullScore(scoreProc))
				    instScoreList.get(k).score.add(scoreProc);
				isFound = true;
				break;
			    }
			}
			if (!isFound) {
			    HtmlElement scoreElement = (HtmlElement) tableEntries.get(4);
			    String scoreRaw = scoreElement.asText();
			    String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
			    if (!isNullScore(scoreProc)) {
				InstSFQScoreStruct instStructAppend = new InstSFQScoreStruct();
				instStructAppend.name = testString;
				instStructAppend.score.add(scoreProc);
				instScoreList.add(instStructAppend);
			    }
			}
		    }
		}
	    }
	    return instScoreList;
	} catch (Exception e) {
	    System.out.println(e);
	}
	return null;
    }

    public List<String> scrapeSubjects(String baseurl, String term) {
	try {
	    HtmlPage page = client.getPage(baseurl + term + "/");
	    HtmlElement depts = page.getFirstByXPath("//div[@class='depts']");
	    List<String> result = new ArrayList<String>();
	    List<HtmlElement> subItems = depts.getByXPath(".//a");
	    for (int i = 0; i < subItems.size(); i++) {
		HtmlElement htmlSubSubject = subItems.get(i);
		result.add(htmlSubSubject.asText());
	    }
	    return result;
	} catch (Exception e) {
	    System.out.println(e);
	}
	return null;
    }

    private Section addSection(Course c, String ins, String sec) {
	String CourseCode = c.getTitle().substring(0, 10);
	String CourseName = c.getTitle().substring(12, c.getTitle().length());
	Section s = new Section();
	s.setCourseCode(CourseCode);
	s.setSection(sec);
	s.setCourseName(CourseName);
	s.setInstructor(ins);
	s.setEnrolledStatus(false);
	if (s.getSection() != null && (s.getSection().startsWith("L") || s.getSection().startsWith("T"))) {
	    c.addSection(s);
	}
	return s;
    }

    public List<Course> scrape(String baseurl, String term, String sub) {
	try {
	    HtmlPage page = client.getPage(baseurl + "/" + term + "/subject/" + sub);
	    List<?> items = page.getByXPath("//div[@class='course']");
	    List<Course> result = new ArrayList<Course>();
	    for (int i = 0; i < items.size(); i++) {
		Course c = new Course();
		HtmlElement htmlItem = (HtmlElement) items.get(i);
		HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
		c.setTitle(title.asText());
		List<?> popupdetailslist = htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
		HtmlElement commoncore = null;
		HtmlElement exclusion = null;
		for (HtmlElement e : (List<HtmlElement>) popupdetailslist) {
		    HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
		    HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
		    if (t.asText().equals("EXCLUSION")) {
			exclusion = d;
		    }
		    if (t.asText().equals("ATTRIBUTES")) {
			commoncore = d;
		    }
		}
		c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
		c.setCommoncore((commoncore == null ? "null" : commoncore.asText()));
		List<?> sections = htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
		for (HtmlElement e : (List<HtmlElement>) sections) {
		    HtmlElement instructor = (HtmlElement) e.getFirstByXPath(".//a");
		    HtmlElement section = (HtmlElement) e.getFirstByXPath(".//td");
		    String ins = (instructor == null ? "TBA" : instructor.asText());
		    String sectiontype = (section == null ? "null" : section.asText());
		    String sec = null;
		    if (sectiontype.startsWith("LA")) {
			sec = sectiontype.substring(0, 3);
		    } else if (sectiontype.startsWith("L")) {
			sec = sectiontype.substring(0, 3);
		    } else if (sectiontype.startsWith("T")) {
			sec = sectiontype.substring(0, 3);
		    }
		    Section addedSection = addSection(c, ins, sec);
		    addSlot(addedSection, e, c, false, ins, sectiontype);
		    e = (HtmlElement) e.getNextSibling();
		    if (e != null && !e.getAttribute("class").contains("newsect"))
			addSlot(addedSection, e, c, true, ins, sectiontype);
		}
		result.add(c);
	    }
	    client.close();
	    return result;
	} catch (Exception e) {
	    System.out.println(e);
	}
	return null;
    }
}