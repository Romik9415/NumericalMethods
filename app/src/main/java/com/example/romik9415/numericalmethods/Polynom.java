package com.example.romik9415.numericalmethods;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Polynom {
    private HashMap<Integer, Double> elements;

    public Polynom() {
        elements = new HashMap<>();
    }

    public void setCoeff(int pow, double val) {
        elements.put(pow, val);
    }

    public double val(double x) {
        double res = 0;
        for (Integer pow : elements.keySet()) {
            res += Math.pow(x, pow) * elements.get(pow);
        }
        return res;
    }

    public String toStr() {
        String s = "";
        for (int i = elements.keySet().size() - 1; i > -1; i--) {
            s += (elements.get(i) > 0 ? " +" : " ") + elements.get(i) + "*x^" + i;
        }
        return s;
    }

    public static String toStr(Polynom p) {
        String s = "";
        for (int i = p.elements.keySet().size() - 1; i > -1; i--) {
            s += (p.elements.get(i) > 0 ? "+" : "") + p.elements.get(i) + "*x^" + i;
        }
        return s;
    }

    //-----------------------L---------------------------
    public static Polynom lagrangian(List<Pair<Double, Double>> input) {
        Polynom r = new Polynom();
        double[] cSums = new double[input.size()]; // sums all c[]

        for (int addIdx = 0; addIdx < input.size(); addIdx++) {
            //log("AddAdr "+addIdx);
            double[] c = new double[input.size()];
            for (int i = 0; i < Math.pow(2, input.size() - 1); i++) {
                String muls = String.format("%" + (input.size() - 1) + "s", Integer.toBinaryString(i)).replace(' ', '0');
                log("()()()() == " + muls);

                //x^4 - x^3*x1 - x^3*x2 - x^3*x3 - x^3*x4 + x^2*x1*x2 + x^2*x1*x3 + x^2*x1*x4
                // + x^2*x2*x3 + x^2*x2*x4 + x^2*x3*x4 - x*x1*x2*x3 - x*x1*x2*x4 - x*x1*x3*x4
                // - x*x2*x3*x4 + x1*x2*x3*x4
                //polynom  x^4 - x^3*x1 - x^3*x2 - x^3*x*x^4 - x^3*x1 - x^3*x2 - x^3*x3 - x^3*x4 + x^2*x1*x2 + x^2*x1*x3 + x^2*x1*x4   + x^2*x2*x3 + x^2*x2*x4 + x^2*x3*x4 - x*x1*x2*x3 - x*x1*x2*x4 - x*x1*x3*x4

                int sign = getSignFromStr(muls);
                int polynomPowerMember = getZeroCount(muls);
                double mp = 1;
                for (int j = 0, ch = 0; j < muls.length(); j++, ch++) {
                    if (ch == addIdx)
                        ch++;
                    //log("bin char "+ch);
                    if (muls.charAt(j) == '1') {
                        mp *= input.get(ch).first;
                        log("() #" + ch + " : mp* =" + input.get(ch).first);
                    }
                }

                c[polynomPowerMember] += sign * mp;
                log("MPF @" + polynomPowerMember + " = " + sign * mp + " || " + c[polynomPowerMember] + " |yi= " + input.get(addIdx).second + "\n|\n|\n|");
                //add to cSums
            }


            double divider = 1;
            for (int dIdx = 0; dIdx < input.size(); dIdx++) {
                if (addIdx == dIdx)
                    continue;
                divider *= input.get(addIdx).first - input.get(dIdx).first;
                log("divider  " + dIdx);
            }
            for (int celem = 0; celem < c.length; celem++) {
                cSums[celem] += c[celem] / (divider / input.get(addIdx).second);
                //log(cSums[celem]+" added:"+c[celem]);
            }
            log("divider for" + addIdx + " = " + divider);
            log(" cSums:" + Arrays.toString(cSums));

            log("()()()() end --------------------------------------------------------\n\n\n");

        }

        for (int i = 0; i < cSums.length; i++) {
            r.setCoeff(i, Algorithm.round(cSums[i], 6));
            //log("set koef: "+i+" "+cSums[i]);
        }
        log("Polynom: " + r.toStr());
        //fill polynom

        return r;
    }

    public static String testLagrangian(Polynom L, List<Pair<Double, Double>> input) {

        String res = "Перевірка:\n\n";
        for (int i = 0; i < input.size(); i++) {
            double result = L.val(input.get(i).first);
            String r = "L(x" + (i + 1) + ")=" + Algorithm.round(result,6) + "; f(x" + (i + 1) + ")=" + input.get(i).second;
            log(r);
            res += r + "\n";
            if (result != input.get(i).second) ;
            //return false;
        }
        res += "\nОтриманий поліном: " + L.toStr();
        //res+="\nmust: 0.01x^4−0.0423333x^3−0.15085x^2+0.834638x+0.962964";
        //log(res);
        return res;
    }

    private static int getSignFromStr(String muls) {
        int count = 0;
        for (int i = 0; i < muls.length(); i++) {
            if (muls.charAt(i) == '1')
                count++;
        }
        //log("sign: "+(count%2==0?1:-1));

        return count % 2 == 0 ? 1 : -1;
    }

    private static int getZeroCount(String muls) {
        int count = 0;
        for (int i = 0; i < muls.length(); i++) {
            if (muls.charAt(i) == '0')
                count++;
        }
        return count;
    }

    public static Polynom lagrangianStep(double x0, double step, double[] yValues) {
        return new Polynom();
    }


    //-----------------------N---------------------------

    protected static double[] computeDividedDifference(final double x[], final double y[]) throws Exception {
        //PolynomialFunctionLagrangeForm.verifyInterpolationArray(x, y);

        final double[] divdiff = y.clone(); // initialization

        final int n = x.length;
        final double[] a = new double [n];
        a[0] = divdiff[0];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n-i; j++) {
                final double denominator = x[j+i] - x[j];
                if (denominator == 0.0) {
                    // This happens only when two abscissas are identical.
                    throw new Exception("denominator == 0");
                }
                divdiff[j] = (divdiff[j+1] - divdiff[j]) / denominator;
            }
            a[i] = divdiff[0];
        }

        return a;
    }

    public static Polynom newtonian(double[] xvalues, double[] y) throws Exception {

        double[] diffs = computeDividedDifference(xvalues,y);
        //double[] xvalues = {1.0, 2.0, 3.0};
        //double[] diffs = {1.0, 7.0, 6.0};

        // Initialize result array
        double[] result = new double[xvalues.length];
        for (int i = 0; i < xvalues.length; ++i) {
            result[i] = 0.0;
        }

        for (int i = 0; i < xvalues.length; ++i) {
            // 'poly' has a degree 'i'. We use 'i - 1' because only terms
            // from 0 to 'i - 1' are used
            double[] poly = getPoly(xvalues, i - 1);

            // Now add to result, do not forget to multiply by the divided
            // difference !
            for (int j = 0; j < poly.length; ++j) {
                result[j] += poly[j] * diffs[i];
            }
        }


        Polynom p =  new Polynom();
        for (int i = result.length-1; i >= 0; i--) {
            p.setCoeff(i,Algorithm.round(result[i],6));
            Log.d("C_POL","Coef for x^" + i + " is: " + result[i]);
        }
        return p;
    }

    public static double[] getPoly(double[] values, int i) {
        // Start poly: 1.0, neutral value for multiplication
        double[] coefs = {1.0};

        // Accumulate values of products
        for (int j = 0; j <= i; ++j) {
            // 'coefsLocal' represent polynom of 1st degree (x - values[j])
            double[] coefsLocal = {-values[j], 1.0};
            coefs = getPolyProduct(coefs, coefsLocal);
        }

        return coefs;
    }

    public static double[] getPolyProduct(double[] coefs1, double[] coefs2) {
        // Get lengths and degree
        int s1 = coefs1.length - 1;
        int s2 = coefs2.length - 1;
        int degree = s1 + s2;

        // Initialize polynom resulting from product, with null values
        double[] coefsProduct = new double[degree + 1];
        for (int k = 0; k <= degree; ++k) {
            coefsProduct[k] = 0.0;
        }

        // Compute products
        for (int i = 0; i <= s1; ++i)   {
            for (int j = 0; j <= s2; ++j)   {
                coefsProduct[i + j] += coefs1[i] * coefs2[j];
            }
        }
        return coefsProduct;
    }





    public static String testNewtone(double[] x, double[] y, List<Pair<Double, Double>> input) {
        String s = "Перевірка (Поліном Н'ютона)\n\n";
        for (int i = 0; i < input.size(); i++) {
            double res = newtone(x, y, input.get(i).first);
            s += "N(" + input.get(i).first + ")=" + res + "\n";
        }
        return s;
    }

    public static String testNewtone2(Polynom N,List<Pair<Double, Double>> input) {
        String s = "Перевірка (Поліном Н'ютона)\n\n";
        for (int i = 0; i < input.size(); i++) {
            double res = N.val(input.get(i).first);
            s += "N(" + input.get(i).first + ")=" + Algorithm.round(res,6) + "\n";
        }
        return s;
    }

    public static double newtone(double[] x, double[] fx, double myx) {
        double ans = 0;
        for (int i = 0; i < x.length; i++) {
            int k[] = new int[i + 1];
            double myf = 1;
            for (int j = 0; j < i + 1; j++) {
                k[j] = j;
            }
            for (int j = 0; j < i; j++) {
                myf *= (myx - x[j]);
            }
            ans += myf * f1(fx, k, x);
        }
        log("Newtone result: " + ans);

        return ans;
    }

    public static double f1(double[] fx, int[] k, double[] x) {
        if (k.length == 1) {
            return fx[k[0]];
        }
        if (k.length == 2) {
            return (fx[k[0]] - fx[k[1]]) / (x[k[0]] - x[k[1]]);
        }

        int[] k1 = new int[k.length - 1];
        int[] k2 = new int[k.length - 1];
        for (int i = 0; i < k.length - 1; i++) {
            k1[i] = k[i];
            k2[i] = k[i + 1];
        }
        return (f1(fx, k1, x) - f1(fx, k2, x)) / (x[k[0]] - x[k[k.length - 1]]);
    }


    public static void log(String mesg) {
        Log.d("C_POL", mesg);
    }
}
