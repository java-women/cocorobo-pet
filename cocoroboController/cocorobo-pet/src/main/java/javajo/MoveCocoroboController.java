package javajo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javajo.dto.DirectionDTO;

/**
* 動作API
 */

public class MoveCocoroboController{
    /**
     * 画像を受け取って、人の位置を判定し、ココロボが進む方向を決定する
     *
     * @param
     * @return apiKey(String)
     *         mode(String)
     * modeの種類
     * forward	COCOROBOが進行方向に進む。
     * left     COCOROBOがその場で左回転する。
     * right	COCOROBOがその場で右回転する。
     * stop	    COCOROBOが停止する。
     * gohome   COCOROBOがドックへ帰還を始める。
     *
     */

    getMovemontDirection(String apiKey){
        /*画像から方向を決める処理を書く*/

        /*モックデータ*/
        List<String> dirction = Arrays.asList("forward", "left", "rigth", "stop", "gohome");
        Collections.shuffle(dirction);

        DirectionDTO directionDTO = new DirectionDTO();
        directionDTO.setApiKey(apiKey);
        directionDTO.setDirection(direction.get(0));


        return ResponseEntity(directionDTO.getApikey, directionDTO.getDirection);

    }
}