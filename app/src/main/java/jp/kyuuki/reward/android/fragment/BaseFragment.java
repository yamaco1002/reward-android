package jp.kyuuki.reward.android.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.kyuuki.reward.android.R;
import jp.kyuuki.reward.android.commons.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG = BaseFragment.class.getName();

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.v(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // super は呼ばない。
        Logger.v(TAG, "onCreateView()");
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        Logger.v(TAG, "onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        Logger.v(TAG, "onDetach()");
        super.onDetach();
    }

}
