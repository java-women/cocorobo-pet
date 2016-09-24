package javajo.model.detect;

import lombok.Data;

/**
 * 顔のパーツ情報Dto
 * using DetectAPI response
 * Created by user on 2016/09/13.
 */
@Data
public class FaceLandmarks {

    Position pupilLeft;
    Position pupilRight;
    Position noseTip;
    Position mouthLeft;
    Position mouthRight;
    Position eyebrowLeftOuter;
    Position eyebrowLeftInner;
    Position eyeLeftOuter;
    Position eyeLeftTop;
    Position eyeLeftBottom;
    Position eyeLeftInner;
    Position eyebrowRightInner;
    Position eyebrowRightOuter;
    Position eyeRightInner;
    Position eyeRightTop;
    Position eyeRightBottom;
    Position eyeRightOuter;
    Position noseRootLeft;
    Position noseRootRight;
    Position noseLeftAlarTop;
    Position noseRightAlarTop;
    Position noseLeftAlarOutTip;
    Position noseRightAlarOutTip;
    Position upperLipTop;
    Position upperLipBottom;
    Position underLipTop;
    Position underLipBottom;

}
