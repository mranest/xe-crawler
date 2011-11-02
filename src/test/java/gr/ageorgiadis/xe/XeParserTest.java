package gr.ageorgiadis.xe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class XeParserTest {

	@Test
	public void testXeParser() throws Exception {
//		Mongo m = new Mongo();
//		DB xedb = m.getDB("xedb");
//		DBCollection ads = xedb.getCollection("ads");
		
		XeParser parser = new XeParser();
		
		Map<String, Set<String>> keyValuesMap = new HashMap<String, Set<String>>();
		
		Set<String> keys = new HashSet<String>();
		for (String each: parser.getAdUrls("/property/enoikiaseis|katoikies|attiki.html")) {
			System.out.println(each);
			XeSpec spec = parser.parse(each);
			
//			BasicDBObject ad = new BasicDBObject();
//			ad.put("url", spec.getUrl());
//			ad.put("description", spec.getDescription());
//			
//			BasicDBObject details = new BasicDBObject();
//			details.putAll(spec.getDetails());
//			ad.put("details", details);
//			
//			ads.insert(ad);
			
//			System.out.println("-- " + spec.getUrl());
//			System.out.println(spec.getDescription());
//			System.out.println(spec.getDetails());

			for (Map.Entry<String, String> eachEntry: spec.getDetails().entrySet()) {
				Set<String> values = keyValuesMap.containsKey(eachEntry.getKey()) ?
						keyValuesMap.get(eachEntry.getKey()) :
						new HashSet<String>();
				values.add(eachEntry.getValue());
				
				keyValuesMap.put(eachEntry.getKey(), values);
			}
		}
		
		System.out.println();

		for (Map.Entry<String, Set<String>> each: keyValuesMap.entrySet()) {
			System.out.println(each.getKey() + " -> " + each.getValue());
		}		
	}
	
	public void testSpec() throws Exception {
		XeParser parser = new XeParser();
		XeSpec spec = parser.parse(
				"http://www.xe.gr/property/enoikiaseis|katoikies|kritika|6986289.html");
		
		System.out.println(spec.getDetails().keySet());
	}

	public void testJodaTime() throws Exception {
		String dateString = "Παρασκευή, 07 Οκτωβρίου 2011";

		System.out.println(XeDateHelper.formatDate(dateString));
	}
}
