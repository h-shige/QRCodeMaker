package artifact;

import com.google.zxing.WriterException;
import data.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeTest {

    @Nested
    class インスタンス生成テスト {
        Mail mail;
        String fileName;
        int width;
        int height;

        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
            fileName = fileName;
            width = 300;
            height = 300;
        }

        @Test
        void 引数にnullが含まれている場合は例外をスローする() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new QRCode(null, fileName, width, height)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new QRCode(mail, null, width, height)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new QRCode(null, null, width, height))
            );
        }

        @Test
        void 引数のfileNameが空白文字のみだった場合は例外をスローする() {
            fileName = "";
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

        @Test
        void 引数のwidthが0以下の場合例外をスローする() {
            width = 0;
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

        @Test
        void 引数のheightが0以下の場合例外をスローする() {
            height = 0;
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

        @Test
        void 引数にnullと空白文字が含まれておらずwidthとheightが0以上の場合は正常にインスタンスが生成される() {
            height = 0;
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

    }

    @Nested
    class 画像ファイル出力テスト {
        QRCode qrCode;
        Mail mail;
        String fileName;
        int width;
        int height;


        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
            fileName = "C:\\Users\\SNPC1054\\IdeaProjects\\QRCodeMaker\\test\\resource\\img\\test.jpg";
            width = 300;
            height = 300;
            qrCode = new QRCode(mail, fileName, width, height);
        }

        @Test
        void writeImageメソッドの後画像が生成される() throws IOException, WriterException {
            //qrCode.writeImage()の前にフォルダが空であることを確認する必要がある
            qrCode.writeImage();
            File directory = new File("C:\\Users\\SNPC1054\\IdeaProjects\\QRCodeMaker\\test\\resource\\img\\");
            assertEquals(1, directory.listFiles().length);
        }
    }

    @Nested
    class URL生成テスト {

        QRCode qrCode;
        Mail mail;

        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
            qrCode = new QRCode(mail, "fileName", 300, 300);
        }

        @Test
        void Mailクラスのインスタンスが下記コメントの状態であればURLが下記コメントの状態になる() {
            /*
            Mailクラス
            "toMail", "subject", "message"
            URL
            mailto:toMail?subject=subject&body=message
             */
            assertEquals("mailto:toMail?subject=subject&body=message", qrCode.createURL(mail));
        }
    }

    @Nested
    class 画像データ生成テスト {
        String url;
        Mail mail;
        QRCode qrCode;
        int width;
        int height;
        String fileName;

        @BeforeEach
        void setUp() {
            url = "mailto:toMail?subject=subject&body=message";
            mail = new Mail("toMail", "subject", "message");
            fileName = "FileName";
            width = 300;
            height = 300;
            qrCode = new QRCode(mail, fileName, width, height);
        }

        @Test
        void createQRImageメソッドを呼び出すとBufferedImageクラスyのインスタンスをを得られる() throws WriterException {
            assertTrue(qrCode.createQRImage(url) instanceof BufferedImage);
        }
    }
}