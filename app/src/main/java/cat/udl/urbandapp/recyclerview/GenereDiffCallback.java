package cat.udl.urbandapp.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.urbandapp.models.MusicalGenere;

public class GenereDiffCallback extends DiffUtil.ItemCallback<MusicalGenere> {


    @Override
    public boolean areItemsTheSame(@NonNull MusicalGenere oldItem, @NonNull MusicalGenere newItem) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull MusicalGenere oldItem, @NonNull MusicalGenere newItem) {
        return false;
    }
}
