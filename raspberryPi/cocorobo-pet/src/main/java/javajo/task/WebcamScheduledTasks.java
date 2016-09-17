package javajo.task;

import com.github.sarxos.webcam.Webcam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Webカメラ情報送信定期実行クラス,
 */
@Component
public class WebcamScheduledTasks {

	private final Logger log = LoggerFactory.getLogger(WebcamScheduledTasks.class);

	private final File filePath = new File(System.getProperty("java.io.tmpdir") + "cocorobo");

	// TODO ココロボの種別(とりあえず固定値)
	private final String cocorobo = "toko";

	/**
	 * 定期的にWebカメラ情報をココロボWebカメラ情報送信APIに送信.
	 *
	 * TODO initialDelayとfixedRateを外部化
	 *
	 * @throws IOException
	 */
	@Scheduled(initialDelay = 10000, fixedRate = 5000)
	public void sendWebcamCapture() throws IOException {
		File file = null;

		try {
			Webcam webcam = getWebcamInstance();

			// Webカメラオープン
			webcam.open();

			// イメージ取得
			BufferedImage image = webcam.getImage();

			// イメージ保存
			if (createTempDir()) {
				file = new File(filePath, String.format("cocorobo-capture_%s_%s.jpg", cocorobo, System.currentTimeMillis()));
				ImageIO.write(image, "jpeg", file);
				log.debug("Capture : {}", file.toPath());

				// TODO サーバにイメージを送る

			} else {
				log.warn("Failed: Unable to create temporary directory : {}", filePath);
			}

		} finally {
			// イメージ削除
			deleteFile(file);
		}
	}

	/**
	 * Webカメラのインスタンスを取得.
	 * <p>
	 * TODO 複数のWebカメラが接続されている場合はこのメソッドを修正
	 *
	 * @return Webcamインスタンス
	 */
	private Webcam getWebcamInstance() {
		Webcam webcam = null;

		// Webカメラ取得
		webcam = Webcam.getDefault();

		if (webcam != null) {
			log.debug("Webcam : {}", webcam.getName());

		} else {
			log.warn("Failed: Webcam Not Found Error");
			throw new NullPointerException("Failed: Webcam Not Found Error");
		}

		return webcam;
	}

	/**
	 * イメージ保存ディレクトリ作成.
	 *
	 * @return true:成功、false:失敗
	 */
	private boolean createTempDir() {
		boolean flag = false;

		if (!filePath.exists()) {
			if (filePath.mkdirs()) {
				flag = true;
				log.debug("Success to create temporary directory. directory : {}", filePath);
			} else {
				log.warn("Failed to create temporary directory. directory : {}", filePath);
			}
		} else {
			flag = true;
			log.debug("Temporary directory is exists.. directory : {}", filePath);
		}

		return flag;
	}

	/**
	 * ファイルを削除.
	 *
	 * TODO 履歴を残しておく場合はここを修正
	 *
	 * @param file 削除対象ファイル
	 */
	private void deleteFile(File file) {
		if (file != null && file.exists()) {
			if (file.delete()) {
				log.debug("Success to delete file. file : {}", file);
			} else {
				log.warn("Failed to delete file. file : {}", file);
			}
		}
	}
}
