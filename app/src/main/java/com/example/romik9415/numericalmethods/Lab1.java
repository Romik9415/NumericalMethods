package com.example.romik9415.numericalmethods;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Lab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public Lab1() {
        // Required empty public constructor
    }
    public static Lab1 newInstance(String param1, String param2) {
        Lab1 fragment = new Lab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText leftEnd = (EditText)getView().findViewById(R.id.leftEnd);
       // Integer leftEndInt= Integer.valueOf(String.valueOf(leftEnd.getText()));

        EditText rightEnd = (EditText)getView().findViewById(R.id.right_end);
        //Integer rightEndInt = Integer.valueOf(String.valueOf(rightEnd.getText()));

        EditText eps = (EditText)getView().findViewById(R.id.eps);
        //Integer epsInt = Integer.valueOf(String.valueOf(eps.getText()));
    }
    //Lab 1 Numerical Methods

    public double myFunction(double x){
        return (pow(x,2)+sin(x));
    };
    public double iteration(double x){
        return sqrt(sin(x));
    };
    public double  firstDerivative(double x){
        return (2*x-cos(x));
    };

    public double secondDerivative(double x){
        return (2+sin(x));
    };

    void simpleIteration(double a, double b, double eps) {
        double x0;
        double x1;
        x0 = (a + b) / 2;
        x1 = iteration(x0);
        for (Integer i = 0; abs(x1 - x0) < eps; i++) {
            x0 = x1;
            x1 = iteration(x1);
        }

    }
    void divede(double a, double b, double eps){
        double x0 = (a+b)/2;
        double x1 = 0;
        if(myFunction(x0)>0){
            x0 = ((a+x0)/2);
        }else {
            x1 = (x0+b)/2;
        }
        for(int i=0;abs(x1-x0)>eps;i++){
            x0 =x1;
            if(myFunction(x1)>0){
                b=x0;
            }else a=x1;
            x1 = (a+b)/2;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab1, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
