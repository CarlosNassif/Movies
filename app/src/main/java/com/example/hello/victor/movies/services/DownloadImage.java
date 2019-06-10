package com.example.hello.victor.movies.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
//
//    ImageView imageView;
//
//    public DownloadImage(ImageView imageView){
//        this.imageView = imageView;
//    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlString = strings[0];

        try {
            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection)
                    url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.connect();

            InputStream is = conexao.getInputStream();
            Bitmap imagem = BitmapFactory.decodeStream(is);
            return imagem;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    protected void onPostExecute(Bitmap result) {
//        super.onPostExecute(result);
//        if (result != null){
//            imageView.setImageBitmap(result);
//        }
////      else {
////            AlertDialog.Builder builder =
////                    new AlertDialog.Builder(CarregarImagemUrl.this).
////                            setTitle("Erro").
////                            setMessage("Não foi possivel carregar imagem, tente
////                                    novamente mais tarde!").
////            setPositiveButton("OK", null);
////            builder.create().show();
////        }
//    }
}

