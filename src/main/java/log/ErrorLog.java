package log;


/**
 * エラーログクラス
 */
public class ErrorLog {

    private final String message;

    /**
     * エラーログ表示クラス
     *
     * @param message エラーメッセージ
     */
    public ErrorLog(final String message) {
        this.message = message;
    }

    /**
     * 文字列の文字を赤にするメソッド
     *
     * @param message 色を付与する文字列
     * @return 色が付与された文字列
     */
    private String addColor(final String message) {
        String red = "\u001b[00;31m";
        String end = "\u001b[00m";
        return red + message + end;
    }

    /**
     * エラーログ出力用メソッド
     */
    public void printErrorLog() {
        System.out.println(addColor(this.message));
    }
}
