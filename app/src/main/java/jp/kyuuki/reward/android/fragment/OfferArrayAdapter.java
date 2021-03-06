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

import java.util.List;

import jp.kyuuki.reward.android.R;
import jp.kyuuki.reward.android.commons.VolleyUtils;
import jp.kyuuki.reward.android.models.Offer;

public class OfferArrayAdapter extends ArrayAdapter<Offer> {
    LayoutInflater mInflater;

    public OfferArrayAdapter(Context context, int textViewResourceId, List<Offer> list) {
        super(context, textViewResourceId, list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_offer, null);

            holder = new ViewHolder();
            holder.iconImage = (ImageView) view.findViewById(R.id.iconImage);
            holder.rensouText = (TextView) view.findViewById(R.id.nameText);
            holder.pointText = (TextView) view.findViewById(R.id.pointText);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Offer offer = getItem(position);

        // View

        // 各種表示データ更新
        holder.rensouText.setText(offer.getName());
        holder.pointText.setText("" + offer.getPoint());

        // http://qiita.com/gari_jp/items/829a54bfa937f4733e29
        ImageContainer imageContainer = (ImageContainer) holder.iconImage.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }

        ImageLoader imageLoader = VolleyUtils.getImageLoader(getContext());
        // TODO: 画像をちゃんとしたものに変更
        ImageListener listener = ImageLoader.getImageListener(holder.iconImage, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
        holder.iconImage.setTag(imageLoader.get(offer.getIconUrl(), listener));

        return view;
    }


    // ViewHolder パターン
    private static class ViewHolder {
        ImageView iconImage;
        TextView rensouText;
        TextView pointText;
    }
}
