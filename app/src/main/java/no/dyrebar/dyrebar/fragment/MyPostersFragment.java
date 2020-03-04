package no.dyrebar.dyrebar.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.adapter.MyPostersAdapter;
import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jMissing;
import no.dyrebar.dyrebar.json.jPoster;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MyPostersFragment extends Fragment implements MyPostersAdapter.OnMyPosterAction
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
                                               + " must implement PermissionInterfaceListener");
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
        return inflater.inflate(R.layout.fragment_my_posters, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        AsyncTask.execute(() ->
                          {
                              String res = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
                              {{
                                  add(new Pair<>("request", "myPosters"));
                                  add(new Pair<>("uid", App.profile.getId()));
                                  add(new Pair<>("token", App.authSession.getToken()));
                              }});
                              ArrayList<Object> items = new jPoster().decode(res);
                              getActivity().runOnUiThread(() ->
                                                          {
                                                              loadMyPosters(items);
                                                          });
                          });
    }

    private void loadMyPosters(ArrayList<Object> items)
    {
        RecyclerView rv = getView().findViewById(R.id.recyclerView_user_my_posters);
        MyPostersAdapter mpa = new MyPostersAdapter(getContext(), this, items);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv.setAdapter(mpa);
    }

    private void missingDeleteRequest(int index, Missing m)
    {
        AsyncTask.execute(() ->
                          {
                              String res = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>()
                              {{
                                add(new Pair<>("request", "delete_missing"));
                                add(new Pair<>("missingId", m.get_ID()));
                                add(new Pair<>("userId", App.profile.getId()));
                                add(new Pair<>("authId", App.authSession.getAuthId()));
                                add(new Pair<>("token", App.authSession.getToken()));
                              }});
                              Log.d(getClass().getName(), "API -> " + res);
                              boolean success = new jStatus().getStatus(res);
                              if (success)
                              {
                                  getActivity().runOnUiThread(() -> removePoster(index));
                              }
                          });
    }

    private void foundDeleteRequest(int index, Found f)
    {
        AsyncTask.execute(() ->
                          {
                              String res = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>()
                              {{
                                  add(new Pair<>("request", "delete_found"));
                                  add(new Pair<>("missingId", f.get_ID()));
                                  add(new Pair<>("userId", App.profile.getId()));
                                  add(new Pair<>("authId", App.authSession.getAuthId()));
                                  add(new Pair<>("token", App.authSession.getToken()));
                              }});
                              Log.d(getClass().getName(), "API -> " + res);
                              boolean success = new jStatus().getStatus(res);
                              if (success)
                              {
                                getActivity().runOnUiThread(() -> removePoster(index));
                              }
                          });
    }

    private void removePoster(int position)
    {
        RecyclerView rv = getView().findViewById(R.id.recyclerView_user_my_posters);
        MyPostersAdapter mpa = (MyPostersAdapter) rv.getAdapter();
        mpa.removeItemByPosition(position);
    }

    @Override
    public void onActionDeletePoster(int index, Object poster)
    {
        if (poster instanceof Missing)
            missingDeleteRequest(index, (Missing) poster);
        else if (poster instanceof Found)
            foundDeleteRequest(index, (Found) poster);
    }

    @Override
    public void onActionSolvedPoster(int index, Object poster)
    {

    }
}