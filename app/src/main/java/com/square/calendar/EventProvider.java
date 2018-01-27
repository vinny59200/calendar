package com.square.calendar;

import android.content.Context;
import android.util.Log;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrateur on 02/07/13.
 */
public class EventProvider {

    static List<Event> getEvents(Context context)throws IOException, SAXException {
        // Log.e("vv", "Start getMusic in provider");
        List<Event> items=new ArrayList<Event>();
        //storage in db
        //Log.e("vv","Getting to storing");
         DBHelper dbh = new DBHelper(context);
         dbh.storeEvent(items);
         items = dbh.getAllEvents();
         return items;
     }



    static void update (Event e, Context c){
        DBHelper dbh= new DBHelper(c);
        dbh.update(e);
    }
}
