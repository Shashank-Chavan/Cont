package com.example.contacts.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.contacts.Data;

import java.util.ArrayList;
import java.util.List;

public class MyDB extends SQLiteOpenHelper {

    public MyDB(Context context) {
        super(context, params.DB_NAME, null, params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + params.DB_Table + " ("
                + params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + params.KEY_NAME + " TEXT,"
                + params.KEY_NUMBER + " TEXT,"
                + params.KEY_EMAIL + " TEXT,"
                + params.KEY_NUMBER2 + " TEXT,"
                + params.KEY_NUMBER3 +  " TEXT)";
        Log.d("db","Query is on - " +  create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Adding values to the database
    //we will add whole Data class consisting of name, number and email to the database
    public void ADD(String name,String number,String number2,String number3, String email){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(params.KEY_NAME,name);
        values.put(params.KEY_NUMBER,number);
        values.put(params.KEY_EMAIL,email);
        values.put(params.KEY_NUMBER2,number2);
        values.put(params.KEY_NUMBER3,number3);

        db.insert(params.DB_Table,null,values);

        Log.d("db","values inserted");
        db.close();
    }

    //In the below code we will fetch data from the database
    //and return it in the form of list
    public List<Data> GetContacts(){
        List<Data> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //generate query to get data
        String select = "SELECT * FROM " + params.DB_Table;
        Cursor cursor = db.rawQuery(select,null);

        //implement loop to get data from SQLite and add them in data List
        if(cursor.moveToFirst()){
            do{
                Data data = new Data();
                dataList.add(new Data(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(5),cursor.getString(4),cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        return dataList;

    }

    //In the below code we will delete data from the database
    // using a variable used in the database.
    public void Delete_Contact(String number){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(params.DB_Table, params.KEY_NUMBER + "=?", new String[]{number});
        Log.d("db","contact with number " +  number + "has been deleted");
        db.close();
    }

    //In the below code we will update data from the database
    // using a variable used in the database(phone number in this case).
    public void Update_Contact(String name,String number,String email,String number2,String number3 ,String o_number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(params.KEY_NAME,name);
        values.put(params.KEY_NUMBER,number);
        values.put(params.KEY_EMAIL,email);
        values.put(params.KEY_NUMBER2,number2);
        values.put(params.KEY_NUMBER3,number3);
        db.update(params.DB_Table, values, params.KEY_NUMBER + "=?", new String[]{o_number});
        Log.d("db","contact with number " +  o_number + "has been updated with values " + name + " "+ number + " "+ email);
        db.close();
    }

}
