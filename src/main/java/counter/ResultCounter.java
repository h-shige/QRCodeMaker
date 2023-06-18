package counter;

/**
 * 実行結果保存クラス
 */
public class ResultCounter {
    /**
     * QRコード画像作成数
     */
    private final int successCount;
    /**
     * エラー件数
     */
    private final int errorCount;

    /**
     * 最初に初期値0でインスタンス化するためのコンストラクタ
     */
    public ResultCounter() {
        this.successCount = 0;
        this.errorCount = 0;
    }

    /**
     * それぞれのカウンターを増加させるためのコンストラクタ
     *
     * @param successCount 　QRコード画像作成数
     * @param errorCount   　エラー件数
     */
    private ResultCounter(final int successCount, final int errorCount) {
        this.successCount = successCount;
        this.errorCount = errorCount;
    }

    /**
     * QRコード画像作成数増加メソッド
     *
     * @param value 増加値
     * @return QRコード画像作成数が増加値だけ増加したResultCounterクラス
     */
    public ResultCounter addSuccess(final int value) {
        int amount = this.successCount + value;
        return new ResultCounter(amount, this.errorCount);
    }

    /**
     * エラー件数増加メソッド
     *
     * @param value 増加値
     * @return エラー件数が増加値だけ増加したResultCounterクラス
     */
    public ResultCounter addError(final int value) {
        int amount = this.errorCount + value;
        return new ResultCounter(this.successCount, amount);
    }

    /**
     * 実行結果表示メソッド
     *
     * @param dataSize メインメソッドの引数で受け取ったxlsxファイルのレコード数
     */
    public void printResult(final int dataSize) {
        System.out.println("データ件数 : " + dataSize);
        System.out.println("QRコード画像作成数 : " + successCount);
        System.out.println("エラー件数 : " + errorCount);
        System.out.println("終了");
    }
}
