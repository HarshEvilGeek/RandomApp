package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;

/**
 * DB Model for recommendations
 * 
 * Created by adugar on 3/25/16.
 */
public class Recommendation extends DbModel
{
    private int id;
    private String recommendationId;
    private String message;
    private String imageUrl;
    private String openUrl;
    private TransactionCategoryUtils.Category category;
    private String reason;

    public Recommendation()
    {

    }

    public Recommendation(String recommendationId, String message, String imageUrl, String openUrl,
            TransactionCategoryUtils.Category category, String reason)
    {
        this.recommendationId = recommendationId;
        this.message = message;
        this.imageUrl = imageUrl;
        this.openUrl = openUrl;
        this.category = category;
        this.reason = reason;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getRecommendationId()
    {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId)
    {
        this.recommendationId = recommendationId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getOpenUrl()
    {
        return openUrl;
    }

    public void setOpenUrl(String openUrl)
    {
        this.openUrl = openUrl;
    }

    public TransactionCategoryUtils.Category getCategory()
    {
        return category;
    }

    public void setCategory(TransactionCategoryUtils.Category category)
    {
        this.category = category;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {
        if (cursor != null) {
            setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Recommendations._ID)));
            setRecommendationId(cursor.getString(cursor
                    .getColumnIndex(PocketBankerContract.Recommendations.RECOMMENDATION_ID)));
            setMessage(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Recommendations.MESSAGE)));
            setImageUrl(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Recommendations.IMAGE_URL)));
            setOpenUrl(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Recommendations.OPEN_URL)));
            setCategory(TransactionCategoryUtils.Category.values()[cursor.getInt(cursor
                    .getColumnIndex(PocketBankerContract.Recommendations.CATEGORY))]);
            setReason(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Recommendations.REASON)));
        }
    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.Recommendations.RECOMMENDATION_ID + " = ?";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { recommendationId };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return model instanceof Recommendation
                && recommendationId.equals(((Recommendation) model).getRecommendationId());
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues cv = new ContentValues();
        cv.put(PocketBankerContract.Recommendations.RECOMMENDATION_ID, recommendationId);
        cv.put(PocketBankerContract.Recommendations.MESSAGE, message);
        cv.put(PocketBankerContract.Recommendations.IMAGE_URL, imageUrl);
        cv.put(PocketBankerContract.Recommendations.OPEN_URL, openUrl);
        cv.put(PocketBankerContract.Recommendations.CATEGORY, category.ordinal());
        cv.put(PocketBankerContract.Recommendations.REASON, reason);
        return cv;
    }

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.RECOMMENDATIONS;
    }
}
