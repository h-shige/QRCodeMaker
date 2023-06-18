package data;

/**
 * メールに付与する情報を格納するクラス
 */
public class Mail {

    /** 宛先 */
    private final String toMail;
    /** 件名 */
    private final String subject;
    /** 本文 */
    private final String message;

    /**
     * コンストラクタ
     * @param toMail 宛先
     * @param subject 件名
     * @param message 本文
     */
    public Mail(final String toMail, final String subject, final String message) {
        if (toMail == null || subject == null || message == null) throw new  IllegalArgumentException("引数が不正です");
        if (toMail.isEmpty() || subject.isEmpty() || message.isEmpty()) throw new  IllegalArgumentException("引数が不正です");
        this.toMail = toMail;
        this.subject = subject;
        this.message = message;
    }

    /**
     * インスタンス変数宛先のgetter
     * @return 宛先
     */
    public String getToMail() {
        return toMail;
    }

    /**
     * インスタンス変数件名のgetter
     * @return 件名
     */
    public String getSubject() {
        return subject;
    }

    /**
     * インスタンス変数本文のgetter
     * @return 本文
     */
    public String getMessage() {
        return message;
    }

    /**
     * 自動生成した確認用toStringメソッド
     * @return オブジェクトの中身
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mail{");
        sb.append("toMail='").append(toMail).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
