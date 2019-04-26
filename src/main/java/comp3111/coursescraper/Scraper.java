package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * WebScraper provide a sample code that scrape web content. After it is
 * constructed, you can call the method scrape with a keyword, the client will
 * go to the default url and parse the page by looking at the HTML DOM. <br>
 * In this particular sample code, it access to HKUST class schedule and quota
 * page (COMP). <br>
 * https://w5.ab.ust.hk/wcq/cgi-bin/1830/subject/COMP <br>
 * where 1830 means the third spring term of the academic year 2018-19 and COMP
 * is the course code begins with COMP. <br>
 * Assume you are working on Chrome, paste the url into your browser and press
 * F12 to load the source code of the HTML. You might be freak out if you have
 * never seen a HTML source code before. Keep calm and move on. Press
 * Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your mouse cursor
 * around, different part of the HTML code and the corresponding the HTML
 * objects will be highlighted. Explore your HTML page from body &rarr; div
 * id="classes" &rarr; div class="course" &rarr;. You might see something like
 * this: <br>
 * 
 * <pre>
 * {@code
 * <div class="course">
 * <div class="courseanchor" style=
"position: relative; float: left; visibility: hidden; top: -164px;"><a name=
"COMP1001">&nbsp;</a></div>
 * <div class="courseinfo">
 * <div class="popup attrword"><span class=
"crseattrword">[3Y10]</span><div class=
"popupdetail">CC for 3Y 2010 &amp; 2011 cohorts</div></div><div class=
"popup attrword"><span class="crseattrword">[3Y12]</span><div class=
"popupdetail">CC for 3Y 2012 cohort</div></div><div class=
"popup attrword"><span class="crseattrword">[4Y]</span><div class=
"popupdetail">CC for 4Y 2012 and after</div></div><div class=
"popup attrword"><span class="crseattrword">[DELI]</span><div class=
"popupdetail">Mode of Delivery</div></div>	
 *    <div class="courseattr popup">
 * 	    <span style=
"font-size: 12px; color: #688; font-weight: bold;">COURSE INFO</span>
 * 	    <div class="popupdetail">
 * 	    <table width="400">
 *         <tbody>
 *             <tr><th>ATTRIBUTES</th><td>Common Core (S&amp;T) for 2010 &amp; 2011 3Y programs<br>Common Core (S&amp;T) for 2012 3Y programs<br>Common Core (S&amp;T) for 4Y programs<br>[BLD] Blended learning</td></tr><tr><th>EXCLUSION</th><td>ISOM 2010, any COMP courses of 2000-level or above</td></tr><tr><th>DESCRIPTION</th><td>This course is an introduction to computers and computing tools. It introduces the organization and basic working mechanism of a computer system, including the development of the trend of modern computer system. It covers the fundamentals of computer hardware design and software application development. The course emphasizes the application of the state-of-the-art software tools to solve problems and present solutions via a range of skills related to multimedia and internet computing tools such as internet, e-mail, WWW, webpage design, computer animation, spread sheet charts/figures, presentations with graphics and animations, etc. The course also covers business, accessibility, and relevant security issues in the use of computers and Internet.</td>
 *             </tr>	
 *          </tbody>
 *      </table>
 * 	    </div>
 *    </div>
 * </div>
 *  <h2>COMP 1001 - Exploring Multimedia and Internet Computing (3 units)</h2>
 *  <table class="sections" width="1012">
 *   <tbody>
 *    <tr>
 *        <th width="85">Section</th><th width="190" style=
"text-align: left">Date &amp; Time</th><th width="160" style=
"text-align: left">Room</th><th width="190" style=
"text-align: left">Instructor</th><th width="45">Quota</th><th width=
"45">Enrol</th><th width="45">Avail</th><th width="45">Wait</th><th width=
"81">Remarks</th>
 *    </tr>
 *    <tr class="newsect secteven">
 *        <td align="center">L1 (1765)</td>
 *        <td>We 02:00PM - 03:50PM</td><td>Rm 5620, Lift 31-32 (70)</td><td><a href
=
"/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align
="center">67</td><td align="center">0</td><td align="center">67</td><td align=
"center">0</td><td align="center">&nbsp;</td></tr><tr class="newsect sectodd">
 *        <td align="center">LA1 (1766)</td>
 *        <td>Tu 09:00AM - 10:50AM</td><td>Rm 4210, Lift 19 (67)</td><td><a href
=
"/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align
="center">67</td><td align="center">0</td><td align="center">67</td><td align=
"center">0</td><td align="center">&nbsp;</td>
 *    </tr>
 *   </tbody>
 *  </table>
 * </div>
 *}
 * </pre>
 * 
 * <br>
 * The code
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
 * }
 * </pre>
 * 
 * extracts all result-row and stores the corresponding HTML elements to a list
 * called items. Later in the loop it extracts the anchor tag &lsaquo; a
 * &rsaquo; to retrieve the display text (by .asText()) and the link (by
 * .getHrefAttribute()).
 * 
 *
 */
