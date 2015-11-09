package com.pszukala.colourcamera;

/**
 * Created by Pszukala on 2015-11-09.
 */
public class ColourWithName {
    private int red, green, blue;
    private String name;

    public ColourWithName(String name, int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public int computeMSE(int r, int g, int b) {
        return (int) (((r - red) * (r - red) + (g - green) * (g - green)
                + (b - blue) * (b - blue)) / 3);
    }

    public String getName(){
        return name;
    }
}
