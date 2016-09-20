package jp.javajo.domain.model.verify;

import lombok.Data;

/**
 * 同一人物判定結果Dto
 * using VerifyAPI response
 * Created by user on 2016/09/13.
 */
@Data
public class Verify {
    Boolean isIdentical;
    Double confidence;
}
