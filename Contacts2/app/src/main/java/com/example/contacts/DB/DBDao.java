package com.example.contacts.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface DBDao {
    @Query("SELECT * FROM DATADB")
    List<DataDB> getAllUsers();
    @Insert
    void insertUser(DataDB data);

    @Query("DELETE FROM DataDB where Ph_no == :no")
    void delet(String no);
    @Delete
    void delete(DataDB data);
    @Query("UPDATE DataDB SET Name = :name , Ph_no = :n , Ph_no1 = :n1 , Ph_no2 = :n2 , Email = :email WHERE Ph_no = :n0 ")
    int updat(String name,String n,String n1,String n2,String email, String n0);
  //  @Update
  //  void update (DataDB dataDB);


}
