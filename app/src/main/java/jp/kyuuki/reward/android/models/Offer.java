package jp.kyuuki.reward.android.models;

/**
 * オファー。
 */
public class Offer {
    private String name;
    private String description;
    private int point;
    private String iconUrl;
    private String executeUrl;

    public Offer(String name, String description, int point, String iconUrl, String executeUrl) {
        this.name = name;
        this.description = description;
        this.point = point;
        this.iconUrl = iconUrl;
        this.executeUrl = executeUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoint() {
        return point;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getExecuteUrl() {
        return executeUrl;
    }
}

