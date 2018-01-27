package com.square.calendar;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.square.calendar.EventDetailFragment;
import com.square.calendar.EventListFragment;
import com.square.calendar.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends FragmentActivity implements EventListFragment.OnEventSelectedListener, EventDetailFragment.OnCancelListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventListFragment frag = new EventListFragment();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, frag);
        ft.commit();
        frag.setOnEventSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        return true;
    }

    public void onEventSelected(Event selected) {
        EventDetailFragment detail = new EventDetailFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout,detail);
            ft.commit();
            detail.setter(selected);


        detail.setOnCancelListener(this);
    }

    @Override
    public void OnCancelSelected() {
        EventListFragment frag = new EventListFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, frag);
            ft.commit();

        // displayFragments();
        frag.setOnEventSelectedListener(this);
    }

}
