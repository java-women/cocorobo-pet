package javajo.model.request;

import javajo.model.detect.Detect;
import lombok.Data;

/**
 * Created by maaya on 2016/09/25.
 */
@Data
public class RequestRedisValue {
    private int width;
    private int height;
    private Detect[] detects;
}
