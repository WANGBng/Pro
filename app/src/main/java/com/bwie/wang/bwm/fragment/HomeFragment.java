package com.bwie.wang.bwm.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.adapter.RecyclerFationAdapter;
import com.bwie.wang.bwm.adapter.RecyclerHotAdapter;
import com.bwie.wang.bwm.adapter.RecyclerLiftAdapter;
import com.bwie.wang.bwm.adapter.home.Home_Menu_One_Adapter;
import com.bwie.wang.bwm.adapter.home.Home_Menu_Two_Adapter;
import com.bwie.wang.bwm.adapter.home.Home_Shop_Details_Recycler_Adapter;
import com.bwie.wang.bwm.adapter.home.Home_Shop_List_Adapter;
import com.bwie.wang.bwm.bean.Bean;
import com.bwie.wang.bwm.bean.home.Home_Menu_One_Bean;
import com.bwie.wang.bwm.bean.home.Home_Menu_Two_Bean;
import com.bwie.wang.bwm.bean.home.Home_Shop_Add_Car;
import com.bwie.wang.bwm.bean.home.Home_Shop_Comment_List_Bean;
import com.bwie.wang.bwm.bean.home.Home_Shop_Details_List_Bean;
import com.bwie.wang.bwm.bean.home.Home_Shop_List_Bean;
import com.bwie.wang.bwm.bean.login.RegistBean;
import com.bwie.wang.bwm.bean.home.XbannerBean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements IView {
    @BindView(R.id.QQQ)
    RelativeLayout QQQ;


    private View view;
    @BindView(R.id.Home_Menu)
    ImageView mHomeMenu;
    @BindView(R.id.Hone_Edit)
    EditText mHomeEdit;
    @BindView(R.id.Home_Search)
    ImageView mHomeSearch;
    private RecyclerView mRecyclerHot;
    private RecyclerView mRecyclerFation;
    private RecyclerHotAdapter mHotAdapter;
    private List<Bean.ResultBean.RxxpBean.CommodityListBean> mCommodityList;
    private RecyclerFationAdapter mFationAdapter;
    private List<Bean.ResultBean.MlssBean.CommodityListBeanXX> mCommodityList1;

    IPrenserterImp mIPrenserterImp;

    private List<XbannerBean.ResultBean> mResult;
    private RecyclerView mRecyclerLift;
    private RecyclerLiftAdapter mLiftAdapter;

    //条目类

    @BindView(R.id.item_one_menu)
    LinearLayout item_one_menu;
    @BindView(R.id.OnClickMenu)
    RecyclerView OnClickMenu;
    private Home_Menu_One_Adapter mMenu_one_adapter;
    private Home_Menu_Two_Adapter mMenuTwoAdapter;
    private Home_Shop_List_Adapter mShopListAdapter;
    @BindView(R.id.Home_Search_Error_Image)
    ImageView Home_Search_Error_Image;
    @BindView(R.id.Home_Search_Error_Text)
    TextView Home_Search_Error_Text;
    private Home_Shop_Details_Recycler_Adapter mHomeShopDetailsRecyclerAdapter;
    //商品详情

    @BindView(R.id.Home_Shop_Details_Image)
    ImageView Home_Shop_Details_Image;
    //添加的购物车

    @BindView(R.id.Shop_Add_Car)
    ImageView Shop_Add_Car;

    private ImageView mShopAddCar;
    private int mCommodityId;
    private List<Home_Shop_Add_Car> mHomeShopAddCars;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        //绑定
        unbinder = ButterKnife.bind(this, view);
        mHomeMenu.bringToFront();
        //轮播图
        //时尚
        initViewPager(view);

        //热门
        initHotView(view);
        initFationView(view);
        //品质生活
        setLeft(view);
        //二级的分类
        twoMenu();
        //商品详情的列表 recyclerview
        HomeDetails();
        //添加到购物车
        mShopAddCar = (ImageView) view.findViewById(R.id.Shop_Add_Car);
        mShopAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                //  数据覆盖
                mHomeShopAddCars = new ArrayList<>();
                Home_Shop_Add_Car homeShopAddCar = new Home_Shop_Add_Car();
                homeShopAddCar.setCommodityId(mCommodityId);
                homeShopAddCar.setCount(1);
                mHomeShopAddCars.add(homeShopAddCar);
                Gson gson = new Gson();
                String toJson = gson.toJson(mHomeShopAddCars);
                map.put("data", toJson);
                mIPrenserterImp.startPut(map, APis.HOME_ADD_SHOP_CAR, RegistBean.class);

            }
        });
        return view;
    }

    /**
     * 轮播图
     */
    private XBanner mXBanner;

    private void initViewPager(View view) {

        mIPrenserterImp.startGet(APis.HOME_RECYCLER_BANNER, XbannerBean.class);
    }

    /**
     * 熱線新品
     */
    private void initHotView(View view) {
        mRecyclerHot = view.findViewById(R.id.Home_Hot_Recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerHot.setLayoutManager(manager);
        //适配器
        mHotAdapter = new RecyclerHotAdapter(getActivity());
        mRecyclerHot.setAdapter(mHotAdapter);
        mIPrenserterImp.startGet(APis.HOME_HOT, Bean.class);

    }

    /**
     * 茉莉时尚
     */
    private void initFationView(View view) {
        mRecyclerFation = view.findViewById(R.id.Home_Fation_Recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerFation.setLayoutManager(manager);
        //适配器
        mFationAdapter = new RecyclerFationAdapter(getActivity());
        mRecyclerFation.setAdapter(mFationAdapter);

    }

    /**
     * 品质生活
     */
    private void setLeft(View view) {
        mRecyclerLift = view.findViewById(R.id.Home_Lift_Recycler);
        //布局格式
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerLift.setLayoutManager(manager);
        //适配器

    }

    /**
     * 类目页点击事件
     */

    boolean boo = false;

    @OnClick(R.id.Home_Menu)
    public void setMenu() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        OnClickMenu.setLayoutManager(manager);
        //适配器
        mMenu_one_adapter = new Home_Menu_One_Adapter(getActivity());
        OnClickMenu.setAdapter(mMenu_one_adapter);
        if (boo) {
            //隐藏
            boo = false;
            item_one_menu.setVisibility(View.GONE);
        } else {
            //显示
            boo = true;
            item_one_menu.setVisibility(View.VISIBLE);
        }

        //网络请求
        mIPrenserterImp.startGet(APis.HOME_MENU, Home_Menu_One_Bean.class);

    }

    /**
     * 搜索的点击事件-----与商品列表展示公用同一个XRecycler
     */
    @OnClick(R.id.Home_Search)
    public void HomeSearch() {
        //搜索的网络请求
        initShopListXRecycler();
        Home_Shop_List.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                initSearch();
            }

            @Override
            public void onLoadMore() {
                initSearch();
            }
        });
        initSearch();
    }

    private void initSearch() {
        //得到输入的值

        if (TextUtils.isEmpty(mHomeEdit.getText().toString().trim())) {
            Home_Shop_List.setVisibility(View.INVISIBLE);
            XBanner.setVisibility(View.VISIBLE);
            Hot_Lift_Fashion.setVisibility(View.VISIBLE);
            OneTwoMenu.setVisibility(View.VISIBLE);
            Home_Search_Error_Text.setVisibility(View.INVISIBLE);
            Home_Search_Error_Image.setVisibility(View.GONE);
        } else {
            String inputHomeEdit = mHomeEdit.getText().toString();
            mIPrenserterImp.startGet(APis.HOME_KEYWORD + "?keyword=" + inputHomeEdit + "&page=" + mPage + "&count=" + mCount, Home_Shop_List_Bean.class);
        }
    }

    /**
     * 获取资源ID
     *
     * @param view
     */
    private void initView(View view) {
        mHomeMenu = (ImageView) view.findViewById(R.id.Home_Menu);
        mHomeSearch = (ImageView) view.findViewById(R.id.Home_Search);
        mXBanner = (XBanner) view.findViewById(R.id.xbanner);
        //qqqqqqqq
        mIPrenserterImp = new IPrenserterImp(this);

    }

    /**
     * 得到数据
     *
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Bean) {

            Bean bean = (Bean) o;
            //热门
            final List<Bean.ResultBean.RxxpBean> rxxp = bean.getResult().getRxxp();
            for (int i = 0; i < rxxp.size(); i++) {
                mCommodityList = rxxp.get(i).getCommodityList();
                mHotAdapter.setMjihe(mCommodityList);
                mHotAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                        mCommodityId = mCommodityList.get(index).getCommodityId();
                        //热门图片的评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS + "?commodityId=" + mCommodityId, Home_Shop_Details_List_Bean.class);

                    }
                });
            }
            //时尚
            List<Bean.ResultBean.MlssBean> mlss = bean.getResult().getMlss();
            for (int i = 0; i < mlss.size(); i++) {
                mCommodityList1 = mlss.get(i).getCommodityList();
                mFationAdapter.setMjihe(mCommodityList1);
                mFationAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                        mCommodityId = mCommodityList.get(index).getCommodityId();
                        //热门图片的评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS + "?commodityId=" + mCommodityId, Home_Shop_Details_List_Bean.class);

                    }
                });
            }
            //生活

            List<Bean.ResultBean.PzshBean> pzsh = bean.getResult().getPzsh();
            mLiftAdapter = new RecyclerLiftAdapter(getActivity());
            mRecyclerLift.setAdapter(mLiftAdapter);
            for (int i = 0; i < pzsh.size(); i++) {
                List<Bean.ResultBean.PzshBean.CommodityListBeanX> beanXList = pzsh.get(i).getCommodityList();
                mLiftAdapter.setMjihe(beanXList);
                mLiftAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int position) {
                        //网络请求
                        mCommodityId = mCommodityList.get(position).getCommodityId();
                        //热门图片评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS + "?commodityId=" + mCommodityId, Home_Shop_Details_List_Bean.class);

                    }
                });

            }
        }
        //XBanner
        if (o instanceof XbannerBean) {
            XbannerBean xbannerBean = (XbannerBean) o;
            mResult = xbannerBean.getResult();
            initXbanner();
        }
        //一级分类
        if (o instanceof Home_Menu_One_Bean) {
            final Home_Menu_One_Bean menuOneBean = (Home_Menu_One_Bean) o;
            mMenu_one_adapter.setMjihe(menuOneBean.getResult());
            mMenu_one_adapter.setCallBack(new Home_Menu_One_Adapter.CallBack() {
                @Override
                public void getItem(int i) {
                    //得到点击的一级条目的id
                    initTwoMenu(i);
                }
            });
        }
        //二级分类
        if (o instanceof Home_Menu_Two_Bean) {
            Home_Menu_Two_Bean menuTwoBean = (Home_Menu_Two_Bean) o;
            mMenuTwoAdapter.setMjihe(menuTwoBean.getResult());
            mMenuTwoAdapter.setCallBack(new Home_Menu_Two_Adapter.CallBack() {
                @Override
                public void callBack(int i) {
                    //详细类列商品表
                    initListShop(i);
                }
            });
        }

        //详细分类商品列表
        if (o instanceof Home_Shop_List_Bean) {
            final Home_Shop_List_Bean homeShopListBean = (Home_Shop_List_Bean) o;
            //
            if (homeShopListBean.getResult().size() > 0) {
                if (mPage == 1) {
                    mShopListAdapter.setMjihe(homeShopListBean.getResult());
                } else {
                    mShopListAdapter.addMjihe(homeShopListBean.getResult());
                    mShopListAdapter.notifyDataSetChanged();
                }
                mPage++;
                Home_Shop_List.loadMoreComplete();
                Home_Shop_List.refreshComplete();
                Home_Search_Error_Text.setVisibility(View.INVISIBLE);
                Home_Search_Error_Image.setVisibility(View.INVISIBLE);
                //点击事件：
                mShopListAdapter.setCallBack(new RecyclerHotAdapter.CallBack() {
                    @Override
                    public void getData(int index) {
                        //网络请求
                        mCommodityId = homeShopListBean.getResult().get(index).getCommodityId();
                        //热门图片的评价
                        mIPrenserterImp.startGet(APis.HOME_SHOP_COMMENT_LIST + mCommodityId + "&page=" + mPage + "&count=" + mCount, Home_Shop_Comment_List_Bean.class);
                        //热门图片的详情
                        mIPrenserterImp.startGet(APis.HOME_SHOP_DETAILS + "?commodityId=" + mCommodityId, Home_Shop_Details_List_Bean.class);

                    }
                });
            }

            if (homeShopListBean.getResult().size() == 0) {
                //查询失败!!!
                //图片和文字显示，其余隐藏
                Home_Search_Error_Text.setVisibility(View.VISIBLE);
                Home_Search_Error_Image.setVisibility(View.VISIBLE);
                XBanner.setVisibility(View.INVISIBLE);
                Hot_Lift_Fashion.setVisibility(View.INVISIBLE);
                OneTwoMenu.setVisibility(View.INVISIBLE);

            }
        }

        //商品评价列表
        if (o instanceof Home_Shop_Comment_List_Bean) {
            Home_Shop_Comment_List_Bean homeShopCommentListBean = (Home_Shop_Comment_List_Bean) o;
            //得到数据集合，
            List<Home_Shop_Comment_List_Bean.ResulteBean> result = homeShopCommentListBean.getResult();
            mHomeShopDetailsRecyclerAdapter.setMjihe(result);
            //显示
            home_shop_details.setVisibility(View.VISIBLE);
            Home_All.setVisibility(View.GONE);
        }
        //商品详情列表
        if (o instanceof Home_Shop_Details_List_Bean) {
            Home_Shop_Details_List_Bean homeShopDetailsListBean = (Home_Shop_Details_List_Bean) o;
            //  Home_Shop_Details_Image；
            String picture = homeShopDetailsListBean.getResult().getPicture();
            String[] split = picture.split("\\,");
            String image = split[0];
            Glide.with(getActivity()).load(image).into(Home_Shop_Details_Image);
            //显示
            home_shop_details.setVisibility(View.VISIBLE);
            Home_All.setVisibility(View.GONE);
        }
        //将商品添加到购物车
        if (o instanceof RegistBean) {
            RegistBean registBean = (RegistBean) o;
            if (registBean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), registBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    @BindView(R.id.Home_Shop_Details)
    RelativeLayout home_shop_details;
    @BindView(R.id.home_all)
    RelativeLayout Home_All;
    @BindView(R.id.Home_Shop_Details_Recycler)
    RecyclerView Home_Shop_Details_Recycler;

    //商品詳情的列表
    private void HomeDetails() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        Home_Shop_Details_Recycler.setLayoutManager(manager);
        //适配器：
        mHomeShopDetailsRecyclerAdapter = new Home_Shop_Details_Recycler_Adapter(getActivity());
        Home_Shop_Details_Recycler.setAdapter(mHomeShopDetailsRecyclerAdapter);

    }

    /**
     * 详细分类商品列表
     */
    @BindView(R.id.Home_Shop_List)
    XRecyclerView Home_Shop_List;
    int mPage = 1;
    @BindView(R.id.xbanner)
    XBanner XBanner;
    @BindView(R.id.Hot_Lift_Fashion)
    RelativeLayout Hot_Lift_Fashion;
    @BindView(R.id.OneTwoMenu)
    RelativeLayout OneTwoMenu;

    private void initListShop(final int i) {
        //XRecycler
        initShopListXRecycler();
        Home_Shop_List.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override       //刷新
            public void onRefresh() {
                mPage = 1;
                //请求网络
                initXRecycler(i, mPage);
                Home_Shop_List.refreshComplete();
            }

            @Override       //加载
            public void onLoadMore() {
                //请求网络
                initXRecycler(i + 1, mPage++);
            }
        });
        initXRecycler(i + 1, mPage++);
    }

    /**
     * 商品列表XRecycler的布局、适配器
     */
    private void initShopListXRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        Home_Shop_List.setLayoutManager(manager);
        //适配器
        mShopListAdapter = new Home_Shop_List_Adapter(getActivity());
        Home_Shop_List.setAdapter(mShopListAdapter);
        //显示并隐藏XBanner、Hot_Lift_Fashion
        Home_Shop_List.setVisibility(View.VISIBLE);
        XBanner.setVisibility(View.INVISIBLE);
        Hot_Lift_Fashion.setVisibility(View.INVISIBLE);
        OneTwoMenu.setVisibility(View.INVISIBLE);
        Home_Search_Error_Text.setVisibility(View.INVISIBLE);
        Home_Search_Error_Image.setVisibility(View.INVISIBLE);
        //设置刷新加载
        Home_Shop_List.setLoadingMoreEnabled(true);
        Home_Shop_List.setPullRefreshEnabled(true);
    }

    /**
     * 商品列表的网络请求
     */
    int mCount = 5;

    private void initXRecycler(int i, int mpage) {
        mIPrenserterImp.startGet(APis.HOME_SHOP_LIST + "categoryId=" + i + "&page=" + 1 + "&count=" + mCount, Home_Shop_List_Bean.class);
    }

    /**
     * 点击后 二级分类
     */
    @BindView(R.id.TwoMenuRecycler)
    RecyclerView TwoMenuRecycler;

    private void initTwoMenu(int i) {
        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        TwoMenuRecycler.setLayoutManager(manager);
        //适配器
        mMenuTwoAdapter = new Home_Menu_Two_Adapter(getActivity());
        TwoMenuRecycler.setAdapter(mMenuTwoAdapter);
        //显示
        TwoMenuRecycler.setVisibility(View.VISIBLE);
        //请求网络
        mIPrenserterImp.startGet(APis.HOME_MENU_TWO + i, Home_Menu_Two_Bean.class);

    }

    /**
     * 初始的二级分类
     */
    private void twoMenu() {
        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        TwoMenuRecycler.setLayoutManager(manager);
        //适配器
        mMenuTwoAdapter = new Home_Menu_Two_Adapter(getActivity());
        TwoMenuRecycler.setAdapter(mMenuTwoAdapter);

        //请求网络
        int Initial_Value = 1001002;
        mIPrenserterImp.startGet(APis.HOME_MENU_TWO + Initial_Value, Home_Menu_Two_Bean.class);
    }


    /**
     * XBanner展示
     */
    List<String> mXbanner_list;

    private void initXbanner() {
        mXbanner_list = new ArrayList<>();
        for (int i = 0; i < mResult.size(); i++) {
            String imageUrl = mResult.get(i).getImageUrl();
            mXbanner_list.add(imageUrl);
        }
        // 为XBanner绑定数据
        //第二个参数为提示文字资源集合
        mXBanner.setData(mXbanner_list, null);
        // XBanner适配数据
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(mXbanner_list.get(position)).into((ImageView) view);
            }
        });
        // 设置XBanner页面切换的时间，即动画时长
        //横向移动
        mXBanner.setPageTransformer(Transformer.Default);
        //渐变，效果不明显
        mXBanner.setPageTransformer(Transformer.Alpha);
        // 缩小本页，同时放大另一页
        mXBanner.setPageTransformer(Transformer.ZoomFade);
        //本页缩小一点，另一页就放大
        mXBanner.setPageTransformer(Transformer.ZoomCenter);
        // 本页和下页同事缩小和放大
        mXBanner.setPageTransformer(Transformer.ZoomStack);
        //本页和下页同时左移
        mXBanner.setPageTransformer(Transformer.Stack);
        //本页刚左移，下页就在后面
        mXBanner.setPageTransformer(Transformer.Zoom);
        mXBanner.setPageChangeDuration(2000);

    }

    /**
     * 请求错误時返回结果
     *
     * @param error
     */
    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "主人,奴家还木有网络呐", Toast.LENGTH_LONG).show();
    }
    //    进行获取焦点，点击返回键返回上一级

    @Override
    public void onResume() {
        super.onResume();
        getFourse();
//        getFourser();

    }
//        进行获取焦点

    long exitTime = 0;
    private void getFourse() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Home_All.setVisibility(View.VISIBLE);
                    home_shop_details.setVisibility(View.GONE);
                    TwoMenuRecycler.setVisibility(View.GONE);
                    QQQ.setVisibility(View.GONE);

//                    navChildsLinear.setVisibility(View.GONE);
//                    navLinear.setVisibility(View.GONE);
                    if ((System.currentTimeMillis() - exitTime) > 1000) {
                        Toast.makeText(getActivity(), "再按一次就退出了哟", Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });



    }//    进行获取焦点
    private void getFourser() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Home_All.setVisibility(View.VISIBLE);
                    OneTwoMenu.setVisibility(View.GONE);
                    QQQ.setVisibility(View.GONE);
                    if ((System.currentTimeMillis() - exitTime) > 1000) {
                        Toast.makeText(getActivity(), "再按一次就退出了哟", Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });



    }//    进行获取焦点
    /**
     * 解决內存溢出
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mIPrenserterImp.onDelet();
    }
}
