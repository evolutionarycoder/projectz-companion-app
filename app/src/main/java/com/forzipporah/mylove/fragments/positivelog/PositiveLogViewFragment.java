package com.forzipporah.mylove.fragments.positivelog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.models.PositiveLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PositiveLogViewFragment extends Fragment {


    public PositiveLogViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_positive_log_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent      i           = getActivity().getIntent();
        PositiveLog positiveLog = i.getParcelableExtra(PositiveLogFragment.POSITIVE_LOG_EXTRA);

        TextView tvPositive = (TextView) view.findViewById(R.id.positiveTV);
        tvPositive.setText(positiveLog.getPositiveFor());

        TextView tvDate = (TextView) view.findViewById(R.id.dateTV);
        tvDate.setText(positiveLog.getDate());
    }
}
