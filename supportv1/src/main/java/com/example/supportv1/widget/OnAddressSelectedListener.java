package com.example.supportv1.widget;


import com.example.supportv1.bean.City;
import com.example.supportv1.bean.County;
import com.example.supportv1.bean.Province;
import com.example.supportv1.bean.Street;

public interface OnAddressSelectedListener {
    void onAddressSelected(Province province, City city, County county, Street street);
}
