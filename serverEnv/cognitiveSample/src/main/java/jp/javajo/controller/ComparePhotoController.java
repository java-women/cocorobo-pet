package jp.javajo.controller;

import jp.javajo.domain.model.detect.Detect;
import jp.javajo.domain.model.verify.Verify;
import jp.javajo.service.CognitiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 顔認証クラス
 * Created by maaya
 */
@RestController
@RequestMapping("/comparePhoto")
public class ComparePhotoController {
    private static final Logger logger = LoggerFactory.getLogger(ComparePhotoController.class);

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
    String compare() {
        logger.debug("画像比較します");

        CognitiveService service = new CognitiveService();
        Detect[] face1 = service.makeFaceInfo(face1Photo);
        Detect[] face2 = service.makeFaceInfo(face2Photo);

        //2枚の写真を比較
        //TODO 今回は必ず一人写っている写真＆一人しか映ってない写真なので配列数は1ですが、実際稼働させる場合は全配列分比較処理を行う必要があります
        Verify result = service.compare2Face(face1[0].getFaceId(), face2[0].getFaceId());

        return service.makeResultStr(result.getIsIdentical(), result.getConfidence());
    }
}
