package counter;

/**
 * 実行結果保存クラス
 */
public class ResultCounter {

    /** QRコード画像作成数 */
    private final int successCount;

    /** エラー件数 */
    private final int errorCount;

    /** 総数 */
    private final int total;

    /**
     * 最初に初期値0でインスタンス化するためのコンストラクタ
     */
    public ResultCounter() {
        this.successCount = 0;
        this.errorCount = 0;
        this.total = 0;
    }

    /**
     * それぞれのカウンターを増加させるためのコンストラクタ
     *
     * @param successCount 　QRコード画像作成数
     * @param errorCount   　エラー件数
     */
    private ResultCounter(final int successCount, final int errorCount, final int total) {
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.total = total;
    }

    /**
     * QRコード画像作成数増加メソッド
     *
     * @param value 増加値
     * @return QRコード画像作成数が増加値だけ増加したResultCounterクラス
     */
    public ResultCounter addSuccess(final int value) {
        int amountSuccessCount = this.successCount + value;
        int amountTotal = this.total + value;
        return new ResultCounter(amountSuccessCount, this.errorCount, amountTotal);
    }

    /**
     * エラー件数増加メソッド
     *
     * @param value 増加値
     * @return エラー件数が増加値だけ増加したResultCounterクラス
     */
    public ResultCounter addError(final int value) {
        int amountErrorCount = this.errorCount + value;
        int amountTotal = this.total + value;
        return new ResultCounter(this.successCount, amountErrorCount, amountTotal);
    }

    /**
     * 実行結果表示メソッド
     */
    public void printResult() {
        System.out.println("実行件数 : " + total);
        System.out.println("QRコード画像作成数 : " + successCount);
        System.out.println("エラー件数 : " + errorCount);
        System.out.println("終了");
    }

}
