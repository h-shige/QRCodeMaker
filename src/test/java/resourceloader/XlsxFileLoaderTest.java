package resourceloader;

import artifact.QRCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XlsxFileLoaderTest {
    @Nested
    class インスタンス生成テスト {
        String inputPath;

        @Test
        void 引数がnullの場合は例外をスローする() {
            inputPath = null;
            assertThrows(IllegalArgumentException.class, () -> new XlsxFileLoader(inputPath));
        }

        @Test
        void 引数が空文字の場合は例外をスローする() {
            inputPath = "";
            assertThrows(IllegalArgumentException.class, () -> new XlsxFileLoader(inputPath));
        }

        @Test
        void 引数がnullではないかつ空文字ではない場合は正常にインスタンスが生成される() {
            inputPath = "inputPath";
            try {
                new XlsxFileLoader(inputPath);
            } catch (IllegalArgumentException e) {
                fail();
            }
        }
    }

    @Nested
    class ファイル読み込みテスト {
        String filePath;

        String imgFolderPath;
        XlsxFileLoader xlsxFileLoader;

        @BeforeEach
        void setUp() {
            filePath = "C:\\Users\\SNPC1054\\IdeaProjects\\QRCodeMaker\\test\\resource\\テスト用メールテンプレート作成データ.xlsx";
            imgFolderPath = "C:\\Users\\SNPC1054\\IdeaProjects\\QRCodeMaker\\test\\resource\\img\\";
            xlsxFileLoader = new XlsxFileLoader(filePath);
        }

        @Test
        void レコードが2行存在する場合はsizeが2のQRCodeクラスのリストを作成しメールクラスの情報がファイルの情報と一致する() throws IOException {
            /*
            ファイルの情報とリストのMailクラスの情報が一致しているかどうかは、
            件名が一致しているかどうかで判断する
            Mail{subject='【0147-01】稲田太郎01 遅刻連絡'}
            Mail{subject='【0147-01】稲田太郎01 欠席連絡'}
             */
            List<QRCode> qrCodeList = xlsxFileLoader.createQRCodeList(imgFolderPath);
            assertAll(
                    () -> assertEquals(2, qrCodeList.size()),
                    () -> assertEquals("【0147-01】稲田太郎01 遅刻連絡", qrCodeList.get(0).getMail().getSubject()),
                    () -> assertEquals("【0147-01】稲田太郎01 欠席連絡", qrCodeList.get(1).getMail().getSubject())
            );
        }
    }
}