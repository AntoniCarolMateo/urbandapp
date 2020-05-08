package cat.udl.urbandapp.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.urbandapp.models.User;

public class UserDiffCallback extends DiffUtil.ItemCallback<User> {
    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.getUsername().equals(newItem.getUsername());
    }

    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        //return oldItem.equals(newItem);
        return false;
    }
}
