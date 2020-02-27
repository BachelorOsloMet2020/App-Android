package no.dyrebar.dyrebar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.activity.AnimalManageActivity;
import no.dyrebar.dyrebar.adapter.MyAnimalAdapter;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jProfileAnimal;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MyAnimalsFragment extends Fragment
{
    FragmentInterface.FragmentListener mListener;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof FragmentInterface.FragmentListener)
        {
            mListener = (FragmentInterface.FragmentListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                                               + " must implement FragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_animals, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.add_animal_fab).setOnClickListener(view -> addAnimal());
        AsyncTask.execute(() -> {
            String ani = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "animals"));
                add(new Pair<>("uid", App.profile.getId()));
            }});
            ArrayList<ProfileAnimal> animals = new jProfileAnimal().decodeArray(ani);
            getActivity().runOnUiThread(() -> loadMyAnimals(animals));
        });
    }

    private void loadMyAnimals(ArrayList<ProfileAnimal> items)
    {
        RecyclerView rv = getView().findViewById(R.id.recyclerView_user_profile);
        MyAnimalAdapter maa = new MyAnimalAdapter(items, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv.setAdapter(maa);
    }


    public void addAnimal()
    {
        Intent intent = new Intent(getActivity(), AnimalManageActivity.class);
        startActivity(intent);
    }

}
