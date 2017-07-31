package com.example.jian.shunmu;

import java.io.Serializable;

/**
 * Created by jian on 2017/3/22.
 */

public class coursetimeBean implements Serializable {
    int startTimeHour;
    int startTimeMinute;
    int endTimeHour;
    int endTimeMinute;

    public coursetimeBean(int startTimeHour,int startTimeMinute ,int endTimeHour,int endTimeMinute) {
        this.startTimeHour = startTimeHour;
        this.endTimeMinute = endTimeMinute;
        this.endTimeHour = endTimeHour;
        this.startTimeMinute = startTimeMinute;
    }

    public int getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(int startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public int getStartTimeMinute() {
        return startTimeMinute;
    }

    public void setStartTimeMinute(int startTimeMinute) {
        this.startTimeMinute = startTimeMinute;
    }

    public int getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(int endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public int getEndTimeMinute() {
        return endTimeMinute;
    }

    public void setEndTimeMinute(int endTimeMinute) {
        this.endTimeMinute = endTimeMinute;
    }
}
