package com.esprit.wellnest.bdconfiguration;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, "test1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table userdetails(username TEXT primary key, password TEXT, addmail TEXT, nom TEXT, prenom TEXT, role TEXT, image_data TEXT)");

        DB.execSQL("create table demandefournisseur(titre TEXT primary key, username TEXT, adresse TEXT, domaineDescription TEXT, activiteInfo TEXT,status TEXT)");
        DB.execSQL("create table produits(nom  TEXT primary key, username TEXT, marque TEXT, prix  TEXT, quantite  TEXT)");

        DB.execSQL("create table reclamations(titrereclamation TEXT primary key , username TEXT, titre_produit TEXT, description TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists userdetails");
        DB.execSQL("drop table if exists demandefournisseur");
        DB.execSQL("drop table if exists produits");
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



    public Boolean insertDemandeFournisseur(String titre, String adresse, String domaineDescription, String activiteInfo, String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("titre", titre);
        contentValues.put("adresse", adresse);
        contentValues.put("domaineDescription", domaineDescription);
        contentValues.put("activiteInfo", activiteInfo);
        contentValues.put("username", username);
        contentValues.put("status", "En attente");

        long result = DB.insert("demandefournisseur", null, contentValues);
        DB.close();
        return result != -1;
    }




    public List<String> getAllDemandes() {
        List<String> demandesList = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT titre FROM demandefournisseur", null);

        while (cursor.moveToNext()) {
            String titre = cursor.getString(cursor.getColumnIndex("titre"));
            demandesList.add(titre);
        }

        cursor.close();
        DB.close();
        return demandesList;
    }



    public Map<String, String> getDemandeDetails(String titre) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Map<String, String> demandeDetails = new HashMap<>();

        Cursor cursor = DB.rawQuery("SELECT * FROM demandefournisseur WHERE titre=?", new String[]{titre});

        if (cursor.moveToFirst()) {
            demandeDetails.put("titre", cursor.getString(cursor.getColumnIndex("titre")));
            demandeDetails.put("adresse", cursor.getString(cursor.getColumnIndex("adresse")));
            demandeDetails.put("domaineDescription", cursor.getString(cursor.getColumnIndex("domaineDescription")));
            demandeDetails.put("activiteInfo", cursor.getString(cursor.getColumnIndex("activiteInfo")));
            demandeDetails.put("status", cursor.getString(cursor.getColumnIndex("status")));
        }

        cursor.close();
        DB.close();
        return demandeDetails;
    }

