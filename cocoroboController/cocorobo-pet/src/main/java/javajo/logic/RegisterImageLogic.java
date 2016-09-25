package javajo.logic;

import javajo.dto.RegisterImgDTO;
import javajo.enums.StatusEnum;
import javajo.hepler.FileHelper;
import javajo.hepler.ImageHelper;
import javajo.model.detect.Detect;
import javajo.model.redis.RedisValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by maaya on 2016/09/25.
 */
@Component
public class RegisterImageLogic {

    /**
     * エラーレスポンス
     *
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
    public RedisValue createRedisValue(String uploadFile, Detect[] faceInfo) throws IOException{
        RedisValue redisModel = new RedisValue();
        ImageHelper image = new ImageHelper(FileHelper.DIR_PATH + uploadFile);
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
        results.setFaceId(faceId);
        results.setResult(StatusEnum.OK.getName());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
