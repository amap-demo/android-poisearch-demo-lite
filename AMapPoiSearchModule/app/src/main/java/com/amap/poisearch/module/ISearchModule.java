package com.amap.poisearch.module;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import com.amap.poisearch.util.CityModel;

/**
 * Created by liangchao_suxun on 2017/4/26.
 */

public interface ISearchModule {


    public static interface IWidget {

        /**
         * 绑定控制类
         * @param delegate
         */
        public void bindDelegate(IDelegate delegate);

        /** 设置所在城市的名字,默认为北京*/
        public void setCityName(String cityName);

        /**
         * 重新加载poi检索结果
         * @param poiItems
         */
        public void reloadPoiList(ArrayList<PoiListItemData> poiItems);

    }

    public static interface IDelegate {

        /**
         * 绑定父控制类。不能处理的逻辑交由父控制类处理
         * @param delegate
         */
        public void bindParentDelegate(IParentDelegate delegate);

        /** 获得显示的view*/
        public View getWidget(Context context);

        /** 设置所在城市*/
        public void setCity(CityModel city);

        /** 对inputStr进行搜索 */
        public void onSearch(String inputStr);

        /** 更换城市 */
        public void onChangeCityName();

        /** 取消 */
        public void onCancel();

        /**
         * 父Delegate，IDelegate不能处理的交给IParentDelegate
         */
        public static interface IParentDelegate {
            /** 更换城市 */
            public void onChangeCityName();

            /** 取消 */
            public void onCancel();
        }
    }


}
