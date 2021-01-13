package by.softeq.borys.parser;

public enum HtmlTagName {
	META, A, DIV, SPAN, SCRIPT, DEFAULT;
	
	public static HtmlTagName getElementTagName(String element) {
		switch (element) {
			case "meta": 
				return META;
			case "a":
				return A;
			case "div":
				return DIV;
			case "span":
				return SPAN;
			case "script":
				return SCRIPT;
			default:
				return DEFAULT;
//				throw new EnumConstantNotPresentException(HtmlTagName.class,
//						element);
		
		
	}
	}
}
