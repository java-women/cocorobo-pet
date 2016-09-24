package javajo.model.detect;

import lombok.Data;

/**
 * 顔からの人物推定Dto
 * using DetectAPI response
 * Created by user on 2016/09/13.
 */
@Data
public class FaceAttributes {

    Double age;
    String gender;
    String smile;
    FacialHair facialHair;
    String glasses;
    HeadPose headPose;

}
