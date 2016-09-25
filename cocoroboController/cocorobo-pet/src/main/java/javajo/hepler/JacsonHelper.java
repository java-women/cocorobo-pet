package javajo.hepler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javajo.dto.CompareImageDTO;

import java.io.IOException;

/**
 * Created by maaya on 2016/09/25.
 */
public class JacsonHelper {

    /**
     * Object -> Json
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String objectForJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }


 }
