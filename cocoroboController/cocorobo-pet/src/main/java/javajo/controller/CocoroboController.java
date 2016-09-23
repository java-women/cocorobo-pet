package javajo.controller;

import javajo.dto.FeelDTO;
import javajo.dto.SpeechDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * COCOROBOのデータ取得API.
 */
@RestController
@RequestMapping("/api")
public class CocoroboController {

    private final Logger log = LoggerFactory.getLogger(CocoroboController.class);

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
     * @param message
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
        log.info("REST request speech : {}, message : {}",cocorobo, message);

        /* モックデータ作成開始 */
        SpeechDTO speechDTO = new SpeechDTO();
        speechDTO.setResultCode(0);
        /* モックデータ作成終了 */

        return new ResponseEntity(new SpeechDTO(), HttpStatus.OK);
    }

}
