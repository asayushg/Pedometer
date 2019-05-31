package saini.ayush.stepcounterandcaloriesburned;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbname="my.db";
    private static final int version = 1;


    public DatabaseHelper(Context context){
        super(context,dbname, null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE RECORD (DATE DATE PRIMARY KEY, STEPS REAL, CALORIES REAL, DISTANCE REAL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String Date, int steps, int calories, int distance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", Date);
        contentValues.put("STEPS", steps);
        contentValues.put("CALORIES",calories);
        contentValues.put("DISTANCE",distance);
        long result = db.insert("RECORD",null,contentValues);
        if(result==-1) return false;
        else return true;
    }
}
