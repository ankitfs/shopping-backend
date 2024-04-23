package com.ankit.utility;

import java.math.BigDecimal;
import java.util.UUID;

public class HelperMethods {

    public static String productSKUGenerator(String pName, BigDecimal price){
        //return UUID.fromString(pName).toString();
        return UUID.randomUUID().toString();
    }
}
