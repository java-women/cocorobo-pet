package javajo;

import javajo.dto.DetectRequest;
import javajo.dto.DetectResponse;
import javajo.dto.PhotoUrl;
import javajo.dto.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 画像を登録する
 */
@RestController
@RequestMapping("/registration")
public class RegistrationFaceController {

    private final String detectUrl = "https://api.projectoxford.ai/face/v1.0/detect";
    private final Logger log = LoggerFactory.getLogger(CocoroboController.class);

    /**
     * 画像を登録する
     *
     * @param 登録する写真のurl
     *
     *
     * @return 登録情報JSON
     */

    @GetMapping(value = "/photo", produces = MediaType.APPLICATION_JSON_VALUE)
    public DetectResponse registerFace(String url) {

        log.debug("REST request to register Face : {}");

        //リクエストの作成
        PhotoUrl photoUrl = new PhotoUrl();
        RequestBody body = new RequestBody();
        photoUrl.setPhotoUrl(url);
        body.setPhotoUrl(photoUrl);

        HttpHeaders headers = createHttpHeaders();


        DetectRequest requests = new DetectRequest(body, headers);

        return new DetectResponse();
    }


    private HttpHeaders createHttpHeaders() {
        ApiKey apiKey = new ApiKey();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Ocp-Apim-Subscription-Key", apiKey.getApiKey());

        return headers;
    }
}