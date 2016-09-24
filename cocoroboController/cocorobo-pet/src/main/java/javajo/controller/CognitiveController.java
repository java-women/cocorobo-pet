package javajo.controller;

import javajo.dto.CompareImageDTO;
import javajo.dto.SpeechDTO;
import javajo.enums.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javajo.dto.CompareImageDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * MS Azure CognitiveAPIとやりとりをするAPI.
 */
@RestController
@RequestMapping("/api")
public class CognitiveController {

    private final Logger log = LoggerFactory.getLogger(CognitiveController.class);

    /**
     * Androidから画像を受け取り結果を返す.
     *
     * @param userId  userId
     * @param faceImg 画像
     * @return JSON形式の文字列：<pre>{@code
     * {
     *     "result": 結果,
     *     "faceId": 顔ID
     * }
     * }</pre>
     */
    @PostMapping(value = "/registerImg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterImgDTO> registerImg(@RequestParam("userId") String userId, @RequestParam("faceImg") MultipartFile faceImg) {
        log.info("REST request registerImg : {}, faceImg : {}", userId, faceImg);

        /* モックデータ作成開始 */
        RegisterImgDTO registerImgDTO = new RegisterImgDTO();
        registerImgDTO.setResult(StatusEnum.OK.getName());
        registerImgDTO.setFaceId("aaaaaaaaaaaaaa");
        /* モックデータ作成終了 */

        return new ResponseEntity(registerImgDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/compareImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompareImageDTO> compareImage(@RequestParam("upload_file") MultipartFile multipartFile) {

        CompareImageDTO results = new CompareImageDTO();

        // ファイルが空の場合は異常終了
        if(multipartFile.isEmpty()){
            results.setResult(StatusEnum.NG.getName());
            return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }

        results.setResult(StatusEnum.OK.getName());

        return new ResponseEntity(results, HttpStatus.OK);
    }

}
