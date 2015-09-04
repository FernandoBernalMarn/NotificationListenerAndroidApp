package com.example.notificationllistener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fbernal on 8/6/2015.
 */
public class HomeFragment extends Fragment {

    TextView lblText;

    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        lblText = (TextView) rootView.findViewById(R.id.label);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg, Toast.LENGTH_LONG).show();
        lblText.setText(msg);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
