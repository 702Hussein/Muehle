package com.example.abdelgani.muehle.Classes;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.abdelgani.muehle.R;

public class Tile extends Button {
	private Node currentNode;
	//private boolean inMill = false;
	private boolean playerWhite;

	public Tile(Context context) {
		super(context);
	}
	public Tile(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Tile, 0,0);
		try{
			playerWhite = typedArray.getBoolean(R.styleable.Tile_playerWhite, false);
		}finally {
			typedArray.recycle();
		}
	}
	public Tile(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public Tile(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void setCurrentNode(Node current){this.currentNode = current;}
	public Node getCurrentNode() {return currentNode;}

	public boolean nodeIsNeighbour(Node node){
		if ( this.getCurrentNode() != null )
			return this.getCurrentNode().getNeighbourNodes().contains(node);
		else
			return true;
	}

	//public void setInMill(boolean value){inMill = value;}
	//public boolean isInMill(){return inMill;}

	public boolean isPlayerWhite() {return playerWhite;	}
}
