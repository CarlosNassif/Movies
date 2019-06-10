package com.example.hello.victor.movies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.example.hello.victor.movies.interfaces.RecyclerInterface;
import com.example.hello.victor.movies.model.Film;
import com.example.hello.victor.movies.services.APIRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  implements RecyclerInterface {

    SearchView searchView;
    List<Film> listFilm =  new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MeuAdapter meuAdapter;
    public static final String ARQ_PREFS = "MeuArquivoPreferencias";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.meuRecyclerView);
        linearLayoutManager  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        meuAdapter = new MeuAdapter(this,listFilm,this);
        recyclerView.setAdapter(meuAdapter);


        searchView = (SearchView) findViewById(R.id.searchable);
        searchView.setQueryHint("Pesquisar filmes");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.replace(' ','+');
                String url = "http://www.omdbapi.com/?apikey=13e060ff&s="+query;
                String stringToJson = null;
                try {
                    stringToJson = new APIRequest().execute(url).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addItemList(stringToJson);
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void addItemList(String stringToJson){
        try {
            JSONObject jsonObject = new JSONObject(stringToJson);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");

            if(!listFilm.isEmpty())
                listFilm.removeAll(listFilm);

            for (int i = 0 ; i < jsonArray.length();i++){
                  String id = jsonArray.getJSONObject(i).getString("imdbID");
                  String url = "http://www.omdbapi.com/?apikey=13e060ff&i="+id;
                  String resp = new APIRequest().execute(url).get();

                  jsonObject = new JSONObject(resp);

//                  System.out.println(jsonObject2.getString("Plot"));
                  addListObject(jsonObject);
                  meuAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addListObject(JSONObject jsonObject){

        Film film = new Film();

        try{
            film.setTitle(jsonObject.getString("Title"));
            film.setImdbId(jsonObject.getString("imdbID"));
            film.setYear(jsonObject.getString("Year"));
            film.setPlot(jsonObject.getString("Plot"));
            film.setLinkPoster(jsonObject.getString("Poster"));
            film.setYear(jsonObject.getString("Year"));
            film.setRuntime(jsonObject.getString("Runtime"));

            listFilm.add(film);
//            System.out.println(film.getTitle());
        }catch (JSONException jsonE){
            jsonE.printStackTrace();
        }
    }

    @Override
    public void onItemClick(Object object) {
        Film film = (Film) object;

        Intent send = new Intent(this,DetailsMovie.class);
        send.putExtra("Film",film);
        send.putExtra("MeuArquivoPreferencias", ARQ_PREFS);
        startActivity(send);

    }


}



