package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView value;
    TextView change1h;
    TextView change24h;
    TextView change7d;
    TextView marketcap;
    TextView volume;
    Button search;
    Coin newCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);//Gets the intent with the specified key

        newCoin = Coin.searchCoin(message);

        value.setText(Double.toString(newCoin.getValue());
        change1h.setText(Double.toString(newCoin.getChange1h());
        change24h.setText(Double.toString(newCoin.getChange24h());
        change7d.setText(Double.toString(newCoin.getChange7d());
        marketcap.setText(Double.toString(newCoin.getMarketcap());
        volume.setText(Double.toString(newCoin.getVolume());
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
