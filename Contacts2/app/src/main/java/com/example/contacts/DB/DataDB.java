package com.example.contacts.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class DataDB {
        @PrimaryKey(autoGenerate = true)
        public int uid;

        @ColumnInfo(name = "Name")
        public String name;

        @ColumnInfo(name = "Ph_no")
        public String p_no;

        @ColumnInfo(name = "Ph_no1")
        public String p_no1;

        @ColumnInfo(name = "Ph_no2")
        public String p_no2;

        @ColumnInfo(name = "Email")
        public String email;


}
