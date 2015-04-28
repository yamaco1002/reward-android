package jp.kyuuki.reward.android.components.api;

import org.json.JSONArray;
import org.json.JSONObject;

import jp.kyuuki.reward.android.BuildConfig;

/**
 * リワードシステム API。
 *
 * - API 仕様にかかわる部分をここに集約。
 * - 通信ライブラリには依存したくない。
 */
abstract public class RewardApi<T> {
    private static final String TAG = RewardApi.class.getName();

    public static String BASE_URL = BuildConfig.BASE_URL;
    // TODO: BuildConfig で管理するか、strings.xml で管理するか
    //public static String BASE_URL = "http://kyuuki.jp:3000";
    //public static String BASE_URL = "http://admin.dolly-reward.com";

    protected String url;
    protected JSONObject jsonRequest;

    public String getUrl() {
        return url;
    };

    public JSONObject getJsonRequest() {
        return jsonRequest;
    };

    abstract public T parseJsonResponse(JSONObject jsonResponse);
    abstract public T parseJsonResponse(JSONArray jsonResponse);
}

