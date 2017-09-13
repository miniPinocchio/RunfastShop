package com.example.runfastshop.bean.spend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class AccountRecords implements Parcelable{
    private Integer currentPage;
    private Integer totalPage;

    private List<AccountRecord> rows;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<AccountRecord> getRows() {
        return rows;
    }

    public void setRows(List<AccountRecord> rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.currentPage);
        dest.writeValue(this.totalPage);
        dest.writeList(this.rows);
    }

    public AccountRecords() {
    }

    protected AccountRecords(Parcel in) {
        this.currentPage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rows = new ArrayList<AccountRecord>();
        in.readList(this.rows, AccountRecord.class.getClassLoader());
    }

    public static final Creator<AccountRecords> CREATOR = new Creator<AccountRecords>() {
        @Override
        public AccountRecords createFromParcel(Parcel source) {
            return new AccountRecords(source);
        }

        @Override
        public AccountRecords[] newArray(int size) {
            return new AccountRecords[size];
        }
    };
}
