package de.neuland.jade4j.filter;

import java.util.Map;

public class JSFilter implements Filter {

	@Override
	public String convert(String source, Map<String, Object> attributes, Map<String, Object> model) {
		return "<script>" + source + "</script>";
	}

}
