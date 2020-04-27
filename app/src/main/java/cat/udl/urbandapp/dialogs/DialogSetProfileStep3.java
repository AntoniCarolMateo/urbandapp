    package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.recyclerview.GenereDiffCallback;
import cat.udl.urbandapp.recyclerview.GeneresAdapter;
import cat.udl.urbandapp.recyclerview.InstrumentAdapter;
import cat.udl.urbandapp.recyclerview.InstrumentDiffCallback;
import cat.udl.urbandapp.viewmodel.TablesViewModel;

    public class DialogSetProfileStep3 extends DialogFragment {

        public View rootView;
        private FragmentActivity activity;
        private ImageView add_generes;
        private RecyclerView recyclerView_generes;
        private TablesViewModel tablesViewModel;

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

            recyclerView_generes.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView_generes.setHasFixedSize(true);
            final GeneresAdapter genereAdapter = new GeneresAdapter(new GenereDiffCallback());
            recyclerView_generes.setAdapter(genereAdapter);

            List<MusicalGenere> list = new ArrayList<MusicalGenere>();
            MusicalGenere genere = new MusicalGenere("Pop");
            list.add(genere);
            genere = new MusicalGenere("Rock");
            list.add(genere);
            genereAdapter.submitList(list);


            add_generes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogAddGenere dialogAddGenere = DialogAddGenere.newInstance(getActivity());
                    dialogAddGenere.show(getParentFragmentManager(), "probando");
                }
            });



            alertDialog.setCanceledOnTouchOutside(false);
            return alertDialog;
        }

        //inicialtizar la vista del dialog
        private void initView() {
            rootView = LayoutInflater.from
                    (getContext()).inflate(R.layout.dialog_set_profile_step3, null, false);
            recyclerView_generes = rootView.findViewById(R.id.recyclerView_generes);
            add_generes = rootView.findViewById(R.id.imageView_addGenere);
        }
}
