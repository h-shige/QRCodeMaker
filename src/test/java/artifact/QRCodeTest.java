package artifact;

import com.google.zxing.WriterException;
import data.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeTest {

    @Nested
    @DisplayName("インスタンス生成テスト")
    class TestInstanceCreation{
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
        @DisplayName("引数にnullが含まれている場合は例外をスローする")
        void testThrowExceptionOnNullArgument() {
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
        @DisplayName("引数のfileNameが空白文字のみだった場合は例外をスローする")
        void testThrowExceptionOnWhitespaceFileName() {
            fileName = "";
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

        @Test
        @DisplayName("引数のwidthが0以下の場合例外をスローする")
        void testThrowExceptionOnNonPositiveWidth() {
            width = 0;
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

        @Test
        @DisplayName("引数のheightが0以下の場合例外をスローする")
        void testThrowExceptionOnNonPositiveHeight() {
            height = 0;
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

        @Test
        @DisplayName("引数にnullと空白文字が含まれておらずwidthとheightが0以上の場合は正常にインスタンスが生成される")
        void testInstanceCreationWithValidArguments() {
            height = 0;
            assertThrows(IllegalArgumentException.class,
                    () -> new QRCode(mail, fileName, width, height));
        }

    }

    @Nested
    @DisplayName("画像ファイル出力テスト")
    class TestImageFileOutput {
        QRCode qrCode;
        Mail mail;
        String fileName;
        int width;
        int height;


        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
            fileName = "/Users/yasu/playground/Java/QRCodeMaker/src/test/java/resource/img/test.jpg";
            width = 300;
            height = 300;
            qrCode = new QRCode(mail, fileName, width, height);
        }

        @Test
        @DisplayName("writeImageメソッドの実行後画像が生成される")
        void testImageGenerationAfterWriteImage() throws IOException, WriterException {
            //qrCode.writeImage()の前にフォルダが空であることを確認する必要がある
            qrCode.writeImage();
            File directory = new File("/Users/yasu/playground/Java/QRCodeMaker/src/test/java/resource/img/");
            assertEquals(1, directory.listFiles().length);
        }
    }

    @Nested
    @DisplayName("URL生成テスト")
    class TestURLGeneration {

        QRCode qrCode;
        Mail mail;

        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
            qrCode = new QRCode(mail, "fileName", 300, 300);
        }

        @Test
        @DisplayName("Mailクラスのインスタンスが下記コメントの状態であればURLが下記コメントの状態になる")
        void testURLGenerationWithMailInstanceInGivenState() {
            /*
            Mailクラス
            "toMail", "subject", "message"
            URL
            mailto:toMail?subject=subject&body=message
             */
            assertEquals("mailto:toMail?subject=subject&body=message", qrCode.getMail().createURL());
        }
    }

    @Nested
    @DisplayName("画像データ生成テスト")
    class TestImageGeneration {
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
        @DisplayName("createQRImageメソッドを呼び出すとBufferedImageクラスのインスタンスをを得られる")
        void testCreateQRImageReturnsBufferedImageInstance() throws WriterException {
            assertTrue(qrCode.createQRImage(url) instanceof BufferedImage);
        }
    }
}