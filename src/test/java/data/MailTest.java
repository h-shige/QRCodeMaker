package data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * テスト結果をエクセルファイルに出力するための試験的内部クラス
 * 現在、テストの実行とともにその内容と結果を出力は可能だが、
 * 将来的には決まったフォーマットで出力したい。
 */
class ResultPrinter implements TestWatcher {

    /** テスト結果の出力先 */
    private static final String FILE_PATH = "/Users/yasu/playground/Java/QRCodeMaker/src/test/document/MailTest-results.xlsx";

    /** 成功時のメッセージ */
    private static final String SUCCESS = "成功";

    /** 失敗時のメッセージ */
    private static final String FAILED = "　失敗";

    /** 結果を書き込むファイル */
    private Workbook workbook;

    /** 結果を書き込むシート */
    private Sheet sheet;

    /** 行数 */
    private int rowCount;

    /**
     * テスト成功時にテスト名と成功した結果を取得するメソッド
     * @param context
     */
    @Override
    public void testSuccessful(ExtensionContext context) {
        String displayName = context.getDisplayName();
        String result = SUCCESS;
        writeResultToExcel(displayName, result);
    }

    /**
     * テスト失敗時にテスト名と失敗した結果を取得するメソッド
     * @param context
     * @param cause
     */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String displayName = context.getDisplayName();
        String result = FAILED;
        writeResultToExcel(displayName, result);
    }

    /**
     * テスト結果をエクセルファイルとして出力するメソッド
     * @param displayName テスト名
     * @param result 結果
     */
    private void writeResultToExcel(String displayName, String result) {
        try {
            if (workbook == null) workbook = new XSSFWorkbook();
            if (sheet == null) createSheet();
            writeResult(displayName, result);
            FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * テスト名とテスト結果を書き込むメソッド
     * @param displayName テスト名
     * @param result 結果
     */
    private void writeResult(String displayName, String result) {
        Row row = sheet.createRow(rowCount++);
        Cell cellDisplayName = row.createCell(0);
        cellDisplayName.setCellValue(displayName);
        Cell cellStatus = row.createCell(1);
        cellStatus.setCellValue(result);
    }

    /**
     * 最初にテスト結果を書き込む前に列名をセルに書き込む
     */
    private void createSheet() {
        sheet = workbook.createSheet("results");
        rowCount = 0;
        writeResult("テスト名", "結果");
    }
}


@ExtendWith(ResultPrinter.class)
class MailTest {

    @Nested
    @DisplayName("インスタンス生成テスト")
    class TestInstanceCreation{
        String toMail, subject, message;

        @BeforeEach
        void setUp() {
            toMail = "toMail";
            subject = "subject";
            message = "message";
        }

        @Test
        @DisplayName("toMailがnullの場合は例外をスローする")
        void testThrowExceptionOnNullToMail() {
            toMail = null;
            assertThrows(IllegalArgumentException.class, () -> new Mail(toMail, subject, message));
        }

        @Test
        @DisplayName("toMailが空文字の場合は例外をスローする")
        void testThrowExceptionOnEmptyToMail() {
            toMail = "";
            assertThrows(IllegalArgumentException.class, () -> new Mail(toMail, subject, message));
        }

        @Test
        @DisplayName("すべての引数がnullではないかつすべての引数が空文字ではない場合は正常にコンストラクタが生成される")
        void testConstructorCreationWithNonEmptyArguments() {
            try {
                new Mail(toMail, subject, message);
            } catch (IllegalArgumentException e) {
                fail();
            }
        }
    }

    @Nested
    @DisplayName("getterテスト")
    class TestGetters {

        Mail mail;

        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
        }

        @Test
        @DisplayName("Mailクラスの宛先がtoMailの場合getToMailの戻り値はtoMail")
        void testGetToMailReturnsToMail() {
            assertEquals("toMail", mail.getToMail());
        }

        @Test
        @DisplayName("Mailクラスの件名がsubjectの場合getSubjectの戻り値はsubject")
        void testGetSubjectReturnsSubject() {
            assertEquals("subject", mail.getSubject());
        }

        @Test
        @DisplayName("Mailクラスの本文がmessageの場合getMessageの戻り値はmessage")
        void testGetMessageReturnsMessage() {
            assertEquals("message", mail.getMessage());
        }
    }
}