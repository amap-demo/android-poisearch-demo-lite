# android-poisearch-lite-demo
AMap 检索的逻辑模块。着重介绍AMap检索的使用方法，通过参数配置，用户可以定制排序规则，类别等。

## 前述 ##
- [高德官网申请Key](http://lbs.amap.com/dev/#/).
- 阅读[参考手册](http://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html).
- 工程基于高德地图Android地图SDK实现

## 配置搭建AndroidSDK工程 ##
- [Android Studio工程搭建方法](http://lbs.amap.com/api/android-sdk/guide/creat-project/android-studio-creat-project/#add-jars).
- [通过maven库引入SDK方法](http://lbsbbs.amap.com/forum.php?mod=viewthread&tid=18786).

## 组件截图 ###
<img src="https://github.com/LiangChaoPossible/android-poisearch-demo-lite/blob/dev_poisearch_instru/poi-search-lite-sc.png" width="50%" />

# Demo结构介绍
类图如下：

<img src="https://github.com/LiangChaoPossible/android-poisearch-demo-lite/blob/dev_poisearch_instru/poisearch-lite-classdiagram.png" width="50%" />

* Module包：处理调用逻辑和显示逻辑，与检索核心逻辑无关，可以不关注
* IPoisearchDelegate.java 便于Module调用，与检索核心逻辑无关，可以不关注
* PoiSearchDelegateImpl.java 调用AMapSearchUtil.java ,并只返回最新的检索对应的结果，与检索核心逻辑无关，可以不关注
* AMapSearchUtil.java 调用AMap检索SDK进行poi检索。详细介绍见下。

# AMapSearchUtil 详细介绍
AMapSearchUtil.java 封装了AMap的Poi检索
## 使用方法
建议出行场景的用户使用方法1。是否按照距离排序可以按照需求判断（如果设置为true，必须要传中心点才有意义） <br />
方法1：  <br />
* isCityLimit默认为true，表示只返回本城市的数据； <br />
* category采用默认类别即全部类别，但是仅不包含公共测试；  <br />
* 页数和每页数量采取了默认，分别是0和15  <br />

```java
/**
     * Poi检索。isCityLimit默认为true，category采用默认类别，页数和每页数量采取了默认值
     * @param context
     * @param keyWord 关键字
     * @param adCode 城市的adcode
     * @param isDistanceSort 是否是距离排序，如果为true，则必须设置中心点。如果为false或者中心点为非法，则为按照权重排序。
     * @param centerLL 中心点
     * @return
     */
    public static void doPoiSearchAysnc(Context context, final long searchId, String keyWord,
                                        String adCode, boolean isDistanceSort, LatLng centerLL , final OnSearchResListener poiListener) {
                                        ...
                                        }

```


方法2：<br />
全部参数需要用户自行设置。详细说明见下。 <br />
```java
	
	/**
     * Poi检索
     * @param context
     * @param keyWord 关键字
     * @param category 类别。具体类别信息参见：http://lbs.amap.com/api/webservice/download。传入类别时，请传入编码不要传入类别名字
     * @param adCode 城市的adCode
     * @param isCityLimit 是否限制在当前城市内
     * @param isDistanceSort 是否是距离排序，如果为true，则必须设置中心点。如果为false或者中心点为非法，则为按照权重排序。
     * @param centerLL 中心点
     * @param page 页数
     * @param pageSize 每页数量
     * @param poiListener 回调
     * @return
     */
    public static void doPoiSearchAysnc(Context context, final long searchId, String keyWord, String category,
                                             String adCode, boolean isCityLimit, boolean isDistanceSort,
                                             LatLng centerLL,
                                             int page,
                                             int pageSize, final OnSearchResListener poiListener) {
                                     
                                        

```


## 参数详细介绍 ##
* keyWord 关键字
* category 类别。<br />
		1. 每个分类用分类代码表示多个类型用“|”分割 <br />
		2. 分类代码由六位数字组成，一共分为三个部分，前两个数字代表大类；中间两个数字代表中类；最后两个数字代表小类。若指定了某个大类，则所属的中类、小类都会被显示。POI分类编码表：http://lbs.amap.com/api/webservice/download <br />
		3. category 的例子 ： 全部类别但是不包含公共厕所  01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|200000|200100|200200|200400|22|97|99 <br />

  详细说明地址：http://lbs.amap.com/api/webservice/guide/api/search

* adCode 城市中文、中文全拼、citycode、adcode ， 如：北京/beijing/010/110000。 本demo中统一传adcode
* isCityLimit 仅返回指定城市数据。 建议出行场景的用户使用
* isDistanceSort 是否是距离排序，如果为true，则必须设置中心点。如果为false或者中心点为无效值，则按照AMap检索的默认排序
* centerLL 中心点，在isDistanceSort为true时有意义
* page 页数。建议出行场景用户设置为0
* pageSize 每页数量。建议出行场景用户设置为15-20之间。本demo设置为15
* poiListener 回调
