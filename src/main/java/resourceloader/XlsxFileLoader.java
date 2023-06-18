package resourceloader;

import artifact.QRCode;
import data.Mail;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * xlsxファイルを読み込むクラス
 */
public class XlsxFileLoader {

    /**
     * 作成したQRコードの保存先のパスにつける
     */
    private static final char FOLDER = '\\';

    /**
     * xlsxファイルのパス
     */
    private final String inputPath;

    /**
     * コンストラクタ
     *
     * @param inputPath 読み込むファイル
     */
    public XlsxFileLoader(final String inputPath) {
        if (inputPath == null || inputPath.isEmpty()) throw new IllegalArgumentException("引数が不正です");
        this.inputPath = inputPath;
    }

    /**
     * xlsxファイルからQRCodeクラスのリストを作成するメソッド
     *
     * @param imgFolderPath 作成したQRコードの画像ファイルの保存先
     * @return QRコードクラスの不変リスト
     */
    public List<QRCode> createQRCodeList(final String imgFolderPath) throws IOException {
        //returnするメールクラスのリスト
        List<QRCode> QRcodeList = new ArrayList<>();
        Workbook excel = WorkbookFactory.create(new File(this.inputPath));
        Sheet sheet = excel.getSheet("メールテンプレート作成データ");

        //行番号
        int rowNumber = 1;
        while (true) {
            Row row = sheet.getRow(rowNumber);
            //その行に値がない場合はループを抜ける。
            try {
                row.getCell(7).getStringCellValue();
            } catch (NullPointerException e) {
                break;
            }
            //セルの値を一時保存するためのリスト
            List<String> cellValueList = new ArrayList<>();
            //列番号
            int colNumber = 7;
            while (true) {
                Cell cell = row.getCell(colNumber);
                //その列に値がない場合は次の行へ
                try {
                    row.getCell(colNumber).getStringCellValue();
                } catch (NullPointerException e) {
                    break;
                }
                cellValueList.add(cell.getStringCellValue());
                //次の列へ
                colNumber++;
            }
            Mail mail = new Mail(cellValueList.get(0), cellValueList.get(1), cellValueList.get(2));
            QRCode qrCode = new QRCode(mail, imgFolderPath + FOLDER + cellValueList.get(3), Integer.parseInt(cellValueList.get(4)),
                    Integer.parseInt(cellValueList.get(5)));
            QRcodeList.add(qrCode);
            //次の行へ
            rowNumber++;
        }
        excel.close();
        return Collections.unmodifiableList(QRcodeList);
    }
}
