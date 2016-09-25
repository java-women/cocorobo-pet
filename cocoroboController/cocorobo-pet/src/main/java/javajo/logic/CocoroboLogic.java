package javajo.logic;

import javajo.enums.MoveCommandEnum;
import javajo.hepler.JacsonHelper;
import javajo.hepler.RedisHelper;
import javajo.model.detect.Detect;
import javajo.model.request.RequestRedisValue;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
	 * TODO 写真に複数人映っていても今回は1人目だけを判定して移動方向を決定する.
	 *
	 * @return 移動方向
	 */
	public String getMoveDirection() throws IOException {

		RequestRedisValue redisValue = getRequestRedisValue();

		String moveCommand = MoveCommandEnum.STOP.getName();
		if (redisValue.getDetects() != null) {
			// TODO 1人目の位置を取得
			Detect detect = redisValue.getDetects()[0];

			// 顔の中心を求める
			double faceCenter = detect.getFaceRectangle().getLeft() + (detect.getFaceRectangle().getWidth() / 2.0);

			if (faceCenter == (redisValue.getWidth() / 2)) {
				moveCommand = MoveCommandEnum.FORWARD.getName();
			} else if (faceCenter < (redisValue.getWidth() / 2)) {
				moveCommand = MoveCommandEnum.LEFT.getName();
			} else if (faceCenter > (redisValue.getWidth() / 2)) {
				moveCommand = MoveCommandEnum.RIGHT.getName();
			} else {
				moveCommand = MoveCommandEnum.GOHOME.getName();
			}
		}

		return moveCommand;
	}

	/**
	 * RequestRedisValueを取得.
	 *
	 * @return
	 * @throws IOException
	 */
	private RequestRedisValue getRequestRedisValue() throws IOException {
		// userに紐づく情報を取得
		RedisHelper redisHelper = new RedisHelper();
		String userInfo = redisHelper.getKeyValue("user");
		redisHelper.hashCode();

		// Jsonに変換
		JacsonHelper jacsonHelper = new JacsonHelper();
		RequestRedisValue redisValue = jacsonHelper.stringToJson(userInfo);

		return redisValue;
	}
}
