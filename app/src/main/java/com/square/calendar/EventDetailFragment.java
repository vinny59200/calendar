package com.square.calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Administrateur on 02/07/13.
 */
public class EventDetailFragment extends Fragment {
    public  static final String MUSIC_TO_EDIT="fetch";
    private EditText idditText;
    private EditText titleEditText;
    private EditText beginDateEditText;
    private EditText beginTimeEditText;
    private EditText endDateEditText;
    private EditText endTimeEditText;
    private Button okBtn;
    private Button cancelBtn;
    private Button deleteBtn;
    private EditText descriptionEditText;
    private Event e1;
    private Event e2;
    private OnCancelListener onCancelListener;
    public interface OnCancelListener{
        public void OnCancelSelected();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //set content view
        View view = inflater.inflate(R.layout.event_detail,null);

        //binding
        bind(view);//set content view

        //retrieve content
        //done by the setter

        //refresh
        if(e1==null)e1=new Event();
        refresh();

        //listeners
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e2 = new Event();
                if( idditText.getText().toString()!=null)
                e2.setId((String) idditText.getText().toString());
                else
                e2.setId("");
                if(titleEditText.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "You did not enter a title", Toast.LENGTH_SHORT).show();

                e2.setTitle((String) titleEditText.getText().toString());
                if(beginDateEditText.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "You did not enter a begin date", Toast.LENGTH_SHORT).show();

                e2.setBeginDate((String) beginDateEditText.getText().toString());

                if(beginTimeEditText.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "You did not enter a begin hour", Toast.LENGTH_SHORT).show();

                e2.setBeginTime((String) beginTimeEditText.getText().toString());

                if(endDateEditText.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "You did not enter an end date", Toast.LENGTH_SHORT).show();

                e2.setEndDate((String) endDateEditText.getText().toString());

                if(endTimeEditText.getText().toString().equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "You did not enter an end hour", Toast.LENGTH_SHORT).show();

                e2.setEndTime((String) endTimeEditText.getText().toString());

                if (descriptionEditText.getText().toString()!=null)
                e2.setDescription((String) descriptionEditText.getText().toString());

                if(new DBHelper(getActivity()).isEventStored(Integer.parseInt(e2.getId()))){
                    EventProvider.update(e2,getActivity());}else{
                    DBHelper dbh =new DBHelper(getActivity());
                    dbh.storeEvent(e2);}
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View view) {
                                             // refresh(t2);
                                             onCancelListener.OnCancelSelected();
                                         }}
        );
        deleteBtn.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View view) {
                                             e2 = new Event();
                                             e2.setId((String)idditText.getText().toString());

                                             // EventProvider.update(e2,getActivity());
                                             DBHelper dbh =new DBHelper(getActivity());
                                             dbh.deleteEvent(e2);
                                         }}
        );


        return view;
    }
    //methods
    public void refresh(){
        idditText.setText(e1.getId());
        titleEditText.setText(e1.getTitle()!=null?e1.getTitle():"");
        beginDateEditText.setText(e1.getBeginDate()!=null?e1.getBeginDate():"");
        beginTimeEditText.setText(e1.getBeginTime()!=null?e1.getBeginTime():"");
        endDateEditText.setText(e1.getEndDate()!=null?e1.getEndDate():"");
        endTimeEditText.setText(e1.getEndTime()!=null?e1.getEndTime():"");
        descriptionEditText.setText(e1.getDescription()!=null?e1.getDescription():"");
    }

    private void bind(View v){
        idditText=(EditText)v.findViewById(R.id.idEditText);
        titleEditText = (EditText) v.findViewById(R.id.titleEditText);
        beginDateEditText =  (EditText) v.findViewById(R.id.editText);
        beginTimeEditText =  (EditText) v.findViewById(R.id.editText3);
        endDateEditText =  (EditText) v.findViewById(R.id.editText2);
        endTimeEditText =  (EditText) v.findViewById(R.id.editText4);
        okBtn =  (Button) v.findViewById(R.id.okBtn);
        cancelBtn = (Button) v.findViewById(R.id.cancelBtn);
        deleteBtn=(Button)v.findViewById(R.id.button);
        descriptionEditText = (EditText) v.findViewById(R.id.descriptionEditText);
    }

    public void setter(Event event){
        this.e1=event;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setOnCancelListener(OnCancelListener listener){
        this.onCancelListener = listener;
    }
}

