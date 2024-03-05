package simu.framework;

public class Trace {

	public enum Level{INFO, WAR, ERR}
	
	private static Level traceLevel;
	
	public static void setTraceLevel(Level lvl){
		traceLevel = lvl;
	}

	public static void out(Level level, String message) {
		if (traceLevel != null) {
			if (traceLevel.ordinal() <= level.ordinal()) {
				System.out.println(message);
			}
		} else {
			System.out.println("Trace level is not set. Unable to log message: " + message);
		}
	}
}