//    public Map<String, String> getProductdetails(String nom) {
//        SQLiteDatabase DB = this.getReadableDatabase();
//        Map<String, String> produit = new HashMap<>();
//
//        Cursor cursor = DB.rawQuery("SELECT * FROM produits WHERE nom=?", new String[]{nom});
//
//        if (cursor.moveToFirst()) {
//            produit.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
//            produit.put("marque", cursor.getString(cursor.getColumnIndex("marque")));
//            produit.put("prix", cursor.getString(cursor.getColumnIndex("prix")));
//            produit.put("quantite", cursor.getString(cursor.getColumnIndex("quantite")));
//        }
//
//        cursor.close();
//        DB.close();
//        return produit;
//    }
//




    public boolean accepterDemande(String titre) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", "Accepté");
        int result = DB.update("demandefournisseur", contentValues, "titre=?", new String[]{titre});
        DB.close();
        return result != -1;
    }

    public boolean refuserDemande(String titre) {
        SQLiteDatabase DB = this.getWritableDatabase();
        int result = DB.delete("demandefournisseur", "titre=?", new String[]{titre});
        DB.close();
        return result != -1;
    }


    public String getFournisseurStatus(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        String status = null;

        Cursor cursor = DB.rawQuery("SELECT status FROM demandefournisseur WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndex("status"));
        }

        cursor.close();
        return status;
    }


    public Map<String, String> DemandeByUser(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Map<String, String> demande = new HashMap<>();

        Cursor cursor = DB.rawQuery("SELECT * FROM demandefournisseur WHERE username=?", new String[]{username});

        if (cursor.moveToFirst()) {
            demande.put("titre", cursor.getString(cursor.getColumnIndex("titre")));
            demande.put("adresse", cursor.getString(cursor.getColumnIndex("adresse")));
            demande.put("domaineDescription", cursor.getString(cursor.getColumnIndex("domaineDescription")));
            demande.put("activiteInfo", cursor.getString(cursor.getColumnIndex("activiteInfo")));
            demande.put("status", cursor.getString(cursor.getColumnIndex("status")));
        }

        cursor.close();
        DB.close();
        return demande;
    }


    public List<Map<String, String>> affichedemandes() {
        List<Map<String, String>> demandeList = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM demandefournisseur", null);

        while (cursor.moveToNext()) {
            Map<String, String> demandeMap = new HashMap<>();
            demandeMap.put("titre", cursor.getString(cursor.getColumnIndex("titre")));
            demandeMap.put("adresse", cursor.getString(cursor.getColumnIndex("adresse")));
            demandeMap.put("domaineDescription", cursor.getString(cursor.getColumnIndex("domaineDescription")));
            demandeMap.put("activiteInfo", cursor.getString(cursor.getColumnIndex("activiteInfo")));
            demandeMap.put("status", cursor.getString(cursor.getColumnIndex("status")));

            demandeList.add(demandeMap);
        }

        cursor.close();
        DB.close();
        return demandeList;
    }
    public List<Map<String, String>> getfournisseurproduits(String username) {
        List<Map<String, String>> produitsList = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM produits WHERE username=?", new String[]{username});

        while (cursor.moveToNext()) {
            Map<String, String> produitMap = new HashMap<>();
            produitMap.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            produitMap.put("marque", cursor.getString(cursor.getColumnIndex("marque")));
            produitMap.put("prix", cursor.getString(cursor.getColumnIndex("prix")));
            produitMap.put("quantite", cursor.getString(cursor.getColumnIndex("quantite")));

            produitsList.add(produitMap);
        }

        cursor.close();
        DB.close();
        return produitsList;
    }







    public List<Map<String, String>> getAllProducts() {
        List<Map<String, String>> produitsList = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM produits", null);

        while (cursor.moveToNext()) {
            Map<String, String> produitMap = new HashMap<>();
            produitMap.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            produitMap.put("marque", cursor.getString(cursor.getColumnIndex("marque")));
            produitMap.put("prix", cursor.getString(cursor.getColumnIndex("prix")));
            produitMap.put("quantite", cursor.getString(cursor.getColumnIndex("quantite")));

            produitsList.add(produitMap);
        }

        cursor.close();
        DB.close();
        return produitsList;
    }


    public List<Map<String, String>>getAllreclamations() {
        List<Map<String, String>> reclamations = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM reclamations" ,null);

        while (cursor.moveToNext()) {
            Map<String, String> reclamationmap = new HashMap<>();
            reclamationmap.put("titrereclamation", cursor.getString(cursor.getColumnIndex("titrereclamation")));
            reclamationmap.put("description", cursor.getString(cursor.getColumnIndex("description")));
            reclamationmap.put("username", cursor.getString(cursor.getColumnIndex("username")));
            reclamationmap.put("titre_produit", cursor.getString(cursor.getColumnIndex("titre_produit")));


            reclamations.add(reclamationmap);
        }

        cursor.close();
        DB.close();
        return reclamations;
    }








    public boolean insererReclamation(String username, String titre_produit, String description, String titrereclamation) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("titre_produit", titre_produit);
        contentValues.put("description", description);
        contentValues.put("titrereclamation", titrereclamation);

        long result = DB.insert("reclamations", null, contentValues);
        DB.close();
        return result != -1;
    }




    public List<Map<String, String>>getReclamationsByUser(String username) {
        List<Map<String, String>> reclamations = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM reclamations WHERE username=?", new String[]{username});

        while (cursor.moveToNext()) {
            Map<String, String> reclamationmap = new HashMap<>();
            reclamationmap.put("titrereclamation", cursor.getString(cursor.getColumnIndex("titrereclamation")));
            reclamationmap.put("description", cursor.getString(cursor.getColumnIndex("description")));


            reclamations.add(reclamationmap);
        }

        cursor.close();
        DB.close();
        return reclamations;
    }

    public boolean updateproduit( String username,String nomProduit1,String marqueedit1,String prixedit1,String quantiteedi1) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nomProduit1);
        contentValues.put("marque", marqueedit1);
        contentValues.put("prix", prixedit1);
        contentValues.put("quantite", quantiteedi1);

        int result = DB.update("produits", contentValues, "username=?", new String[]{username});
        DB.close();

        return result != -1;
    }

    public boolean updateDescriptionReclamation( String username,String nouvelleDescription) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", nouvelleDescription);

        int result = DB.update("reclamations", contentValues, "username=?", new String[]{username});
        DB.close();

        return result != -1;
    }


    public boolean supprimerReclamation(String titrereclamation) {
        SQLiteDatabase DB = this.getWritableDatabase();
        int result = DB.delete("reclamations", "titrereclamation=?", new String[]{titrereclamation});
        DB.close();
        return result != -1;
    }




    public boolean deleteproduct(String nom) {
        SQLiteDatabase DB = this.getWritableDatabase();
        int result = DB.delete("produits", "nom=?", new String[]{nom});
        DB.close();
        return result != -1;
    }



    public Boolean insertProduct(String nom, String username, String marque, String prix, String quantite) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nom", nom);
        contentValues.put("marque", marque);
        contentValues.put("prix", prix);
        contentValues.put("quantite", quantite);
        contentValues.put("username", username);

        long result = DB.insert("produits", null, contentValues);
        DB.close();
        return result != -1;
    }








}