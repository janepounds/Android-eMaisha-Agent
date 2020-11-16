package com.cabraltech.emaishaagentsapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;


public class SucessDialogFragment extends DialogFragment {
    private Context context;
    private NavController navController;
    private TextView popContents;
    private String contents;
    private String contents1;




    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        // Get the layout inflater
        // Inflate and set the layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.fragment_sucess_dialog, null);
        builder.setView(view);
        fillViews(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
            }
        });




        return builder.create();
    }

    public void fillViews(View view){
        popContents = view.findViewById(R.id.pop_up_content);
//        if(getArguments().containsKey("farmer")){
//
//            contents = getArguments().getString("farmer_name");
//            contents1 = getArguments().getString("village");
//            popContents.setText("Farmer " + contents +  "based in" +contents1 +"has been successfully registered and his profile created");
//        }else if(getArguments().containsKey("association")){
//
//        }




    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);




    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }



}