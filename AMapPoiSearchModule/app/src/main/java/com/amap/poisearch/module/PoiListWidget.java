package com.amap.poisearch.module;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.amap.poisearch.R;

/**
 * Created by liangchao_suxun on 2017/4/26.
 */

public class PoiListWidget extends FrameLayout {

    private PoiListView mPoiListView;
    private PoiListAdapter mPoiListAdapter;

    private IParentWidget mParentWidget = null;

    public PoiListWidget(Context context) {
        super(context);
        init();
    }

    public PoiListWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PoiListWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ArrayList<PoiListItemData> pois = new ArrayList<>();

    public void setParentWidget(IParentWidget widget) {
        this.mParentWidget = widget;
    }

    public void setFavAddressVisible(boolean isVisible) {
        mPoiListAdapter.setFavAddressVisible(isVisible);
        mPoiListAdapter.notifyDataSetChanged();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_poi_list, this);
        mPoiListView = (PoiListView)findViewById(R.id.poi_lv);

        mPoiListAdapter = new PoiListAdapter(getContext(), pois);
        mPoiListView.setAdapter(mPoiListAdapter);
    }


    public void reloadPoiList(ArrayList<PoiListItemData> poiItems) {
        pois.clear();
        pois.addAll(poiItems);
        mPoiListAdapter.onLoadFinished();
        mPoiListAdapter.notifyDataSetChanged();
    }

    public void onLoading() {
        mPoiListAdapter.onLoading();
        mPoiListAdapter.notifyDataSetChanged();
    }

    public void setCurrLoc(Location currLoc) {
        mPoiListAdapter.setCurrLoc(currLoc);
        mPoiListAdapter.notifyDataSetChanged();
    }

    public static interface IParentWidget {
    }

}
