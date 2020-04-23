package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.core.app.DialogCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.recyclerview.InstrumentAdapter;
import cat.udl.urbandapp.recyclerview.InstrumentDiffCallback;
import cat.udl.urbandapp.viewmodel.TablesViewModel;

public class DialogSetProfileStep2 extends DialogFragment {

    public View rootView;
    private FragmentActivity activity;
    private Button addInstrument;
    private SharedPreferences mPreferences;
    private TablesViewModel tablesViewModel;
    private RecyclerView recyclerInstruments;

    public static DialogSetProfileStep2 newInstance(FragmentActivity activity) {
        DialogSetProfileStep2 dialog = new DialogSetProfileStep2();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        tablesViewModel = new TablesViewModel(getActivity().getApplication());
        mPreferences = PreferencesProvider.providePreferences();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Next Step", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DialogSetProfileStep3 dialog3 = DialogSetProfileStep3.newInstance(getActivity());
                dialog3.show(getParentFragmentManager(), "probando");
            }
        });
        builder.setNegativeButton("Prevous Step", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogSetProfileStep1 dialog1 = DialogSetProfileStep1.newInstance(getActivity());
                dialog1.show(getParentFragmentManager(), "probando");
            }
        });
        initView();
        // Set other dialog properties

        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        recyclerInstruments.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerInstruments.setHasFixedSize(true);
        final InstrumentAdapter instrumentAdapter = new InstrumentAdapter(new InstrumentDiffCallback(), tablesViewModel);
        recyclerInstruments.setAdapter(instrumentAdapter);

        tablesViewModel.getInstruments();

        addInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddInstrument dialogAddInstrument = DialogAddInstrument.newInstance(getActivity());
                dialogAddInstrument.show(getParentFragmentManager(), "probando");
            }
        });

        tablesViewModel.getInstruments().observe(this, new Observer<List<Instrument>>() {
            @Override
            public void onChanged(List<Instrument> i) {
                instrumentAdapter.submitList(i);
            }
        });


        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    //inicialtizar la vista del dialog
    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_set_profile_step2, null, false);
        addInstrument = rootView.findViewById(R.id.addInstrument);
        recyclerInstruments = rootView.findViewById(R.id.recyclerView_instruments);
    }

    public void verificarYPedirPermisosDeCamara() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (estadoDePermiso != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{(Manifest.permission.CAMERA)}, 1);
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método


        }
    }

}

