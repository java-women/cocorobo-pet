package javajo.controller;

import javajo.config.Constants;
import javajo.dto.RequestSpeechDTO;
import javajo.dto.SpeechDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ペットモード操作クラス.
 */
@RestController
@RequestMapping("/api")
public class PetController {

	private final Logger log = LoggerFactory.getLogger(PetController.class);

	// TODO とりあえずURLは固定 本番用のURLが決まったら修正
	private final String SPEECH_URL = "http://javajo-erk5.azurewebsites.net/cocorobo-pet/api/speeches/toko";
	private final String START_MESSAGE = "ペットモードを開始します。";
	private final String END_MESSAGE = "ペットモードを終了します。";

	/**
	 * ペットモードを開始する.
	 *
	 * @return メッセージ
	 */
	@PutMapping(value = "/pet-mode/start", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startPetMode() {
		log.debug("REST request to start pet mode");

		if (!Constants.isPetMode()) {
			SpeechDTO speechDTO = sentMessageForCocorobo(START_MESSAGE);

			if (speechDTO.getResultCode() == 0) {
				Constants.setPetMode(true);
				log.info("Success to start pet mode.");
				return new ResponseEntity<>("Success to start pet mode.", HttpStatus.OK);

			} else {
				log.warn("Failed to start pet mode.");
				return new ResponseEntity<>("Failed to start pet mode.", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			log.warn("Already pet mode.");
			return new ResponseEntity<>("Already pet mode.", HttpStatus.OK);
		}
	}

	/**
	 * ペットモードを終了する.
	 *
	 * @return メッセージ
	 */
	@PutMapping(value = "/pet-mode/end", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> endPetMode() {
		log.debug("REST request to start pet mode");

		if (Constants.isPetMode()) {
			SpeechDTO speechDTO = sentMessageForCocorobo(END_MESSAGE);

			if (speechDTO.getResultCode() == 0) {
				Constants.setPetMode(false);
				log.info("Success to end pet mode.");
				return new ResponseEntity<>("Success to end pet mode.", HttpStatus.OK);

			} else {
				log.warn("Failed to end pet mode.");
				return new ResponseEntity<>("Failed to end pet mode.", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			log.warn("Not pet mode.");
			return new ResponseEntity<>("Not pet mode.", HttpStatus.OK);
		}
	}

	/**
	 * CocoroboAPIのHttpHeaderを作成します.
	 *
	 * @return Headers
	 */
	private HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		return headers;
	}

	/**
	 * COCOROBOに発話させる.
	 *
	 * @param message COCOROBOに発話させるメッセージ
	 * @return SpeechDTO
	 */
	private SpeechDTO sentMessageForCocorobo(String message) {
		// request bodyの作成
		RequestSpeechDTO requestSpeechDTO = new RequestSpeechDTO();
		requestSpeechDTO.setMessage(END_MESSAGE);

		//HttpHeaderの作成
		HttpHeaders headers = createHttpHeaders();

		//request用Entity作成
		HttpEntity<RequestSpeechDTO> request = new HttpEntity(requestSpeechDTO, headers);

		// CocoroboApiの発話APIを実行.
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(SPEECH_URL, request, SpeechDTO.class);
	}

}
