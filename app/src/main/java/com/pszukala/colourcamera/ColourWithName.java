package com.pszukala.colourcamera;

/**
 * Created by Pszukala on 2015-11-09.
 */
public class ColourWithName {
    public int Red, Green, Blue;
    public String Name;

    public ColourWithName(String name, int red, int green, int blue){
        this.Red = red;
        this.Green = green;
        this.Blue = blue;
        this.Name = name;
    }

    public int computeMSE(int r, int g, int b) {
        return (int) Math.sqrt(((r - Red) * (r - Red) + (g - Green) * (g - Green)
                + (b - Blue) * (b - Blue)) / 3);
    }

    public int redMSE(int r){
        return (int) Math.sqrt((r-Red)*(r-Red));
    }

    public int greenMSE(int g){
        return (int) Math.sqrt((g-Green)*(g-Green));
    }

    public int blueMSE(int b){
        return (int) Math.sqrt(((b - Blue) * (b - Blue)));
    }
}
