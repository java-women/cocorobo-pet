package javajo.hepler;

import javajo.model.detect.Detect;
import javajo.model.request.RequestDetect;
import javajo.model.request.RequestVerify;
import javajo.model.verify.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Serviceクラスとしていますが、DB接続サービス層ではありません。
 * 今回はCognitiveAPiとの接続サービス層
 * Created by user on 2016/09/20.
 */
public class CognitiveHelper {

    private static final Logger logger = LoggerFactory.getLogger(CognitiveHelper.class);

    //Face API
    @Value("${javajo.cognitive.apiKey}")
    private String apiKey;
    @Value("${javajo.cognitive.verifyUrl}")
    private String verifyUrl;
    @Value("${javajo.cognitive.detectUrl}")
    private String detectUrl;
    @Value("${javajo.cognitive.photoDomain}")
    private String photoDomain;

    /**
     * DetectAPIの実行
     *
     * @param photoUrl 登録したい画像のURL
     * @return 登録写真情報
     */
    public Detect[] makeFaceInfo(String photoUrl) {
        logger.debug("Face情報の登録をします: " + photoUrl);

        //request bodyの作成
        RequestDetect postBody = new RequestDetect();
        postBody.setUrl(photoDomain + photoUrl);

        //HttpHeaderの作成
        HttpHeaders headers = createHttpHeaders();

        //request用Entity作成
        HttpEntity<RequestDetect> request = new HttpEntity<>(postBody, headers);

        //API実行
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return rt.postForObject(detectUrl, request, Detect[].class);
    }

    /**
     * VarifyAPIの実行
     *
     * @param faceId1 1つ目の写真のfaceId
     * @param faceId2 2つ目の写真のfaceId
     * @return 同一人物判定結果
     */
    public Verify compare2Face(String faceId1, String faceId2) {
        logger.debug("同一人物判定をします");

        //request bodyの作成
        RequestVerify postBody = new RequestVerify();
        postBody.setFaceId1(faceId1);
        postBody.setFaceId2(faceId2);

        //HttpHeaderの作成
        HttpHeaders headers = createHttpHeaders();

        //request用Entity作成
        HttpEntity<RequestVerify> request = new HttpEntity<>(postBody, headers);

        //API実行
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return rt.postForObject(verifyUrl, request, Verify.class);
    }

    /**
     * 結果返却用の文字列を作成
     *
     * @param isIdentical 同一人物判定 true: 同一人物 false: 別人判定
     * @param confidence  類似度
     * @return 表示文字列
     */
    public String makeResultStr(Boolean isIdentical, Double confidence) {
        String decision;
        if (isIdentical) {
            decision = "同一人物です";
        } else {
            decision = "別人です";
        }

        return "2枚の写真に写ってる人は" + decision + "。類似度は " + confidence + " です。";
    }

    /**
     * CognitiveAPIのHttpHeaderを作成します
     *
     * @return Headers
     */
    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Ocp-Apim-Subscription-Key", apiKey);

        return headers;
    }
}
