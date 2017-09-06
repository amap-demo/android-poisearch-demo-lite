package com.amap.poisearch.module;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.amap.api.services.core.PoiItem;
import com.amap.poisearch.search.IPoisearchDelegate;
import com.amap.poisearch.search.IPoisearchDelegate.Callback;
import com.amap.poisearch.search.PoiSearchDeleteImpl;
import com.amap.poisearch.module.ISearchModule.IDelegate;
import com.amap.poisearch.module.ISearchModule.IWidget;
import com.amap.poisearch.util.CityModel;
import com.amap.poisearch.util.CityUtil;

/**
 * Created by liangchao_suxun on 2017/4/26.
 */

public class SearchModuleDelegate implements IDelegate {

    private IWidget mWidget;

    /**
     * 默认为北京
     */
    private CityModel mCurrCity;

    private Context mContext;

    private IParentDelegate mParentDelegate;

    private IPoisearchDelegate mPoisearchDelegate;

    @Override
    public void bindParentDelegate(IParentDelegate delegate) {
        this.mParentDelegate = delegate;
    }

    @Override
    public View getWidget(Context context) {
        if (context == null) {
            return null;
        }

        this.mContext = context;

        if (mWidget != null) {
            return (View)mWidget;
        }

        mWidget = new SearchModuleWidget(context);
        mWidget.bindDelegate(this);

        // 获得context时，对mCurrCity进行检测，如果还没有初始化，则默认为默认值
        if (this.mCurrCity == null) {
            this.mCurrCity = CityUtil.getDefCityModel(context);
        }

        mWidget.setCityName(mCurrCity.getCity());

        init(context);


        return (View)mWidget;
    }

    public void init(Context context) {
        mPoisearchDelegate = new PoiSearchDeleteImpl();
        reload(null);
    }

    public CityModel getCurrCity() {
        return mCurrCity;
    }

    @Override
    public void setCity(CityModel city) {
        if (city == null) {
            return;
        }

        this.mCurrCity = city;
        if (mWidget != null) {
            mWidget.setCityName(this.mCurrCity.getCity());
            reload(null);
        }
    }

    private void reload(List<PoiItem> items) {
        ArrayList<PoiListItemData> poiItems = new ArrayList<>();
        if (items != null) {
            for (PoiItem searchItem : items) {
                poiItems.add(new PoiListItemData(PoiListItemData.SEARCH_DATA, searchItem));
            }
        }

        mWidget.reloadPoiList(poiItems);
    }

    private long mCurrSearchId = 0;

    @Override
    public void onSearch(String inputStr) {
        if (TextUtils.isEmpty(inputStr)) {
            // 显示收藏的位置的选择panel
            reload(null);
            return;
        }

        String adCode = mCurrCity == null ? "" : mCurrCity.getAdcode();

        mPoisearchDelegate.doSearch(mContext, adCode, inputStr, new Callback() {
            @Override
            public void onSucc(List<PoiItem> poiItems) {
                reload(poiItems);
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                Toast.makeText(mContext, "检索失败 " + errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChangeCityName() {
        if (mParentDelegate == null) {
            return;
        }

        mParentDelegate.onChangeCityName();
    }

    @Override
    public void onCancel() {
        if (mParentDelegate == null) {
            return;
        }

        mParentDelegate.onCancel();
    }

}
