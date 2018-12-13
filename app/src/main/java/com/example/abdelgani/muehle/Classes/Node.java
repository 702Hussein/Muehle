package com.example.abdelgani.muehle.Classes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class Node extends View{
	private int millHelper = 222;
	private ArrayList<Node> neighbourNodes = new ArrayList<Node>(4);
	private boolean occupied = false;

	public Node(Context context) {
		super(context);
	}
	public Node(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	public Node(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public Node(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}


	public int getMillHelper() {
		return millHelper;
	}
	public void setMillHelper(int millHelper) {
		this.millHelper = millHelper;
	}

	public ArrayList<Node> getNeighbourNodes() {
		return neighbourNodes;
	}
	public void setNeighbourNodes(ArrayList<Node> neighbourNodes) {
		this.neighbourNodes = neighbourNodes;
	}
	public void setNeighbourNodes(Node... neighbours) {
		this.neighbourNodes.addAll(Arrays.asList(neighbours));
	}

	public boolean isOccupied() {
		return occupied;
	}
	public boolean isFree() {
		return !occupied;
	}
	public void setOccupied(boolean occupied) {
		Log.d("Node",this.toString());
		this.occupied = occupied;
	}


}
