package com.mph.letapp.utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public abstract class Mapper<T1, T2> {

    public abstract T2 map(T1 value);

    public abstract T1 reverseMap(T2 value);

    public List<T2> map(List<T1> values) {
        List<T2> returnValues = new ArrayList<>(values.size());
        for (T1 value : values) {
            returnValues.add(map(value));
        }
        return returnValues;
    }

    public List<T1> reverseMap(List<T2> values) {
        List<T1> returnValues = new ArrayList<>(values.size());
        for (T2 value : values) {
            returnValues.add(reverseMap(value));
        }
        return returnValues;
    }

    public Function<List<T1>, List<T2>> map() {
        return new Function<List<T1>, List<T2>>() {
            @Override
            public List<T2> apply(List<T1> t1s) throws Exception {
                List<T1> filteredList = new ArrayList<>();
                for (T1 t1 : t1s) {
                    if (t1 != null) {
                        filteredList.add(t1);
                    }
                }
                return map(filteredList);
            }
        };
    }
}
