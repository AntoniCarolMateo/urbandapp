package cat.udl.urbandapp.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;

public class UsersAdapter extends ListAdapter<User, UsersAdapter.UserHolder> {


    public UsersAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);

    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users_filtered,parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final User current_user = (User) getItem(position);
        holder.username.setText(current_user.getUsername());
        holder.general_exp.setRating(current_user.getGen_exp());
    }

    class UserHolder extends RecyclerView.ViewHolder {
        TextView username;
        RatingBar general_exp;
        ImageView profile_photo;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textView_username_filter);
            general_exp = itemView.findViewById(R.id.ratingBar_exp_filter);
            profile_photo = itemView.findViewById(R.id.imageView_photo_filter);
        }
    }

}
