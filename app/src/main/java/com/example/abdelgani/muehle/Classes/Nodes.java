package com.example.abdelgani.muehle.Classes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class Nodes extends View{
	private int millHelper = 222;
	private ArrayList<Nodes> neighbourNodes = new ArrayList<Nodes>(4);
	private boolean occupied = false;

	public Nodes(Context context) {
		super(context);
	}
	public Nodes(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	public Nodes(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public Nodes(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}


	public int getMillHelper() {
		return millHelper;
	}
	public void setMillHelper(int millHelper) {
		this.millHelper = millHelper;
	}

	public ArrayList<Nodes> getNeighbourNodes() {
		return neighbourNodes;
	}
	public void setNeighbourNodes(ArrayList<Nodes> neighbourNodes) {
		this.neighbourNodes = neighbourNodes;
	}
	public void setNeighbourNodes(Nodes[] neighbours) {
		this.neighbourNodes.addAll(Arrays.asList(neighbours));
	}

	public boolean isOccupied() {
		return occupied;
	}
	public boolean isFree() {
		return !occupied;
	}
	public void setOccupied(boolean occupied) {
		Log.d("Nodes",this.toString());
		this.occupied = occupied;
	}


}
