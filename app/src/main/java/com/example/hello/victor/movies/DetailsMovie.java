package com.example.hello.victor.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello.victor.movies.model.Film;
import com.example.hello.victor.movies.model.Film2;
import com.example.hello.victor.movies.services.APIRequest;
import com.example.hello.victor.movies.services.DownloadImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DetailsMovie extends AppCompatActivity {

    protected TextView nomeFilme, descricao, diretor, pais, tempo;
    protected ImageView poster;
    private Film film;
    private boolean assistindo = false;
    public static String ARQ_PREFS;

    SharedPreferences configuracoes;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        nomeFilme = findViewById(R.id.txt_title_details);
        poster = findViewById(R.id.PosterActDes);
        descricao = findViewById(R.id.DescricaoActDet);
        diretor = findViewById(R.id.DiretorActDet);
        pais = findViewById(R.id.PaisActDet);
        tempo = findViewById(R.id.TempoActDet);

        Intent intent = getIntent();
        film = (Film) intent.getSerializableExtra("Film");
        ARQ_PREFS = (String) intent.getSerializableExtra("MeuArquivoPreferencias");

        configuracoes = getSharedPreferences(ARQ_PREFS, Context.MODE_PRIVATE);
        editor = configuracoes.edit();


        nomeFilme.setText(film.getTitle()+" "+ film.getYear());
        descricao.setText(film.getPlot());
        diretor.setText(film.getDirector());
        pais.setText(film.getCountry());
        tempo.setText(film.getRuntime());

        try {
            Bitmap bitmap = new DownloadImage().execute(film.getLinkPoster()).get();
            poster.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void GosteiBtn(View V) {

        String salvarBD = "";
        try{
            configuracoes.getString(film.getImdbId(),salvarBD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("String salvarBD GosteiBtn: "+salvarBD);

        salvarBD = "gostei;"+film.getGenre()+";";

        editor.putString(film.getImdbId(),salvarBD);

        Toast.makeText(this, film.getTitle() + " foi marcado como 'gostei'.", Toast.LENGTH_LONG).show();

    }

    public void NaoGosteiBtn(View V) {

        String salvarBD = "";
        try{
            configuracoes.getString(film.getImdbId(),salvarBD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("String salvarBD NaoGosteiBtn: "+salvarBD);

        salvarBD = "naogostei;"+film.getGenre()+";";

        editor.putString(film.getImdbId(),salvarBD);

        Toast.makeText(this, film.getTitle() + " foi marcado como 'não gostei'.", Toast.LENGTH_LONG).show();

    }

    public void JaAssistiBtn(View v) {

        String salvarBD = "";
        try{

            configuracoes.getString(film.getImdbId(),salvarBD);
            Toast.makeText(this, "Já sabemos disso...", Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            editor.putString(film.getImdbId(),salvarBD);
            Toast.makeText(this, "Suas recomendações serão atualizadas.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        System.out.println("String salvarBD JaAssistiBtn: "+salvarBD);

    }

    public void ComecarAssistirBtn(View v) {

        assistindo = true;

    }




}
