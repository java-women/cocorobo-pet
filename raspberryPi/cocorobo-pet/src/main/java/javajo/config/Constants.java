package javajo.config;

/**
 * Created by Eriko on 2016/09/22.
 */
public class Constants {

	private static boolean petMode = false;

	public static boolean isPetMode() {
		return petMode;
	}

	public static void setPetMode(boolean petMode) {
		Constants.petMode = petMode;
	}

}
