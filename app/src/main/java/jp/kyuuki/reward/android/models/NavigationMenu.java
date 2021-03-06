package jp.kyuuki.reward.android.models;

import jp.kyuuki.reward.android.R;

/**
 * メニュー項目。
 */
public enum NavigationMenu {
    OFFER_LIST(
            R.string.menu_offer_list
    ),
    POINT_EXCHANGE(
            R.string.menu_point_exchange
    ),
    POINT_HISTORY(
            R.string.menu_point_history
    ),
    HELP(
            R.string.menu_help
    ),
    ABOUT(
            R.string.menu_about
    );

    public int resource;

    private NavigationMenu(int resource) {
        this.resource = resource;
    }
}
