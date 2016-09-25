package javajo.controller;

import javajo.dto.DirectionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
* 動作API
 */

public class MoveCocoroboController{
    /**
     * 画像を受け取って、人の位置を判定し、ココロボが進む方向を決定する
     *
     * @param cocorobo ココロボの種類(String)
     * @param userId ユーザID(String)
     * @param url 写真(String)
     *
     * @return modeの種類
     * modeの種類
     * forward	COCOROBOが進行方向に進む。
     * left     COCOROBOがその場で左回転する。
     * right	COCOROBOがその場で右回転する。
     * stop	    COCOROBOが停止する。
     * gohome   COCOROBOがドックへ帰還を始める。
     *
     */

    @GetMapping(value = "/direction/{cocorobo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMovemontDirection(@PathVariable String cocorobo, String userId, String url){
        /*画像から方向を決める処理を書く*/

        /*モックデータ*/
        List<String> direction = Arrays.asList("forward", "left", "rigth", "stop", "gohome");
        Collections.shuffle(direction);

        DirectionDTO directionDTO = new DirectionDTO();
        directionDTO.setDirection(direction.get(0));


        return new ResponseEntity(directionDTO, HttpStatus.OK);

    }
}