package com.bank.gestionstock.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    String articlesTable = "articles";
    String operationsTable =   "operations";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "gestionStock",null,version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE articles(article_id INTEGER PRIMARY KEY AUTOINCREMENT, libelle TEXT NOT NULL , qte INTEGER NOT NULL,price REAL NOT NULL)");
        db.execSQL("CREATE TABLE operations(operation_id INTEGER PRIMARY KEY AUTOINCREMENT, articleId INTEGER NOT NULL  , qte INTEGER NOT NULL,date TEXT NOT NULL, FOREIGN KEY(articleId) REFERENCES articles(article_id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS articles");
        db.execSQL("DROP TABLE  IF EXISTS operations");
        onCreate(db);
    }

    public ArrayList<Article> getArticles(){
        ArrayList<Article> articles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor     = db.rawQuery("SELECT * FROM "+articlesTable,null);
        while (cursor.moveToNext() ){
            articles.add(new Article(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getDouble(3)));

        }
        cursor.close();
     return articles;
    }

    public long addArticle(Article article){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues.put("libelle",article.getLibelle());
        contentValues.put("qte",article.getQuantite());
        contentValues.put("price",article.getPrice());
        try {

            return  db.insertOrThrow(articlesTable, null, contentValues);

        }catch(Exception ex){

            return 0;
        }

    }


    public ArrayList<Operation> getOperations(){
        ArrayList<Operation> transactions = new ArrayList<Operation>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor     = db.rawQuery("SELECT operation_id,libelle,articleId,operations.qte,date FROM "+ operationsTable + " INNER JOIN " + articlesTable + " WHERE articles.article_id = operations.articleId ",null);

        while (cursor.moveToNext() ){
            transactions.add(new Operation(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4)));
        }
        cursor.close();
        return transactions;
    }

    public long addOperation(Operation operation,ContentValues article){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues.put("articleId",operation.getArticleId());
        contentValues.put("qte",operation.getQuantite());
        contentValues.put("date",operation.getDate());
        try {
             long status = db.insertOrThrow(operationsTable, null, contentValues);
             db.update(articlesTable,article,"article_id = ?",new String[]{String.valueOf(operation.getArticleId())});
            return status;

        }catch(Exception ex){

            return 0;
        }

    }
}
