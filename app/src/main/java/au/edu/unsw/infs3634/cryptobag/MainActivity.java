package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.cryptobag.MESSAGE";
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private Boolean wideScreen;

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

        //
        CoinAdapter.RecyclerViewClickListener listener = new CoinAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (wideScreen) {
                    final FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Bundle arguments = new Bundle();
                    arguments.putInt("position", position);
                    DetailFragment fragment = new DetailFragment();
                    fragment.setArguments(arguments);
                    transaction.replace(R.id.detail_container, fragment);
                    transaction.commit();
                } else {
                    //If the device is not a wide screen, we can just launch the DetailActivity with an intent since we don't need to use a fragment
                    newActivity(position);
                }
            }
        };

        Gson gson = new Gson();
        CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        List<Coin> coins = response.getData();

        //
        mAdapter = new CoinAdapter(coins, listener);
        recyclerView.setAdapter(mAdapter);
    }

    //Method to start a new activity (DetailActivity) by using an intent
    public void newActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class); //Creates new intent, linking the context('this' activity) to a class ('DetailActivity')
        intent.putExtra("position", position); //Specifies the position on the
        startActivity(intent); //Starts the intent/instance of the DisplayMessageActivity that is specified by the intent
    }
}
