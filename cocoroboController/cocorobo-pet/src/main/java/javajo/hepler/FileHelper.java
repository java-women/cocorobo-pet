package javajo.hepler;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * ファイル操作
 * Created by maaya on 2016/09/25.
 */
public class FileHelper {

    private File uploadDirectory;
    public static final String DIR_PATH = "webapps/cocorobo-pet/WEB-INF/classes/static/";

    /**
     * ファイルの保存
     * @param multipartFile formから設定された画像ファイル
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String name = UUID.randomUUID().toString() + ".jpg";
        File uploadFile = new File(uploadDirectory, DIR_PATH + name);

        // (2)
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);

        return name;

    }
}
