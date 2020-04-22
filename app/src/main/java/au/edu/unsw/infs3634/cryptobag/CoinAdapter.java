 package au.edu.unsw.infs3634.cryptobag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;

//Create CoinAdapter to get data from Coin.java to display in the RecyclerView
public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private List<Coin> mCoins;
    //private RecyclerViewClickListener mListener;
    private MainActivity mParentActivity;
    //private TextView coinName;
    //private TextView value;
    //private TextView change1h;
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.cryptobag.MESSAGE";
    private boolean wideScreen;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Coin coin = (Coin) v.getTag();
            System.out.println(v.getTag());
            if (wideScreen) {
                Bundle arguments = new Bundle();
                arguments.putString(DetailFragment.ARG_ITEM_ID, coin.getId());
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detail_container, fragment)
                        .commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailFragment.ARG_ITEM_ID, coin.getId());
                context.startActivity(intent);
            }
        }
    };

    //Constructor for the CoinAdapter
    public CoinAdapter(MainActivity parent, List<Coin> coins, boolean wideScreen) {
        mParentActivity = parent;
        mCoins = coins;
        this.wideScreen = wideScreen;

    }

    //Create an interface RecyclerViewClickListener to be instantiated later
    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    //Create a CoinViewHolder class that manages the coin_list_row xml file
    public class CoinViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {
        //public TextView textView;
        private TextView coinName;
        private TextView value;
        private TextView change1h;
        private ImageView image;

        //Constructor for the CoinViewHolder
        public CoinViewHolder(View v) { //}, RecyclerViewClickListener listener) {
            super(v);
            //textView = v;
            //super(itemView);

            //mListener = listener;
            //v.setOnClickListener(this);
            coinName = (TextView) itemView.findViewById(R.id.tvName);
            value = (TextView) itemView.findViewById(R.id.tvValue);
            change1h = (TextView) itemView.findViewById(R.id.tvChange);
            image = v.findViewById(R.id.ivArt);
            //itemView.setOnClickListener(this);
        }

        /*
        @Override
        public void onClick (View view) {
            int position = getAdapterPosition();
            //Coin coin = mDataset.get(position);
            mListener.onClick(view, getAdapterPosition());

            //Intent intent = new Intent(this, DetailActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, coin.getSymbol());
            //startActivity(intent);

        } */
    }

    @Override
    public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        //CoinViewHolder vh = new CoinViewHolder(v);
        //return vh;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
        return new CoinViewHolder(v);//, mListener);
    }

    //Override the onBindViewHolder method to be able to access widgets inside the view
    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        //holder.textView.setText(mCoins.get(position));

        //Create a formatter to help round the dollar values to 2dp and also to add a "$"
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Coin coin = mCoins.get(position);
        Glide.with(holder.itemView) //Specify view
                .load("https://c1.coinlore.com/img/" + coin.getNameid() + ".png") //Load from provided URL
                .fitCenter()
                .into(holder.image);
        holder.coinName.setText(coin.getName());
        //holder.value.setText(formatter.format(coin.getValue()));
        //holder.change1h.setText(Double.toString(coin.getChange1h()) + " %");

        holder.value.setText(NumberFormat.getCurrencyInstance().format(Double.valueOf(coin.getPriceUsd())));
        holder.change1h.setText(coin.getPercentChange24h() + " %");
        //To-do Set Colour
        if (Double.valueOf(coin.getPercentChange24h()) > 0 ) {
            holder.change1h.setTextColor(Color.GREEN);
        }
        else if (Double.valueOf(coin.getPercentChange24h()) < 0) {
            holder.change1h.setTextColor(Color.RED);
        }
        holder.itemView.setTag(coin);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mCoins.size();
    }

    public void setCoins(List<Coin> coins) {
        mCoins.clear();
        mCoins.addAll(coins);
        notifyDataSetChanged();
    }
}
