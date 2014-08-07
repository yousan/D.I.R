package com.example.yousan.myapplication17;

import java.util.ArrayList;

/**
 * Created by yousan on 2014/08/07.
 */
public class CircularArrayList<E> extends ArrayList<E> {

    private int index = 0;

    public E next() {
        if (super.size() == 0) { // avoid divide by zero
            return null;
        }
        E object = super.get(index);
        // sizeを超えない順繰りの正数
        // size=10, index: 0, 1, 2, 3, ..., 8, 9
        index = (index + 1) % super.size();
        return object;
    }

    public void reset() {
        index = 0;
    }

    public int getCurrentPosition() {
        return index;
    }

}
