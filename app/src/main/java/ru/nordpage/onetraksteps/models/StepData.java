package ru.nordpage.onetraksteps.models;

import com.google.gson.annotations.SerializedName;

public class StepData {

    @SerializedName("aerobic")
    private int mAerobic;
    @SerializedName("date")
    private long mDate;
    @SerializedName("run")
    private int mRun;
    @SerializedName("walk")
    private int mWalk;

    public int getAerobic() {
        return mAerobic;
    }

    public void setAerobic(int aerobic) {
        mAerobic = aerobic;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public int getRun() {
        return mRun;
    }

    public void setRun(int run) {
        mRun = run;
    }

    public int getWalk() {
        return mWalk;
    }

    public void setWalk(int walk) {
        mWalk = walk;
    }

}
