package com.example.abdelgani.muehle.Classes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class Node extends View{
	private int[] millHelper = new int[3];
	private ArrayList<Node> neighbourNodes = new ArrayList<>(4);
	private boolean occupied = false;
	private Tile currentTile;

	public Node(Context context) {
		super(context);
	}
	public Node(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	public Node(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {	super(context, attrs, defStyleAttr);}
	public Node(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {	super(context, attrs, defStyleAttr, defStyleRes);}

	public int[] getMillHelper() {
		return millHelper;
	}
	public void setMillHelper(int[] millHelper) {
		this.millHelper = millHelper;
	}

	public ArrayList<Node> getNeighbourNodes() {
		return neighbourNodes;
	}
	public void setNeighbourNodes(Node... neighbours) {	this.neighbourNodes.addAll(Arrays.asList(neighbours));}

	public boolean isOccupied() {
		return occupied;
	}
	public boolean isFree() {
		return !occupied;
	}
	public Node setOccupied(boolean occupied) {	Log.d("Node",this.toString());	this.occupied = occupied; return this; }

	public void setCurrentTile(Tile newTile){currentTile = newTile;}
	public Tile getCurrentTile(){return currentTile;}

	public int[] get3rdNodeIndex(Node second){
		int[] index = new int[this.getMillHelper().length];
		for(int i = 0; i < this.getMillHelper().length; i++){
			int tmp = this.getMillHelper()[i] - second.getMillHelper()[i];
			if(tmp == 0)
				index[i] = this.getMillHelper()[i];
			else {
				tmp += this.getMillHelper()[i];
				if (tmp < 0)
					tmp += 3;
				index[i] = tmp % 3;
			}
		}
		return  index;
	}
}
