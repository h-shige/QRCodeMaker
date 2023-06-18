package artifact;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import data.Mail;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * QRコード作成クラス
 */
public class QRCode {

    /**
     * メールの内容を保持したMailクラス
     */
    private final Mail mail;

    /**
     * 保存するQRコードのファイル名
     */
    private final String fileName;

    /**
     * QRコードのサイズ(横幅)
     */
    private final int width;
    /**
     * QRコードのサイズ(高さ)
     */
    private final int height;


    /**
     * @param mail     メールの内容を保持したMailクラス
     * @param fileName 保存するQRコードのファイル名
     * @param width    QRコードのサイズ(横幅)
     * @param height   QRコードのサイズ(高さ)
     */
    public QRCode(final Mail mail, final String fileName,
                  final int width, final int height) {
        if (fileName == null || mail == null) throw new IllegalArgumentException("引数が不正です");
        if (fileName.isEmpty()) throw new IllegalArgumentException("引数が不正です");
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("引数が不正です");
        this.mail = mail;
        this.fileName = fileName;
        this.width = width;
        this.height = height;
    }

    /**
     * QRコード出力メソッド
     *
     * @throws WriterException
     * @throws IOException     QRコードの画像ファイルが出力できない場合の例外
     */
    public void writeImage() throws WriterException, IOException {
        String url = createURL(this.mail);
        BufferedImage qrImage = createQRImage(url);
        File qrFile = new File(this.fileName);
        ImageIO.write(qrImage, "jpg", qrFile);
    }

    /**
     * QRコードの画像作成メソッド
     *
     * @param url 作成するQRコードのURL
     * @return 作成したQRコードの画像データ(BufferedImageクラスのインスタンス)
     * @throws WriterException
     */
    BufferedImage createQRImage(final String url) throws WriterException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, this.width, this.height, hintMap);
        BufferedImage qrImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        qrImage.createGraphics();
        for (int j = 0; j < this.width; j++) {
            for (int k = 0; k < this.height; k++) {
                qrImage.setRGB(j, k, bitMatrix.get(j, k) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return qrImage;
    }

    /**
     * QRコード作成用URL作成メソッド
     *
     * @param mail 宛先、件名、本文を持ったメールクラス
     * @return QRコード作成用URL
     */
    String createURL(final Mail mail) {
        return "mailto:" + mail.getToMail() + "?subject=" + mail.getSubject() + "&body=" + mail.getMessage();
    }

    /**
     * インスタンス変数Mailのgetter
     *
     * @return このインスタンスが持つMailクラスのインスタンス
     */
    public Mail getMail() {
        return mail;
    }

    /**
     * 自動生成したtoStringメソッド
     * @return オブジェクトの中身
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QRCode{");
        sb.append("mail=").append(mail);
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append('}');
        return sb.toString();
    }
}
