package com.esprit.wellnest.bdconfiguration;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "test1.db", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table userdetails(username TEXT primary key, password TEXT, addmail TEXT, nom TEXT, prenom TEXT, role TEXT, image_data TEXT)");




    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists userdetails");

        onCreate(DB);
    }

    public Boolean insertdata(String username, String password, String addmail, String nom, String prenom, String role, String imageBase64) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("addmail", addmail);
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("role", role);
        contentValues.put("image_data", imageBase64);
        long result = DB.insert("userdetails", null, contentValues);
        DB.close();
        return result != -1;
    }


    public Boolean checkusername(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM userdetails WHERE username=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM userdetails WHERE username=? AND password=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean insertImage(String username, String imageBase64) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image_data", imageBase64);
        int result = DB.update("userdetails", contentValues, "username=?", new String[]{username});
        DB.close();
        return result != -1;
    }

    public String getImageData(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT image_data FROM userdetails WHERE username=?", new String[]{username});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String imageData = cursor.getString(cursor.getColumnIndex("image_data"));
            cursor.close();
            return imageData;
        }
        cursor.close();
        return null;
    }
    public String getUserMail(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT addmail FROM userdetails WHERE username=?", new String[]{username});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String userMail = cursor.getString(cursor.getColumnIndex("addmail"));
            cursor.close();
            return userMail;
        }
        cursor.close();
        return null;
    }


    public String getNom(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT nom FROM userdetails WHERE username=?", new String[]{username});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex("nom"));
            cursor.close();
            return nom;
        }
        cursor.close();
        return null;
    }


    public String getPrenom(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT prenom FROM userdetails WHERE username=?", new String[]{username});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
            cursor.close();
            return prenom;
        }
        cursor.close();
        return null;
    }



    public String getUserRole(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        String role = null;

        Cursor cursor = DB.rawQuery("SELECT role FROM Userdetails WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex("role"));
        }

        cursor.close();
        return role;
    }
    public boolean updateUserProfile(String username, String newEmail, String newNom, String newPrenom) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("addmail", newEmail);
        contentValues.put("nom", newNom);
        contentValues.put("prenom", newPrenom);

        int result = DB.update("userdetails", contentValues, "username=?", new String[]{username});
        DB.close();

        return result != -1;
    }


}
