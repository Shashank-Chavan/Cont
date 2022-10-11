package com.example.contacts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contacts.DB.DBDao;
import com.example.contacts.DB.DataDB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class VM extends AndroidViewModel {
    MutableLiveData<List<DataDB>> list1;
    MutableLiveData<List<DataDB>> list2;
    List<DataDB> db = new ArrayList<>();
    @Inject
    DBDao dbDao;
    public VM(@NonNull Application application) {
        super(application);
        db.sort(new Comparator<DataDB>() {
            @Override
            public int compare(DataDB dataDB, DataDB t1) {
                return  dataDB.name.compareTo(t1.name);
            }
        });
        ((MyApp)getApplication()).getAppComponent().inject(this);
        list1 = new MutableLiveData<>();
        db = dbDao.getAllUsers();

        list1.setValue(db);
    }
    public void showList(){
        db = dbDao.getAllUsers();
        list1.postValue(db);
    }
    public void insert_data(DataDB dataDB){
        dbDao.insertUser(dataDB);
        db.add(dataDB);
    }
    public void delete_Data(String phone_no){
        dbDao.delet(phone_no);
    }
    public void update_Date(String name,String no1,String no2,String no3,String email,String no0){
        dbDao.updat(name,no1,no2,no3,email,no0);
    }

    public LiveData<List<DataDB>> getLiveData(){return list1;}
}
