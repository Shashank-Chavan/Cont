package com.example.contacts;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contacts.DB.AppDatabase;
import com.example.contacts.DB.DataDB;
//import com.example.contacts.Data;
import com.example.contacts.databinding.FragmentEditContactBinding;

import java.util.List;

//import com.example.contacts.databinding.FragmentEditContactBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditContact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditContact extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    public EditContact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditContact.
     */
    // TODO: Rename and change types and number of parameters
    public static EditContact newInstance(String param1, String param2,String param3,String param4, String param5,String param6) {
        EditContact fragment = new EditContact();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }
    EditText Ename,Enumber,Eemail,Enumber2,Enumber3;
    Button Save;
    MainActivity mainActivity;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditContactBinding fragmentEditContactBinding;
        fragmentEditContactBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_contact,container,false);
        view = fragmentEditContactBinding.getRoot();
        fragmentEditContactBinding.setDetails(new Data(mParam1,mParam2,mParam3,mParam5,mParam6));
        //FragmentEditContactBinding editContactBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_contact, container, false);
        // Inflate the layout for this fragment
        //View view =  editContactBinding.getRoot();
        //editContactBinding.getContactDetail();
        //name,number and email data recieved from contact_detail fragment will be used here to prefill the data
        //View view = inflater.inflate(R.layout.fragment_edit_contact, container, false);.
        mainActivity=(MainActivity)getActivity();
        mainActivity.toolbar.setTitle("Edit Contact");
        Ename = view.findViewById(R.id.editName);
       // Ename.setText(mParam1);
        Enumber = view.findViewById(R.id.editNumber);
       // Enumber.setText(mParam2);
        Eemail = view.findViewById(R.id.editEmail);
       // Eemail.setText(mParam3);
        Enumber2 = view.findViewById(R.id.editNumber2);
       // Enumber2.setText(mParam5);
        Enumber3 = view.findViewById(R.id.editNumber3);
       // Enumber3.setText(mParam6);
        Save = view.findViewById(R.id.editSave);
        VM vm = new ViewModelProvider(this.getActivity()).get(VM.class);
        //Now after making changes in the data , the new data will be saved in the sqlite database
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Ename.getText().toString())||TextUtils.isEmpty(Enumber.getText().toString())||TextUtils.isEmpty(Eemail.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Fill data");
                    builder.setMessage("Name, 1 phone number and email must not be empty ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    vm.getLiveData().observe(requireActivity(), new Observer<List<DataDB>>() {
                        @Override
                        public void onChanged(List<DataDB> dataDBS) {
                            vm.update_Date(Ename.getText().toString(), Enumber.getText().toString(), Enumber2.getText().toString(), Enumber3.getText().toString(), Eemail.getText().toString(), mParam2);
                            Toast.makeText(getContext(), "Data with name " + Ename.getText().toString() + "has been updated", Toast.LENGTH_SHORT);
                        }
                    });

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.Container, new Contact_Page()).commit();
                    Toast.makeText(getContext(), "new Data Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}