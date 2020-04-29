package cat.udl.urbandapp.recyclerview;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.urbandapp.models.MusicalGenere;

public class GenereDiffCallback extends DiffUtil.ItemCallback<MusicalGenere> {


    @Override
    public boolean areItemsTheSame(@NonNull MusicalGenere oldItem, @NonNull MusicalGenere newItem) {
        Log.d("InstrumentCallbackDiff","oldItem: " + oldItem.toString());
        Log.d("InstrumentCallbackDiff","newItem: " + newItem.toString());
        return oldItem.getName().equals(newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull MusicalGenere oldItem, @NonNull MusicalGenere newItem) {
        return oldItem.equals(newItem);
    }
}
