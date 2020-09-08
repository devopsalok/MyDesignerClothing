package com.mydesignerclothing.mobile.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;
import java.util.TreeSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_LAYOUT = "param1";
    private static final String KEY_TOOLBAR = "param2";
    private static final String KEY_NAV_HOST = "param3";
    private static final int defaultInt = -1;

    // TODO: Rename and change types of parameters
    private int layoutRes;
    private int toolbarId;
    private int navHostId;
    Set<Integer> integerSet = new TreeSet<>();
    private NavController navController;

    private AppBarConfiguration appBarConfig;

    public static BaseFragment newInstance(int layoutResId, int toolbarId, int navHostId) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_LAYOUT, layoutResId);
        args.putInt(KEY_TOOLBAR, toolbarId);
        args.putInt(KEY_NAV_HOST, navHostId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layoutRes = getArguments().getInt(KEY_LAYOUT);
            toolbarId = getArguments().getInt(KEY_TOOLBAR);
            navHostId = getArguments().getInt(KEY_NAV_HOST);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        integerSet.add(R.id.home_dest);
        integerSet.add(R.id.shop_dest);
        integerSet.add(R.id.create_dest);
        integerSet.add(R.id.basket_dest);
        integerSet.add(R.id.about_dest);
        appBarConfig = new AppBarConfiguration.Builder(integerSet).build();

        if (toolbarId == defaultInt || navHostId == defaultInt) return;

        Toolbar toolbar = requireActivity().findViewById(toolbarId);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        navController = Navigation.findNavController(requireActivity(), navHostId);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig);
    }

    public boolean onBackPressed() {
//        return navController.popBackStack();
        return NavigationUI.navigateUp(navController, appBarConfig);
    }

    public void popToRoot() {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRes == defaultInt) {
            return null;
        } else {
            return inflater.inflate(layoutRes, container, false);
        }
    }
}
