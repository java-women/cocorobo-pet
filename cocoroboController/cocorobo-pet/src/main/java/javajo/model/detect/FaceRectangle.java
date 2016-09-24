package javajo.model.detect;

import lombok.Data;

/**
 * 顔アングルDto
 * using DetectAPI response
 * Created by user on 2016/09/13.
 */
@Data
public class FaceRectangle {

    Double top;
    Double left;
    Double width;
    Double height;
}
