package cat.udl.urbandapp.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.urbandapp.models.Instrument;

public class InstrumentDiffCallback extends DiffUtil.ItemCallback<Instrument> {
    @Override
    public boolean areItemsTheSame(@NonNull Instrument oldItem, @NonNull Instrument newItem) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Instrument oldItem, @NonNull Instrument newItem) {
        return false;
    }
}
