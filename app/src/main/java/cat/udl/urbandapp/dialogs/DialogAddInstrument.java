package cat.udl.urbandapp.dialogs;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.preferences.PreferencesProvider;

import cat.udl.urbandapp.recyclerview.InstrumentAdapter;
import cat.udl.urbandapp.recyclerview.InstrumentDiffCallback;
import cat.udl.urbandapp.viewmodel.TablesViewModel;

public class DialogAddInstrument extends DialogFragment implements LifecycleOwner {

    public View rootView;
    private FragmentActivity activity;
    private TablesViewModel viewModel;
    private SharedPreferences mPreferences;

    private Spinner choice_family;
    private Spinner choice_instrument;
    private Button addInstrument;
    private RatingBar experienceBar;
    private RecyclerView listaIntrumentos;


    public static DialogAddInstrument newInstance(FragmentActivity activity) {
        DialogAddInstrument dialog = new DialogAddInstrument();
        dialog.activity = activity;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.mPreferences = PreferencesProvider.providePreferences();
        initView();
        viewModel = new TablesViewModel(getActivity().getApplication());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Aplicar cambios", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //APlicariamos cambios en la base de datos
            }
        });
        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        choice_family.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_family.setSelection(position);
                String  str = (String) choice_family.getSelectedItem();
                switch(str) {
                    case ("Viento"):
                        ArrayAdapter<CharSequence> adapter_ins = ArrayAdapter.createFromResource(getContext(),
                                R.array.spinnerWindInstruments, android.R.layout.simple_spinner_item);
                        adapter_ins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        choice_instrument.setAdapter(adapter_ins);
                        break;
                    case ("Cuerda"):
                        ArrayAdapter<CharSequence> adapter_cu = ArrayAdapter.createFromResource(getContext(),
                                R.array.spinnerStringInstruments, android.R.layout.simple_spinner_item);
                        adapter_cu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        choice_instrument.setAdapter(adapter_cu);
                        break;
                    case ("Percusión"):
                        ArrayAdapter<CharSequence> adapter_per = ArrayAdapter.createFromResource(getContext(),
                                R.array.spinnerPercusionInstruments, android.R.layout.simple_spinner_item);
                        adapter_per.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        choice_instrument.setAdapter(adapter_per);
                        break;
                    case ("Otros"):
                        ArrayAdapter<CharSequence> adapter_oth = ArrayAdapter.createFromResource(getContext(),
                                R.array.spinnerOthers, android.R.layout.simple_spinner_item);
                        adapter_oth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        choice_instrument.setAdapter(adapter_oth);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TODO: ICONO ELIMINR FALTA IMPLEMENTAR VISUALMENTE

        addInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _instrument = (String) choice_instrument.getSelectedItem();
                int _expirience = experienceBar.getProgress();
                viewModel.addInstrument(_instrument, _expirience);
            }
        });


        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;


    }

    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_add_instrument, null, false);

        choice_family = rootView.findViewById(R.id.spinner_instruments_families);
        ArrayAdapter<CharSequence> adapter_fam = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerFamilies, android.R.layout.simple_spinner_item);
        adapter_fam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice_family.setAdapter(adapter_fam);

        choice_instrument = rootView.findViewById(R.id.spinner_instruments);

        experienceBar = rootView.findViewById(R.id.ratingBar_exp);
        addInstrument = rootView.findViewById(R.id.button_add_instrument);

        listaIntrumentos = rootView.findViewById(R.id.recyclerView);
        listaIntrumentos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaIntrumentos.setHasFixedSize(true);
        final InstrumentAdapter adapter = new InstrumentAdapter(new InstrumentDiffCallback(), viewModel);
        listaIntrumentos.setAdapter(adapter);

        viewModel.getResponseAddedInstrument().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Toast.makeText(getContext(), "Instrumento añadido correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Error añadiendo el isntrumento", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }




}
