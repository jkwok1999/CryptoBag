package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Test1
    //private static final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.cryptobag.MESSAGE";
    private static final String TAG = "MainActivity";
    //private Button button;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starting");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button = (Button) findViewById(R.id.button);

        recyclerView = (RecyclerView) findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ArrayList<Coin> myDataset = Coin.getCoins();

        CoinAdapter.RecyclerViewClickListener listener = new CoinAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                newActivity(position);
            }
        };

        mAdapter = new CoinAdapter(Coin.getCoins(), listener);
        recyclerView.setAdapter(mAdapter);
    }

    public void newActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class); //Creates new intent, linking the context('this' activity) to a class ('DetailActivity')
        intent.putExtra(EXTRA_MESSAGE, position);
        startActivity(intent); //Starts the intent/instance of the DisplayMessageActivity that is specified by the intent
    }
}
