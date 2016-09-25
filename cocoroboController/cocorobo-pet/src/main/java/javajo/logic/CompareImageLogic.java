package javajo.logic;

import javajo.dto.CompareImageDTO;
import javajo.enums.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Created by maaya on 2016/09/25.
 */
@Component
public class CompareImageLogic {

    /**
     * 同一人物判定閾値
     */
    private static final Double THRESHOLD = 0.4;

    /**
     * リクエストレスポンス
     * @return
     */
    public ResponseEntity createApiResponse(StatusEnum status) {
        CompareImageDTO results = new CompareImageDTO();
        results.setResult(status.getName());

        switch (status) {
            case OK:
                return new ResponseEntity<>(results, HttpStatus.OK);

            case NG:
                return new ResponseEntity<>(results, HttpStatus.OK);

            default:
                //すみません今のところ通る予定ないのでとりあえず・・・
                return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * エラーレスポンス
     * @return
     */
    public ResponseEntity responseError() {
        CompareImageDTO results = new CompareImageDTO();
        results.setResult(StatusEnum.NG.getName());
        return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
    }

    /**
     * 類似度が閾値以上だったら同一人物として判定する
     * @param confidence
     * @return
     */
    private boolean isIdentical(Double confidence) {
        if (confidence >= THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 正常値返却
     * @param confidence
     * @return
     */
    public ResponseEntity jadgeIdentical(Double confidence) {
        if(isIdentical(confidence)) {
            return createApiResponse(StatusEnum.OK);

        } else {
            return createApiResponse(StatusEnum.NG);
        }
    }
}
