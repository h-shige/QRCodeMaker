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
    private static final char FOLDER = '/';

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
        List<QRCode> qrCodeList = new ArrayList<>();
        Workbook excel = WorkbookFactory.create(new File(this.inputPath));
        //読み込むシート
        Sheet sheet = excel.getSheetAt(2);

        for (int rowNumber = 1; sheet.getRow(rowNumber).getCell(3) != null
                     && sheet.getRow(rowNumber).getCell(3).getCellType() != CellType.BLANK; rowNumber++) {
            //読み込む行
            Row row = sheet.getRow(rowNumber);
            //セルの値を一時保存するためのリスト
            List<String> cellValueList = new ArrayList<>();

            for (int colNumber = 3; row.getCell(colNumber) != null
                    && row.getCell(colNumber).getCellType() != CellType.BLANK; colNumber++) {
                //読み込む列
                Cell cell = row.getCell(colNumber);
                cellValueList.add(cell.getStringCellValue());
            }
            Mail mail = new Mail(cellValueList.get(0), cellValueList.get(1), cellValueList.get(2));
           QRCode qrCode = new QRCode(mail, imgFolderPath + FOLDER + cellValueList.get(3),
                   Integer.parseInt(cellValueList.get(4)), Integer.parseInt(cellValueList.get(5)));
           qrCodeList.add(qrCode);
        }
        excel.close();
        return Collections.unmodifiableList(qrCodeList);
    }
}
