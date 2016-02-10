package com.example.homework7;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MyDialogFragment_DatePicker extends DialogFragment {
    public static final String DATE_ARGS = "date_argument";
    public static final String TEXT_ARGS = "text_argument";
    private Date mDate;
    private String mText;

    public static MyDialogFragment_DatePicker newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(DATE_ARGS, date);

        MyDialogFragment_DatePicker fragment = new MyDialogFragment_DatePicker();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate = (Date)getArguments().getSerializable(DATE_ARGS);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day).getTime();
                getArguments().putSerializable(DATE_ARGS, mDate);
                System.out.println("xing " + mDate);
            }
        });
        final EditText editText = (EditText) v.findViewById(R.id.editText);
        mText = "No input on EditText";

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(editText != null) {
                    mText = editText.getText().toString();
                }
                else{
                    mText = "No input on EditText";
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(v)
                .setTitle("Date Picker")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                sendResult(Activity.RESULT_OK);
                                System.out.println("xing send result"+mDate);
                            }
                        });
        return alertDialogBuilder.create();
    }

    private void sendResult(int resultCode){
        if (getTargetFragment() == null) return;

        Intent i = new Intent();
        i.putExtra(DATE_ARGS, mDate);
        i.putExtra(TEXT_ARGS,mText);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
