package gr.ageorgiadis.xe;

import java.util.Map;

public class XeSpec {

	private final String url;
	private final String description;
	private final Map<String, String> details;

	public XeSpec(String url, String description, Map<String, String> details) {
		this.url = url;
		this.description = description;
		this.details = details;
	}
	
	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}
	
	public Map<String, String> getDetails() {
		return details;
	}
	
}
