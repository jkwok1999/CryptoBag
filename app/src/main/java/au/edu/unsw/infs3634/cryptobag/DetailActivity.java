package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView value;
    TextView change1h;
    TextView change24h;
    TextView change7d;
    TextView marketcap;
    TextView volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);//Gets the intent with the specified key

        Coin newCoin = Coin.searchCoin(message);

        value.setText(Double.toString(newCoin.getValue());
        change1h.setText(Double.toString(newCoin.getChange1h());
        change24h.setText(Double.toString(newCoin.getChange24h());
        change7d.setText(Double.toString(newCoin.getChange7d());
        marketcap.setText(Double.toString(newCoin.getMarketcap());
        volume.setText(Double.toString(newCoin.getVolume());
    }

}
