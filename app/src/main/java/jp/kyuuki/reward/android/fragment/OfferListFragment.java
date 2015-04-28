package jp.kyuuki.reward.android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.List;

import jp.kyuuki.reward.android.R;

import jp.kyuuki.reward.android.commons.Logger;
import jp.kyuuki.reward.android.commons.VolleyUtils;
import jp.kyuuki.reward.android.components.api.GetOffers;
import jp.kyuuki.reward.android.components.api.RewardApi;
import jp.kyuuki.reward.android.models.MediaUser;
import jp.kyuuki.reward.android.models.Offer;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class OfferListFragment extends BaseFragment implements AbsListView.OnItemClickListener {

    {
        TAG = OfferListFragment.class.getName();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // Model
    List<Offer> offers;

    // 通信
    public RequestQueue mRequestQueue;

    // このフラグメントにデータ取得の責任も持たせてしまう。
    // 将来的にはデータ取得は別にするかも。
    /*
     * 状態管理
     *
     * - 参考: http://idios.hatenablog.com/entry/2012/07/07/235137
     */
    private State state = State.INITIAL;

    enum State {
        INITIAL {
            @Override
            public void start(OfferListFragment fragment) {
                fragment.getCampaignData();
                transit(fragment, GETTING_OFFERS);
            }
        },

        GETTING_OFFERS {
            @Override
            public void successGetCampaginData(OfferListFragment fragment, List<Offer> offers) {
                fragment.showCampaignData();
                transit(fragment, READY);
            }

            @Override
            public void failureGetCampaginData(OfferListFragment fragment) {
                // TODO: 端末の通信状態を確認
                // TODO: サーバーの状態を確認
                // TODO: エラーダイアログを表示
                Activity activity = fragment.getActivity();
                if (activity != null) {
                    Toast.makeText(activity, activity.getString(R.string.error_communication), Toast.LENGTH_LONG).show();
                }
            }
        },

        READY;

        // 状態遷移
        private static void transit(OfferListFragment fragment, State nextState) {
            Logger.i("STATE", fragment.state + " -> " + nextState);
            fragment.state = nextState;
        }

        /*
         * イベント
         */
        // 初期処理開始
        public void start(OfferListFragment fragment) {
            Logger.e("STATE", fragment.state.toString());
            throw new IllegalStateException();
        }

        // キャンペーン情報取得成功
        public void successGetCampaginData(OfferListFragment fragment, List<Offer> offers) {
            Logger.e("STATE", fragment.state.toString());
            throw new IllegalStateException();
        }

        // キャンペーン情報取得失敗
        public void failureGetCampaginData(OfferListFragment fragment) {
            Logger.e("STATE", fragment.state.toString());
            throw new IllegalStateException();
        }
   }

    // キャンペーン情報 (案件情報) 取得
    private void getCampaignData() {
        MediaUser mediaUser = MediaUser.getMediaUser(getActivity());

        // TODO: 状態遷移でユーザー登録が確実にすんでいるようにする。
        long mediaUserId = -1;
        if (mediaUser != null) {
            mediaUserId = mediaUser.mediaUserId;
        }

        // TODO: メディア ID 取得
        final GetOffers api = GetOffers.create(1, mediaUserId);

        JsonArrayRequest request = new JsonArrayRequest(api.getUrl(),

            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Logger.i("HTTP", "body is " + response.toString());

                    offers = api.parseJsonResponse(response);
                    state.successGetCampaginData(OfferListFragment.this, offers);
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Logger.e("HTTP", "error = " + error.getMessage());

                    state.failureGetCampaginData(OfferListFragment.this);
                }
            }
        );

        mRequestQueue.add(request);
    }

    private void showCampaignData() {
        // この時点で Activity が存在しないパターンがある
        if (getActivity() == null) {
            Logger.e(TAG, "showCampaignData() getActivity is null.");
            return;
        }

        mAdapter = new OfferArrayAdapter(getActivity(), R.layout.row_offer, offers);
        // http://skyarts.com/blog/jp/skyarts/?p=3964
//        // API 9 で動かすための苦肉の策。
//        if (mListView instanceof ListView) {
//            ((ListView) mListView).setAdapter(adapter);
//        } else {
//            ((GridView) mListView).setAdapter(adapter);
//        }
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    // TODO: Rename and change types of parameters
    public static OfferListFragment newInstance(String param1, String param2) {
        OfferListFragment fragment = new OfferListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfferListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // このフラグメントは回転しても作り直さない
        setRetainInstance(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // リスト関連の処理はデータ受信後に行う
        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 回転しても onCreateView が走るので INITIAL の時のみ開始処理
        if (state == State.INITIAL) {
            mRequestQueue = VolleyUtils.getRequestQueue(this.getActivity());
            state.start(this);
        }

        View view = inflater.inflate(R.layout.fragment_offerlist, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        // リスト関連の処理はデータ受信後に行う
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
//
//        // Set OnItemClickListener so we can be notified on item clicks
//        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
            mListener = null;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }

        Offer offer = (Offer) parent.getItemAtPosition(position);
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(offer.getExecuteUrl()));
        startActivity(i);
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
