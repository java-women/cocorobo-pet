package javajo.logic;

import javajo.enums.MoveCommandEnum;
import javajo.model.detect.Detect;
import org.springframework.stereotype.Component;

/**
 * COCOROBO用のロジッククラス.
 *
 * Created by Eriko on 2016/09/25.
 */
@Component
public class CocoroboLogic {

	/**
	 * Cocoroboの移動方向を取得.
	 *
	 * @return 移動方向
	 */
	public String getMoveDirection() {
		// TODO Detectを取得する
		Detect detect = null;

		// 顔の中心を求める
		double center = detect.getFaceRectangle().getLeft() + detect.getFaceRectangle().getWidth() / 2.0;

		if (center < (detect.getWidth() / 2)) {
			// 左
			MoveCommandEnum.LEFT.getName();
		}

		return null;
	}

}
