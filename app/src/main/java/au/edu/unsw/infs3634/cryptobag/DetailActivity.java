package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;

public class DetailActivity extends AppCompatActivity {

    ImageView logo;
    TextView name;
    TextView symbol;
    TextView value;
    TextView change1h;
    TextView change24h;
    TextView change7d;
    TextView marketcap;
    TextView volume;
    Button search;
    Coin newCoin;

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);//Gets the intent with the specified key
        int position = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);

        //newCoin = Coin.searchCoin(message);
        //newCoin = Coin.getCoins().get(position);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new DetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.scrollDetailActivity, fragment).commit();
    }
}
