package com.kvb.xkcd_comicaday.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kvb.xkcd_comicaday.R;
import com.kvb.xkcd_comicaday.model.ComicOfDayBean;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.kvb.xkcd_comicaday.utils.ComicConstants.ARG_COMIC_COUNT;
import static com.kvb.xkcd_comicaday.utils.ComicConstants.ARG_COMIC_LATEST_NUM;
import static com.kvb.xkcd_comicaday.utils.ComicConstants.ARG_SECTION_NUMBER;
import static com.kvb.xkcd_comicaday.utils.ComicConstants.URL_COMIC_OF_DAY;
import static com.kvb.xkcd_comicaday.utils.ComicConstants.URL_COMIC_OF_SPECIFIC_DAY_BASE;
import static com.kvb.xkcd_comicaday.utils.ComicConstants.URL_COMIC_OF_SPECIFIC_DAY_RELATIVE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private static final String TAG = "PlaceholderFragment";
    ImageView imageView;
    TextView mImgName;
    TextView mImgDate;
    
    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, int comicCount, long latestComicNum) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ARG_COMIC_COUNT, comicCount);
        args.putLong(ARG_COMIC_LATEST_NUM, latestComicNum);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mImgDate = (TextView) rootView.findViewById(R.id.image_date);
        mImgDate.setText("Date: ");
        int curpg = getArguments().getInt(ARG_SECTION_NUMBER);
        int totPg = getArguments().getInt(ARG_COMIC_COUNT);
        long latestNum = getArguments().getLong(ARG_COMIC_LATEST_NUM);
        if(curpg == 1)
        {
            ComicController.getInstance().fetchComics(URL_COMIC_OF_DAY, comicOfDayCallback);
        }else{
            if(curpg <= totPg) {
                long comicNum = latestNum-curpg;
                String finalUrl = URL_COMIC_OF_SPECIFIC_DAY_BASE + comicNum + URL_COMIC_OF_SPECIFIC_DAY_RELATIVE;
                ComicController.getInstance().fetchComics(finalUrl, comicOfDayCallback);
            }
        }

        CardView cardView = rootView.findViewById(R.id.card_view);
        imageView = rootView.findViewById(R.id.image_holder);
        mImgName = rootView.findViewById(R.id.image_name);

        return rootView;
    }


    Callback comicOfDayCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Toast.makeText(getActivity(), "Failed to fetch image", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String responseString = responseBody.string();
                Log.i(TAG, "onResponse: successfully fetched image" + responseString);
                final ComicOfDayBean comicOfDayBean = new Gson().fromJson(responseString,ComicOfDayBean.class);
                Log.i(TAG, "onResponse: image url is "+comicOfDayBean.getImg());
                //load image
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get().load(comicOfDayBean.getImg()).into(imageView);
                        mImgName.setText(comicOfDayBean.getTitle());
                        mImgDate.setText("Date: "+comicOfDayBean.getDay()+"/"+comicOfDayBean.getMonth()+"/"+comicOfDayBean.getYear());
                    }
                });
            }else{
                Log.i(TAG, "onResponse: failed for some reason" + response.body());
            }
        }
    };


}

// TODO: 15/6/18 first get the comic number of current comic, then load 5 comics prior to this one