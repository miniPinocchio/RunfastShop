package com.example.runfastshop.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.shopcaradater.CarAdapter;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.fragment.BusinessFragment;
import com.example.runfastshop.util.CustomUtils;
import com.example.runfastshop.util.ViewUtils;
import com.example.runfastshop.view.AddWidget;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 */
public class ProductDetailActivity extends ToolBarActivity implements AddWidget.OnAddClick {

    @BindView(R.id.tv_food_name)
    TextView tvFoodName;

    @BindView(R.id.tv_food_sale)
    TextView tvFoodSale;
    @BindView(R.id.tv_food_price)
    TextView tvFoodPrice;
    @BindView(R.id.iv_sub)
    ImageView ivSub;
    @BindView(R.id.tv_product_count)
    TextView tvProductCount;

    private View blackView;

    public BottomSheetBehavior behavior;
    private boolean sheetScrolling;
    private FrameLayout layoutShopCar;
    private ImageView iv_shop_car;
    private TextView car_badge;
    private TextView business_title;
    private TextView car_limit;
    private TextView tv_amount;
    private CarAdapter carAdapter;

    private int shopWidth;
    private int[] carLoc;
    private CoordinatorLayout rootview;
    private BigDecimal decimal;
    private BusinessFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        initView();
        initShopCar();
        initData();
    }

    private void initView() {
        fragment = CustomUtils.fragment;
        rootview = (CoordinatorLayout) findViewById(R.id.rootview);
    }

    private void initShopCar() {
        layoutShopCar = (FrameLayout) findViewById(R.id.layout_shop_car);
        iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
        car_badge = (TextView) findViewById(R.id.car_badge);
        business_title = (TextView) findViewById(R.id.business_title);
        car_limit = (TextView) findViewById(R.id.car_limit);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        RecyclerView carRecView = (RecyclerView) findViewById(R.id.car_recyclerview);
        carRecView.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);
        carAdapter = new CarAdapter(new ArrayList<FoodBean>(), this);
        carAdapter.bindToRecyclerView(carRecView);
        blackView = findViewById(R.id.blackview);
        behavior = BottomSheetBehavior.from(findViewById(R.id.car_container));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                sheetScrolling = false;
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    blackView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                sheetScrolling = true;
                blackView.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(blackView, slideOffset);
            }
        });
    }

    private void initData() {
        FoodBean foodBean = (FoodBean) getIntent().getSerializableExtra("food");
        if (foodBean != null) {
            tvFoodName.setText(foodBean.getName());
            tvFoodSale.setText(foodBean.getSale());
            tvFoodPrice.setText(foodBean.getPrice() + "");
            if (foodBean.getSelectCount() > 0) {
                ivSub.setVisibility(View.VISIBLE);
                tvProductCount.setText(foodBean.getSelectCount()+"");
            }
            dealCar(foodBean);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_collection,R.id.iv_sub, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collection:
                break;
            case R.id.iv_sub:
                break;
            case R.id.iv_add:
                break;
        }
    }

    public void toggleCar(View view) {
        if (sheetScrolling) {
            return;
        }
        if (carAdapter.getItemCount() == 0) {
            return;
        }
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onAddClick(View view, FoodBean fb) {
        dealCar(fb);
        int[] addLoc = new int[2];
        view.getLocationInWindow(addLoc);
        if (shopWidth == 0) {
            carLoc = new int[2];
            shopWidth = iv_shop_car.getWidth() / 2;
            iv_shop_car.getLocationInWindow(carLoc);
            carLoc[0] = carLoc[0] + shopWidth - view.getWidth() / 2;
        }

        Path path = new Path();
        path.moveTo(addLoc[0], addLoc[1]);
        path.quadTo(addLoc[0] - 500, addLoc[1] - 200, carLoc[0], carLoc[1]);

        final TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.circle_red);
        textView.setText("1");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(view.getWidth(), view.getHeight());
        rootview.addView(textView, lp);
        ViewAnimator.animate(textView).path(path).accelerate().duration(400).onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                rootview.removeView(textView);
            }
        }).start();
    }

    @Override
    public void onSubClick(FoodBean fb) {
        dealCar(fb);
    }

    private void dealCar(FoodBean foodBean) {
        HashMap<String, Long> typeSelect = new HashMap<>();
        BigDecimal amount = new BigDecimal(0.0);
        int total = 0;
        boolean hasFood = false;
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            fragment.getFoodAdapter().notifyDataSetChanged();
        }
        List<FoodBean> flist = carAdapter.getData();
        int p = -1;
        for (int i = 0; i < flist.size(); i++) {
            FoodBean fb = flist.get(i);
            total += fb.getSelectCount();
            if (typeSelect.containsKey(fb.getType())) {
                typeSelect.put(fb.getType(), typeSelect.get(fb.getType()) + fb.getSelectCount());
            } else {
                typeSelect.put(fb.getType(), fb.getSelectCount());
            }
            amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
            if (fb.getId() == foodBean.getId()) {
                hasFood = true;
                if (foodBean.getSelectCount() == 0) {
                    p = i;
                } else {
                    carAdapter.setData(i, foodBean);
                }
            }
        }
        if (p >= 0) {
            carAdapter.remove(p);
        } else if (!hasFood) {
            carAdapter.addData(foodBean);
            if (typeSelect.containsKey(foodBean.getType())) {
                typeSelect.put(foodBean.getType(), typeSelect.get(foodBean.getType()) + foodBean.getSelectCount());
            } else {
                typeSelect.put(foodBean.getType(), foodBean.getSelectCount());
            }
            amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
            total += foodBean.getSelectCount();
        }
        if (total > 0) {
            car_badge.setVisibility(View.VISIBLE);
            car_badge.setText(total + "");
        } else {
            car_badge.setVisibility(View.INVISIBLE);
        }
        fragment.getTypeAdapter().updateBadge(typeSelect);
        updateAmount(amount);
    }

    private void updateAmount(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0.0)) == 0) {
            car_limit.setText("¥20 起送");
            car_limit.setTextColor(Color.parseColor("#a8a8a8"));
            car_limit.setBackgroundColor(Color.parseColor("#535353"));
            findViewById(R.id.car_nonselect).setVisibility(View.VISIBLE);
            findViewById(R.id.amount_container).setVisibility(View.GONE);
            iv_shop_car.setImageResource(R.drawable.icon_not_shop_car);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            car_limit.setEnabled(false);
        } else if (amount.compareTo(new BigDecimal(20.0)) < 0) {
            car_limit.setText("还差 ¥" + (new BigDecimal(20.0).subtract(amount)) + " 起送");
            car_limit.setTextColor(Color.parseColor("#a8a8a8"));
            car_limit.setBackgroundColor(Color.parseColor("#535353"));
            findViewById(R.id.car_nonselect).setVisibility(View.GONE);
            findViewById(R.id.amount_container).setVisibility(View.VISIBLE);
            iv_shop_car.setImageResource(R.drawable.icon_not_shop_car);
            car_limit.setEnabled(false);
        } else {
            car_limit.setText("去结算");
            car_limit.setTextColor(Color.WHITE);
            car_limit.setBackgroundColor(Color.parseColor("#59d178"));
            findViewById(R.id.car_nonselect).setVisibility(View.GONE);
            findViewById(R.id.amount_container).setVisibility(View.VISIBLE);
            iv_shop_car.setImageResource(R.drawable.icon_shop_car);
            car_limit.setEnabled(true);
        }
        tv_amount.setText("¥" + amount);
        decimal = amount;
    }

    public void clearCar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView tv = new TextView(this);
        tv.setText("清空购物车?");
        tv.setTextSize(14);
        tv.setPadding(ViewUtils.dip2px(this, 16), ViewUtils.dip2px(this, 16), 0, 0);
        tv.setTextColor(Color.parseColor("#757575"));
        AlertDialog alertDialog = builder
                .setNegativeButton("取消", null)
                .setCustomTitle(tv)
                .setPositiveButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<FoodBean> flist = carAdapter.getData();
                        for (int i = 0; i < flist.size(); i++) {
                            FoodBean fb = flist.get(i);
                            fb.setSelectCount(0);
                        }
                        carAdapter.setNewData(new ArrayList<FoodBean>());
                        fragment.getFoodAdapter().notifyDataSetChanged();
                        car_badge.setVisibility(View.INVISIBLE);
                        fragment.getTypeAdapter().updateBadge(new HashMap<String, Long>());
                        updateAmount(new BigDecimal(0.0));
                    }
                })
                .show();
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ContextCompat.getColor(this, R.color.dodgerblue));
        nButton.setTypeface(Typeface.DEFAULT_BOLD);
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ContextCompat.getColor(this, R.color.dodgerblue));
        pButton.setTypeface(Typeface.DEFAULT_BOLD);
    }

}
