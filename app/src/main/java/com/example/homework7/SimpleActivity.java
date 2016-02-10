package com.example.homework7;

import android.app.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class SimpleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_activity_main);

        Intent intent = getIntent();
        Date date = (Date) intent.getSerializableExtra(MyDialogFragment_DatePicker.DATE_ARGS);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(date))
                .commit();
    }



    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Date date) {
            Bundle args = new Bundle();
            args.putSerializable(MyDialogFragment_DatePicker.DATE_ARGS, date);

            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        private Date mDate;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.simple_fragment_date, container, false);

            mDate = (Date) getArguments().getSerializable(MyDialogFragment_DatePicker.DATE_ARGS);
            TextView textView = (TextView) rootView.findViewById(R.id.textview);
            textView.setText(mDate.toString());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
            datePicker.init(year, month, day, new DatePicker.OnDateChangedListener(){
                public void onDateChanged(DatePicker view, int year, int month, int day){
                    mDate = new GregorianCalendar(year, month, day).getTime();
                    getArguments().putSerializable(MyDialogFragment_DatePicker.DATE_ARGS, mDate);
                }
            });

            Button done = (Button) rootView.findViewById(R.id.done);
            done.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Log.d("SimpleActivity", "before setResult");
                    Intent intent = new Intent();
                    intent.putExtra(MyDialogFragment_DatePicker.DATE_ARGS, mDate);
                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                }
            });
            Button cancel = (Button) rootView.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    getActivity().finish();
                }
            });
            return rootView;
        }
    }

}