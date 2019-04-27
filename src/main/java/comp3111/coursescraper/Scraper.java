package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper {
    public class CourseSFQStruct {
	public String courseCode;
	public List<String> score = new ArrayList<>();
    }

    public class InstSFQScoreStruct {
	public String name;
	public List<String> score = new ArrayList<>();
    }

    private static Section addSection(final Course c, final String ins, final String sec) {
	final String CourseCode = c.getTitle().substring(0, 10);
	final String CourseName = c.getTitle().substring(12, c.getTitle().length());
	final Section s = new Section();
	s.setCourseCode(CourseCode);
	s.setSection(sec);
	s.setCourseName(CourseName);
	s.setInstructor(ins);
	s.setEnrolledStatus(false);
	if (s.getSection() != null && (s.getSection().startsWith("L") || s.getSection().startsWith("T")))
	    c.addSection(s);
	return s;
    }

    private static void addSlot(final Section section, final HtmlElement e, final Course c, final boolean secondRow,
	    final String ins, final String sectionType) {
	final String times[] = e.getChildNodes().get(secondRow ? 0 : 3).asText().split(" ");
	final String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();
	if (times[0].equals("TBA"))
	    return;
	for (int j = 0; j < times[0].length(); j += 2) {
	    final String code = times[0].substring(j, j + 2);
	    if (Slot.DAYS_MAP.get(code) == null)
		break;
	    final Slot s = new Slot();
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

    private static final boolean isNullScore(final String s) {
	if (s.equals("-"))
	    return true;
	return false;
    }

    private final WebClient client;

    public Scraper() {
	client = new WebClient();
	client.getOptions().setCssEnabled(false);
	client.getOptions().setJavaScriptEnabled(false);
    }

    public List<Course> scrape(final String baseurl, final String term, final String sub) {
	try {
	    final HtmlPage page = client.getPage(baseurl + "/" + term + "/subject/" + sub);
	    final List<?> items = page.getByXPath("//div[@class='course']");
	    final List<Course> result = new ArrayList<>();
	    for (int i = 0; i < items.size(); i++) {
		final Course c = new Course();
		final HtmlElement htmlItem = (HtmlElement) items.get(i);
		final HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
		c.setTitle(title.asText());
		final List<?> popupdetailslist = htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
		HtmlElement commoncore = null;
		HtmlElement exclusion = null;
		for (final HtmlElement e : (List<HtmlElement>) popupdetailslist) {
		    final HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
		    final HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
		    if (t.asText().equals("EXCLUSION"))
			exclusion = d;
		    if (t.asText().equals("ATTRIBUTES"))
			commoncore = d;
		}
		c.setExclusion(exclusion == null ? "null" : exclusion.asText());
		c.setCommoncore(commoncore == null ? "null" : commoncore.asText());
		final List<?> sections = htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
		for (HtmlElement e : (List<HtmlElement>) sections) {
		    final HtmlElement instructor = (HtmlElement) e.getFirstByXPath(".//a");
		    final HtmlElement section = (HtmlElement) e.getFirstByXPath(".//td");
		    final String ins = instructor == null ? "TBA" : instructor.asText();
		    final String sectiontype = section == null ? "null" : section.asText();
		    String sec = null;
		    if (sectiontype.startsWith("LA"))
			sec = sectiontype.substring(0, 3);
		    else if (sectiontype.startsWith("L"))
			sec = sectiontype.substring(0, 3);
		    else if (sectiontype.startsWith("T"))
			sec = sectiontype.substring(0, 3);
		    final Section addedSection = addSection(c, ins, sec);
		    addSlot(addedSection, e, c, false, ins, sectiontype);
		    e = (HtmlElement) e.getNextSibling();
		    if (e != null && !e.getAttribute("class").contains("newsect"))
			addSlot(addedSection, e, c, true, ins, sectiontype);
		}
		result.add(c);
	    }
	    client.close();
	    return result;
	} catch (final Exception e) {
	    System.out.println(e);
	}
	return null;
    }

    public List<CourseSFQStruct> scrapeCourseSFQ(final String baseurl, final List<Section> sections) {
	try {
	    final HtmlPage page = client.getPage(baseurl);
	    final List<CourseSFQStruct> courseScoreList = new ArrayList<>();
	    for (final Section curSection : sections) {
		final String courseCode = curSection.getCourseCode();
		final String courseSub = courseCode.substring(0, 4);
		final String courseNum = courseCode.substring(5, 9);
		final String courseCodeProc = courseSub + courseNum;
		final String XPathIn = ".//b[@id='" + courseSub + "']";
		final HtmlElement header = page.getFirstByXPath(XPathIn);
		final HtmlElement table = (HtmlElement) header.getNextSibling().getNextSibling();
		final HtmlElement tableRow = table.getFirstByXPath(".//tr[td[contains(text(),'" + courseNum + "')]]");
		HtmlElement curRow = (HtmlElement) tableRow.getNextElementSibling();
		while (true) {
		    final List<?> tableEntries = curRow.getByXPath(".//td");
		    final HtmlElement testSectionAttr = (HtmlElement) tableEntries.get(1);
		    final String testSection = testSectionAttr.asText().trim();
		    final String curSectionCode = curSection.getSection().trim();
		    if (testSection.equals(curSectionCode)) {
			final HtmlElement sectScore = (HtmlElement) tableEntries.get(3);
			final String scoreRaw = sectScore.asText();
			final String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
			if (!Scraper.isNullScore(scoreProc)) {
			    boolean isFound = false;
			    for (final CourseSFQStruct cur : courseScoreList)
				if (cur.courseCode.equals(courseCodeProc)) {
				    cur.score.add(scoreProc);
				    isFound = true;
				    break;
				}
			    if (!isFound) {
				final CourseSFQStruct out = new CourseSFQStruct();
				out.courseCode = courseCodeProc;
				out.score.add(scoreProc);
				courseScoreList.add(out);
			    }
			}
			break;
		    }
		    curRow = (HtmlElement) curRow.getNextElementSibling();
		    if (curRow == null)
			break;
		}
	    }
	    return courseScoreList;
	} catch (final Exception e) {
	    System.out.println(e);
	}
	return null;
    }

    public List<InstSFQScoreStruct> scrapeInstSFQ(final String baseurl) {
	try {
	    final HtmlPage page = client.getPage(baseurl);
	    final List<?> tables = page.getByXPath(".//table[@border='1']");
	    final List<InstSFQScoreStruct> instScoreList = new ArrayList<>();
	    for (int i = 0; i < tables.size(); i++) {
		final HtmlElement table = (HtmlElement) tables.get(i);
		final List<?> tableRows = table.getByXPath(".//tr");
		for (int j = 0; j < tableRows.size(); j++) {
		    final HtmlElement tableRow = (HtmlElement) tableRows.get(j);
		    final List<?> tableEntries = tableRow.getByXPath(".//td");
		    if (tableEntries.size() != 8)
			continue;
		    final HtmlElement tableEntryTest = (HtmlElement) tableEntries.get(2);
		    final String testString = tableEntryTest.asText().trim();
		    if (testString.matches("[A-Z][\\s\\S]+")) {
			boolean isFound = false;
			for (int k = 0; k < instScoreList.size(); k++)
			    if (instScoreList.get(k).name.equals(testString)) {
				final HtmlElement scoreElement = (HtmlElement) tableEntries.get(4);
				final String scoreRaw = scoreElement.asText();
				final String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
				if (!Scraper.isNullScore(scoreProc))
				    instScoreList.get(k).score.add(scoreProc);
				isFound = true;
				break;
			    }
			if (!isFound) {
			    final HtmlElement scoreElement = (HtmlElement) tableEntries.get(4);
			    final String scoreRaw = scoreElement.asText();
			    final String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
			    if (!Scraper.isNullScore(scoreProc)) {
				final InstSFQScoreStruct instStructAppend = new InstSFQScoreStruct();
				instStructAppend.name = testString;
				instStructAppend.score.add(scoreProc);
				instScoreList.add(instStructAppend);
			    }
			}
		    }
		}
	    }
	    return instScoreList;
	} catch (final Exception e) {
	    System.out.println(e);
	}
	return null;
    }

    public List<String> scrapeSubjects(final String baseurl, final String term) {
	try {
	    final HtmlPage page = client.getPage(baseurl + term + "/");
	    final HtmlElement depts = page.getFirstByXPath("//div[@class='depts']");
	    final List<String> result = new ArrayList<>();
	    final List<HtmlElement> subItems = depts.getByXPath(".//a");
	    for (int i = 0; i < subItems.size(); i++) {
		final HtmlElement htmlSubSubject = subItems.get(i);
		result.add(htmlSubSubject.asText());
	    }
	    return result;
	} catch (final Exception e) {
	    System.out.println(e);
	}
	return null;
    }
}