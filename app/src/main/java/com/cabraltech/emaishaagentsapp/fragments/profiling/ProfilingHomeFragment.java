package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingHomeBinding;


public class ProfilingHomeFragment extends Fragment {

    private  Context context;
    private FragmentProfilingHomeBinding binding;
    private NavController navController;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public ProfilingHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_home,container,false);
        this.setHasOptionsMenu(true);
        setHasOptionsMenu(true);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        return  binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        binding.layoutProfilingFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation to step 1
                navController.navigate(R.id.action_profilingHomeFragment_to_profilingFarmerFragment3)
                ;

            }
        });

        binding.layoutProfilingAssociation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_profilingHomeFragment_to_profilingAssociationFragment2);
            }
        });

        binding.layoutAgroInputDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_profilingHomeFragment_to_profilingAgroInputDealersFragment2);
            }
        });
        binding.layoutDashboardBulkBuyers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_profilingHomeFragment_to_profilingBulkBuyersFragment2);
            }
        });


    }

    public  void onNavigationItemSelected (int itemPosition,
                                                      long itemId){
        navController.popBackStack();

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            navController.popBackStack();
            //getActivity().getSupportFragmentManager().popBackStack();


          //  getFragmentManager().popBackStack();
          getActivity().onBackPressed();
            Toast.makeText(getContext(), "Back", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}