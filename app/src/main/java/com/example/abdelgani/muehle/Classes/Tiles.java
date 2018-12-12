package com.example.abdelgani.muehle.Classes;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class Tiles extends Button {
	private Nodes currentNode;
	private boolean inMill = false;
	private boolean playerWhite;

	public Tiles(Context context) {
		super(context);
	}
	public Tiles(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public Tiles(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public Tiles(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void setCurrentNode(Nodes current){this.currentNode = current;}
	public Nodes getCurrentNode() {return currentNode;}

	public boolean nodeIsNeighbour(Nodes node){
		if ( this.getCurrentNode() != null )
			return this.getCurrentNode().getNeighbourNodes().contains(node);
		else
			return true;
	}

	public void setInMill(boolean value){inMill = value;}
	public boolean isInMill(){return inMill;}
}
