package au.edu.unsw.infs3634.cryptobag;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailFragment extends Fragment {

    //Declare all the variables for the elements we will be interacting with in the layout
    private ImageView logo;
    private TextView name;
    private TextView symbol;
    private TextView value;
    private TextView change1h;
    private TextView change24h;
    private TextView change7d;
    private TextView marketcap;
    private TextView volume;
    private Button search;
    private Coin newCoin;
    public static final String ARG_ITEM_ID = "item_id";
    private static final String TAG = "DetailFragment";
    private CoinDatabase mDb;
    private ImageView ivLogo;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instantiate Database Object
        mDb = Room.databaseBuilder(getContext(), CoinDatabase.class, "coin-database").build();

        if(getArguments().containsKey(ARG_ITEM_ID)) {

            /*
            //Create Retrofit Instance & parse the retrieved JSON using GSON deserialiser
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coinlore.net/").addConverterFactory(GsonConverterFactory.create()).build();

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
                    //Add loop
                    for(Coin coin : coins) {
                        if(coin.getId().equals(getArguments().getString(ARG_ITEM_ID))) {
                            newCoin = coin;
                            break;
                        }
                    }
                    updateUi();
                    DetailFragment.this.getActivity().setTitle(newCoin.getName());
                }



                @Override
                public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: FAILURE");
                }
            }); */

            //new GetCoinTask().execute();

            new GetCoinDBTask().execute(getArguments().getString(ARG_ITEM_ID));
        }
    }

    private class GetCoinDBTask extends AsyncTask <String, Void, Coin> {

        @Override
        protected Coin doInBackground(String... ids) {
            //To-do: Return coin with specific id
            return mDb.coinDao().getCoin(ids[0]);
        }

        @Override
        protected void onPostExecute(Coin coin) {
            //To-do: Set mCoin & updateUi
            newCoin = coin;
            updateUi();
        }
    }

    /*
    private class GetCoinTask extends AsyncTask<Void, Void, List<Coin>> {

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
            for(Coin coin : coins) {
                if(coin.getId().equals(getArguments().getString(ARG_ITEM_ID))) {
                    newCoin = coin;
                    break;
                }
            }
            updateUi();
            DetailFragment.this.getActivity().setTitle(newCoin.getName());
        }
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        //int position = 0;

        //Find a coin in the json object
        //Gson gson = new Gson();
        //CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        //List<Coin> coins = response.getData();

        /*
        boolean wide = false;
        if(this.getArguments() != null) {
            wide = getArguments().getBoolean("wide", true);
        }

        //If it is a wide screen then,
        if(wide) {
            newCoin = coins.get(getArguments().getInt("position"));
        }
        //If it is not a wide screen, we just use an intent (the same way we ran the DetailActivity before)
        //Here we get the intent that was sent from the MainActivity class
        else {
            Intent intent = getActivity().getIntent();
            position = intent.getIntExtra("position",0);
            newCoin = coins.get(position);
            //System.out.println(newCoin.getName());
        }
         */

        updateUi();

        return v;
    }

    //Googles the coin with the name of the cryptocurrency that was clicked
    public void googleCoin (String coinName) {
        String url = "https://www.google.com/search?q=" + coinName;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void updateUi() {
        //Pair the variables in the file with the widgets in the layout file
        View rootView = getView();
        if (newCoin != null) {
            name = (TextView) rootView.findViewById(R.id.name);
            symbol = rootView.findViewById(R.id.symbol);
            value = rootView.findViewById(R.id.value);
            change1h = rootView.findViewById(R.id.change1h);
            change24h = rootView.findViewById(R.id.change24h);
            change7d = rootView.findViewById(R.id.change7d);
            marketcap = rootView.findViewById(R.id.marketcap);
            volume = rootView.findViewById(R.id.volume);
            search = rootView.findViewById(R.id.search);
            ivLogo = rootView.findViewById(R.id.ivLogo);


            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            //Sets the text of the widgets in the layout file to the values of the coin object we got earlier
            name.setText(newCoin.getName());
            symbol.setText(newCoin.getSymbol());
            value.setText(formatter.format(Double.valueOf(newCoin.getPriceUsd())));
            change1h.setText(String.valueOf(newCoin.getPercentChange1h() + " %"));
            change24h.setText(String.valueOf(newCoin.getPercentChange24h()) + " %");
            change7d.setText(String.valueOf(newCoin.getPercentChange7d()) + " %");
            marketcap.setText(formatter.format(Double.valueOf(newCoin.getMarketCapUsd())));
            volume.setText(Double.toString(newCoin.getVolume24()));
            Glide.with(this) //Specify view
                    .load("https://c1.coinlore.com/img/" + newCoin.getNameid() + ".png") //Load from provided URL
                    .fitCenter()
                    .into(ivLogo);

            //Listener that allows the search button to do what is under onClick() when clicked
            search.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    googleCoin(newCoin.getName());
                }
            });
        }
    }
}
