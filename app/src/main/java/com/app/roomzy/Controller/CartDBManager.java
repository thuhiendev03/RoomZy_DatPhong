package com.app.roomzy.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.roomzy.Models.Room;

import java.util.ArrayList;

public class CartDBManager {
    private DatabaseHelper dbHelper;

    public CartDBManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addRoomToCart(Room room) {
        if (!checkIsDataAlreadyInDBorNot(room.getId())) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ID, room.getId());
            values.put(DatabaseHelper.COLUMN_NAME, room.getName());
            values.put(DatabaseHelper.COLUMN_ADDRESS, room.getAddress());
            values.put(DatabaseHelper.COLUMN_IMAGE_URL, room.getImageURL());
            values.put(DatabaseHelper.COLUMN_PRICE, room.getPrice());
            values.put(DatabaseHelper.COLUMN_RATE, room.getRate());
            values.put(DatabaseHelper.COLUMN_TYPE, room.getType());
            values.put(DatabaseHelper.COLUMN_SUB_IMAGES, room.getSubImages().toString());
            values.put(DatabaseHelper.COLUMN_SUB_ROOMS, room.getSubRooms().toString());
            values.put(DatabaseHelper.COLUMN_DESCRIPTION, room.getDescription());
            values.put(DatabaseHelper.COLUMN_LOCATION_ID, room.getLocationId());
            values.put(DatabaseHelper.COLUMN_CATEGORIES_ID, room.getCategoriesId());

            db.insert(DatabaseHelper.TABLE_CART, null, values);
            return  true;

        }
        return  false;
    }

    @SuppressLint("Range")
    public ArrayList<Room> getAllCartRooms() {
        ArrayList<Room> roomList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_CART;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                room.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                room.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS)));
                room.setImageURL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URL)));
                room.setPrice(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE)));
                room.setRate(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_RATE)));
                room.setType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TYPE)));
                room.setSubImages(stringToList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_IMAGES))));
                room.setSubRooms(stringToList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_ROOMS))));
                room.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)));
                room.setLocationId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION_ID)));
                room.setCategoriesId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORIES_ID)));
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    public boolean removeRoomFromCart(String roomId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.COLUMN_ID + "=?", new String[]{roomId});
        db.close();
        return  true;
    }

    public boolean checkIsDataAlreadyInDBorNot(String roomId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CART + " WHERE " + DatabaseHelper.COLUMN_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{roomId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;

    }
    public boolean deleteRow(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from "+ DatabaseHelper.TABLE_CART);
        return  true;
    }
    private ArrayList<String> stringToList(String str) {
        str = str.replace("[", "").replace("]", "").replace(" ", "");
        String[] items = str.split(",");
        ArrayList<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item);
        }
        return list;
    }
}
