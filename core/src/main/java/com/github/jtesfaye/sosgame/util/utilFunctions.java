package com.github.jtesfaye.sosgame.util;

public class utilFunctions {

    public static Pair<Integer, Integer> getBoardDimensions(String dim) {

        String[] vals = dim.split("x");

        int width = Integer.parseInt(vals[0]);
        int height = Integer.parseInt(vals[1]);

        return new Pair<> (width, height);
    }
}
