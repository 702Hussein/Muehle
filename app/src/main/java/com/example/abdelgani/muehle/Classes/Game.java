package com.example.abdelgani.muehle.Classes;

public class Game {
	private Player owner;
	private Tile[] tiles;
	private int tilesInHand = 9;
	private final Node[][][] nodes;



	public Game(Node[][][] nodes, Tile[] playerTiles){
		this.nodes = nodes;
		this.tiles = playerTiles;
	}
	public Game(Node[][][] nodes, Tile[] playerTiles, Player player){
		this.nodes = nodes;
		this.tiles = playerTiles;
		this.owner = player;
	}


}
