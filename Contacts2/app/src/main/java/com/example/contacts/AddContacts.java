package com.example.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddContacts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddContacts extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddContacts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddContacts.
     */
    // TODO: Rename and change types and number of parameters
    public static AddContacts newInstance(String param1, String param2) {
        AddContacts fragment = new AddContacts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button Add;
    EditText Name;
    EditText Number;
    EditText Email;
    EditText Number2;
    EditText Number3;
    Adapter adapter;
    MainActivity mainActivity;
    public String name, number, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_contacts, container, false);
        Add = view.findViewById(R.id.Add_Contacts);
        Name = view.findViewById(R.id.Name);
        Number = view.findViewById(R.id.Number);
        Email = view.findViewById(R.id.Email);
        Number2 = view.findViewById(R.id.Number2);
        Number3 = view.findViewById(R.id.Number3);
        //ViewModel viewModel = new ViewModelProvider(this.getActivity()).get(ViewModel.class);
        VM vm = new ViewModelProvider(this.getActivity()).get(VM.class);
       ((MainActivity)getActivity()).toolbar.setTitle("Add Contacts");
        // ON click of add button the data would be uploaded to our database
        // After that the user will be directed to the contacts list page
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* This was the insert code written for SQLite Database.Now we have used Room Database and Dagger2 for implementing it.So this code is only for reference purpose
                MyDB db = new MyDB(getContext());
                db.ADD(Name.getText().toString(), Number.getText().toString(), Number2.getText().toString(), Number3.getText().toString(), Email.getText().toString());*/
                if(TextUtils.isEmpty(Name.getText().toString())||TextUtils.isEmpty(Number.getText().toString())||TextUtils.isEmpty(Email.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Fill data");
                    builder.setMessage("Name, 1 phone number and email is necessary  ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(getContext(), "Contact Added ", Toast.LENGTH_SHORT);
                    Contact_Page contact_page = Contact_Page.newInstance(null, null);
                    //in the below code the recycler view will be updates before opening the contacts page
                    Fragment fragment = Contact_Page.newInstance(name, number);
                    getFragmentManager().beginTransaction().replace(R.id.Container, new Contact_Page()).commit();

                    /*The Data will be added to DataDB entity and will be directly inserted using view model in the database without Actually initializing or calling the database in this code*/
                    DataDB dataDB = new DataDB();
                    dataDB.name = Name.getText().toString();
                    dataDB.p_no = Number.getText().toString();
                    dataDB.p_no1 = Number2.getText().toString();
                    dataDB.p_no2 = Number3.getText().toString();
                    dataDB.email = Email.getText().toString();
                    //using view model(dagger implemented) the data is being inserted in the database
                    vm.getLiveData().observe(requireActivity(), new Observer<List<DataDB>>() {
                        @Override
                        public void onChanged(List<DataDB> dataDBS) {
                            vm.insert_data(dataDB);
                            Toast.makeText(getContext(), "Contact Added ", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (activity != null) {
            activity.getActionBar();
        }
    }
}