package com.square.calendar;

import android.util.Log;

import java.util.Random;

public class Event {
	private String id;
	private String title;
	private String beginDate;
	private String beginTime;
	private String endDate;
	private String endTime;
	private String description;
public static int randInt(int min, int max){
    Random rand=new Random();
    int randomNum = rand.nextInt((max-min)+1)+min;
    return randomNum;
}
	public Event(){
		this.id=""+randInt(0,1000000000);
		this.title="";
		this.beginDate="";
		this.endDate="";
		this.beginTime="";
		this.endTime="";
		this.description="";
		Log.e("vv", "contructor"+this.id);
	}
	public Event(String id){
		this.id=id;
	}
	public Event(String id, String title, String beginDate,String beginTime, String endDate, String endTime, String description){
        this.id=id;
		this.title=title;
		this.beginDate=beginDate;
		this.beginTime=beginTime;
		this.endDate=endDate;
		this.endTime=endTime;
		this.description=description;
	}
	public Event( String title, String beginDate,String beginTime, String endDate, String endTime, String description){
        this.id=""+randInt(0,1000000000);
		this.title=title;
		this.beginDate=beginDate;
		this.beginTime=beginTime;
		this.endDate=endDate;
		this.endTime=endTime;
		this.description=description;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
        return beginDate+"-"+beginTime+"-->"+endDate+"-"+endTime+":"+title+"/n"+description;
	}
}