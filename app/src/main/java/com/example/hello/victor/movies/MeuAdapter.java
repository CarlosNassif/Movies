package com.example.hello.victor.movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hello.victor.movies.interfaces.RecyclerInterface;
import com.example.hello.victor.movies.model.Film;
import com.example.hello.victor.movies.services.DownloadImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MeuAdapter extends RecyclerView.Adapter<MeuAdapter.MeuViewHolder> {

    public static RecyclerInterface recyclerInterface;
    Context context;
    private List<Film> listFilm;

    public static class MeuViewHolder extends RecyclerView.ViewHolder{
        protected TextView txtNome;
        protected TextView txtPlot;
        protected TextView txtRuntime;
        protected TextView txtyear;
        protected ImageView imageView;

        public MeuViewHolder(final View itemView, final List<Film> listFilm){
            super(itemView);
            txtPlot = itemView.findViewById(R.id.txt_plot);
            txtNome = itemView.findViewById(R.id.txt_movie_title);
            txtRuntime = itemView.findViewById(R.id.txt_runtime);
            txtyear = itemView.findViewById(R.id.txt_year);
            imageView = itemView.findViewById(R.id.image_movie);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    recyclerInterface.onItemClick(listFilm.get(getLayoutPosition()));
                }
            });
        }

    }

    public MeuAdapter(Context context, List<Film> listFilm, RecyclerInterface recyclerInterface){
        this.listFilm = listFilm;
        this.context = context;
        this.recyclerInterface = recyclerInterface;
    }


    public void onBindViewHolder(MeuViewHolder viewHolder,final int i ){
        Film film = listFilm.get(i);
        viewHolder.txtNome.setText(film.getTitle());
        viewHolder.txtPlot.setText(film.getPlot());
        viewHolder.txtyear.setText("Year :"+film.getYear());
        viewHolder.txtRuntime.setText("Runtime :"+film.getRuntime());

        try {
            Bitmap bitmap = new DownloadImage().execute(film.getLinkPoster()).get();
            viewHolder.imageView.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public MeuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_list,viewGroup,false);

        return new MeuViewHolder(itemView,listFilm);

    }

    @Override
    public int getItemCount(){
            return listFilm.size();
    }
}