public class Scraper {
	private WebClient client;

	/**
	 * Default Constructor
	 */
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

	private void addSlot(HtmlElement e, Course c, boolean secondRow, String ins, String sectionType) {
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

			// List to save output data
			List<CourseSFQStruct> courseScoreList = new ArrayList<CourseSFQStruct>();

			for (Section curSection : sections) {

				// Get the table containing all sections of the subject
				String courseCode = curSection.getCourseCode();
				String courseSub = courseCode.substring(0, 4);
				String courseNum = courseCode.substring(4, 8);
				String XPathIn = ".//b[@id='" + courseSub + "']";
				HtmlElement header = page.getFirstByXPath(XPathIn);
				header = (HtmlElement) header.getNextSibling();
				HtmlElement table = (HtmlElement) header.getNextSibling();

				// Find the <tr> row containing the courseCode XXXX1111
				HtmlElement tableRow = table.getFirstByXPath(".//tr[td[contains(text(),'" + courseNum + "')]]");

				// Indicator: whether this section has been found (to skip the outer loop)
				boolean sectFound = false;

				// This while loop iterate Rows down, find the row containing the section.
				HtmlElement curRow = (HtmlElement) tableRow.getNextSibling();
				while (true) {
					List<?> tableEntries = curRow.getByXPath(".//td");
					HtmlElement testSectionAttr = (HtmlElement) tableEntries.get(1);
					String testSection = testSectionAttr.asText().trim();

					// Found
					if (testSection == curSection.getSection()) {
						HtmlElement sectScore = (HtmlElement) tableEntries.get(3);
						String scoreRaw = sectScore.asText();
						String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
						if (!isNullScore(scoreProc)) {
							CourseSFQStruct out = new CourseSFQStruct();
							out.section = curSection;
							out.score = scoreProc;
							courseScoreList.add(out);
							sectFound = true;
							break;
						} else {
							sectFound = true;
							break;
						}
					}
					curRow = (HtmlElement) curRow.getNextSibling();
				}
				if (sectFound)
					break;
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

			// Select <table> elements containing course information
			List<?> tables = page.getByXPath(".//table[@border='1']");

			// List to save output data
			List<InstSFQScoreStruct> instScoreList = new ArrayList<InstSFQScoreStruct>();

			for (int i = 0; i < tables.size(); i++) {
				HtmlElement table = (HtmlElement) tables.get(i);
				List<?> tableRows = table.getByXPath(".//tr");
				for (int j = 0; j < tableRows.size(); j++) {
					HtmlElement tableRow = (HtmlElement) tableRows.get(j);
					List<?> tableEntries = tableRow.getByXPath(".//td");

					// For any <tr> element containing Instructor SFQ data, the third <td> element
					// of the <tr> must begin with Uppercase letter. (Which is the name of the
					// Instructor)
					if (tableEntries.size() != 8)
						continue;
					HtmlElement tableEntryTest = (HtmlElement) tableEntries.get(2);
					String testString = tableEntryTest.asText().trim();

					// Check whether this <tr> element contains Instructor SFQ data
					if (testString.matches("[A-Z][\\s\\S]+")) {
						boolean isFound = false;

						// If Instructor has been recorded -- Direct append:
						for (int k = 0; k < instScoreList.size(); k++) {
							if (instScoreList.get(k).name.equals(testString)) {
								System.out.println("exist");
								HtmlElement scoreElement = (HtmlElement) tableEntries.get(4);
								String scoreRaw = scoreElement.asText();
								String scoreProc = scoreRaw.substring(0, scoreRaw.indexOf("("));
								if (!isNullScore(scoreProc))
									instScoreList.get(k).score.add(scoreProc);
								isFound = true;
								break;
							}
						}

						// If Instructor has NOT been recorded -- Create one:
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

	private void addSection(Course c, String ins, String sec) {
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
					addSection(c, ins, sec);
					addSlot(e, c, false, ins, sectiontype);
					e = (HtmlElement) e.getNextSibling();
					if (e != null && !e.getAttribute("class").contains("newsect"))
						addSlot(e, c, true, ins, sectiontype);
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
