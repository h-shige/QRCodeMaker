import artifact.QRCode;
import counter.ResultCounter;
import log.ErrorLog;
import resourceloader.XlsxFileLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * メインメソッド
     *
     * @param args args[0]が読み込むQRコードデータ　args[1]がQRコードが作成されるフォルダ
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 読み込むファイルの相対パス
        final String relativeXlsxFilePath = "./src/main/resources/input-data/QRコード作成データ.xlsx";
        // 読み込むファイルの絶対パス
        final String xlsxFilePath = new File(relativeXlsxFilePath).getAbsolutePath();
        // QRコードの保存先の相対パス
        final String relativeImgFolderPath = "./src/main/resources/img";
        // QRコードの保存先の絶対パス
        final String imgFolderPath = new File(relativeImgFolderPath).getAbsolutePath();

        XlsxFileLoader xlsxFileLoader = new XlsxFileLoader(xlsxFilePath);
        try {
            //ファイルを読み込んでその情報を格納したQRCodeクラスのリストを作成
            List<QRCode> qrCodeList = xlsxFileLoader.createQRCodeList(imgFolderPath);

            System.out.println("データ件数 : " + qrCodeList.size());
            printLine();
            //QRコード画像作成数
            ResultCounter resultCounter = new ResultCounter();
            //QRコード作成
            for (int i = 0; i < qrCodeList.size(); i++) {
                try {
                    System.out.printf("%3d件目 => メール件名 : " + qrCodeList.get(i).getMail().getSubject() + System.lineSeparator(), i + 1);
                    qrCodeList.get(i).writeImage();
                    resultCounter = resultCounter.addSuccess(1);
                } catch (Exception e) {
                    ErrorLog errorLog = new ErrorLog(String.format("%3d件目 => QRコードの作成に失敗しました", i + 1));
                    errorLog.printErrorLog();
                    resultCounter = resultCounter.addError(1);
                }
            }
            printLine();
            resultCounter.printResult(qrCodeList.size());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * コンソールにラインを引くメソッド
     */
    private static void printLine() {
        System.out.println("-------------------------------------------------------------------------------------");
    }

}
