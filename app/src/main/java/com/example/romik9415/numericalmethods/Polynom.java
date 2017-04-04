package com.example.romik9415.numericalmethods;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Polynom {
    private HashMap<Integer,Double> elements;

    public Polynom(){
        elements = new HashMap<>();
    }
    public void setCoeff(int pow,double val){
        elements.put(pow,val);
    }

    public double val(double x){
        double res = 0;
        for (Integer pow: elements.keySet()) {
            res += Math.pow(x,pow) * elements.get(pow);
        }
        return res;
    }

    public String toStr(){
        String s = "";
        for (int i = elements.keySet().size()-1; i >-1; i--) {
            s+=(elements.get(i)>0?" +":" ")+elements.get(i)+"*x^"+i;
        }
        return s;
    }
    public static String toStr(Polynom p){
        String s = "";
        for (int i = p.elements.keySet().size()-1; i >-1; i--) {
            s+=(p.elements.get(i)>0?"+":"")+p.elements.get(i)+"*x^"+i;
        }
        return s;
    }

    //-----------------------L---------------------------
    public static Polynom lagrangian(List<Pair<Double, Double>> input){
        Polynom r = new Polynom();
        double[] cSums= new double[input.size()]; // sums all c[]

        for (int addIdx = 0; addIdx < input.size(); addIdx++) {
            //log("AddAdr "+addIdx);
            double[] c= new double[input.size()];
            for (int i = 0; i < Math.pow(2,input.size()-1); i++){
                String muls = String.format("%"+(input.size()-1)+"s", Integer.toBinaryString(i)).replace(' ', '0');
                log("()()()() == "+muls);

                //x^4 - x^3*x1 - x^3*x2 - x^3*x3 - x^3*x4 + x^2*x1*x2 + x^2*x1*x3 + x^2*x1*x4
                // + x^2*x2*x3 + x^2*x2*x4 + x^2*x3*x4 - x*x1*x2*x3 - x*x1*x2*x4 - x*x1*x3*x4
                // - x*x2*x3*x4 + x1*x2*x3*x4
                //polynom  x^4 - x^3*x1 - x^3*x2 - x^3*x*x^4 - x^3*x1 - x^3*x2 - x^3*x3 - x^3*x4 + x^2*x1*x2 + x^2*x1*x3 + x^2*x1*x4   + x^2*x2*x3 + x^2*x2*x4 + x^2*x3*x4 - x*x1*x2*x3 - x*x1*x2*x4 - x*x1*x3*x4

                int sign = getSignFromStr(muls);
                int polynomPowerMember = getZeroCount(muls);
                double mp = 1;
                for (int j = 0,ch=0; j < muls.length(); j++,ch++) {
                    if(ch==addIdx)
                        ch++;
                    //log("bin char "+ch);
                    if(muls.charAt(j) == '1'){
                        mp *= input.get(ch).first;
                        log("() #"+ch+" : mp* ="+input.get(ch).first);
                    }
                }

                c[polynomPowerMember]+=sign * mp;
                log("MPF @"+polynomPowerMember+" = "+sign*mp+" || "+c[polynomPowerMember]+" |yi= "+input.get(addIdx).second+"\n|\n|\n|");
                //add to cSums
            }


            double divider = 1;
            for (int dIdx = 0; dIdx < input.size(); dIdx++) {
                if(addIdx == dIdx)
                    continue;
                divider *= input.get(addIdx).first - input.get(dIdx).first;
                log("divider  "+dIdx);
            }
            for (int celem = 0; celem < c.length; celem++) {
                cSums[celem] += c[celem]/(divider/ input.get(addIdx).second);
                //log(cSums[celem]+" added:"+c[celem]);
            }
            log("divider for"+addIdx+" = "+divider);
            log(" cSums:"+Arrays.toString(cSums));

            log("()()()() end --------------------------------------------------------\n\n\n");

        }

        for (int i = 0; i < cSums.length; i++) {
            r.setCoeff(i,Algorithm.round(cSums[i],3));
            //log("set koef: "+i+" "+cSums[i]);
        }
        log("Polynom: "+r.toStr());
        //fill polynom

        return r;
    }

    public static String testLagrangian(Polynom L,List<Pair<Double, Double>> input){

        String res = "Перевірка";
        for (int i = input.size()-1; i >-1 ; i--) {
            double result = L.val(input.get(i).first);
            String r = "L(x"+(i+1)+")="+result+"; f(x"+(i+1)+")="+input.get(i).second;
            log(r);
            res+=r+"\n";
            if(result != input.get(i).second);
                //return false;
        }
        res+="\nОтриманий поліном: "+L.toStr();
        //res+="\nmust: 0.01x^4−0.0423333x^3−0.15085x^2+0.834638x+0.962964";
        //log(res);
        return res;
    }
    private static int getSignFromStr(String muls) {
        int count = 0;
        for (int i = 0; i < muls.length(); i++) {
            if(muls.charAt(i) == '1')
                count++;
        }
        //log("sign: "+(count%2==0?1:-1));

        return count%2==0?1:-1;
    }
    private static int getZeroCount(String muls) {
        int count = 0;
        for (int i = 0; i < muls.length(); i++) {
            if(muls.charAt(i) == '0')
                count++;
        }
        return count;
    }

    public static Polynom lagrangianStep(double x0,double step,double[] yValues){
        return new Polynom();
    }


    //-----------------------N---------------------------

    public static Polynom newtonian(ArrayList<HashMap.Entry<Double,Double>> input){
        return new Polynom();
    }


    public static void log(String mesg){
        Log.d("C_POL",mesg);
    }
}
