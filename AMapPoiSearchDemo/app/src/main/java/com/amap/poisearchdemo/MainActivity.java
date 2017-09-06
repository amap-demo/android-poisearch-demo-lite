package com.amap.poisearchdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.amap.poisearch.module.ISearchModule.IDelegate.IParentDelegate;
import com.amap.poisearch.module.SearchModuleDelegate;
import com.amap.poisearch.util.CityModel;
import com.google.gson.Gson;

public class MainActivity extends BaseMapActivity {

    private SearchModuleDelegate mSearchModuelDeletage;

    public static int MAIN_ACTIVITY_REQUEST_CHOOSE_CITY_ADDRESS_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout contentView = (RelativeLayout)findViewById(R.id.content_view);

        mSearchModuelDeletage = new SearchModuleDelegate();
        mSearchModuelDeletage.bindParentDelegate(mSearchModuleParentDelegate);
        contentView.addView(mSearchModuelDeletage.getWidget(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private SearchModuleDelegate.IParentDelegate mSearchModuleParentDelegate = new IParentDelegate() {
        @Override
        public void onChangeCityName() {
            showToast("选择城市");
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CityChooseActivity.class);
            intent.putExtra(CityChooseActivity.CURR_CITY_KEY, mSearchModuelDeletage.getCurrCity().getCity());
            MainActivity.this.startActivityForResult(intent, MAIN_ACTIVITY_REQUEST_CHOOSE_CITY_ADDRESS_CODE);
        }

        @Override
        public void onCancel() {
            showToast("取消");
        }
    };


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (MAIN_ACTIVITY_REQUEST_CHOOSE_CITY_ADDRESS_CODE == requestCode && resultCode == RESULT_OK) {
            String currCityStr = data.getStringExtra(CityChooseActivity.CURR_CITY_KEY);
            Gson gson = new Gson();
            CityModel cityModel = gson.fromJson(currCityStr, CityModel.class);
            mSearchModuelDeletage.setCity(cityModel);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

