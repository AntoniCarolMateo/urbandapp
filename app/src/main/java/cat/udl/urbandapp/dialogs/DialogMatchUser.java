package cat.udl.urbandapp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import cat.udl.urbandapp.DefaultActivity;
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
        builder.setPositiveButton("Inspecciona su perfil!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });
        builder.setNegativeButton("Ver má tarde", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        userViewModel.getResponseLiveDataMatch().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Toast.makeText(getContext(), "username :" + user.getUsername() +" gen_exp :" + user.getGen_exp(),Toast.LENGTH_SHORT).show();
                username_match.setText(user.getUsername());
                exp_match.setRating(user.getGen_exp());
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
    }
}

