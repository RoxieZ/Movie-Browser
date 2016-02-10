package com.example.homework7;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.} interface
 * to handle interaction events.
 * Use the {@link CoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoverFragment extends Fragment {

    public int REQUEST_DATE = 0;
    public int PICK_CONTACT_REQUEST = 1;
    public int REQUEST_DATE_ACTIVITY = 2;
    private OnButtonSelectedListener mListener;
    private TextView text_result;
    private TextView text_result1;


    public static CoverFragment newInstance(String param1, String param2) {
        CoverFragment fragment = new CoverFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public CoverFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_cover, container, false);
        View.OnClickListener onClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int id = view.getId();
                switch (id){
                    case R.id.button1:
                        showDatePickerDialogGetResult();
                        break;
                    case R.id.button2:
                        startNewActivity();
                        break;
                    case R.id.button3:
                        startContactActivity();
                        break;

                    default:
                        break;
                }

            }
        };
        text_result = (TextView) rootView.findViewById(R.id.txt_result);
        text_result1 = (TextView) rootView.findViewById(R.id.txt_result1);
        (rootView.findViewById(R.id.button1)).setOnClickListener(onClickListener);
        (rootView.findViewById(R.id.button2)).setOnClickListener(onClickListener);
        (rootView.findViewById(R.id.button3)).setOnClickListener(onClickListener);
        return rootView;
    }

    public void showDatePickerDialogGetResult(){
        Date date = new Date(System.currentTimeMillis());
        MyDialogFragment_DatePicker dialog = MyDialogFragment_DatePicker.newInstance(date);
        dialog.setTargetFragment(CoverFragment.this, REQUEST_DATE);
        dialog.show(getFragmentManager(), "DatePicker Dialog: Get Result");
    }

    public void startContactActivity(){
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    public void startNewActivity(){
        Date date = new Date(System.currentTimeMillis());
        Intent i = new Intent(getActivity(), SimpleActivity.class);
        i.putExtra(MyDialogFragment_DatePicker.DATE_ARGS, date);
        //startActivity(i);
        //getActivity().startActivityForResult(i, REQUEST_DATE);
        startActivityForResult(i, REQUEST_DATE_ACTIVITY);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnButtonSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnButtonSelectedListener {
        public void onButtonItemSelected(int position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("MovieDetailFragment", "onActivityResult");

        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(MyDialogFragment_DatePicker.DATE_ARGS);
            text_result.setText(date.toString());
            String text = (String) data.getSerializableExtra(MyDialogFragment_DatePicker.TEXT_ARGS);
            text_result1.setText(text.toString());
        }
        if (requestCode == REQUEST_DATE_ACTIVITY) {
            Date date = (Date) data.getSerializableExtra(MyDialogFragment_DatePicker.DATE_ARGS);
            text_result.setText(date.toString());
        }

        if (requestCode == PICK_CONTACT_REQUEST) {
            // Get the URI that points to the selected contact
            Uri contactUri = data.getData();

            // We only need the Number and Name columns,
            String[] projection = {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};

            Cursor cursor = getActivity().getContentResolver()
                    .query(contactUri, projection, null, null, null);
            cursor.moveToFirst();

            // Retrieve the data
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = cursor.getString(column);
            column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String name = cursor.getString(column);

            text_result.setText("Name: " + name + "\nTelephone: " + number);
            text_result.startAnimation(
                    AnimationUtils.loadAnimation(getActivity(), R.anim.myanimation));

        }
    }
}
