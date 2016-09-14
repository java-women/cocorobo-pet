package javajo;

import javajo.dto.FeelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *     <li>mattee：待って〜！って顔</li>
     *     <li>shoboon：置いてかれた、しょぼーん</li>
     *     <li>waai：喜び</li>
     *     <li>dere：デレデレ</li>
     *     <li>tun：ツン</li>
     * </ul>
     */
    @GetMapping(value = "/feels/{cocorobo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeelDTO> getFeel(@PathVariable String cocorobo) {
        log.debug("REST request to get Feel : {}", cocorobo);

        /* モックデータ作成開始 */
        List<String> feel = Arrays.asList("mattee", "shoboon", "waai", "dere", "tun");
        Collections.shuffle(feel);

        FeelDTO feelDTO = new FeelDTO();
        feelDTO.setResult(true);
        feelDTO.setFeel(feel.get(0));
        /* モックデータ作成終了 */

        return new ResponseEntity(feelDTO, HttpStatus.OK);
    }

}
