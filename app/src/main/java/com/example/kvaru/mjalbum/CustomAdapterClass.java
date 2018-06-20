package com.example.kvaru.mjalbum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kvaru.mjalbum.datamodels.MainDataModelClass;
import com.example.kvaru.mjalbum.datamodels.Result;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapterClass extends ArrayAdapter<Result> {

    private Context context;
    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewGenre;
    private TextView textViewTimeLength;
    private TextView textViewPrice;
    private TextView textViewCountry;
    private TextView textViewCurrency;
    private TextView textViewPreviewURL;
    private ImageView imageViewAlbum;
    private List<Result> itunesList = new ArrayList<>();
    public  Result result;

    public CustomAdapterClass(@NonNull Context context,int resource,@NonNull List<Result> objects) {
        super(context, resource, objects);
        this.context = context;
        this.itunesList = objects;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        result = itunesList.get(position);
       if (convertView == null){
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listitem,parent,false);
       }

       textViewName = (TextView)convertView.findViewById(R.id.Nametv);
       textViewDate = (TextView)convertView.findViewById(R.id.Datetv);
       textViewGenre = (TextView)convertView.findViewById(R.id.Genetv);
       textViewTimeLength = (TextView)convertView.findViewById(R.id.Durationtv);
       textViewPrice = (TextView)convertView.findViewById(R.id.Pricetv);
       textViewCountry = (TextView)convertView.findViewById(R.id.Countrytv);
       textViewCurrency = (TextView)convertView.findViewById(R.id.Currencytv);
       textViewPreviewURL = (TextView)convertView.findViewById(R.id.PreviewURLtv);
       imageViewAlbum = (ImageView)convertView.findViewById(R.id.AlbumImgView);

        if (imageViewAlbum != null){
            new ImageDownload().execute(result.getArtworkUrl100());
        }

       textViewName.setText(result.getTrackName());
       textViewDate.setText(result.getReleaseDate().substring(0,10));
       textViewGenre.setText(result.getPrimaryGenreName());
       textViewTimeLength.setText(String.valueOf((result.getTrackTimeMillis()/1000)/60)+" min");
       textViewPrice.setText(String.valueOf(result.getTrackPrice())+"$");
       textViewCountry.setText(result.getCountry());
       textViewCurrency.setText(result.getCurrency());
       textViewPreviewURL.setText(result.getPreviewUrl());

        return convertView;
    }


class ImageDownload extends AsyncTask<String, Void, Bitmap>{



    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadImg(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewAlbum != null){
            if(Cache.getInstance().getLru().get("bitmap_image")!=null){
                imageViewAlbum.setImageBitmap((Bitmap)Cache.getInstance().getLru().get("bitmap_image"));
            }
            if (bitmap != null){
                imageViewAlbum.setImageBitmap(bitmap);
            }else {
                Drawable drawable = imageViewAlbum.getContext().getResources().getDrawable(R.drawable.ic_launcher_background);
                imageViewAlbum.setImageDrawable(drawable);
            }
        }
    }
}

    private Bitmap downloadImg(String url) {
        HttpURLConnection urlConnection = null;

        try {
            URL imgUrl = new URL(url);
            urlConnection =(HttpURLConnection) imgUrl.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null){
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Cache.getInstance().getLru().put("bitmap_image", bitmap);
                return bitmap;
            }
        }catch (Exception e){
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}