package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);//Gets the intent with the specified key

        newCoin = Coin.searchCoin(message);

        Log.d(TAG,"Coin-Symbol =");

        //System.out.println(newCoin.getName());

        name = findViewById(R.id.name);
        symbol = findViewById(R.id.symbol);
        value = findViewById(R.id.value);
        change1h = findViewById(R.id.change1h);
        change24h = findViewById(R.id.change24h);
        change7d = findViewById(R.id.change7d);
        marketcap = findViewById(R.id.marketcap);
        volume = findViewById(R.id.volume);
        search = findViewById(R.id.search);

        name.setText(newCoin.getName());
        symbol.setText(newCoin.getSymbol());
        value.setText(Double.toString(newCoin.getValue()));
        change1h.setText(Double.toString(newCoin.getChange1h()));
        change24h.setText(Double.toString(newCoin.getChange24h()));
        change7d.setText(Double.toString(newCoin.getChange7d()));
        marketcap.setText(Double.toString(newCoin.getMarketcap()));
        volume.setText(Double.toString(newCoin.getVolume()));
    }

    public void clickButton(View view) {
        googleCoin(newCoin.getName());
    }

    public void googleCoin (String coinName) {
        String url = "https://www.google.com/search?q=" + coinName;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
