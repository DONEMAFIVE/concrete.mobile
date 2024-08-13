package ru.zzbo.concretemobile.db.dbStructures;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.zzbo.concretemobile.utils.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "recipe.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private  Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            File sdcard = Environment.getExternalStorageDirectory();
            String dbfile = sdcard.getAbsolutePath() + File.separator + "IP_192.168.250.9" + File.separator + "recipe"  + File.separator;
            DB_PATH = dbfile;
            System.err.println(DB_PATH);
        }
        this.mContext = context;
        copyDataBase();
        this.getReadableDatabase();
    }

    public boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME); // Путь к уже созданной пустой базе в андроиде
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Создаст базу, если она не создана
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            copyDataBase();
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        createDataBase();
        if (mDataBase == null) {
            createDataBase();
            mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return mDataBase;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Добавление нового столбца в таблицу
//            db.execSQL("ALTER TABLE mytable ADD COLUMN age INTEGER DEFAULT 0");
        }
        // Добавляйте дополнительные условия для последующих версий
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }
}