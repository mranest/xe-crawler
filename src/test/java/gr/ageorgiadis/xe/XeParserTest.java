package gr.ageorgiadis.xe;

import org.junit.Test;

public class XeParserTest {

	@Test
	public void testXeParser() throws Exception {
//		Mongo m = new Mongo();
//		DB xedb = m.getDB("xedb");
//		DBCollection ads = xedb.getCollection("ads");
		
		XeParser parser = new XeParser();
		
		for (String each: parser.getAdUrls("/property/enoikiaseis|katoikies|attiki.html")) {
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
			
			System.out.println("-- " + spec.getUrl());
			System.out.println(spec.getDescription());
			System.out.println(spec.getDetails());
			
			break;
		}
	}
	
}
