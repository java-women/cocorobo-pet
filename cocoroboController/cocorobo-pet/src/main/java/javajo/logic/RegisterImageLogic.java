package javajo.logic;

import javajo.dto.RegisterImgDTO;
import javajo.enums.StatusEnum;
import javajo.hepler.ImageHelper;
import javajo.model.detect.Detect;
import javajo.model.request.RequestRedisValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Created by maaya on 2016/09/25.
 */
public class RegisterImageLogic {

    /**
     * エラーレスポンス
     * @return
     */
    public ResponseEntity responseError() {
        RegisterImgDTO results = new RegisterImgDTO();
        results.setResult(StatusEnum.NG.getName());
        return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
    }

    /**
     * Redisデータ作成
     * @param uploadFile
     * @param faceInfo
     * @return
     * @throws IOException
     */
    public RequestRedisValue createRedisValue(String uploadFile, Detect[] faceInfo) throws IOException{
        RequestRedisValue redisModel = new RequestRedisValue();
        ImageHelper image = new ImageHelper(uploadFile);
        redisModel.setDetects(faceInfo);
        redisModel.setWidth(image.getWidth());
        redisModel.setHeight(image.getWidth());

        return redisModel;
    }

    /**
     * OKレスポンス
     * @param faceId
     * @return
     */
    public ResponseEntity createApiResponse(String faceId) {
        RegisterImgDTO results = new RegisterImgDTO();
        results.setResult(StatusEnum.OK.getName());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
