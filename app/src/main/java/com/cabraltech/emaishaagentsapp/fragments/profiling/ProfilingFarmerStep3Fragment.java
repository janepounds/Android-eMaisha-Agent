package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep3Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingFarmerStep3Fragment extends Fragment {

    private Context context;
    private FragmentProfilingFarmerStep3Binding binding;
    private NavController navController;
    PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;
    String[] descriptionData = {"Personal\nDetails", "Contact\nDetails", "Farming\nDetails"};
    public ProfilingFarmerStep3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_farmer_step3,container,false);
        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.farmerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Regular.ttf");
        //inflating pop up
       // ConstraintLayout constraintLayout = (ConstraintLayout) binding.
       // ((AppCompatActivity)getActivity()).getLayoutInflater().  getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Farmer Profiling");
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up window
            showPopup();

            }
        });
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popback stack to step 2
                navController.popBackStack();
            }
        });



    }
    public void showPopup() {

        View popupView = getLayoutInflater().inflate(R.layout.pop_up, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView thankYouTv = (TextView) popupView.findViewById(R.id.thank_you_tv);
        TextView contentTv = (TextView) popupView.findViewById(R.id.pop_up_content);
        ImageView popUpImg = (ImageView) popupView.findViewById(R.id.pop_up_img);



        // Initialize more widgets from `popup_layout.xml`

        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
      //  anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
       // popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
        //        location[0], location[1] + anchorView.getHeight());

    }
}