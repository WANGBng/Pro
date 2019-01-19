package com.bwie.wang.bwm.Until;

/**
 * @author lenovo
 *         各种网址接口的后半部分
 */
public class APis {

    //登录：

    public static final String MAIN_LOGIN = "user/v1/login";
    //注册：

    public static final String REGIST_NET = "user/v1/register";
    //用户也轮播图：

    public static final String HOME_RECYCLER_BANNER = "commodity/v1/bannerShow";
    //一级商品类目查询

    public static final String HOME_MENU = "commodity/v1/findFirstCategory";
    //二级商品类目查询

    public static final String HOME_MENU_TWO = "commodity/v1/findSecondCategory?firstCategoryId=";
    //详细商品列表

    public static final String HOME_SHOP_LIST = "commodity/v1/findCommodityByCategory?";
    //热门

    public static final String HOME_HOT = "commodity/v1/commodityList";
    //圈子

    public static final String FIND_PATH = "circle/v1/findCircleList";
    //获取个人信息

    public static final String MAIN_PROFILE = "user/verify/v1/getUserById";
    //我的圈子

    public static final String MAIN_FIND = "circle/verify/v1/findMyCircleById";
    //根据关键字搜索

    public static final String HOME_KEYWORD = "commodity/v1/findCommodityByKeyword";
    //商品评论列表

    public static final String HOME_SHOP_COMMENT_LIST = "commodity/v1/CommodityCommentList?commodityId=";
    //商品的详情

    public static final String HOME_SHOP_DETAILS = "commodity/v1/findCommodityDetailsById";
    //同步购物车数据

    public static final String HOME_ADD_SHOP_CAR = "order/verify/v1/syncShoppingCart";

    //我的购物车(0=查看全部  1=查看待付款  2=查看待收货  3=查看待评价  9=查看已完成)

    public static final String FRAGMENT_LIST = "order/verify/v1/findOrderListByStatus?";
    //查询购物车

    public static final String FRAGEMENT_SHOP_CAR = "order/verify/v1/findShoppingCart";
    //新增收货地址

    public static final String ADD_ADRESS = "user/verify/v1/addReceiveAddress";
    //我的地址列表

    public static final String ADRESS_LIST = "user/verify/v1/receiveAddressList";
    //创建订单

    public static final String CREATE_ORDER = "order/verify/v1/createOrder";


}
