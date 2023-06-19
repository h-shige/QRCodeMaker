package resourceloader;

import artifact.QRCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XlsxFileLoaderTest {
    @Nested
    @DisplayName("インスタンス生成テスト")
    class TestInstanceCreation{
        String inputPath;

        @Test
        @DisplayName("引数がnullの場合は例外をスローする")
        void testThrowExceptionOnNullArgument() {
            inputPath = null;
            assertThrows(IllegalArgumentException.class, () -> new XlsxFileLoader(inputPath));
        }

        @Test
        @DisplayName("引数が空文字の場合は例外をスローする")
        void testThrowExceptionOnEmptyStringArgument() {
            inputPath = "";
            assertThrows(IllegalArgumentException.class, () -> new XlsxFileLoader(inputPath));
        }

        @Test
        @DisplayName("引数がnullではないかつ空文字ではない場合は正常にインスタンスが生成される")
        void testInstanceCreationWithNonEmptyArguments() {
            inputPath = "inputPath";
            try {
                new XlsxFileLoader(inputPath);
            } catch (IllegalArgumentException e) {
                fail();
            }
        }
    }

    @Nested
    @DisplayName("ファイル読み込みテスト")
    class testFileReading {
        String filePath;

        String imgFolderPath;
        XlsxFileLoader xlsxFileLoader;

        @BeforeEach
        void setUp() {
            filePath = "/Users/yasu/playground/Java/QRCodeMaker/src/test/java/resource/input-data/テスト用QRコード作成データ.xlsx";
            imgFolderPath = "/Users/yasu/playground/Java/QRCodeMaker/src/test/java/resource/img//";
            xlsxFileLoader = new XlsxFileLoader(filePath);
        }

        @Test
        @DisplayName("レコードが2行存在する場合はsizeが2のQRCodeクラスのリストを作成しメールクラスの情報がファイルの情報と一致する")
        void testCreateQRCodeListWithTwoRecordsAndMatchFileInformation() throws IOException {
            /*
            ファイルの情報とリストのMailクラスの情報が一致しているかどうかは、
            件名が一致しているかどうかで判断する
            Mail{subject='〇〇アンケート 回答 参加者01 '}
            Mail{subject='〇〇アンケート 回答 参加者02 '}
             */
            List<QRCode> qrCodeList = xlsxFileLoader.createQRCodeList(imgFolderPath);
            assertAll(
                    () -> assertEquals(2, qrCodeList.size()),
                    () -> assertEquals("〇〇アンケート 回答 参加者01 ", qrCodeList.get(0).getMail().getSubject()),
                    () -> assertEquals("〇〇アンケート 回答 参加者02 ", qrCodeList.get(1).getMail().getSubject())
            );
        }
    }
}