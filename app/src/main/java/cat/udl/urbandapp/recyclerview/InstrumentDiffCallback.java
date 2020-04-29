package cat.udl.urbandapp.recyclerview;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.urbandapp.models.Instrument;

public class InstrumentDiffCallback extends DiffUtil.ItemCallback<Instrument> {
    @Override
    public boolean areItemsTheSame(@NonNull Instrument oldItem, @NonNull Instrument newItem) {
        Log.d("InstrumentCallbackDiff","oldItem: " + oldItem.toString());
        Log.d("InstrumentCallbackDiff","newItem: " + newItem.toString());
        return oldItem.getNameInstrument().equals(newItem.getNameInstrument());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Instrument oldItem, @NonNull Instrument newItem) {
        return oldItem.equals(newItem);
    }
}
