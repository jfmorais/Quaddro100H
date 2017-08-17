package br.com.quaddro.quaddro100h.lab08.app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroListFragment;
import br.com.quaddro.quaddro100h.lab08.domain.model.Hotel;

public class HotelListFragment extends QuaddroListFragment {

    List<Hotel> lista;

    ArrayAdapter<Hotel> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lista = new ArrayList<>();
        lista.add(new Hotel("New Beach Hotel", "Av. Boa Viagem", 4.5f));
        lista.add(new Hotel("Refice Hotel", "Av. Boa Viagem", 4.0f));
        lista.add(new Hotel("Canario Hotel", "Rua dos Navegantes", 3.0f));
        lista.add(new Hotel("Byanca Beach Hotel", "Rua Mamnguape", 4.0f));
        lista.add(new Hotel("Grand Hotel Dor", "Av. Bernardo", 3.5f));
        lista.add(new Hotel("Hotel Cool", "Av. Conselheiro Aguiar", 4.0f));
        lista.add(new Hotel("Hotel Infinito", "Rua Ribeito de Brito", 5.0f));
        lista.add(new Hotel("Hotel Tulipa", "Av. Boa Viagem", 5.0f));

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                lista);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Serializable model = (Serializable) l.getItemAtPosition(position);
        View frame = getActivity().findViewById(R.id.detalhe);
        if (frame == null) {
            Intent i = new Intent(getActivity(), HotelActivity.class);
            i.putExtra("hotel", model);
            getActivity().startActivity(i);
        } else {
            HotelFragment hotelFragment = new HotelFragment();
            Bundle b = new Bundle();
            b.putSerializable("hotel", model);
            hotelFragment.setArguments(b);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe, hotelFragment, "LAB")
                    .commit();
        }
    }
}
