package cat.udl.urbandapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.TablesViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogAddGenere extends DialogFragment implements LifecycleOwner {

    public View rootView;
    public FragmentActivity activity;
    private ListView listView;
    private String lv_items[] = { "Rock", "Pop","Country"};
    private List<MusicalGenere> my_sel_items;

    private SharedPreferences mPreferences;
    private TablesViewModel viewModel;
    private UserViewModel userViewModel;
    private Button button_add_genere;



    public static DialogAddGenere newInstance(FragmentActivity activity, TablesViewModel viewModel) {
        DialogAddGenere dialog = new DialogAddGenere();
        dialog.userViewModel = new UserViewModel(activity.getApplication());
        dialog.activity = activity;
        dialog.viewModel = viewModel;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mPreferences = PreferencesProvider.providePreferences();

        initView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Aplicar cambios", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                    viewModel.saveGeneres();
                    userViewModel.firstSetUpDone();
            }
        });
        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        button_add_genere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addGenenres(my_sel_items);

            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                my_sel_items = new ArrayList<MusicalGenere>();
                SparseBooleanArray a = listView.getCheckedItemPositions();

                for (int i = 0; i< lv_items.length; i++){
                    MusicalGenere genre = new MusicalGenere();
                    if (a.valueAt(i)){
                        genre.setName(listView.getAdapter().getItem(i).toString());
                        my_sel_items.add(genre);

                    }
                }
                Log.d("values", my_sel_items.toString());
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;

    }

    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_add_genere, null, false);
        listView = rootView.findViewById(R.id.listView_generes);
        listView.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_multiple_choice, lv_items));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        button_add_genere = rootView.findViewById(R.id.button);


    }



}




