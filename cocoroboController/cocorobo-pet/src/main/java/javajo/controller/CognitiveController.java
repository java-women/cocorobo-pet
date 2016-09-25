package javajo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import javajo.dto.CompareImageDTO;
import javajo.hepler.CognitiveHelper;
import javajo.hepler.FileHelper;
import javajo.hepler.JacsonHelper;
import javajo.hepler.RedisHelper;
import javajo.logic.CompareImageLogic;
import javajo.logic.RegisterImageLogic;
import javajo.model.detect.Detect;
import javajo.model.redis.RedisValue;
import javajo.model.verify.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RegisterImageLogic registerImageLogicimageLogic;
    @Autowired
    private CompareImageLogic compareImageLogic;

    private static final String REDIS_KEY = "user";

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
    public ResponseEntity registerImage(@RequestParam("userId") String userId, @RequestParam("faceImage") MultipartFile faceImg) throws IOException {
        log.info("REST request registerImage : {}, faceImage : {}", userId, faceImg);

        //画像をローカルに保存
        String uploadFile;
        try {
            uploadFile = new FileHelper().saveFile(faceImg);
        } catch (IOException e) {
            return registerImageLogicimageLogic.responseError();
        }

        //受け取った写真データをDetectAPIに送付
        Detect[] faceInfo = new CognitiveHelper().makeFaceInfo(uploadFile);

        //JSONデータ作成
        RedisValue redisModel = registerImageLogicimageLogic.createRedisValue(uploadFile, faceInfo);

        //DetectAPIのデータをredisに登録
        RedisHelper redis = new RedisHelper();
        try {
            redis.deleteKey(REDIS_KEY);
            redis.setKeyValue(REDIS_KEY, new JacsonHelper().objectForJson(redisModel));
        } catch (JsonProcessingException e) {
            return registerImageLogicimageLogic.responseError();
        } finally {
            redis.disconnect();
        }

        //ハッカソンなのでとりあえず1人目の情報だけ・・・
        return registerImageLogicimageLogic.createApiResponse(faceInfo[0].getFaceId());
    }

    @PostMapping(value = "/compareImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompareImageDTO> compareImage(@RequestParam("compareFile") MultipartFile faceImg) throws IOException {
        // ファイルが空の場合は異常終了
        if (faceImg.isEmpty()) {
            return compareImageLogic.responseError();
        }

        //画像をローカルに保存
        String uploadFile;
        try {
            uploadFile = new FileHelper().saveFile(faceImg);
        } catch (IOException e) {
            return compareImageLogic.responseError();
        }

        //受け取った写真データをDetectAPIに送付
        Detect[] faceInfo = new CognitiveHelper().makeFaceInfo(uploadFile);

        //Redisから教師データを取得
        RedisHelper redis = new RedisHelper();
        RedisValue sourceFaceInfo;
        try {
            sourceFaceInfo = new JacsonHelper().jsonForRedisValue(redis.getKeyValue(REDIS_KEY));
        } finally {
            redis.disconnect();
        }

        //varifyAPI
        //とりあえず1人目の情報だけ・・・
        Verify verify = new CognitiveHelper().compare2Face(faceInfo[0].getFaceId(), sourceFaceInfo.getDetects()[0].getFaceId());

        //判定結果返却
        return compareImageLogic.jadgeIdentical(verify.getConfidence());
    }

}
