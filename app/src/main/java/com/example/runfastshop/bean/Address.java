package com.example.runfastshop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;

/**
 * 地址匹配管理
 * Created by 天上白玉京 on 2017/7/31.
 */

public class Address implements Parcelable{

    public String title;

    public String address;

    public LatLng latLng;

    public int status;

    @Override
    public String toString() {
        return "Address{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", latLng=" + latLng +
                ", status=" + status +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeParcelable(this.latLng, flags);
        dest.writeInt(this.status);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.title = in.readString();
        this.address = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.status = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
