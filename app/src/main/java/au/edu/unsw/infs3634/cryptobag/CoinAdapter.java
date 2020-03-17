 package au.edu.unsw.infs3634.cryptobag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private ArrayList<Coin> mCoins;
    private RecyclerViewClickListener mListener;
    //private TextView coinName;
    //private TextView value;
    //private TextView change1h;
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.cryptobag.MESSAGE";

    public CoinAdapter(ArrayList<Coin> coins, RecyclerViewClickListener listener) {
        mCoins = coins;
        mListener = listener;

    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public TextView textView;
        private TextView coinName;
        private TextView value;
        private TextView change1h;

        public CoinViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            //textView = v;
            //super(itemView);

            mListener = listener;
            v.setOnClickListener(this);
            coinName = (TextView) itemView.findViewById(R.id.name);
            value = (TextView) itemView.findViewById(R.id.value);
            change1h = (TextView) itemView.findViewById(R.id.value);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick (View view) {
            int position = getAdapterPosition();
            //Coin coin = mDataset.get(position);
            mListener.onClick(view, getAdapterPosition());

            //Intent intent = new Intent(this, DetailActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, coin.getSymbol());
            //startActivity(intent);

        }
    }

    @Override
    public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        //CoinViewHolder vh = new CoinViewHolder(v);
        //return vh;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
        return new CoinViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        //holder.textView.setText(mCoins.get(position));
        Coin coin = mCoins.get(position);
        holder.coinName.setText(coin.getName());
        holder.value.setText(coin.getName());
        holder.change1h.setText(Double.toString(coin.getChange1h()) + " %");
    }

    @Override
    public int getItemCount() {
        return mCoins.size();
    }


}
