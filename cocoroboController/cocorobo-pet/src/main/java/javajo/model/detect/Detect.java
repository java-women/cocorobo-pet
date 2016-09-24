package javajo.model.detect;

import lombok.Data;

/**
 * DetectAPIの返却値Dto
 * Created by user on 2016/09/13.
 */
@Data
public class Detect {

    String faceId;
    FaceRectangle faceRectangle;
}
