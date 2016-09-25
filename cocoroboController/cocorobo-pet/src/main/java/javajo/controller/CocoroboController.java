package javajo.controller;

import javajo.dto.*;
import javajo.enums.StatusEnum;
import javajo.hepler.JacsonHelper;
import javajo.model.verify.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * COCOROBOのデータ取得API.
 */
@RestController
@RequestMapping("/api")
public class CocoroboController {

    private final Logger log = LoggerFactory.getLogger(CocoroboController.class);
    private final String SPEECH_URL = "https://developer.cloudlabs.sharp.co.jp/cloudlabs-api/cocorobo/speech";
    private final String API_KEY = "api key";
    private final String AUTH_URL = "https://developer.cloudlabs.sharp.co.jp/cloudlabs-api/cocorobo/auth";

    /**
     * 指定されたCOCOROBOの感情を取得する.
     *
     * @param cocorobo COCOROBOの識別子：例)toko
     * @return JSON形式の文字列：<pre>{@code
     * {
     *     "result": 実行結果(boolean),
     *     "feel": 目玉の種類(String)
     * }
     * }</pre>
     * <p>feelの詳細</p>
     * <ul>
     *     <li>normal：普通の顔</li>
     *     <li>mattee：待って〜！って顔</li>
     *     <li>shoboon：置いてかれた、しょぼーん</li>
     *     <li>waai：喜び</li>
     *     <li>mukii：ムキーッ！</li>
     *     <li>kiraan：キラーン</li>
     *     <li>sukii：目がハート</li>
     * </ul>
     */
    @GetMapping(value = "/feels/{cocorobo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeelDTO> getFeel(@PathVariable String cocorobo) {
        log.debug("REST request to get Feel : {}", cocorobo);

        /* モックデータ作成開始 */
        List<String> feel = Arrays.asList("normal", "mattee", "shoboon", "waai", "mukii", "kiraan", "sukii");
        Collections.shuffle(feel);

        FeelDTO feelDTO = new FeelDTO();
        feelDTO.setResult(true);
        feelDTO.setFeel(feel.get(0));
        /* モックデータ作成終了 */

        return new ResponseEntity(feelDTO, HttpStatus.OK);
    }

    /**
     * 指定されたCOCOROBOに指定した文字列で音声発話させる.
     *
     * @param cocorobo COCOROBOの識別子：例)toko
     * @param message メッセージ
     * @return JSON形式の文字列(COCOROBO音声発話APIのレスポンス)：<pre>{@code
     * {
     *     "resultCode": 結果コード,
     *     "data":レスポンスデータ詳細(子要素はnull),
     *     "errorCode": エラーコード,
     *     "errorMessage":エラーメッセージ
     * }
     * }</pre>
     */
    @PostMapping(value = "/speeches/{cocorobo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SpeechDTO> speech(@PathVariable String cocorobo, @RequestBody String message) {
        log.info("REST request speech : {}, message : {}", cocorobo, message);
        if (sendAuthForCocorobo()) {
            return new ResponseEntity(sentMessageForCocorobo(message), HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * COCOROBOの利用認証する.
     *
     * @return
     */
    private boolean sendAuthForCocorobo() {
        boolean result = false;

        // request bodyの作成
        RequestAuthDTO requestAuthDTO = new RequestAuthDTO();
        requestAuthDTO.setApikeyCocorobo(API_KEY);

        //HttpHeaderの作成
        HttpHeaders headers = createHttpHeaders();

        //request用Entity作成
        HttpEntity<RequestAuthDTO> request = new HttpEntity(requestAuthDTO, headers);

        // CocoroboApiの利用認証APIを実行.
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(AUTH_URL, request, String.class);
        JacsonHelper jacsonHelper = new JacsonHelper();
        try {
            Map map = jacsonHelper.stringForMap(response);
            if (Integer.parseInt((String) map.get("resultCode")) == 0) {
                result = true;
            }
        } catch (IOException e) {
            log.error("IOException", e);
        }

        return result;
    }

    /**
     * COCOROBOに発話させる.
     *
     * @param message COCOROBOに発話させるメッセージ
     * @return SpeechDTO
     */
    private String sentMessageForCocorobo(String message) {
        // request bodyの作成
        RequestSpeechDTO requestSpeechDTO = new RequestSpeechDTO();
        requestSpeechDTO.setMessage(message);
        requestSpeechDTO.setApikey_cocorobo(API_KEY);

        //HttpHeaderの作成
        HttpHeaders headers = createHttpHeaders();

        //request用Entity作成
        HttpEntity<RequestSpeechDTO> request = new HttpEntity(requestSpeechDTO, headers);

        // CocoroboApiの発話APIを実行.
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(SPEECH_URL, request, String.class);
    }

    /**
     * CocoroboAPIのHttpHeaderを作成します.
     *
     * @return Headers
     */
    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());

        return headers;
    }

    /**
     * Cocoroboを動作させる.
     * * @param verify Verify
     * @return JSON形式の文字列
     */
    @PostMapping(value = "/moves/{cocorobo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MoveDTO> move(@PathVariable String cocorobo, Verify verify) {
        log.info("REST request to move. cocorobo : {}, verify : {}",cocorobo, verify);

        // TODO 方向計算

        // TODO COCOROBOの動作APIを呼び出す

        /* モックデータ作成開始 */
        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setResult(StatusEnum.OK.getName());
        /* モックデータ作成終了 */

        return new ResponseEntity(new MoveDTO(), HttpStatus.OK);
    }

}
