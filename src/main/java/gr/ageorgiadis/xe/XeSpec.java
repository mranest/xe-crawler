package gr.ageorgiadis.xe;

import java.util.Map;

public class XeSpec {

	private final String url;
	private final String description;
	private final Map<String, Object> details;

	public XeSpec(String url, String description, Map<String, Object> details) {
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
	
	public Map<String, Object> getDetails() {
		return details;
	}
	
}
