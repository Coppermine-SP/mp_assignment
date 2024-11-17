package corp.cloudint.chapter11_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student_database_utf8.db";
    private static String DATABASE_PATH;
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";
    private static final String COL_ID = "index_id";
    private static final String COL_NAME = "name";
    private static final String COL_STUDENT_ID = "student_id";

    private Context context;
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
        copyDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        /*
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_STUDENT_ID + " TEXT)";

        db.execSQL(create);
         */
    }

    private void copyDatabase(){
        try{
            File file = new File(DATABASE_PATH);
            if(!file.exists()){
                InputStream input = context.getAssets().open(DATABASE_NAME);
                OutputStream output = new FileOutputStream(DATABASE_PATH);

                byte[] buf = new byte[1024];
                int len;
                while((len = input.read(buf)) > 0) {
                    output.write(buf, 0, len);
                }
                output.flush();
                output.close();
                input.close();

                Log.d("DatabaseHelper", "Database copied.");
            }
        }
        catch(Exception e){
            Log.e("DatabaseHelper", "copyDatabase Exception!\n" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String name, String studentId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_STUDENT_ID, studentId);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT index_id AS _id, name, student_id FROM " + TABLE_NAME, null);
    }

    public void updateData(long id, String name, String studentId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_STUDENT_ID, studentId);
        db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteData(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
