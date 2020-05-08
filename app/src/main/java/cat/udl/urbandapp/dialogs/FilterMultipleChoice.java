package cat.udl.urbandapp.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.TablesViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class FilterMultipleChoice extends DialogFragment implements LifecycleOwner {
    public View rootView;
    public FragmentActivity activity;
    private ListView listView;
    private UserViewModel userViewModel;
    private Button button_done;

    public static FilterMultipleChoice newInstance(FragmentActivity activity) {
        FilterMultipleChoice dialog = new FilterMultipleChoice();
        dialog.activity = activity;
        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_multiple, null, false);
    }

}
