package com.main.dhbworld.Fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.main.dhbworld.Enums.InteractionState;
import com.main.dhbworld.MainActivity;
import com.main.dhbworld.R;


public class DialogCofirmationUserInteraction extends MaterialAlertDialogBuilder {

    private String nameOfSelectedState;
    private InteractionState selectedState;


    public DialogCofirmationUserInteraction(@NonNull Context context, int message, int buttonText) {
        super(context);
        this.setTitle(R.string.sind_sie_sicher);
        this.setMessage(message);


        this.setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //TODO can be removed
        this.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast= Toast.makeText(context, R.string.danke_fuer_event, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });


    }

    public DialogCofirmationUserInteraction(@NonNull Context context, InteractionState[] states, int buttonText) {
        super(context);
        this.setTitle(R.string.sind_sie_sicher);

        nameOfSelectedState = states[0].getText();

        selectedState = states[0];

        String[] stateNames = new String[states.length];
        for (int i=0; i<states.length; i++) {
            stateNames[i] = states[i].getText();
        }

        this.setSingleChoiceItems(
                stateNames,
                0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        nameOfSelectedState=states[item].getText();
                        selectedState = states[item];

                    }
                }
        );

        this.setNegativeButton(R.string.abbrechen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //TODO can be removed
        this.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast= Toast.makeText(context, R.string.danke_fuer_event, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });


    }

    public String getNameOfSelectedState() {
        return nameOfSelectedState;
    }

    public InteractionState getSelectedState() {
        return selectedState;
    }
}