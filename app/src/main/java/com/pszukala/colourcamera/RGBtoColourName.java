package com.pszukala.colourcamera;

import java.util.ArrayList;

/**
 * Created by Pszukala on 2015-11-09.
 */
public class RGBtoColourName {

    private ArrayList<ColourWithName> colorList = null;

    public RGBtoColourName(){
        colorList = initColorList();
    }

    private ArrayList<ColourWithName> initColorList() {
        ArrayList<ColourWithName> colorList = new ArrayList<ColourWithName>();
        colorList.add(new ColourWithName("AliceBlue", 0xF0, 0xF8, 0xFF));
        colorList.add(new ColourWithName("AntiqueWhite", 0xFA, 0xEB, 0xD7));
        colorList.add(new ColourWithName("Aqua", 0x00, 0xFF, 0xFF));
        colorList.add(new ColourWithName("Aquamarine", 0x7F, 0xFF, 0xD4));
        colorList.add(new ColourWithName("Azure", 0xF0, 0xFF, 0xFF));
        colorList.add(new ColourWithName("Beige", 0xF5, 0xF5, 0xDC));
        colorList.add(new ColourWithName("Bisque", 0xFF, 0xE4, 0xC4));
        colorList.add(new ColourWithName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColourWithName("BlanchedAlmond", 0xFF, 0xEB, 0xCD));
        colorList.add(new ColourWithName("Blue", 0x00, 0x00, 0xFF));
        colorList.add(new ColourWithName("BlueViolet", 0x8A, 0x2B, 0xE2));
        colorList.add(new ColourWithName("Brown", 0xA5, 0x2A, 0x2A));
        colorList.add(new ColourWithName("BurlyWood", 0xDE, 0xB8, 0x87));
        colorList.add(new ColourWithName("CadetBlue", 0x5F, 0x9E, 0xA0));
        colorList.add(new ColourWithName("Chartreuse", 0x7F, 0xFF, 0x00));
        colorList.add(new ColourWithName("Chocolate", 0xD2, 0x69, 0x1E));
        colorList.add(new ColourWithName("Coral", 0xFF, 0x7F, 0x50));
        colorList.add(new ColourWithName("CornflowerBlue", 0x64, 0x95, 0xED));
        colorList.add(new ColourWithName("Cornsilk", 0xFF, 0xF8, 0xDC));
        colorList.add(new ColourWithName("Crimson", 0xDC, 0x14, 0x3C));
        colorList.add(new ColourWithName("Cyan", 0x00, 0xFF, 0xFF));
        colorList.add(new ColourWithName("DarkBlue", 0x00, 0x00, 0x8B));
        colorList.add(new ColourWithName("DarkCyan", 0x00, 0x8B, 0x8B));
        colorList.add(new ColourWithName("DarkGoldenRod", 0xB8, 0x86, 0x0B));
        colorList.add(new ColourWithName("DarkGray", 0xA9, 0xA9, 0xA9));
        colorList.add(new ColourWithName("DarkGreen", 0x00, 0x64, 0x00));
        colorList.add(new ColourWithName("DarkKhaki", 0xBD, 0xB7, 0x6B));
        colorList.add(new ColourWithName("DarkMagenta", 0x8B, 0x00, 0x8B));
        colorList.add(new ColourWithName("DarkOliveGreen", 0x55, 0x6B, 0x2F));
        colorList.add(new ColourWithName("DarkOrange", 0xFF, 0x8C, 0x00));
        colorList.add(new ColourWithName("DarkOrchid", 0x99, 0x32, 0xCC));
        colorList.add(new ColourWithName("DarkRed", 0x8B, 0x00, 0x00));
        colorList.add(new ColourWithName("DarkSalmon", 0xE9, 0x96, 0x7A));
        colorList.add(new ColourWithName("DarkSeaGreen", 0x8F, 0xBC, 0x8F));
        colorList.add(new ColourWithName("DarkSlateBlue", 0x48, 0x3D, 0x8B));
        colorList.add(new ColourWithName("DarkSlateGray", 0x2F, 0x4F, 0x4F));
        colorList.add(new ColourWithName("DarkTurquoise", 0x00, 0xCE, 0xD1));
        colorList.add(new ColourWithName("DarkViolet", 0x94, 0x00, 0xD3));
        colorList.add(new ColourWithName("DeepPink", 0xFF, 0x14, 0x93));
        colorList.add(new ColourWithName("DeepSkyBlue", 0x00, 0xBF, 0xFF));
        colorList.add(new ColourWithName("DimGray", 0x69, 0x69, 0x69));
        colorList.add(new ColourWithName("DodgerBlue", 0x1E, 0x90, 0xFF));
        colorList.add(new ColourWithName("FireBrick", 0xB2, 0x22, 0x22));
        colorList.add(new ColourWithName("FloralWhite", 0xFF, 0xFA, 0xF0));
        colorList.add(new ColourWithName("ForestGreen", 0x22, 0x8B, 0x22));
        colorList.add(new ColourWithName("Fuchsia", 0xFF, 0x00, 0xFF));
        colorList.add(new ColourWithName("Gainsboro", 0xDC, 0xDC, 0xDC));
        colorList.add(new ColourWithName("GhostWhite", 0xF8, 0xF8, 0xFF));
        colorList.add(new ColourWithName("Gold", 0xFF, 0xD7, 0x00));
        colorList.add(new ColourWithName("GoldenRod", 0xDA, 0xA5, 0x20));
        colorList.add(new ColourWithName("Gray", 0x80, 0x80, 0x80));
        colorList.add(new ColourWithName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColourWithName("GreenYellow", 0xAD, 0xFF, 0x2F));
        colorList.add(new ColourWithName("HoneyDew", 0xF0, 0xFF, 0xF0));
        colorList.add(new ColourWithName("HotPink", 0xFF, 0x69, 0xB4));
        colorList.add(new ColourWithName("IndianRed", 0xCD, 0x5C, 0x5C));
        colorList.add(new ColourWithName("Indigo", 0x4B, 0x00, 0x82));
        colorList.add(new ColourWithName("Ivory", 0xFF, 0xFF, 0xF0));
        colorList.add(new ColourWithName("Khaki", 0xF0, 0xE6, 0x8C));
        colorList.add(new ColourWithName("Lavender", 0xE6, 0xE6, 0xFA));
        colorList.add(new ColourWithName("LavenderBlush", 0xFF, 0xF0, 0xF5));
        colorList.add(new ColourWithName("LawnGreen", 0x7C, 0xFC, 0x00));
        colorList.add(new ColourWithName("LemonChiffon", 0xFF, 0xFA, 0xCD));
        colorList.add(new ColourWithName("LightBlue", 0xAD, 0xD8, 0xE6));
        colorList.add(new ColourWithName("LightCoral", 0xF0, 0x80, 0x80));
        colorList.add(new ColourWithName("LightCyan", 0xE0, 0xFF, 0xFF));
        colorList.add(new ColourWithName("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2));
        colorList.add(new ColourWithName("LightGray", 0xD3, 0xD3, 0xD3));
        colorList.add(new ColourWithName("LightGreen", 0x90, 0xEE, 0x90));
        colorList.add(new ColourWithName("LightPink", 0xFF, 0xB6, 0xC1));
        colorList.add(new ColourWithName("LightSalmon", 0xFF, 0xA0, 0x7A));
        colorList.add(new ColourWithName("LightSeaGreen", 0x20, 0xB2, 0xAA));
        colorList.add(new ColourWithName("LightSkyBlue", 0x87, 0xCE, 0xFA));
        colorList.add(new ColourWithName("LightSlateGray", 0x77, 0x88, 0x99));
        colorList.add(new ColourWithName("LightSteelBlue", 0xB0, 0xC4, 0xDE));
        colorList.add(new ColourWithName("LightYellow", 0xFF, 0xFF, 0xE0));
        colorList.add(new ColourWithName("Lime", 0x00, 0xFF, 0x00));
        colorList.add(new ColourWithName("LimeGreen", 0x32, 0xCD, 0x32));
        colorList.add(new ColourWithName("Linen", 0xFA, 0xF0, 0xE6));
        colorList.add(new ColourWithName("Magenta", 0xFF, 0x00, 0xFF));
        colorList.add(new ColourWithName("Maroon", 0x80, 0x00, 0x00));
        colorList.add(new ColourWithName("MediumAquaMarine", 0x66, 0xCD, 0xAA));
        colorList.add(new ColourWithName("MediumBlue", 0x00, 0x00, 0xCD));
        colorList.add(new ColourWithName("MediumOrchid", 0xBA, 0x55, 0xD3));
        colorList.add(new ColourWithName("MediumPurple", 0x93, 0x70, 0xDB));
        colorList.add(new ColourWithName("MediumSeaGreen", 0x3C, 0xB3, 0x71));
        colorList.add(new ColourWithName("MediumSlateBlue", 0x7B, 0x68, 0xEE));
        colorList.add(new ColourWithName("MediumSpringGreen", 0x00, 0xFA, 0x9A));
        colorList.add(new ColourWithName("MediumTurquoise", 0x48, 0xD1, 0xCC));
        colorList.add(new ColourWithName("MediumVioletRed", 0xC7, 0x15, 0x85));
        colorList.add(new ColourWithName("MidnightBlue", 0x19, 0x19, 0x70));
        colorList.add(new ColourWithName("MintCream", 0xF5, 0xFF, 0xFA));
        colorList.add(new ColourWithName("MistyRose", 0xFF, 0xE4, 0xE1));
        colorList.add(new ColourWithName("Moccasin", 0xFF, 0xE4, 0xB5));
        colorList.add(new ColourWithName("NavajoWhite", 0xFF, 0xDE, 0xAD));
        colorList.add(new ColourWithName("Navy", 0x00, 0x00, 0x80));
        colorList.add(new ColourWithName("OldLace", 0xFD, 0xF5, 0xE6));
        colorList.add(new ColourWithName("Olive", 0x80, 0x80, 0x00));
        colorList.add(new ColourWithName("OliveDrab", 0x6B, 0x8E, 0x23));
        colorList.add(new ColourWithName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColourWithName("OrangeRed", 0xFF, 0x45, 0x00));
        colorList.add(new ColourWithName("Orchid", 0xDA, 0x70, 0xD6));
        colorList.add(new ColourWithName("PaleGoldenRod", 0xEE, 0xE8, 0xAA));
        colorList.add(new ColourWithName("PaleGreen", 0x98, 0xFB, 0x98));
        colorList.add(new ColourWithName("PaleTurquoise", 0xAF, 0xEE, 0xEE));
        colorList.add(new ColourWithName("PaleVioletRed", 0xDB, 0x70, 0x93));
        colorList.add(new ColourWithName("PapayaWhip", 0xFF, 0xEF, 0xD5));
        colorList.add(new ColourWithName("PeachPuff", 0xFF, 0xDA, 0xB9));
        colorList.add(new ColourWithName("Peru", 0xCD, 0x85, 0x3F));
        colorList.add(new ColourWithName("Pink", 0xFF, 0xC0, 0xCB));
        colorList.add(new ColourWithName("Plum", 0xDD, 0xA0, 0xDD));
        colorList.add(new ColourWithName("PowderBlue", 0xB0, 0xE0, 0xE6));
        colorList.add(new ColourWithName("Purple", 0x80, 0x00, 0x80));
        colorList.add(new ColourWithName("Red", 0xFF, 0x00, 0x00));
        colorList.add(new ColourWithName("RosyBrown", 0xBC, 0x8F, 0x8F));
        colorList.add(new ColourWithName("RoyalBlue", 0x41, 0x69, 0xE1));
        colorList.add(new ColourWithName("SaddleBrown", 0x8B, 0x45, 0x13));
        colorList.add(new ColourWithName("Salmon", 0xFA, 0x80, 0x72));
        colorList.add(new ColourWithName("SandyBrown", 0xF4, 0xA4, 0x60));
        colorList.add(new ColourWithName("SeaGreen", 0x2E, 0x8B, 0x57));
        colorList.add(new ColourWithName("SeaShell", 0xFF, 0xF5, 0xEE));
        colorList.add(new ColourWithName("Sienna", 0xA0, 0x52, 0x2D));
        colorList.add(new ColourWithName("Silver", 0xC0, 0xC0, 0xC0));
        colorList.add(new ColourWithName("SkyBlue", 0x87, 0xCE, 0xEB));
        colorList.add(new ColourWithName("SlateBlue", 0x6A, 0x5A, 0xCD));
        colorList.add(new ColourWithName("SlateGray", 0x70, 0x80, 0x90));
        colorList.add(new ColourWithName("Snow", 0xFF, 0xFA, 0xFA));
        colorList.add(new ColourWithName("SpringGreen", 0x00, 0xFF, 0x7F));
        colorList.add(new ColourWithName("SteelBlue", 0x46, 0x82, 0xB4));
        colorList.add(new ColourWithName("Tan", 0xD2, 0xB4, 0x8C));
        colorList.add(new ColourWithName("Teal", 0x00, 0x80, 0x80));
        colorList.add(new ColourWithName("Thistle", 0xD8, 0xBF, 0xD8));
        colorList.add(new ColourWithName("Tomato", 0xFF, 0x63, 0x47));
        colorList.add(new ColourWithName("Turquoise", 0x40, 0xE0, 0xD0));
        colorList.add(new ColourWithName("Violet", 0xEE, 0x82, 0xEE));
        colorList.add(new ColourWithName("Wheat", 0xF5, 0xDE, 0xB3));
        colorList.add(new ColourWithName("White", 0xFF, 0xFF, 0xFF));
        colorList.add(new ColourWithName("WhiteSmoke", 0xF5, 0xF5, 0xF5));
        colorList.add(new ColourWithName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColourWithName("YellowGreen", 0x9A, 0xCD, 0x32));
        return colorList;
    }

    public String getColourNameFromRgb(int r, int g, int b) {
        ColourWithName closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColourWithName c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }
}
