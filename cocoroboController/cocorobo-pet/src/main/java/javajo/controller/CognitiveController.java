package javajo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import javajo.dto.CompareImageDTO;
import javajo.dto.RegisterImgDTO;
import javajo.enums.StatusEnum;
import javajo.hepler.*;
import javajo.logic.RegisterImageLogic;
import javajo.model.detect.Detect;
import javajo.model.request.RequestRedisValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * MS Azure CognitiveAPIとやりとりをするAPI.
 */
@RestController
@RequestMapping("/api")
public class CognitiveController {

    private final Logger log = LoggerFactory.getLogger(CognitiveController.class);

    @Autowired
    private RegisterImageLogic logic;

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
    @PostMapping(value = "/registerImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerImage(@RequestParam("userId") String userId, @RequestParam("faceImage") MultipartFile faceImg) throws IOException{
        log.info("REST request registerImage : {}, faceImage : {}", userId, faceImg);

        //画像をローカルに保存
        String uploadFile;
        try {
            uploadFile = new FileHelper().saveFile(faceImg);
        } catch (IOException e) {
            return logic.responseError();
        }

        //受け取った写真データをDetectAPIに送付
        Detect[] faceInfo = new CognitiveHelper().makeFaceInfo(uploadFile);

        //JSONデータ作成
        RequestRedisValue redisModel = logic.createRedisValue(uploadFile, faceInfo);

        //DetectAPIのデータをredisに登録
        RedisHelper redis = new RedisHelper();
        try{
            redis.setKeyValue("user", new JacsonHelper().objectForJson(redisModel));
        } catch (JsonProcessingException e) {
            return logic.responseError();
        } finally {
            redis.disconnect();
        }

        //ハッカソンなのでとりあえず1人目の情報だけ・・・
        return logic.createApiResponse(faceInfo[0].getFaceId());
    }

    @PostMapping(value = "/compareImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompareImageDTO> compareImage(@RequestParam("compareFile") MultipartFile multipartFile) {
        CompareImageDTO results = new CompareImageDTO();

        // ファイルが空の場合は異常終了
        if(multipartFile.isEmpty()){
            results.setResult(StatusEnum.NG.getName());
            return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }


        //受け取った写真データをDetectAPIに送付

        //Redisから教師データを取得

        //varifyAPI

        //判定結果返却

        results.setResult(StatusEnum.OK.getName());

        return new ResponseEntity(results, HttpStatus.OK);
    }

}
