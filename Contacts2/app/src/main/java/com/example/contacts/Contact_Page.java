package com.example.contacts;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.contacts.DB.AppDatabase;
import com.example.contacts.DB.DBDao;
import com.example.contacts.DB.DataDB;
import com.example.contacts.Dagger_Implementation.AppComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contact_Page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact_Page extends Fragment implements Adapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contact_Page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact_Page.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact_Page newInstance(String param1, String param2) {
        Contact_Page fragment = new Contact_Page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ((AppImplementation)getContext()).getAppComponent().inject(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button Add;
    //private ViewModel viewModel;
    private VM vm;
    RecyclerView recyclerView;
    Adapter adapter;
    MainActivity mainActivity;
    public List<DataDB> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact__page, container, false);
        Add = view.findViewById(R.id.add_Contacts);
        // Move to Add_Contact Page on button click
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.Container, new AddContacts()).addToBackStack(null).commit();
            }
        });
        vm = new ViewModelProvider(this.getActivity()).get(VM.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mainActivity=(MainActivity)getActivity();
        mainActivity.toolbar.setTitle("Contact list");
        // implementing view model from here on
        // this will prevent data loss/ restarting om orientation change
        vm.getLiveData().observe(requireActivity(), new Observer<List<DataDB>>() {
            @Override
            public void onChanged(List<DataDB> dataDBS) {
                //vm.showList();
                dataDBS.sort(new Comparator<DataDB>() {
                    @Override
                    public int compare(DataDB dataDB, DataDB t1) {
                        return  dataDB.name.toLowerCase().compareTo(t1.name.toLowerCase());
                    }
                });
                adapter = new Adapter(dataDBS,Contact_Page.this::onItemClick);
                recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }
    /*View model implementation ends*/
    @Override
    public void onItemClick(DataDB data) {
        String id = String.valueOf(data.uid);
        Fragment fragment = ContactDetails.newInstance(data.name, data.p_no, data.email,id,data.p_no1,data.p_no2);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Container,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
