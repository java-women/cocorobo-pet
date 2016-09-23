package javajo;

import javajo.dto.RequestVerify;
import javajo.dto.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ohashi on 2016/09/23.
 */
public class FaceAuthentication {

    private final Logger log = LoggerFactory.getLogger(FaceAuthentication.class);
    private final String verifyUrl = "https://api.projectoxford.ai/face/v1.0/verify";
    /**
     *@param 登録したfacceId1(String)
     *       登録したfaceId2(String)
     *
     *@return APIの実行結果JSON
     * {
            "isIdentical":boolean,
            "confidence":Number?
        }
     **/

    @GetMapping(value = "/face/compare", produces = MediaType.APPLICATION_JSON_VALUE)
    public Verify  compareFace(String faceId1, String faceId2) {
        log.debug("REST request to compare face : {}");

        //request bodyの作成
        RequestVerify postBody = new RequestVerify(); //body
        postBody.setFaceId1(faceId1);
        postBody.setFaceId2(faceId2);

        HttpHeaders headers = createHttpHeaders();//header

        HttpEntity<RequestVerify> request = new HttpEntity<>(postBody, headers);

        //APIを実行
        RestTemplate rt = new RestTemplate();
        return rt.postForObject(verifyUrl, request,Verify.class);

    }

    //HttpHeaderを作る

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        ApiKey apiKey = new ApiKey();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Ocp-Apim-Subscription-Key", apiKey.getApiKey());

        return headers;
    }


}
