package au.edu.unsw.infs3634.cryptobag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        int position = 0;

        //Pair the variables in the file with the widgets in the layout file
        name = v.findViewById(R.id.name);
        symbol = v.findViewById(R.id.symbol);
        value = v.findViewById(R.id.value);
        change1h = v.findViewById(R.id.change1h);
        change24h = v.findViewById(R.id.change24h);
        change7d = v.findViewById(R.id.change7d);
        marketcap = v.findViewById(R.id.marketcap);
        volume = v.findViewById(R.id.volume);
        search = v.findViewById(R.id.search);

        //Find a coin in the json object
        Gson gson = new Gson();
        CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        List<Coin> coins = response.getData();

        //
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
            System.out.println(newCoin.getName());
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        //Sets the text of the widgets in the layout file to the values of the coin object we got earlier
        System.out.println(newCoin.getName());
        name.setText(newCoin.getName());
        symbol.setText(newCoin.getSymbol());
        value.setText(formatter.format(Double.valueOf(newCoin.getPriceUsd())));
        change1h.setText(String.valueOf(newCoin.getPercentChange1h() + " %"));
        change24h.setText(String.valueOf(newCoin.getPercentChange24h()) + " %");
        change7d.setText(String.valueOf(newCoin.getPercentChange7d()) + " %");
        marketcap.setText(formatter.format(Double.valueOf(newCoin.getMarketCapUsd())));
        volume.setText(Double.toString(newCoin.getVolume24()));

        //Listener that allows the search button to do what is under onClick() when clicked
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                googleCoin(newCoin.getName());
            }
        });
        return v;
    }

    //Googles the coin with the name of the cryptocurrency that was clicked
    public void googleCoin (String coinName) {
        String url = "https://www.google.com/search?q=" + coinName;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
