package of.cgi.assignment.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ContentType{
	HTML("text/html"),
	TXT("text/plain");

	private final String mimeType;

	ContentType(String mimeType) {
		this.mimeType = mimeType;
	}

	public static List<ContentType> lookUp(String[] allTypes) {
		List<ContentType> types = new ArrayList<>();
		for(String aType : allTypes){
			for(ContentType t : ContentType.values()){
				if(aType.startsWith(t.mimeType)){
					types.add(t);
				}
			}
		}
		return types;
	}

	@Override
	public String toString() {
		return mimeType;
	}
}
