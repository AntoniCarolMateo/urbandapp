package cat.udl.urbandapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.preferences.PreferencesProvider;

public class DialogAddGenere extends DialogFragment implements LifecycleOwner {

    public View rootView;
    private SharedPreferences mPreferences;

    Button button;
    CheckBox Rock_gnr,Pop_gnr;


    public static DialogAddGenere newInstance(FragmentActivity activity) {
        DialogAddGenere dialog = new DialogAddGenere();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mPreferences = PreferencesProvider.providePreferences();
        addListenerOnButtonClick();
      
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

    private void addListenerOnButtonClick(){
        //Getting instance of CheckBoxes and Button from the activty_main.xml file
        //Rock_gnr =(CheckBox)findViewById(R.id.Rock_gnr);
        //Pop_gnr=(CheckBox)findViewById(R.id.Pop_gnr);
        //button=(Button)findViewById(R.id.button);
        //button.setOnClickListener(new View.OnClickListener() {

            /*
            @Override
            public void onClick(View view) {

                if(Rock_gnr.isChecked()){

                }
                if(Pop_gnr.isChecked()){

                }
                }





        });*/
    }
}




