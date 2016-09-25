package javajo.dto;

import lombok.Data;

/**
 * 動作API用DTO.
 */
@Data
public class MoveDTO {

    // 実行結果
    public String result;

    public String moveCommand;

}
