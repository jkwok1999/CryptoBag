package au.edu.unsw.infs3634.cryptobag.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("coins_num")
    @Expose
    private Integer coinsNum;
    @SerializedName("time")
    @Expose
    private Integer time;

    /**
     * No args constructor for use in serialization
     *
     */
    public Info() {
    }

    /**
     *
     * @param time
     * @param coinsNum
     */
    public Info(Integer coinsNum, Integer time) {
        super();
        this.coinsNum = coinsNum;
        this.time = time;
    }

    public Integer getCoinsNum() {
        return coinsNum;
    }

    public void setCoinsNum(Integer coinsNum) {
        this.coinsNum = coinsNum;
    }

    public Info withCoinsNum(Integer coinsNum) {
        this.coinsNum = coinsNum;
        return this;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Info withTime(Integer time) {
        this.time = time;
        return this;
    }

}

/*
Use this tool offline:Maven pluginGradle pluginAnt taskCLIJava API
        Reference
        properties
        For each property present in the 'properties' de */
