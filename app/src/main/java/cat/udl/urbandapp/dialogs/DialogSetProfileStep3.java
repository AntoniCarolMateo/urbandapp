    package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cat.udl.urbandapp.R;

    public class DialogSetProfileStep3 extends DialogFragment {

        public View rootView;
        private FragmentActivity activity;
        private Button nextStep;

        public static DialogSetProfileStep3 newInstance(FragmentActivity activity) {
            DialogSetProfileStep3 dialog = new DialogSetProfileStep3();
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

                }
            });
            builder.setNegativeButton("Prevous Step", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogSetProfileStep2 dialog2 = DialogSetProfileStep2.newInstance(getActivity());
                    dialog2.show(getParentFragmentManager(),"probando");
                }
            });
            // Set other dialog properties

            AlertDialog alertDialog = builder.setView(rootView)
                    .setCancelable(true)
                    .create();


            alertDialog.setCanceledOnTouchOutside(false);
            return alertDialog;
        }

        //inicialtizar la vista del dialog
        private void initView() {
            rootView = LayoutInflater.from
                    (getContext()).inflate(R.layout.dialog_set_profile_step3, null, false);
        }
}
