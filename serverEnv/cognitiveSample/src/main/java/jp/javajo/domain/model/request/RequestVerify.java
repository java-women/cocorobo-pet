package jp.javajo.domain.model.request;

import lombok.Data;

/**
 * VarifyAPIリクエストクラス
 * using VarifyAPI request
 * Created by user on 2016/09/13.
 */
@Data
public class RequestVerify {
    String faceId1;
    String faceId2;
}
