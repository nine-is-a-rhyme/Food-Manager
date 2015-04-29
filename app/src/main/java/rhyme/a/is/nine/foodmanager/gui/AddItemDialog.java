package rhyme.a.is.nine.foodmanager.gui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemDialog extends DialogFragment {

    private boolean startScanner;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Barcodescanner starten?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startScanner = true;
                        new IntentIntegrator(getActivity()).initiateScan();
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), ProductActivity.class);
                        getActivity().startActivityForResult(intent, 0);
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }


    public boolean getValue()
    {
        return this.startScanner;
    }


}
