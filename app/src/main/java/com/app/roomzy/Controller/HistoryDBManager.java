package com.app.roomzy.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.roomzy.Models.Room;

import java.util.ArrayList;

public class HistoryDBManager {
    private DatabaseHelper dbHelper;

    public HistoryDBManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addRoomToViewed(Room room) {
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

            db.insert(DatabaseHelper.TABLE_VIEWED, null, values);
            db.close();
        }
    }


    @SuppressLint("Range")
    public ArrayList<Room> getAllViewedRooms() {
        ArrayList<Room> roomList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_VIEWED;
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

    public void updateViewedRoom(Room room, int id) {
        if (checkIsDataAlreadyInDBorNot(room.getId())) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
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

            db.update(DatabaseHelper.TABLE_VIEWED, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{room.getId()});
            db.close();
        }
    }
    @SuppressLint("Range")
    public boolean checkIsDataAlreadyInDBorNot(String roomId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_VIEWED + " WHERE " + DatabaseHelper.COLUMN_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{roomId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    @SuppressLint("Range")
    public boolean CheckIsDataAlreadyInDBorNot(String pid) {

        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String Query = "select * from "+ DatabaseHelper.TABLE_VIEWED ;

        Cursor cursor = db.rawQuery(Query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            if (pid.equalsIgnoreCase( cursor.getString(cursor.getColumnIndex("id")))){
                cursor.close();
                return true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }
    public void deleteRow(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from "+ DatabaseHelper.TABLE_VIEWED);
    }

    public void removeRoomFromViewed(String roomId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_VIEWED, DatabaseHelper.COLUMN_ID + "=?", new String[]{roomId});
        db.close();
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
