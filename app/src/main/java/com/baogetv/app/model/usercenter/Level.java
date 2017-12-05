package com.baogetv.app.model.usercenter;

import android.support.annotation.NonNull;

/**
 * Created by chalilayang on 2017/12/5.
 */

public class Level implements Comparable<Level> {
    public final int score;
    public final int index;
    public Level(int s, int i) {
        score = s;
        index = i;
    }

    @Override
    public int compareTo(@NonNull Level o) {
        return score - o.score;
    }
}
