package com.example.abdelgani.muehle.Classes;

import android.view.View;

import java.util.ArrayList;

public class Player
{
	private String name;
	private ArrayList<Tile> tiles;
	private boolean active = true;
	private int tilesInHand = 9;
	private boolean isWhite = true;
	private Node startingArea;

	public Player (String name)
	{
		this.name = name;
	}

	public String getName(){ return this.name; }

	public Player setTiles(ArrayList<Tile> tiles){ this.tiles = tiles; return this; }
	public ArrayList<Tile> getTiles(){ return this.tiles;}

	public void setActive(boolean active){ this.active = active; }
	public boolean isActive() {	return active;	}

	public void decreaseTilesInHand() { this.tilesInHand--; }
	public int getTilesInHand() { return this.tilesInHand; }

	public Player setPlayerWhite(boolean isWhite) { this.isWhite = isWhite; return this; }
	public boolean isPlayerWhite() { return isWhite; }

	public Player setStartingArea(Node startingArea){ this.startingArea = startingArea; return this; }
	public Node getStartingArea() { return this.startingArea; }
}
