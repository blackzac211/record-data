package unist.record.common;

import javax.servlet.http.HttpServletRequest;

public class CommonSecurity {
	public static boolean checkReferer(HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		if(referer.startsWith("http://software.unist.ac.kr")) {
			return true;
		}
		if(referer.startsWith("https://software.unist.ac.kr")) {
			return true;
		}
		return true;
	}
}
