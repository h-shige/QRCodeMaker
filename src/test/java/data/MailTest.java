package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailTest {

    @Nested
    class インスタンス生成テスト {
        String toMail, subject, message, fileName;

        @BeforeEach
        void setUp() {
            toMail = "toMail";
            subject = "subject";
            message = "message";
        }

        @Test
        void toMailがnullの場合は例外をスローする() {
            toMail = null;
            assertThrows(IllegalArgumentException.class, () -> new Mail(toMail, subject, message));
        }

        @Test
        void toMailが空文字の場合は例外をスローする() {
            toMail = "";
            assertThrows(IllegalArgumentException.class, () -> new Mail(toMail, subject, message));
        }

        @Test
        void すべての引数がnullではないかつすべての引数が空文字ではない場合は正常にコンストラクタが生成される() {
            try {
                new Mail(toMail, subject, message);
            } catch (IllegalArgumentException e) {
                fail();
            }
        }
    }

    @Nested
    class getterテスト {

        Mail mail;

        @BeforeEach
        void setUp() {
            mail = new Mail("toMail", "subject", "message");
        }

        @Test
        void Mailクラスの宛先がtoMailの場合getToMailの戻り値はtoMail() {
            assertEquals("toMail", mail.getToMail());
        }

        @Test
        void Mailクラスの件名がsubjectの場合getSubjectの戻り値はsubject() {
            assertEquals("subject", mail.getSubject());
        }

        @Test
        void Mailクラスの本文がmessageの場合getMessageの戻り値はmessage() {
            assertEquals("message", mail.getMessage());
        }
    }
}