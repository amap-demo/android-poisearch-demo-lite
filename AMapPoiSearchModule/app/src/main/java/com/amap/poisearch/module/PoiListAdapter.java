 package com.amap.poisearch.module;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.poisearch.R;

/**
 * Created by liangchao_suxun on 2017/4/26.
 */

public class PoiListAdapter extends BaseAdapter {

    private Context context;

    ArrayList<PoiListItemData> data;

    private int loadStatus;

    private Location mCurrLoc;

    private boolean isFavViewVisible = true;

    private static final int LOAD_STATUS_LOADING = 0;
    private static final int LOAD_STATUS_FINISHED = 1;


    public PoiListAdapter(Context context , ArrayList<PoiListItemData> data) {
        this.context = context;
        this.data = data;
    }


    public void onLoading(){
        loadStatus = LOAD_STATUS_LOADING;
    }

    public void onLoadFinished(){
        loadStatus = LOAD_STATUS_FINISHED;
    }

    public void setFavAddressVisible(boolean isVisible) {
        isFavViewVisible = isVisible;
    }

    public void setCurrLoc(Location currLoc) {
        if (currLoc == null) {
            return;
        }

        this.mCurrLoc = currLoc;
    }

    @Override
    public int getCount() {
        if (loadStatus == LOAD_STATUS_LOADING) {
            return 1;
        } else {
            if (data == null) {
                return 0;
            }

            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (loadStatus == LOAD_STATUS_LOADING) {
            return 0;
        }

        if (position >= 0) {
            return 1;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (loadStatus == LOAD_STATUS_FINISHED) {
            if (getItemViewType(position) == 1) {
                if (convertView == null) {
                    convertView = new PoiListItemWidget(context);
                }

                if (convertView.getTag() == null) {
                    PoiItemWidgetTag tag = new PoiItemWidgetTag();

                    tag.iconIV = (ImageView)convertView.findViewById(R.id.icon_iv);
                    tag.titleTV = (TextView)convertView.findViewById(R.id.title_tv);
                    tag.subTitleTV = (TextView)convertView.findViewById(R.id.sub_title_tv);
                    tag.disTV = (TextView)convertView.findViewById(R.id.dis_tv);

                    convertView.setTag(tag);
                }

                PoiItemWidgetTag tag = (PoiItemWidgetTag)convertView.getTag();

                PoiListItemData poiItem = data.get(position);
                tag.from(poiItem);

                return convertView;
            }

            return new View(context);

        } else if (loadStatus == LOAD_STATUS_LOADING) {
            convertView = getLoadingView(context);
            return convertView;
        }

        return new View(context);
    }

    private RelativeLayout mLoadingView = null;
    private View getLoadingView(Context context) {
        if (mLoadingView == null) {
            mLoadingView = new RelativeLayout(context);
            LayoutInflater.from(context).inflate(R.layout.widget_poi_list_loading_item, mLoadingView);
        }

        return mLoadingView;
    }

    private class PoiItemWidgetTag{
        public ImageView iconIV;
        public TextView titleTV;
        public TextView subTitleTV;
        public TextView disTV;

        public PoiListItemData mPoiItem;

        public void from(PoiListItemData poiItem) {
            mPoiItem = poiItem;

            titleTV.setText(mPoiItem.poiItem.getTitle());
            subTitleTV.setText(mPoiItem.poiItem.getSnippet());

            if (mPoiItem.type == PoiListItemData.HIS_DATA) {
                iconIV.setImageResource(R.mipmap.time);
            } else {
                iconIV.setImageResource(R.mipmap.poi);
            }

            if (mPoiItem.type != PoiListItemData.HIS_DATA) {
                initDisTV(poiItem);
            } else {
                disTV.setVisibility(View.GONE);
            }
        }

        private void initDisTV(PoiListItemData poiItem) {
            String calDis = poiItem.calDis(mCurrLoc);
            if (!TextUtils.isEmpty(calDis)) {
                disTV.setText(calDis);
                disTV.setVisibility(View.VISIBLE);
            } else {
                disTV.setVisibility(View.GONE);
            }
        }
    }
}
