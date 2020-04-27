package cat.udl.urbandapp.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.MusicalGenere;

public class GeneresAdapter extends ListAdapter<MusicalGenere, GeneresAdapter.GenereHolder> {



    public GeneresAdapter(@NonNull DiffUtil.ItemCallback<MusicalGenere> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public GenereHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genere_list, parent, false);
        return new GeneresAdapter.GenereHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenereHolder holder, int position) {
            final MusicalGenere current_genere = getItem(position);
            holder.name_genere.setText(current_genere.getName());

            holder.button_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //remove Genere
                }
            });
    }






    class GenereHolder extends RecyclerView.ViewHolder {

        private TextView name_genere;
        private ImageView button_del;
        public GenereHolder(@NonNull View itemView) {
            super(itemView);
            name_genere = itemView.findViewById(R.id.textView_name_genere);
            button_del = itemView.findViewById(R.id.button_delete_gen);
        }


    }
}
