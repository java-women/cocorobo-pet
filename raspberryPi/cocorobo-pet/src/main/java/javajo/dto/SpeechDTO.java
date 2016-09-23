package javajo.dto;

import lombok.Data;

/**
 * 発話用DTO.
 */
@Data
public class SpeechDTO {

    // 結果コード
    private int resultCode;

    // レスポンスデータ詳細(子要素はnull)
    private String data;

    // エラーコード
    private String errorCode;

    // エラーメッセージ
    private String errorMessage;
}
