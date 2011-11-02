package gr.ageorgiadis.xe;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XeParser {

	private static final String USER_AGENT = 
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/534.51.22 (KHTML, like Gecko) Version/5.1.1 Safari/534.51.22";
	
	private static final String BASE_URL = "http://www.xe.gr";
	private static final String SPEC_MODIFIER = "?mode=spec";
	private static final String PAGE_MODIFIER_FORMAT = "?page={0}";
	
	private static final String TRUE_VALUE = "Ναι";
	private static final String FALSE_VALUE = "Όχι";
	
	private static final String HITS_TRAILING = " επισκέψεις"; 

	private static final Logger LOGGER = LoggerFactory.getLogger(XeParser.class);
	
	private final Map<String, String> keyMap;
	
	public XeParser() {
		ResourceBundle keys = ResourceBundle.getBundle("gr.ageorgiadis.xe.Keys");
		
		keyMap = new HashMap<String, String>();
		for (String each: keys.keySet()) {
			keyMap.put(keys.getString(each), each);
		}
	}
	
	public List<String> getAdUrls(String path) throws IOException {
		return getAdUrls(path, 1);
	}
	
	public List<String> getAdUrls(String path, int page) throws IOException {
		String pageModifier = page > 1 ? 
				MessageFormat.format(PAGE_MODIFIER_FORMAT, page) :
				"";
		Document doc = Jsoup.connect(BASE_URL + path + pageModifier)
				.userAgent(USER_AGENT)
				.get();
		Elements adAnchors = doc.select("div.r_desc > h2 > a");
		
		List<String> adUrls = new ArrayList<String>();
		for (Element each: adAnchors) {
			String adUrl = BASE_URL + each.attr("href");
			
			adUrls.add(adUrl);
		}
		
		return adUrls;
	}
	
	public XeSpec parse(String url) throws IOException {
		XeSpec spec = parseAd(url);
		parseSpec(url, spec);
		
		return spec;
	}
	
	private XeSpec parseAd(String url) throws IOException {
		Document doc = Jsoup.connect(url)
				.userAgent(USER_AGENT)
				.get();
		
		Map<String, Object> details = new HashMap<String, Object>();
		
		Element description = doc.select("p.d.d-google-banner").first();
		
		Element price = doc.select("div.d_price > h2").first();
		{
			String key = Keys.PRICE_KEY;
			String value = price != null ? price.text().substring(2) : null;
			
			handleKeyValue(key, value, details);
		}
		
		// Parse ad details
		Elements rows = doc.select("table.ad_details_table tr");
		
		for (Element each: rows) {
			String key = each.select("th").text().replaceAll(":", "");
			String value = each.select("td").text();

			handleKeyValue(key, value, details);
		}

		return new XeSpec(url, description.text(), details);
	}
	
	private void parseSpec(String url, XeSpec spec) throws IOException {
		Document doc = Jsoup.connect(url + SPEC_MODIFIER).get();
		
		// Parse specs
		Elements rows = doc.select("div.box_contents tr");
		
		for (Element each: rows) {
			String key = each.select("th").text().replaceAll(":", "");
			String value = each.select("td").text();

			handleKeyValue(key, value, spec.getDetails());
		}
	}
	
	private void handleKeyValue(String key, Object value, Map<String, Object> details) {
		if (keyMap.containsKey(key)) {
			key = keyMap.get(key);
		} else {
			LOGGER.warn("Missing mapping for key: [{}]", key);
		}

		if (Keys.isSkipped(key)) {
			return;
		}
		
		if (Keys.isFullDate(key)) {
			try {
				value = parseFullDate((String) value);
			} catch (ParseException e) {
				LOGGER.warn("Could not parse full date: {}", value);
			}
		}
		else
		if (Keys.isNumeric(key)) {
			value = parseNumeric((String) value);
		}
		else
		if (((String) value).matches(".*" + HITS_TRAILING)) {
			value = Integer.valueOf(((String) value).replaceAll(HITS_TRAILING, ""));
		}
		else
		if (value.equals(TRUE_VALUE)) {
			value = Boolean.TRUE;
		}
		else
		if (value.equals(FALSE_VALUE)) {
			value = Boolean.FALSE;
		}
		
		details.put(key, value);
	}
	
	private Date parseFullDate(String fullDate) throws ParseException {
		return XeDateHelper.formatDate(fullDate);
	}
	
	private Integer parseNumeric(String numeric) {
		return Integer.valueOf(numeric.replaceAll("\\.", ""));
	}
	
}
