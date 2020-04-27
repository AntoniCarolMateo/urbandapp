package cat.udl.urbandapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.preferences.PreferencesProvider;

public class DialogAddGenere extends DialogFragment implements LifecycleOwner {

    public View rootView;
    private SharedPreferences mPreferences;

    public static DialogAddGenere newInstance(FragmentActivity activity) {
        DialogAddGenere dialog = new DialogAddGenere();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.mPreferences = PreferencesProvider.providePreferences();
        initView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Aplicar cambios", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //APlicariamos cambios en la base de datos
            }
        });
        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;

    }

    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_add_genere, null, false);
    }
}
