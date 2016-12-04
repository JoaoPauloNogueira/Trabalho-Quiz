package com.example.joaopaulo.quizapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao Paulo on 25/11/2016.
 */

public class ViewPagerSelecaoAdapter extends FragmentPagerAdapter {

    private List<Fragment> listaFragmentos = new ArrayList<Fragment>();

    public ViewPagerSelecaoAdapter(List<Fragment> lista, FragmentManager fragmentManager) {

        super(fragmentManager);

        listaFragmentos = lista;
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragmentos.get(position);
    }

    @Override
    public int getCount() {

        return listaFragmentos.size();
    }
}
