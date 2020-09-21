package com.example.webservicesimagealdo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.webservicesimagealdo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragementEstradisticasBarras.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragementEstradisticasBarras#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragementEstradisticasBarras extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
BarChart graficabarras;
    private OnFragmentInteractionListener mListener;

    public FragementEstradisticasBarras() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragementEstradisticasBarras.
     */
    // TODO: Rename and change types and number of parameters
    public static FragementEstradisticasBarras newInstance(String param1, String param2) {
        FragementEstradisticasBarras fragment = new FragementEstradisticasBarras();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_fragement_estradisticas_barras, container, false);
graficabarras=(BarChart)vista.findViewById(R.id.graficaBarras);
List<BarEntry> entradas=new ArrayList<>();
entradas.add(new BarEntry(0f,2));
        entradas.add(new BarEntry(1f,5));
        entradas.add(new BarEntry(2f,6));
        entradas.add(new BarEntry(3f,8));
        entradas.add(new BarEntry(4f,3));
        entradas.add(new BarEntry(5f,1));
        BarDataSet datos=new BarDataSet(entradas,"Grafica de Barras");
        BarData data=new BarData(datos);
        datos.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setBarWidth(0.9f);
        graficabarras.setData(data);
        graficabarras.setFitBars(true);
        graficabarras.invalidate();


        return vista;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
