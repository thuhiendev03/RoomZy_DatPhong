package com.app.roomzy.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "roomzy.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_CART = "cart";
    public static final String TABLE_VIEWED = "viewed";

    // Common column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_IMAGE_URL = "imageURL";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SUB_IMAGES = "subImages";
    public static final String COLUMN_SUB_ROOMS = "subRooms";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LOCATION_ID = "locationId";
    public static final String COLUMN_CATEGORIES_ID = "categoriesId";

    // Create table statements
    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + " (" +
            COLUMN_ID + " TEXT PRIMARY KEY," +
            COLUMN_NAME + " TEXT," +
            COLUMN_ADDRESS + " TEXT," +
            COLUMN_IMAGE_URL + " TEXT," +
            COLUMN_PRICE + " INTEGER," +
            COLUMN_RATE + " INTEGER," +
            COLUMN_TYPE + " TEXT," +
            COLUMN_SUB_IMAGES + " TEXT," +
            COLUMN_SUB_ROOMS + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_LOCATION_ID + " TEXT," +
            COLUMN_CATEGORIES_ID + " TEXT" +
            ")";

    private static final String CREATE_TABLE_VIEWED = "CREATE TABLE " + TABLE_VIEWED + " (" +
            COLUMN_ID + " TEXT PRIMARY KEY," +
            COLUMN_NAME + " TEXT," +
            COLUMN_ADDRESS + " TEXT," +
            COLUMN_IMAGE_URL + " TEXT," +
            COLUMN_PRICE + " INTEGER," +
            COLUMN_RATE + " INTEGER," +
            COLUMN_TYPE + " TEXT," +
            COLUMN_SUB_IMAGES + " TEXT," +
            COLUMN_SUB_ROOMS + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_LOCATION_ID + " TEXT," +
            COLUMN_CATEGORIES_ID + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_VIEWED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIEWED);
        onCreate(db);
    }
}
