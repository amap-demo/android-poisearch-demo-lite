package com.amap.poisearch.module;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.amap.poisearch.R;
import com.amap.poisearch.module.ISearchModule.IDelegate;
import com.amap.poisearch.module.ISearchModule.IWidget;
import com.amap.poisearch.module.PoiSearchWidget.IParentWidget;

/**
 * Created by liangchao_suxun on 2017/4/26.
 */

class SearchModuleWidget extends RelativeLayout implements IWidget {

    private PoiSearchWidget mPoiSearchWidget;

    private PoiListWidget mPoiListWidget;

    private IDelegate mDelegate;

    public SearchModuleWidget(Context context) {
        super(context);
        init();
    }

    public SearchModuleWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchModuleWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_search_module, this);

        mPoiSearchWidget = (PoiSearchWidget)findViewById(R.id.poi_search_widget);
        mPoiListWidget = (PoiListWidget)findViewById(R.id.poi_list_widget);

        mPoiSearchWidget.setParentWidget(mPoiSearchParentWidgetDelegate);
        mPoiListWidget.setParentWidget(mPoiListParentWidgetDelegate);
    }

    @Override
    public void bindDelegate(IDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void setCityName(String cityName) {
        if (mPoiSearchWidget != null) {
            mPoiSearchWidget.setCityName(cityName);
        }
    }


    @Override
    public void reloadPoiList(ArrayList<PoiListItemData> poiItems) {
        mPoiListWidget.reloadPoiList(poiItems);
    }

    private PoiSearchWidget.IParentWidget mPoiSearchParentWidgetDelegate = new IParentWidget() {
        @Override
        public void onInput(String inputStr) {
            mPoiListWidget.onLoading();
            mDelegate.onSearch(inputStr);
        }

        @Override
        public void onChangeCityName() {
            mDelegate.onChangeCityName();
        }

        @Override
        public void onCancel() {
            mDelegate.onCancel();
        }
    };

    private PoiListWidget.IParentWidget mPoiListParentWidgetDelegate = new PoiListWidget.IParentWidget() {
    };
}
