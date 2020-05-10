package cat.udl.urbandapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.TablesViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class FilterMultipleChoice extends DialogFragment implements LifecycleOwner {

    private String type;
    public View rootView;
    public FragmentActivity activity;
    private ListView listView;
    private UserViewModel userViewModel;
    private TablesViewModel tablesViewModel;
    private Button button_done;
    private String lv_instruments[] = { "Guitarra","Trompeta", "Piano", "Maracas"};
    private String lv_genres[] = {"Pop","Rock","Country","Metal"};
    private List<Instrument> my_sel_items_ins = new ArrayList<>();
    private List<MusicalGenere> my_sel_items_gen = new ArrayList<>();

    public static FilterMultipleChoice newInstance(FragmentActivity activity, UserViewModel userViewModel, TablesViewModel tablesViewModel, String t) {
        FilterMultipleChoice dialog = new FilterMultipleChoice();
        dialog.userViewModel = userViewModel;
        dialog.type = t;
        dialog.tablesViewModel = tablesViewModel;
        dialog.activity = activity;
        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Aplicar cambios", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (type == "INSTRUMENTS") {
                        Log.d("KELOKE", my_sel_items_ins.toString());
                        userViewModel.filterInstruments(my_sel_items_ins);
                    }
                    if (type == "GENRES") {
                        userViewModel.filterGenres(my_sel_items_gen);
                    }
                }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == "INSTRUMENTS") {
                    Instrument instr = new Instrument();
                    SparseBooleanArray a = listView.getCheckedItemPositions();

                    instr.setNameInstrument(listView.getAdapter().getItem(position).toString());;
                    if (a.valueAt(0)){
                        my_sel_items_ins.add(instr);
                        Toast.makeText(getContext(), "KELOKE" + my_sel_items_ins.toString(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for (int i = 0; i < my_sel_items_ins.size();i++){
                            my_sel_items_ins.remove(i);
                            Toast.makeText(getContext(), "KELOKE" + my_sel_items_ins.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                if (type == "GENRES") {
                    MusicalGenere genre = new MusicalGenere();
                    genre.setName(listView.getAdapter().getItem(position).toString());
                    SparseBooleanArray a = listView.getCheckedItemPositions();
                    if (a.valueAt(0)) {
                        my_sel_items_gen.add(genre);
                    }
                    else{
                        for (int i = 0; i < my_sel_items_gen.size();i++){
                            my_sel_items_gen.remove(i);
                        }

                }
                    Log.d("values", my_sel_items_gen.toString());
                }
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;


    }

    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_multiple, null, false);
        listView = rootView.findViewById(R.id.list_view_filters);
        if (this.type == "INSTRUMENTS") {
            listView.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_multiple_choice, lv_instruments));
        }
        if (this.type == "GENRES") {
            listView.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_multiple_choice, lv_genres));
        }
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        button_done = rootView.findViewById(R.id.button_done);

    }

}
