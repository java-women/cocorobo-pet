package javajo.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by Eriko on 2016/09/22.
 */
public class Constants {

	// TODO ココロボの種別(とりあえず固定値)
	public static final String cocorobo = "toko";

	// TODO とりあえずURLは固定 本番用のURLが決まったら修正
	private static final String BASE_URL = "http://javajo-api.azurewebsites.net/cocorobo-pet";
	public static final String SPEECH_URL = BASE_URL + "/api/speeches/" + cocorobo;
	public static final String COMPARE_IMAGE_URL = BASE_URL + "/api/compareImage";

	private static boolean petMode = false;

	public static boolean isPetMode() {
		return petMode;
	}

	public static void setPetMode(boolean petMode) {
		Constants.petMode = petMode;
	}

	/**
	 * CocoroboAPIのHttpHeaderを取得.
	 *
	 * @return Headers
	 */
	public static HttpHeaders getJsonHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		return headers;
	}

	/**
	 * CocoroboAPIのHttpHeaderを取得.
	 *
	 * @return Headers
	 */
	public static HttpHeaders getMultiPartHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE.toString());

		return headers;
	}

}
