package javajo.hepler;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * ファイル操作
 * Created by maaya on 2016/09/25.
 */
public class FileHelper {

    private File uploadDirectory;
    private static final String NAME = "masterUser.jpg";

    /**
     * ファイルの保存
     * @param multipartFile formから設定された画像ファイル
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        File uploadFile =
                new File(uploadDirectory, NAME);

        // (2)
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);

        return NAME;

    }
}
