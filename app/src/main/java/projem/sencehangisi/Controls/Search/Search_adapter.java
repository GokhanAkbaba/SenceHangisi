package projem.sencehangisi.Controls.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import projem.sencehangisi.R;

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<SearchInfo> contactList;
    private List<SearchInfo> contactListFiltered;
    private SearchAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adsoyad, kullanici;
        public ImageView thumbnail;
        public ImageButton tkpAra;

        public MyViewHolder(View view) {
            super(view);
            adsoyad = view.findViewById(R.id.search_adsoyad);
            kullanici = view.findViewById(R.id.search_kullaniciAdi);
            thumbnail = view.findViewById(R.id.search_foto);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public Search_adapter(Context context, List<SearchInfo> contactList, SearchAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered=contactList;
        this.listener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_ara, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        final SearchInfo contact = contactListFiltered.get(position);
        holder.adsoyad.setText(contact.getAd_soyad());
        holder.kullanici.setText(contact.getKul_adi());

        Glide.with(context)
                .load(contact.getKul_image())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<SearchInfo> filteredList = new ArrayList<>();
                    for (SearchInfo row : contactList) {
                        if (row.getAd_soyad().toLowerCase().contains(charString.toLowerCase()) || row.getKul_adi().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<SearchInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface SearchAdapterListener {
        void onContactSelected(SearchInfo contact);
    }

}
