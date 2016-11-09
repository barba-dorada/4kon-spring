package ru.cwl.app;

import java.math.BigDecimal;

/**
 * Created by admin on 09.11.2016.
 */
public class T2 extends Tab2<BigDecimal,String,String>{

    public T2() {
        super(new BigDecimal("0.00"));
    }

    @Override
    BigDecimal add(BigDecimal v0, BigDecimal v1) {
        return v0.add( v1);
    }
}
