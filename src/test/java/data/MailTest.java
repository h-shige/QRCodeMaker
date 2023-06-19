package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * テスト結果を画面に表示するための試験的内部クラス
 * 将来的にテストを実行すればそれをエクセルファイルに書き込み自動的に
 * 単体テスト実行結果のドキュメントを作成できるようにしたい
 */
class ResultPrinter implements TestWatcher {

    private static final String SUCCESS = "成功";

    private static final String FAILED = "　失敗";

    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = context.getDisplayName();
        System.out.println(testName + " : " + SUCCESS);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        System.out.println(testName + " : " + FAILED);
    }
}


@ExtendWith(ResultPrinter.class)
class MailTest {

    @Nested
    @DisplayName("インスタンス生成テスト")
    class TestInstanceCreation{
        String toMail, subject, message, fileName;

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