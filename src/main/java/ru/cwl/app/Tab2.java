package ru.cwl.app;

import java.util.*;

/**
 * Created by admin on 09.11.2016.
 */
class Tab2<V, D1, D2> {
    private final V z;
    Map<D1, Map<D2, V>> m = new HashMap<>();

    Tab2(V zero) {
        z = zero;
    }

    public V get(D1 d1, D2 d2) {
        Map<D2, V> v1 = m.get(d1);
        if (v1 != null) {
            V v2 = v1.get(d2);
            if (v2 != null) {
                return v2;
            }
        }
        return z;
    }

    public void add(D1 d1, D2 d2, V v) {
        V v0 = get(d1, d2);
        v0 = add(v0, v);

        Map<D2, V> v1 = m.get(d1);
        if (v1 == null) {
            v1 = new HashMap<D2, V>();
            m.put(d1, v1);
        }
        v1.put(d2, v0);
    }

    V add(V v0, V v1) {
        return null;
    }
    public List<D1> getRowsName(){
        return new ArrayList<D1>(m.keySet());
    }
    public List<D2> getColumnsName(){
        Set<D2> s=new HashSet<D2>();
        for (D1 d1 : getRowsName()) {
            s.addAll(m.get(d1).keySet());
        }
        return new ArrayList<D2>(s);
    }

}
