import artifact.QRCode;
import counter.ResultCounter;
import log.ErrorLog;
import resourceloader.XlsxFileLoader;

import java.io.IOException;
import java.util.List;

public class Main {

    /**
     * メインメソッド
     *
     * @param args args[0]が読み込むQRコードデータ　args[1]がQRコードが作成されるフォルダ
     */
    public static void main(String[] args) {

        printArgs(args);

        // 読み込むファイル
        final String xlsxFilePath = args[0];
        // QRコードの保存先
        final String imgFolderPath = args[1];

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

    /**
     * 引数のパスを画面表示するメソッド
     * @param args メインメソッドの引数で受け取った文字列の配列
     */
    private static void printArgs(final String[] args) {
        System.out.println();
        System.out.println("メールテンプレートファイル : " + args[0]);
        System.out.println("QRコード保存フォルダ : " + args[1]);
    }
}
