package com.example.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.DB.AppDatabase;
import com.example.contacts.DB.DataDB;
import com.example.contacts.databinding.FragmentContactDetailsBinding;

import java.util.List;

//import com.example.contacts.databinding.FragmentEditContactBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;


    public ContactDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactDetails newInstance(String param1, String param2,String param3,String param4,String param5,String param6) {
        ContactDetails fragment = new ContactDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
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
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }

    public String getmParam1() {
        return mParam1;
    }

    Button Email,Call,Edit,Delete;
    FragmentContactDetailsBinding binding;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initializing ViewModel
        VM vm = new ViewModelProvider(this.getActivity()).get(VM.class);

        //Setting the toolbar title
        ((MainActivity)getActivity()).toolbar.setTitle("Contact Details");
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_details, container, false);
        view = binding.getRoot();
        binding.setContact(new Data(mParam1,mParam2,mParam3,mParam5,mParam6));

        // Here we have used data binding for setting the text of views and hence I have commented the setText views
        TextView name = view.findViewById(R.id.D_name);
       // name.setText(mParam1);
        TextView number = view.findViewById(R.id.D_No);
      //  number.setText(mParam2);
        TextView email = view.findViewById(R.id.D_Email);
       // email.setText(mParam3);
        TextView number2 = view.findViewById(R.id.D_No2);
       // number2.setText(mParam5);
        TextView number3 = view.findViewById(R.id.D_No3);
       // number3.setText(mParam6);
        String TO = email.getText().toString();
        Email = view.findViewById(R.id.Email);

        //Sending Email using Intent on click of Email button
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("intent", "sending email");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, TO);
                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i,"chavanshashank670@gmail.com"));

            }
        });
        Call = view.findViewById(R.id.Call);
        String[] nums = getActivity().getResources().getStringArray(R.array.Numbers);
        String[] nums1 = {mParam2,mParam5,mParam6};
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Number");
                builder.setItems(nums1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Now sending call request on the click of call button
                        //Default call app on the mobile will be opened with the number dialed
                                Intent call = new Intent(Intent.ACTION_DIAL);
                                call.setData(Uri.parse("tel:" + nums1[i]));
                                startActivity(call);
                    }
                });
                builder.show();
            }
        });
        Edit = view.findViewById(R.id.Edit);
        //OnClick of Edit button the user will be directed to the Edit Contact page
        // rest will be taken care in the EditContact Fragment
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //name , number , email data will be passed to edit page fragment
                Fragment fragment = EditContact.newInstance(mParam1,mParam2,mParam3,mParam4,mParam5,mParam6);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.Container,fragment).addToBackStack(null).commit();
            }
        });

        /*OnClick of Delete button the contact of the user will be deleted from the database.
         For doing this we need a reference and in our case, it is the number of the candidate,
         by reference of which the contact will be removed from database*/
        Delete = view.findViewById(R.id.Delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.getLiveData().observe(requireActivity(), new Observer<List<DataDB>>() {
                    @Override
                    public void onChanged(List<DataDB> dataDBS) {
                        vm.delete_Data(mParam2);
                    }
                });
                Toast.makeText(getContext(),"Contact with number - " + mParam2 + " deleted",Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.Container,new Contact_Page()).commit();
            }
        });
        return view;
    }
}