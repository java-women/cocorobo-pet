package javajo;

import javajo.dto.DetecctRequest;
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
     * @param 登録する画像のURL
     *
     * @return 登録情報JSON
     */

    @GetMapping(value = "/photo", produces = MediaType.APPLICATION_JSON_VALUE)
    public DetectResponse getFeel(String url) {

        log.debug("REST request to get Feel : {}", cocorobo);

        //リクエストの作成
        PhotoUrl photoUrl = new PhotoUrl();
        RequestBody body = new RequestBody();
        body.setPhotoUrl(photoUrl.setPhotoUrl(url));

        HttpHeaders headers = createHttpHeaders();


        DetectRequests requests = new DetectRequests(body, headers);

        return new DetectResponse(detectUrl,requests);
    }


    private HttpHeaders createHttpHeaders() {
        Apikey apiKey = new ApiKey();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Ocp-Apim-Subscription-Key", apiKey.getApiKey);

        return headers;
    }
}