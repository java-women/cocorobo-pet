package jp.javajo.controller;

import jp.javajo.domain.model.detect.Detect;
import jp.javajo.domain.model.request.RequestDetect;
import jp.javajo.domain.model.request.RequestVerify;
import jp.javajo.domain.model.verify.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * 顔認証クラス
 * Created by maaya
 */
@Controller
@RequestMapping("/comparePhoto")
public class ComparePhotoController {
    private static final Logger logger = LoggerFactory.getLogger(ComparePhotoController.class);

    //Face API
    private final String apiKey = "xxxxxxx YOUR API KEY xxxxxxxx";
    private final String varifyUrl = "https://api.projectoxford.ai/face/v1.0/verify";
    private final String detectUrl = "https://api.projectoxford.ai/face/v1.0/detect";

    //比較写真(サンプルなのでとりあえず固定値)
    private final String face1Photo = "https://lh4.googleusercontent.com/--GpNLlqUzww/AAAAAAAAAAI/AAAAAAAAEZo/IMO7HAJ-XNk/photo.jpg";
    private final String face2Photo = "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAltAAAAJGI3NzdhOWY3LWExYjctNDFmZC05NjdmLWRjYmIxZWJlMmU0MQ.jpg";

    /**
     * 実行クラス
     * ①face.detectAPIに画像を登録する
     * ②face.varifyAPIで画像を比較する
     * ③比較結果を出力する
     */
    @RequestMapping(method = RequestMethod.GET)
    String compare(@RequestParam String area, Model model) {
        logger.debug("画像比較します");

        Detect face1 = makeFaceInfo(face1Photo);
        Detect face2 = makeFaceInfo(face2Photo);

        Verify result = compare2Face(face1.getFaceId(), face2.getFaceId());

        return makeResultStr(result.getIsIdentical(), result.getConfidence());
    }


    /**
     * DetectAPIの実行
     * @param photoUrl 登録したい画像のURL
     * @return 登録写真情報
     */
    private Detect makeFaceInfo(String photoUrl) {
        logger.debug("Face情報の登録をします");

        RequestDetect postBody = new RequestDetect();
        postBody.setUrl(photoUrl);

        RestTemplate rt = new RestTemplate();
        return rt.postForObject(detectUrl, postBody, Detect.class);
    }

    /**
     * VarifyAPIの実行
     * @param faceId1 1つ目の写真のfaceId
     * @param faceId2 2つ目の写真のfaceId
     * @return 同一人物判定結果
     */
    private Verify compare2Face(String faceId1, String faceId2) {
        logger.debug("同一人物判定をします");

        RequestVerify postBody = new RequestVerify();
        postBody.setFaceId1(faceId1);
        postBody.setFaceId2(faceId2);

        RestTemplate rt = new RestTemplate();
        return rt.postForObject(varifyUrl, postBody, Verify.class);
    }

    /**
     * 結果返却用の文字列を作成
     * @param isIdentical 同一人物判定 true: 同一人物 false: 別人判定
     * @param confidence 類似度
     * @return 表示文字列
     */
    private String makeResultStr(Boolean isIdentical, Double confidence) {
        String decision;
        if(isIdentical) {
            decision = "同一人物です";
        } else {
            decision = "別人です";
        }

        return "2枚の写真に写ってる人は" + decision + "。類似度は " + confidence + " です。";
    }
}
