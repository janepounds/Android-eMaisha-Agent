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

import java.util.ArrayList;
import java.util.List;


public class SucessDialogFragment extends DialogFragment {
    private Context context;
    private NavController navController;
    private TextView popContents;
    private String contents;
    private List<String> farmer = new ArrayList<String>();





    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        // Get the layout inflater
        // Inflate and set the layout for the dialog

        //loop thru bundle and set textview
        if(getArguments()!=null) {
            if(getArguments().getString("profiledUser").equalsIgnoreCase("farmer")){
                String name = getArguments().getString("farmername");
                String village = getArguments().getString("village");
                popContents.setText("Farmer " + name +" based in " +village+ "  has been successfully\nRegistered and his profile created");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("association")){
                String ass_name = getArguments().getString("association_name");
                String village = getArguments().getString("assc_village");
                popContents.setText("Association " + ass_name +" based in " +village+ "  has been profiled successfully");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("agroinput")){
                String agroinput = getArguments().getString("agrobiz");
                String village = getArguments().getString("agro_village");
                popContents.setText("Agro Input Buyer " + agroinput +" based in " +village+ "  has been profiled successfully");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("bulkbuyer")){
                String bulkbuyer = getArguments().getString("bizname");
                String village = getArguments().getString("bulkvillage");
                popContents.setText("Association " + bulkbuyer +" based in " +village+ "  has been profiled successfully");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("market")){
                String market = getArguments().getString("market_name");
                popContents.setText("Market " + market +" has been successfully created");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("market_price")){
                popContents.setText("Market price has been successfully created");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("scouting")){
                popContents.setText("Scouting Report has been successfully created");

            }else if(getArguments().getString("profiledUser").equalsIgnoreCase("pest")){
                popContents.setText("Pest Report has been successfully created");

            }



        }

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