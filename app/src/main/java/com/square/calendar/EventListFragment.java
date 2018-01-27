package com.square.calendar;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrateur on 02/07/13.
 */
public class EventListFragment extends Fragment  {

   public interface OnEventSelectedListener
   {
       public void onEventSelected(Event event);
   }

    private OnEventSelectedListener onEventSelectedListener;
    private ListView eventListListView;
    private ArrayList<Event> items ;
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflater
        final LayoutInflater _inflater = inflater;

        //set content view
        View view = inflater.inflate(R.layout.event_list,null);

        //binding
        bind(view);

        // retrieve event list
       new AsyncTask<Void, Void, ArrayList<Event>>(){
           ProgressDialog dialog=null;
           @Override
           protected void onPreExecute() {
               dialog = new ProgressDialog(getActivity());
               dialog.setTitle("Download in progress");
               dialog.setMessage("Please wait ...");
               dialog.setCancelable(false);
               dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
               dialog.show();
           }

           @Override
           protected ArrayList<Event> doInBackground(Void... voids) {
               try {
                return new ArrayList(EventProvider.getEvents(getActivity()));
               } catch (IOException e) {
                   e.printStackTrace();return null;
               } catch (SAXException e) {
                   e.printStackTrace();return null;
               }
           }

           @Override
           protected void onPostExecute(ArrayList<Event> events) {
               dialog.dismiss();
               items=events;
               //refresh
               refresh(items,_inflater);
           }
       }.execute();

        //onItemClickListener
        this.eventListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Log.e("vv", "before setting the item"+items.get(i).getTitle());
                onEventSelectedListener.onEventSelected(items.get(i));
            }
        });
        button = (Button)view.findViewById(R.id.buttonPlus);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                onEventSelectedListener.onEventSelected(new Event());

            }

        });
        return view;

    }

    public void refresh(ArrayList<Event> tracks,final LayoutInflater lyInf){

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int i) {
                return items.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Event e = (Event) getItem(i);
                final LayoutInflater lf = lyInf;
                LinearLayout cell = (LinearLayout) lf.inflate(R.layout.list_cell, null);
                TextView titleTextView = (TextView)cell.findViewById(R.id.firstline);
                TextView timeTextView = (TextView)cell.findViewById(R.id.secondline);
                TextView descriptionTextView = (TextView)cell.findViewById(R.id.descriptionTextView);
                titleTextView.setText(e.getTitle());
                timeTextView.setText(e.getBeginDate()+"-"+e.getBeginTime()+ "-->"+e.getEndDate()+"-"+e.getEndTime()+":");
                descriptionTextView.setText(e.getDescription());
                return cell;
            }
        };

        eventListListView.setAdapter(adapter);
    }

    public void bind(View v){
        this.eventListListView = (ListView) v.findViewById(R.id.eventListListView);
    }

    public void setOnEventSelectedListener(OnEventSelectedListener listener)
    {
        this.onEventSelectedListener=listener;
    }
}
