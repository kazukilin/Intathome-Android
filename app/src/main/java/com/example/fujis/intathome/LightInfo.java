package com.example.fujis.intathome;

public class LightInfo {
    public String begin,end,ontime;
    public Boolean function;

    public LightInfo(String begin, String end, Boolean function, String ontime){
        this.begin = begin;
        this.end = end;
        this.function = function;
        this.ontime = ontime;
    }
}
