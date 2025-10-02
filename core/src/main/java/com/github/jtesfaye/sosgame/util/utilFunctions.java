package com.github.jtesfaye.sosgame.util;

public class utilFunctions {

    public static int[] getBoardDimensions(String dim) {

        String[] vals = dim.split("x");

        int width = Integer.parseInt(vals[0]);
        int height = Integer.parseInt(vals[1]);

        return new int[] {width, height};

    }

}
