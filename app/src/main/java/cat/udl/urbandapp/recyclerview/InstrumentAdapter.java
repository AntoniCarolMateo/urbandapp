package cat.udl.urbandapp.recyclerview;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.dialogs.DialogSetProfileStep1;
import cat.udl.urbandapp.dialogs.DialogSetProfileStep2;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.viewmodel.TablesViewModel;

public class InstrumentAdapter extends ListAdapter<Instrument, InstrumentAdapter.InstrumentHolder> {

    private AdapterView.OnItemClickListener InstrumentListener;
    private FragmentActivity activity;
    private final static String TAG ="InstrumentAdapter";
    private TablesViewModel tablesViewModel;

    public InstrumentAdapter(@NonNull DiffUtil.ItemCallback<Instrument> diffCallback, TablesViewModel tablesViewModel,
                             FragmentActivity activity) {
        super(diffCallback);
        this.tablesViewModel = tablesViewModel;
        this.activity = activity;
    }

    @NonNull
    @Override
    public InstrumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instrument_list, parent, false);
        return new InstrumentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentHolder holder, int position) {
        final Instrument currentInstrument = getItem(position);
        holder.textViewName.setText(currentInstrument.getNameInstrument());
        holder.ratingExpirience.setRating(currentInstrument.getExpirience());

        holder.button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: remove instrument
                        tablesViewModel.removeInstrument(currentInstrument);
            }
        });

    }

    class InstrumentHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private RatingBar ratingExpirience;
        private ImageView icon;
        private ImageView button_del;

        public InstrumentHolder(@NonNull View itemView) {
            super(itemView);
            //tablesViewModel = new TablesViewModel(tablesViewModel.getApplication());
            textViewName = itemView.findViewById(R.id.textView_nameInstrument);
            ratingExpirience = itemView.findViewById(R.id.ratingBar_exp_ins);
            icon = itemView.findViewById(R.id.icon_instrument);
            button_del = itemView.findViewById(R.id.button_delete_ins);

        }


    }
}
