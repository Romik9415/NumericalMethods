package com.example.romik9415.numericalmethods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class Algorithm {

    public static Set<Set<String>> booleanSet(Set<String> set){
        int size = set.size();
        String[] elsArray = set.toArray(new String[size]);
        Set<Set<String>> B = new HashSet<>();

        for(int binStrIdx=0;binStrIdx<Math.pow(2,size);binStrIdx++){

            Set<String> bSubset= new HashSet<>();
            String binStr= Integer.toBinaryString(binStrIdx);
            System.out.println(binStr);

            for(int binCharPos=0; binCharPos<binStr.length(); binCharPos++){
                //int charNum = binCharPos+1;//binStr.length()-binCharPos-1;
                if(binStr.charAt(binStr.length()-1-binCharPos)=='1'){
                    int pos = size-1-binCharPos;//size-charNum;
                    bSubset.add(elsArray[pos]);
                    System.out.println("Adding at "+pos+" = "+elsArray[pos]);
                }

            }
            B.add(bSubset);
        }

        return B;
    }

    static double round(double value) {
        int places =3;
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    static double round(double value,int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    static double round2(double value,int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
