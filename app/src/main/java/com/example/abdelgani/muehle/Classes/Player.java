package com.example.abdelgani.muehle.Classes;

public class Player
{
	private String name;
	private Tile[] tiles;
	private boolean active = true;
	private int tilesInHand = 9;
	private boolean isWhite = true;

	public Player (String name)
	{
		this.name = name;
	}

	public String getName(){ return this.name; }

	public void setTiles(Tile[] tiles){	this.tiles = tiles;	}
	public Tile[] getTiles(){ return this.tiles;}

	public void setActive(boolean active){ this.active = active; }
	public boolean isActive() {	return active;	}

	public void decreaseTilesInHand() { this.tilesInHand--; }
	public int getTilesInHand() { return this.tilesInHand; }

	public void setPlayerWhite(boolean isWhite) { this.isWhite = isWhite; }
	public boolean isPlayerWhite() { return isWhite; }
}
