package com.example.kvaru.mjalbum;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    public String mArtistName;
    public String mTrackName;
    public String mReleaseDate;
    public String mCountry;
    public String mCurrency;
    public Double mPrice;
    public Integer mTrackTime;
    public String mPreviewURL;
    public String mGenre;

    public TextView textViewArtistName;
    public TextView textViewTrackName;
    public TextView textViewTrackTime;
    public TextView textViewCountry;
    public TextView textViewCurrency;
    public TextView textViewReleaseDate;
    public TextView textViewTrackPrice;
    public TextView textViewGenre;

    public Button buttonPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        textViewArtistName = (TextView)findViewById(R.id.displayArtisttv);
        textViewTrackName = (TextView)findViewById(R.id.displayTrackNametv);
        textViewTrackTime = (TextView)findViewById(R.id.displayLengthtv);
        textViewCountry = (TextView)findViewById(R.id.displayCountryNametv);
        textViewCurrency = (TextView)findViewById(R.id.displayCurrencyNametv);
       // textViewPreviewURL = (TextView)findViewById(R.id.display);
        textViewReleaseDate = (TextView)findViewById(R.id.displayDatetv);
        textViewTrackPrice = (TextView)findViewById(R.id.displayPricetv);
        textViewGenre = (TextView)findViewById(R.id.displayGenreListtv);
        buttonPreview = (Button)findViewById(R.id.btnPreview);

        Intent intent = getIntent();
        mArtistName = intent.getStringExtra("ArtistName");
        mCountry = intent.getStringExtra("Country");
        mCurrency = intent.getStringExtra("Currency");
        mPreviewURL = intent.getStringExtra("PreviewURL");
        mPrice = intent.getDoubleExtra("TrackPrice",0.00);
        mReleaseDate = intent.getStringExtra("ReleaseDate");
        mGenre = intent.getStringExtra("PrimaryGenreName");
        mTrackTime = intent.getIntExtra("TrackTime",0);
        mTrackName =intent.getStringExtra("TrackName");

        textViewArtistName.setText(mArtistName);
        textViewTrackName.setText(mTrackName);
        textViewTrackTime.setText(String.valueOf((mTrackTime/1000)/60)+" min");
        textViewCountry.setText(mCountry);
        textViewCurrency.setText(mCurrency);
        textViewReleaseDate.setText(mReleaseDate.substring(0,10));
        textViewTrackPrice.setText(String.valueOf(mPrice)+"$");
        textViewGenre.setText(mGenre);

        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPreviewURL));
                startActivity(browserIntent);
            }
        });


    }
}
