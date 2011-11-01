package gr.ageorgiadis.xe;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XeParser {

	private final String BASE_URL = "http://www.xe.gr";
	private final String SPEC_MODIFIER = "?mode=spec";
	private final String PAGE_MODIFIER_FORMAT = "?page={0}";
	
	public List<String> getAdUrls(String path) throws IOException {
		return getAdUrls(path, 1);
	}
	
	public List<String> getAdUrls(String path, int page) throws IOException {
		String pageModifier = page > 1 ? 
				MessageFormat.format(PAGE_MODIFIER_FORMAT, page) :
				"";
		Document doc = Jsoup.connect(
				BASE_URL + path + pageModifier).get();
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
		Document doc = Jsoup.connect(url).get();
		
		Map<String, String> details = new HashMap<String, String>();
		
		Element description = doc.select("p.d.d-google-banner").first();
		
		Element price = doc.select("div.d_price > h2").first();
		{
			String key = "Τιμή";
			String value = price != null ? price.text().substring(2) : null;
			
			details.put(key, value);
		}
		
		// Parse ad details
		Elements rows = doc.select("table.ad_details_table tr");
		
		for (Element each: rows) {
			String key = each.select("th").text().replaceAll(":", "");
			String value = each.select("td").text();

			details.put(key, value);
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

			spec.getDetails().put(key, value);
		}
	}
	
}
