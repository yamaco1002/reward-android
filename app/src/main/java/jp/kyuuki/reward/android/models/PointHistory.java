package jp.kyuuki.reward.android.models;

import java.util.Date;

/**
 * ポイント履歴。
 */
public class PointHistory {
    // Android の場合はパフォーマンス的な問題で Setter, Getter 使わない方向で統一。
    public String detail;
    public int pointChage;
    public  Date createdAt;  // TODO: 取得日は DB 作成日とは別にした方がよい。

    public PointHistory(String detail, int pointChange, Date createdAt) {
        this.detail = detail;
        this.pointChage = pointChange;
        this.createdAt = createdAt;
    }
}

