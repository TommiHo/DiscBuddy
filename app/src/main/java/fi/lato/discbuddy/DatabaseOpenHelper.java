package fi.lato.discbuddy;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "LaTo_database";
    private static final int DATABASE_VERSION = 20;

    // Table Names
    private final String TABLE_PLAYERS = "players";
    private final String TABLE_COURSES = "courses";
    private final String TABLE_HIGHSCORES = "highscores";

    // Player Table Columns
    private final String KEY_PLAYER_NAME = "name";

    //Course Table Columns
    private final String KEY_COURSE_NAME = "name";
    private final String KEY_COURSE_PAR = "par";
    private final String KEY_COURSE_HOLECOUNT = "holeCount";

    // highscore table columns
    private final String KEY_HIGHSCORES_SCORE = "score";



    public DatabaseOpenHelper(Context context) {
        // Context, database name, optional cursor factory, database version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYERS_TABLE = "CREATE TABLE "+ TABLE_PLAYERS +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_PLAYER_NAME +" TEXT);";
        String CREATE_COURSES_TABLE = "CREATE TABLE "+ TABLE_COURSES +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_COURSE_NAME +" TEXT, " + KEY_COURSE_PAR+" INTEGER, "
                + KEY_COURSE_HOLECOUNT + " INTEGER);";
        String CREATE_HIGHSCORES_TABLE = "CREATE TABLE "+ TABLE_HIGHSCORES +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_HIGHSCORES_SCORE +" INTEGER);";
        // create new tables
        db.execSQL(CREATE_PLAYERS_TABLE);
        db.execSQL(CREATE_COURSES_TABLE);
        db.execSQL(CREATE_HIGHSCORES_TABLE);
        // create sample data
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME, "Mauno Koivisto");
        // insert data to database, name of table, "Nullcolumnhack", values
        db.insert(TABLE_PLAYERS, null, values);
        // a more data...
        values.put(KEY_PLAYER_NAME, "Tommi Honkonen");
        db.insert(TABLE_PLAYERS, null, values);

        values.put(KEY_COURSE_NAME, "Laajavuori PRO");
        values.put(KEY_COURSE_PAR, 54);
        values.put(KEY_COURSE_HOLECOUNT, 18);
        db.insert(TABLE_COURSES, null, values);

        values.put(KEY_COURSE_NAME, "Keljonkangas");
        values.put(KEY_COURSE_HOLECOUNT, 9);
        db.insert(TABLE_COURSES, null, values);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COURSES);
        onCreate(db);
    }
}
