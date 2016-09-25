package javajo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;

/**
 * ペットモード操作クラス.
 */
@RestController
@RequestMapping("/api")
public class PetController {

	private final Logger log = LoggerFactory.getLogger(PetController.class);

	private final String START_MESSAGE = "ペットモードを開始します。";
	private final String END_MESSAGE = "ペットモードを終了します。";

	/**
	 * ペットモードを開始する.
	 *
	 * @return メッセージ
	 */
	@PutMapping(value = "/pet-mode/start", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startPetMode() throws IOException {
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
	public ResponseEntity<String> endPetMode() throws IOException {
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
	 * COCOROBOに発話させる.
	 *
	 * @param message COCOROBOに発話させるメッセージ
	 * @return SpeechDTO
	 */
	private SpeechDTO sentMessageForCocorobo(String message) throws IOException {
		// request bodyの作成
		RequestSpeechDTO requestSpeechDTO = new RequestSpeechDTO();
		requestSpeechDTO.setMessage(END_MESSAGE);

		//HttpHeaderの作成
		HttpHeaders headers = Constants.getJsonHttpHeaders();

		//request用Entity作成
		HttpEntity<RequestSpeechDTO> request = new HttpEntity(requestSpeechDTO, headers);

		// CocoroboApiの発話APIを実行.
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(Constants.SPEECH_URL, request, String.class);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response, SpeechDTO.class);
	}

}
