package javajo.hepler;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by maaya on 2016/09/25.
 */
public class ImageHelper {
    private static BufferedImage read;

    /**
     * 画像読み込み
     * @param fileName
     * @throws IOException
     */
    public ImageHelper(String fileName) throws IOException{
        read = ImageIO.read(new File(fileName));
    }

    /**
     * 横幅の取得
     * @return
     * @throws IOException
     */
    public int getWidth() throws IOException{
        return read.getWidth();
    }

    /**
     * 縦幅の取得
     * @return
     * @throws IOException
     */
    public int getHeight() throws IOException {
        return read.getHeight();
    }
}
