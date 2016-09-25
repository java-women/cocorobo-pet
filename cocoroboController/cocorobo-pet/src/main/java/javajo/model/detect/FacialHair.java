package javajo.model.detect;

import lombok.Data;

/**
 * 髪型Dto
 * using DetectAPI response
 * Created by user on 2016/09/13.
 */
@Data
public class FacialHair {

    Double mustache;
    Double beard;
    Double sideburns;

}
