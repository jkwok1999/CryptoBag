package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.cryptobag.MESSAGE";
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    private Boolean wideScreen;
    private CoinAdapter mAdapter;
    private CoinDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate: starting");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate a RecyclerView and pair it with the one in the layout file
        recyclerView = (RecyclerView) findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Sets a LinearLayoutManager for the RecyclerView which

        //Variable to decide what screen size to use
        wideScreen = findViewById(R.id.detail_container) != null;

        //Gson gson = new Gson();
        //CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        //List<Coin> coins = response.getData();

        //
        mAdapter = new CoinAdapter(this, new ArrayList<Coin>(), wideScreen);
        recyclerView.setAdapter(mAdapter);

        /*
        //Create Retrofit Instance & parse the retrieved JSON using GSON deserialiser
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinlore.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Get service & Call object for the request
        CoinService service = retrofit.create(CoinService.class);
        Call<CoinLoreResponse> coinsCall = service.getCoins();

        //Execute network request
        //Response<CoinLoreResponse> coinsResponse = coinsCall.execute();
        coinsCall.enqueue(new Callback<CoinLoreResponse>() {
            @Override
            public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                Log.d(TAG, "onResponse: SUCCESS");
                List<Coin> coins = response.body().getData();
                mAdapter.setCoins(coins);
            }

            @Override
            public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                Log.d(TAG, "On:Failure: FAILURE" + t.getLocalizedMessage());
            }
        }); */

        //Create Database
        mDb = Room.databaseBuilder(this, CoinDatabase.class, "coin-database").build(); //Can also use getApplicationContext() instead of this

        //Execute Asynctask
        new GetCoinDbTask().execute();
        new GetCoinTask().execute();
    }

    private class GetCoinTask extends AsyncTask <Void, Void, List<Coin>> {

        @Override
        protected List<Coin> doInBackground(Void... voids) {
            try {
                //Create Retrofit Instance & parse the retrieved JSON using GSON deserialiser
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.coinlore.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //Get service & Call object for the request
                CoinService service = retrofit.create(CoinService.class);
                Call<CoinLoreResponse> coinsCall = service.getCoins();

                //Execute network request
                Response<CoinLoreResponse> coinResponse = coinsCall.execute();
                List<Coin> coins = coinResponse.body().getData();

                Log.d(TAG, "doInBackground: SUCCESS");

                //Delete Current coins from the DB
                mDb.coinDao().deleteAll(mDb.coinDao().getCoins().toArray(new Coin[mDb.coinDao().getCoins().size()]));

                //Insert coins into the DB
                mDb.coinDao().insertAll(coins.toArray(new Coin[coins.size()]));

                return coins;
            } catch (IOException e) {
                Log.d(TAG, "On:Failure: FAILURE");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Coin> coins) {
            //super.onPostExecute(coins);
            mAdapter.setCoins(coins);
        }
    }

    private class GetCoinDbTask extends AsyncTask<Void, Void, List<Coin>> {
        @Override
        protected List<Coin> doInBackground(Void... voids) {
            return mDb.coinDao().getCoins();
        }

        @Override
        protected void onPostExecute(List<Coin> coins) {
            mAdapter.setCoins(coins);
        }

    }

}
