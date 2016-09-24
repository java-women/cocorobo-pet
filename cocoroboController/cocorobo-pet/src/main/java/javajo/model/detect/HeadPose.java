package javajo.model.detect;

import lombok.Data;

/**
 * ポージングDto
 * using DetectAPI response
 * Created by user on 2016/09/13.
 */
@Data
public class HeadPose {

    Double roll;
    Integer yaw;
    Integer pitch;
    
}
