package cat.udl.urbandapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import cat.udl.urbandapp.views.DefaultActivity;
import cat.udl.urbandapp.views.MusicoActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogMatchUser extends DialogFragment implements LifecycleOwner {

    private UserViewModel userViewModel;
    private DefaultActivity activity;
    public View rootView;

    private ImageView profile_photo_match;
    private TextView username_match;
    private RatingBar exp_match;

    private User userMatch;


    public static DialogMatchUser newInstance(DefaultActivity activity, UserViewModel userViewModel) {
        DialogMatchUser dialog = new DialogMatchUser();
        dialog.activity = activity;
        dialog.userViewModel = userViewModel;
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Ver Perfil!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent profile = new Intent(getActivity(), MusicoActivity.class);
                profile.putExtra("username", userMatch.getUsername());
                Log.d("MatchDialog","user " + userMatch.getUsername());
                startActivity(profile);

            }
        });
        builder.setNegativeButton("Ver má tarde", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;


    }

    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_match_user, null, false);
        profile_photo_match = rootView.findViewById(R.id.imageView_match_);
        username_match = rootView.findViewById(R.id.textView_match_username);
        exp_match = rootView.findViewById(R.id.ratingBar_match_exp);

        userMatch = new User();
        userViewModel.getMatch();
        userViewModel.getResponseLiveDataMatch().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Toast.makeText(getContext(), "username :" + user.getUsername() +" gen_exp :" + user.getGen_exp(),Toast.LENGTH_SHORT).show();
                userMatch = user;
                username_match.setText(user.getUsername());
                exp_match.setRating(user.getGen_exp());
            }
        });
    }
}

