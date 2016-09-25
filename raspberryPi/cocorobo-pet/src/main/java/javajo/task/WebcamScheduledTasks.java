package javajo.task;

import javajo.config.Constants;
import javajo.dto.CompareImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

/**
 * Webカメラ情報送信定期実行クラス,
 */
@Component
public class WebcamScheduledTasks {

	private final Logger log = LoggerFactory.getLogger(WebcamScheduledTasks.class);

	private final File filePath = new File(System.getProperty("java.io.tmpdir") + "/cocorobo");

	/**
	 * 定期的にWebカメラ情報をココロボWebカメラ情報送信APIに送信.
	 *
	 * TODO initialDelayとfixedRateを外部化
	 *
	 * @throws IOException
	 */
	@Scheduled(initialDelay = 10000, fixedRate = 10000)
	public void sendWebcamCapture() throws IOException {
		// ペットモードでない場合は写真を撮らない
		if (!Constants.isPetMode()) {
			log.debug("Not pet mode.");
			return;
		}

		if (createTempDir()) {
			File file = new File(filePath, String.format("cocorobo-capture_%s_%s.jpg", Constants.cocorobo, System.currentTimeMillis()));

			ProcessBuilder pb = new ProcessBuilder("fswebcam", "--no-timestamp", "--no-banner", file.getPath());
			Process p = null;

			try {
				// キャプチャを取得
				p = pb.start();
				p.waitFor();
				log.debug("Capture : {}", file.toPath());

				// サーバにイメージを送る
				//HttpHeaderの作成
				HttpHeaders headers = Constants.getMultiPartHttpHeaders();

				//request用Entity作成
				MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
				parts.add("compareFile", new FileSystemResource(file));
				HttpEntity<MultiValueMap> request = new HttpEntity<>(parts, headers);

				// CocoroboApiの画像比較APIを実行.
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.postForObject(Constants.COMPARE_IMAGE_URL, request, CompareImageDTO.class);

			} catch (IOException | InterruptedException e) {
				log.error("Failed to take the capture.", e);

			} finally {
				// イメージ削除
				deleteFile(file);

				// プロセス破棄
				if (p != null) {
					p.destroy();
				}

			}
		}
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
