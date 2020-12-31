package com.example.carapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {
    public static final String DB_Table = "car";
    public static final String DB_Name = "car.db";
    public static final int DB_Version = 1;
    public static final String DB_Id = "id";
    public static final String DB_Model = "model";
    public static final String DB_Color = "color";
    public static final String DB_Description = "description";
    public static final String DB_Image = "image";
    public static final String DB_Dbl = "distancePerLetter";


    public MyDataBase(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

       // db.execSQL("DROP TABLE IF EXISTS DB_Table");
        db.execSQL("CREATE TABLE " + DB_Table + " (" + DB_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
               DB_Model + " TEXT, " + DB_Color + " TEXT, " + DB_Description + " TEXT," +
               DB_Image + " TEXT, " + DB_Dbl + " REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DB_Table");
        onCreate(db);

    }
    public void clear(ArrayList<Cars>allcar){
        SQLiteDatabase db = getWritableDatabase();
        for(int i=0 ;i<allcar.size();i++)
        {
            String args[] = {String.valueOf(allcar.get(i).getId())};
            int result = db.delete(DB_Table, "id=?", args);

        }


    }

    public boolean insertRow(Cars car) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_Model, car.getModel());
        values.put(DB_Color, car.getColor());
        values.put(DB_Description, car.getDescription());
        values.put(DB_Image, car.getImage());
        values.put(DB_Dbl, car.getDbl());
        long result = db.insert(DB_Table, null, values);
        return result != -1;

    }

    public boolean updateRow(Cars car) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_Model, car.getModel());
        values.put(DB_Color, car.getColor());
        values.put(DB_Description, car.getDescription());
        values.put(DB_Image, car.getImage());
        values.put(DB_Dbl, car.getDbl());
        String args[] = {String.valueOf(car.getId())};
        int result = db.update(DB_Table, values, "id=?", args);
        return result > 0;

    }

    public long getcarcount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, DB_Table);
    }

    public boolean deleteRow(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {String.valueOf(id)};
        int result = db.delete(DB_Table, "id=?", args);
        return result > 0;

    }

    public ArrayList<Cars> getallcars() {
        ArrayList<Cars> allcars = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_Table, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DB_Id));
                String model = c.getString(c.getColumnIndex(DB_Model));
                String color = c.getString(c.getColumnIndex(DB_Color));
                String description = c.getString(c.getColumnIndex(DB_Description));
                String image = c.getString(c.getColumnIndex(DB_Image));
                double dbl = c.getDouble(c.getColumnIndex(DB_Dbl));
                Cars car = new Cars(id, model, color, image, description, dbl);
                allcars.add(car);
            }
            while (c.moveToNext());
            c.close();
        }
        return allcars;
    }
    public Cars getcars(int Id) {
        Cars allcars ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_Table+" WHERE "+DB_Id+"=?",new String[]{String.valueOf(Id)});
        if (c.moveToFirst()) {

                int id = c.getInt(c.getColumnIndex(DB_Id));
                String model = c.getString(c.getColumnIndex(DB_Model));
                String color = c.getString(c.getColumnIndex(DB_Color));
                String description = c.getString(c.getColumnIndex(DB_Description));
                String image = c.getString(c.getColumnIndex(DB_Image));
                double dbl = c.getDouble(c.getColumnIndex(DB_Dbl));
                Cars car = new Cars(id, model, color, image, description, dbl);

            c.close();
            return car;
        }
        return null;
    }
    public ArrayList<Cars> search(String model_name) {
        ArrayList<Cars> allcars = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_Table+" WHERE "+DB_Model+"=?" , new String []{model_name} );
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DB_Id));
                String model = c.getString(c.getColumnIndex(DB_Model));
                String color = c.getString(c.getColumnIndex(DB_Color));
                String description = c.getString(c.getColumnIndex(DB_Description));
                String image = c.getString(c.getColumnIndex(DB_Image));
                double dbl = c.getDouble(c.getColumnIndex(DB_Dbl));
                Cars car = new Cars(id, model, color, image, description, dbl);
                allcars.add(car);
            }
            while (c.moveToNext());
            c.close();
        }
        return allcars;
    }

}
