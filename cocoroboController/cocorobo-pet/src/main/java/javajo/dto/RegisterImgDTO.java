package javajo.dto;

import lombok.Data;

/**
 * 画像登録DTO.
 */
@Data
public class RegisterImgDTO {

    // 結果コード
    private String result;

    // 顔ID
    private String faceId;
}
