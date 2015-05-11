package jp.kyuuki.reward.android.components.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.kyuuki.reward.android.models.MediaUser;
import jp.kyuuki.reward.android.models.Offer;

/**
 * 案件情報取得 API
 */
public class GetOffers extends RewardApi<List<Offer>> {

    /*
     * HTTP リクエスト仕様
     */
    public static GetOffers create(long mid, long uid) {
        GetOffers api = new GetOffers();

        // URL
        api.url = BASE_URL + "/offers.json?mid=" + mid + "&uid=" + uid;
        // Volley では GET のクエリー文字列は自前で作らないとダメらしい。
        // TODO: 署名を付けるときに共通化

        // Body
        api.jsonRequest = null;

        return api;
    }

    /*
     * HTTP レスポンス仕様
     */
    @Override
    public List<Offer> parseJsonResponse(JSONArray jsonResponse) {
        return json2Offers(jsonResponse);
    }

    @Override
    public List<Offer> parseJsonResponse(JSONObject jsonResponse) {
        throw new IllegalAccessError();
    }

    public static List<Offer> json2Offers(JSONArray a) {
        ArrayList<Offer> list = new ArrayList<Offer>();
        for (int i = 0, len = a.length(); i < len; i++) {
            JSONObject o = null;
            try {
                o = a.getJSONObject(i);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (o == null) {
                continue;
            }

            Offer offer = json2Offer(o);
            list.add(offer);
        }

        return list;
    }

    // TODO: データが不正の場合の処理
    public static Offer json2Offer(JSONObject o) {
        String name = "";
        String detail = "";
        String iconUrl = "";
        String executeUrl = "";
        String requirement = "";
        String requirementDetail = "";
        String period = "";
        int point = 0;
        int price = 0;
        try {
            name = o.getString("name");
            detail = o.getString("detail");
            iconUrl = o.getString("icon_url");
            executeUrl = o.getString("execute_url");
//            JSONArray advertisements = o.getJSONArray("advertisements");
//            if (advertisements != null) {
//                if (advertisements.get(0) != null) {
//                    JSONObject oo = advertisements.getJSONObject(0);
//                    point = oo.getInt("point");
//                }
//            }
            point = o.getInt("point");
            price = o.getInt("price");
            requirement = o.getString("requirement");
            requirementDetail = o.getString("requirement_detail");
            period = o.getString("period");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Offer offer = new Offer(name, detail, price, point, iconUrl, executeUrl, requirement, requirementDetail, period);

        return offer;
    }

}
