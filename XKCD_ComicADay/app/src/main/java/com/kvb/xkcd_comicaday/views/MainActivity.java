package com.kvb.xkcd_comicaday.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kvb.xkcd_comicaday.R;
import com.kvb.xkcd_comicaday.model.ComicOfDayBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.kvb.xkcd_comicaday.utils.ComicConstants.URL_COMIC_OF_DAY;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int daysOfComics;
    private String imgShareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ComicController.getInstance().fetchComics(URL_COMIC_OF_DAY, getFirstComicCallback);

        daysOfComics = 5;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public ImageView imageView;

            @Override
            public void onClick(View view) {
                //todo make this share to whatsapp
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
//                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Check out this XKCD comic! ");
                imageView = (ImageView) findViewById(R.id.image_holder);
                intent.putExtra(Intent.EXTRA_STREAM, ComicController.getInstance().getLocalBitmapUri(MainActivity.this, imageView));

//                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(ComicController.getInstance().getLocalBitmapUri(this, )))
                intent.setPackage("com.whatsapp");
                try
                {
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Whatsapp may not be installed ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Callback getFirstComicCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Toast.makeText(MainActivity.this, "Failed to fetch image", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String responseString = responseBody.string();
                Log.i(TAG, "onResponse: successfully fetched image" + responseString);
                final ComicOfDayBean comicOfDayBean = new Gson().fromJson(responseString,ComicOfDayBean.class);
                Log.i(TAG, "onResponse: image url is "+comicOfDayBean.getImg());
                imgShareUrl = comicOfDayBean.getImg();

                //load image
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    // Create the adapter that will return a fragment for each of the three
                    // primary sections of the activity.
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), daysOfComics, comicOfDayBean.getNum());

                    // Set up the ViewPager with the sections adapter.
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    mViewPager.setOffscreenPageLimit(5);
                    }
                });
            }else{
                Log.i(TAG, "onResponse: failed for some reason" + response.body());
            }
        }
    };

}

// TODO: 10/09/18  use retrofit instead