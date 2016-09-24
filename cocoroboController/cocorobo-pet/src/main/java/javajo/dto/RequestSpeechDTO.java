package javajo.dto;

import lombok.Data;

/**
 * 発話リクエスト用DTO.
 */
@Data
public class RequestSpeechDTO {

    // COCOROBO API用APIキー文字列
    private String apikey_cocorobo;

    // COCOROBO に発話させるメッセージの内容
    private String message;

}
