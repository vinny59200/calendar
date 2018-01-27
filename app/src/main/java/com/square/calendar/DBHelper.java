package com.square.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrateur on 03/07/13.
 */
public class DBHelper  extends SQLiteOpenHelper {
    private static final String DB_NAME="calendar";
    private static final SQLiteDatabase.CursorFactory FACTORY = null;
    private static final int  VERSION =2;

    SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context){
        super(context,DB_NAME,FACTORY,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {

            String query = "CREATE TABLE event (id VARCHAR(255) , title VARCHAR(255), " +
                    "begindate VARCHAR(255),  begintime VARCHAR(255), " +
                    "enddate VARCHAR(255),endtime VARCHAR(255), " +
                    "description VARCHAR(1024));";
            //Log.e("vv", ""+query);
            db.execSQL(query);

        }
        catch(Exception e){}

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
        onCreate(db);
    }


    public int storeEvent(List<Event> events){
        db=this.getWritableDatabase();
        // Log.e("vv", "In the storing");
        int insertedTrackCount =0;
        for(Event event: events){
            ContentValues cont =mapEvent2Db(event);
            // Log.e("vv", "Ready to insert "+track.toString());
            // check existence of the element in db and skip if it exists if()
            String[] columns={"id","title",
                    "begindate","begintime",
                    "enddate","endtime",
                    "description"};
            String[] params = {event.getId()};
            Cursor c = db.query("event",columns,"id=?",params,null,null,null);

            int i=c.getCount();
            // Log.e("vv",i+"");
            // db.execSQL("DELETE FROM "+DB_NAME+ " WHERE id='" );

            if(i==0) {
                db.insert("event",null,cont);
                insertedTrackCount++;
            }
            c.close();
        }
        return insertedTrackCount;
    }
    public int storeEvent(Event event){
        db=this.getWritableDatabase();
        // Log.e("vv", "In the storing");
        int insertedTrackCount =0;
        //for(Event event: events){
            ContentValues cont =mapEvent2Db(event);
            // Log.e("vv", "Ready to insert "+track.toString());
            // check existence of the element in db and skip if it exists if()
        String[] columns={"id","title",
                "begindate","begintime",
                "enddate","endtime",
                "description"};
            String[] params = {event.getId()};
            //Cursor c = db.query("event",columns,"id=?",params,null,null,null);

            //int i=c.getCount();
            // Log.e("vv",i+"");
            // db.execSQL("DELETE FROM "+DB_NAME+ " WHERE id='" );

            //if(i==0) {
                db.insert("event",null,cont);
        Log.e("vv", "insert "+event.getId());
                insertedTrackCount++;
            //}
          //  c.close();
        //}
        return insertedTrackCount;
    }

    public void update(Event e){
       String[] params = {e.getId()};
       db=  this.getWritableDatabase();
        Log.e("vv", "update " + e.getId());
       db.update("event", mapEvent2Db(e), "id=?", params);

    }
    public List<Event> getAllEvents(){
        db=this.getReadableDatabase();
        Log.e("vv","Getting it all");
        String[] columns={"id","title",
                "begindate","begintime",
                "enddate","endtime",
                "description"};  //String[] params = {""};

        Cursor c = db.query("event",columns,null,null,null,null,null);
       // Log.e("vv",c.getCount()+" rows");
        c.moveToFirst();
        ArrayList <Event> eventSet = new ArrayList<Event>();

        while(!c.isAfterLast()){
            Event tmp_t= new Event(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
            Log.e("vv","Loading "+c.getString(1));
            eventSet.add(tmp_t);
            c.moveToNext();
        }
        c.close();
        Comparator<Event> beginDateComparator = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if(o1.getBeginDate().equals(o2.getBeginDate())){
                    return o1.getBeginTime().compareTo(o2.getBeginTime());
                }
                return o1.getBeginDate().compareTo(o2.getBeginDate());
            }
        };

         Collections.sort(eventSet, beginDateComparator);

        List<Event> result=new ArrayList<Event>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String strDate = sdf.format(now);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
        String strTime = sdfTime.format(now);
        Log.e("vv","date & time of now"+strDate+"-"+strTime);
        for(Event e:eventSet){
            Log.e("vv","in the sanitizer");
           // if(e.getEndDate().equals(""))continue;
            if(Integer.parseInt(e.getEndDate())>Integer.parseInt(strDate)){
                result.add(e);
                Log.e("vv","adding a greater date"+e.getEndDate()+"-"+e.getEndTime());

            }
            else if(Integer.parseInt(e.getEndDate())==Integer.parseInt(strDate)){
               // if(e.getEndTime().equals(""))continue;
                if(Integer.parseInt(e.getEndTime())>Integer.parseInt(strTime)){
                    result.add(e);
                    Log.e("vv", "adding a greater time"+e.getEndDate()+"-"+e.getEndTime());
                }
            }
            else{
                Log.e("vv","filtering "+e.getTitle()+"-"+e.getEndDate()+"-"+e.getEndTime());
            }
        }
        return result;
    }
    public boolean isEventStored(int id){
        db=this.getReadableDatabase();
        Log.e("vv","Getting it all");
        String[] columns={"id","title",
                "begindate","begintime",
                "enddate","endtime",
                "description"};
        String[] params = {""+id};

        Cursor c = db.query("event",columns,"id=?",params,null,null,null);
        // Log.e("vv",c.getCount()+" rows");
       if(c.getCount()>0){
           Log.e("vv","exist? "+id);
           return true;
       }
        return false;
    }
    public SQLiteDatabase open() {
        db=this.getWritableDatabase();
        return db;
    }

    public void close(){
        db.close();
    }

    private ContentValues mapEvent2Db (Event e){
        ContentValues contV = new ContentValues();
        contV.put("id",e.getId());
        contV.put("title",e.getTitle());
        contV.put("begindate",e.getBeginDate());
        contV.put("begintime",e.getBeginTime());
        contV.put("enddate",e.getEndDate());
        contV.put("endtime", e.getEndTime());
        contV.put("description", e.getDescription());
        return contV;
    }


    public void deleteEvent(Event e2) {
        Log.e("vv","delete");
    String[] params = {e2.getId()};
        Log.e("vv","delete "+e2.getId());
    db=  this.getWritableDatabase();
    db.delete("event","id=?",params);
}

}
