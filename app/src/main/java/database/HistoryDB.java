package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDB extends SQLiteOpenHelper {
    private Context context;
    private static String DB_NAME="history";
    private static int VERSION=1;
    private static HistoryDB instance;

    private HistoryDB(Context context)
    {
        super(context,DB_NAME,null,VERSION);
    }
    public static synchronized HistoryDB getInstance(Context context)
    {
        if (instance==null)
        {
            instance=new HistoryDB(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table historylist(id int, expression varchar(20),result varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
