package jp.kyuuki.reward.android.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.List;

import jp.kyuuki.reward.android.R;
import jp.kyuuki.reward.android.commons.VolleyUtils;
import jp.kyuuki.reward.android.models.Offer;
import jp.kyuuki.reward.android.models.PointHistory;

public class PointHistoryArrayAdapter extends ArrayAdapter<PointHistory> {
    LayoutInflater mInflater;

    public PointHistoryArrayAdapter(Context context, int textViewResourceId, List<PointHistory> list) {
        super(context, textViewResourceId, list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_point_history, null);

            holder = new ViewHolder();
            holder.achievedAtText = (TextView) view.findViewById(R.id.achieved_at);
            holder.rensouText = (TextView) view.findViewById(R.id.nameText);
            holder.pointText = (TextView) view.findViewById(R.id.pointText);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        PointHistory pointHistory = getItem(position);

        // View

        // 各種表示データ更新
        holder.achievedAtText.setText(DateFormatUtils.ISO_DATE_FORMAT.format(pointHistory.createdAt) + "\n" + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(pointHistory.createdAt));  // TODO: 日付と時刻でちゃんとレイアウトをわける
        holder.rensouText.setText(pointHistory.detail);
        if (pointHistory.pointChage >= 0) {
            holder.pointText.setText("+" + pointHistory.pointChage);
        } else {
            holder.pointText.setText("-" + pointHistory.pointChage);
        }

        return view;
    }


    // ViewHolder パターン
    private static class ViewHolder {
        TextView achievedAtText;
        TextView rensouText;
        TextView pointText;
    }
}
