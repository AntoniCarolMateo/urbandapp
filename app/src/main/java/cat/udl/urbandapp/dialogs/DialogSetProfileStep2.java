package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.core.app.DialogCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cat.udl.urbandapp.R;

public class DialogSetProfileStep2 extends DialogFragment {

    public View rootView;
    private FragmentActivity activity;
    private Button addInstrument;

    public static DialogSetProfileStep2 newInstance(FragmentActivity activity) {
        DialogSetProfileStep2 dialog = new DialogSetProfileStep2();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();

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
        // Set other dialog properties

        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        addInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddInstrument dialogAddInstrument = DialogAddInstrument.newInstance(getActivity());
                dialogAddInstrument.show(getParentFragmentManager(), "probando");
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

    //TODO: hacer tabla en el layout, recicler view, guardar los cambios en el json
}

