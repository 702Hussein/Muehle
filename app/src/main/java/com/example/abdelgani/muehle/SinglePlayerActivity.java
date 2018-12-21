package com.example.abdelgani.muehle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.example.abdelgani.muehle.Classes.Node;
import com.example.abdelgani.muehle.Classes.Player;
import com.example.abdelgani.muehle.Classes.Tile;

import java.util.ArrayList;

public class SinglePlayerActivity extends AppCompatActivity {

	private static final String TAG = "__________";
	Node[][][] nodes = new Node[3][3][3];
	ArrayList<Tile> whiteTiles = new ArrayList<>(9), blackTiles = new ArrayList<>(9);
	int offset;
	boolean pickTile = false;
	Player[] players = new Player[2];
	Database sqLiteDB ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_board);
		Log.d(TAG, "Singleplayer startup");
		try {
			//region misc
			offset = (int) ((getResources().getDimension(R.dimen.nodeSize) - getResources().getDimension(R.dimen.tileSize) + 0.5) / 2);
			sqLiteDB = new Database(this);
			//endregion
			//region prepare nodes
			nodes[0][0][0] = findViewById(R.id.node_outer_topLeft);
			nodes[0][0][1] = findViewById(R.id.node_outer_topMid);
			nodes[0][0][2] = findViewById(R.id.node_outer_topRight);
			nodes[0][1][0] = findViewById(R.id.node_outer_midLeft);
			nodes[0][1][2] = findViewById(R.id.node_outer_midRight);
			nodes[0][2][0] = findViewById(R.id.node_outer_botLeft);
			nodes[0][2][1] = findViewById(R.id.node_outer_botMid);
			nodes[0][2][2] = findViewById(R.id.node_outer_botRight);
			nodes[1][0][0] = findViewById(R.id.node_middle_topLeft);
			nodes[1][0][1] = findViewById(R.id.node_middle_topMid);
			nodes[1][0][2] = findViewById(R.id.node_middle_topRight);
			nodes[1][1][0] = findViewById(R.id.node_middle_midLeft);
			nodes[1][1][2] = findViewById(R.id.node_middle_midRight);
			nodes[1][2][0] = findViewById(R.id.node_middle_botLeft);
			nodes[1][2][1] = findViewById(R.id.node_middle_botMid);
			nodes[1][2][2] = findViewById(R.id.node_middle_botRight);
			nodes[2][0][0] = findViewById(R.id.node_inner_topLeft);
			nodes[2][0][1] = findViewById(R.id.node_inner_topMid);
			nodes[2][0][2] = findViewById(R.id.node_inner_topRight);
			nodes[2][1][0] = findViewById(R.id.node_inner_midLeft);
			nodes[2][1][2] = findViewById(R.id.node_inner_midRight);
			nodes[2][2][0] = findViewById(R.id.node_inner_botLeft);
			nodes[2][2][1] = findViewById(R.id.node_inner_botMid);
			nodes[2][2][2] = findViewById(R.id.node_inner_botRight);

			nodes[0][0][0].setOnDragListener(dragListenerEarlyGame);
			nodes[0][0][1].setOnDragListener(dragListenerEarlyGame);
			nodes[0][0][2].setOnDragListener(dragListenerEarlyGame);
			nodes[0][1][0].setOnDragListener(dragListenerEarlyGame);
			nodes[0][1][2].setOnDragListener(dragListenerEarlyGame);
			nodes[0][2][0].setOnDragListener(dragListenerEarlyGame);
			nodes[0][2][1].setOnDragListener(dragListenerEarlyGame);
			nodes[0][2][2].setOnDragListener(dragListenerEarlyGame);
			nodes[1][0][0].setOnDragListener(dragListenerEarlyGame);
			nodes[1][0][1].setOnDragListener(dragListenerEarlyGame);
			nodes[1][0][2].setOnDragListener(dragListenerEarlyGame);
			nodes[1][1][0].setOnDragListener(dragListenerEarlyGame);
			nodes[1][1][2].setOnDragListener(dragListenerEarlyGame);
			nodes[1][2][0].setOnDragListener(dragListenerEarlyGame);
			nodes[1][2][1].setOnDragListener(dragListenerEarlyGame);
			nodes[1][2][2].setOnDragListener(dragListenerEarlyGame);
			nodes[2][0][0].setOnDragListener(dragListenerEarlyGame);
			nodes[2][0][1].setOnDragListener(dragListenerEarlyGame);
			nodes[2][0][2].setOnDragListener(dragListenerEarlyGame);
			nodes[2][1][0].setOnDragListener(dragListenerEarlyGame);
			nodes[2][1][2].setOnDragListener(dragListenerEarlyGame);
			nodes[2][2][0].setOnDragListener(dragListenerEarlyGame);
			nodes[2][2][1].setOnDragListener(dragListenerEarlyGame);
			nodes[2][2][2].setOnDragListener(dragListenerEarlyGame);

			nodes[0][0][0].setNeighbourNodes(nodes[0][0][1], nodes[0][1][0]);
			nodes[0][0][1].setNeighbourNodes(nodes[0][0][0], nodes[0][0][2], nodes[1][0][1]);
			nodes[0][0][2].setNeighbourNodes(nodes[0][0][1], nodes[0][1][2]);
			nodes[0][1][0].setNeighbourNodes(nodes[0][0][0], nodes[0][2][0], nodes[1][1][0]);
			nodes[0][1][2].setNeighbourNodes(nodes[0][0][2], nodes[0][2][2], nodes[1][1][2]);
			nodes[0][2][0].setNeighbourNodes(nodes[0][1][0], nodes[0][2][1]);
			nodes[0][2][1].setNeighbourNodes(nodes[0][2][0], nodes[0][2][2], nodes[1][2][1]);
			nodes[0][2][2].setNeighbourNodes(nodes[0][2][1], nodes[0][1][2]);
			nodes[1][0][0].setNeighbourNodes(nodes[1][0][1], nodes[1][1][0]);
			nodes[1][0][1].setNeighbourNodes(nodes[0][0][1], nodes[1][0][0], nodes[1][0][2], nodes[2][0][1]);
			nodes[1][0][2].setNeighbourNodes(nodes[1][0][1], nodes[1][1][2]);
			nodes[1][1][0].setNeighbourNodes(nodes[0][1][0], nodes[1][0][0], nodes[1][2][0], nodes[2][1][0]);
			nodes[1][1][2].setNeighbourNodes(nodes[0][1][2], nodes[1][0][2], nodes[1][2][2], nodes[2][1][2]);
			nodes[1][2][0].setNeighbourNodes(nodes[1][1][0], nodes[1][2][1]);
			nodes[1][2][1].setNeighbourNodes(nodes[0][2][1], nodes[1][2][0], nodes[1][2][2], nodes[2][2][1]);
			nodes[1][2][2].setNeighbourNodes(nodes[1][2][1], nodes[1][1][2]);
			nodes[2][0][0].setNeighbourNodes(nodes[2][0][1], nodes[2][1][0]);
			nodes[2][0][1].setNeighbourNodes(nodes[1][0][1], nodes[2][0][0], nodes[2][0][2]);
			nodes[2][0][2].setNeighbourNodes(nodes[2][0][1], nodes[2][1][2]);
			nodes[2][1][0].setNeighbourNodes(nodes[1][1][0], nodes[2][0][0], nodes[2][2][0]);
			nodes[2][1][2].setNeighbourNodes(nodes[1][1][2], nodes[2][0][2], nodes[2][2][2]);
			nodes[2][2][0].setNeighbourNodes(nodes[2][1][0], nodes[2][2][1]);
			nodes[2][2][1].setNeighbourNodes(nodes[1][2][1], nodes[2][2][0], nodes[2][2][2]);
			nodes[2][2][2].setNeighbourNodes(nodes[2][1][2], nodes[2][2][1]);

			nodes[0][0][0].setMillHelper(new int[]{0, 0, 0});
			nodes[0][0][1].setMillHelper(new int[]{0, 0, 1});
			nodes[0][0][2].setMillHelper(new int[]{0, 0, 2});
			nodes[0][1][0].setMillHelper(new int[]{0, 1, 0});
			nodes[0][1][2].setMillHelper(new int[]{0, 1, 2});
			nodes[0][2][0].setMillHelper(new int[]{0, 2, 0});
			nodes[0][2][1].setMillHelper(new int[]{0, 2, 1});
			nodes[0][2][2].setMillHelper(new int[]{0, 2, 2});
			nodes[1][0][0].setMillHelper(new int[]{1, 0, 0});
			nodes[1][0][1].setMillHelper(new int[]{1, 0, 1});
			nodes[1][0][2].setMillHelper(new int[]{1, 0, 2});
			nodes[1][1][0].setMillHelper(new int[]{1, 1, 0});
			nodes[1][1][2].setMillHelper(new int[]{1, 1, 2});
			nodes[1][2][0].setMillHelper(new int[]{1, 2, 0});
			nodes[1][2][1].setMillHelper(new int[]{1, 2, 1});
			nodes[1][2][2].setMillHelper(new int[]{1, 2, 2});
			nodes[2][0][0].setMillHelper(new int[]{2, 0, 0});
			nodes[2][0][1].setMillHelper(new int[]{2, 0, 1});
			nodes[2][0][2].setMillHelper(new int[]{2, 0, 2});
			nodes[2][1][0].setMillHelper(new int[]{2, 1, 0});
			nodes[2][1][2].setMillHelper(new int[]{2, 1, 2});
			nodes[2][2][0].setMillHelper(new int[]{2, 2, 0});
			nodes[2][2][1].setMillHelper(new int[]{2, 2, 1});
			nodes[2][2][2].setMillHelper(new int[]{2, 2, 2});
			//endregion
			// region prepare tiles
			whiteTiles.add((Tile) findViewById(R.id.tileW_1));
			whiteTiles.add((Tile) findViewById(R.id.tileW_2));
			whiteTiles.add((Tile) findViewById(R.id.tileW_3));
			whiteTiles.add((Tile) findViewById(R.id.tileW_4));
			whiteTiles.add((Tile) findViewById(R.id.tileW_5));
			whiteTiles.add((Tile) findViewById(R.id.tileW_6));
			whiteTiles.add((Tile) findViewById(R.id.tileW_7));
			whiteTiles.add((Tile) findViewById(R.id.tileW_8));
			whiteTiles.add((Tile) findViewById(R.id.tileW_9));
			blackTiles.add((Tile) findViewById(R.id.tileB_1));
			blackTiles.add((Tile) findViewById(R.id.tileB_2));
			blackTiles.add((Tile) findViewById(R.id.tileB_3));
			blackTiles.add((Tile) findViewById(R.id.tileB_4));
			blackTiles.add((Tile) findViewById(R.id.tileB_5));
			blackTiles.add((Tile) findViewById(R.id.tileB_6));
			blackTiles.add((Tile) findViewById(R.id.tileB_7));
			blackTiles.add((Tile) findViewById(R.id.tileB_8));
			blackTiles.add((Tile) findViewById(R.id.tileB_9));

			for (Tile tile : whiteTiles) {
				tile.setOnLongClickListener(longClickListenerEarlyGame);
				tile.setCurrentNode((Node) findViewById(R.id.startAreaP1));
			}
			for (Tile tile : blackTiles) {
				tile.setOnLongClickListener(longClickListenerEarlyGame);
				tile.setCurrentNode((Node) findViewById(R.id.startAreaP2));
			}
			//endregion
			//region prepare players
			String nameB = "black", nameW = "white";
			players[0] = new Player(nameW);
			players[1] = new Player(nameB);
			players[0].setTiles(whiteTiles).setStartingArea((Node) findViewById(R.id.startAreaP1));
			players[0].getStartingArea().setOccupied(true).setOnDragListener(dragListenerPickTile);
			players[0].getStartingArea().setEnabled(false);
			players[1].setPlayerWhite(false).setActive(false).setTiles(blackTiles).setStartingArea((Node) findViewById(R.id.startAreaP2));
			players[1].getStartingArea().setOccupied(true).setOnDragListener(dragListenerPickTile);
			players[1].getStartingArea().setEnabled(false);
			//endregion
		} catch (Exception exc) {
			Toast.makeText(getApplicationContext(),getResources().getString(R.string.GameSetUpError),Toast.LENGTH_LONG).show();
			this.finish();
		}
	}

	View.OnLongClickListener longClickListenerEarlyGame = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (((Tile) v).getCurrentNode() != getActivePlayer().getStartingArea())
				return false;
			ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			ClipData clipData = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
			View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
			v.startDrag(clipData, dragShadow, v, 0);
			return true;
		}
	};
	View.OnLongClickListener longClickListenerMidGame = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if(getInactivePlayer().getTiles().contains(v))
				return false;
			ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			ClipData clipData = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
			View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
			v.startDrag(clipData, dragShadow, v, 0);
			return true;
		}
	};
	View.OnLongClickListener longClickListenerPickTile = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (checkForMill((Tile) v))
				return false;
			if (getActivePlayer().getTiles().contains(v))
				return false;
			if (((Tile)v).getCurrentNode() == getInactivePlayer().getStartingArea())
				return false;
			ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			ClipData clipData = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
			View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
			v.startDrag(clipData, dragShadow, v, 0);
			return true;
		}
	};

	View.OnDragListener dragListenerEarlyGame = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			Node receivingNode = (Node) v;
			Tile currentTile = (Tile) event.getLocalState();
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
					return receivingNode.isFree();
				case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
					Log.d("EG_DROP", " before move");
					move(currentTile, receivingNode);
					Log.d("EG_DROP", " after move");
					getActivePlayer().decreaseTilesInHand();
					if (getActivePlayer().getTilesInHand() == 0 )
						getActivePlayer().setCurrentPhase(Player.Phase.MID_GAME);
					if (checkForMill(currentTile)) {
						v.setBackground(null);
						preparePickTile();
					}
					else
						switchPlayers();
					Log.d("EG_DROP", " before leave");
					return true;
				case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
					v.setBackground(null);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
					try {
						Drawable background = currentTile.getBackground().getConstantState().newDrawable();
						v.setBackground(background);
					} catch (Exception ignored) {
					}
					break;
				case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
					v.setBackground(null);
					break;
			}
			return false;
		}
	};
	View.OnDragListener dragListenerMidGame = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			Node receivingNode = (Node) v;
			Tile currentTile = (Tile) event.getLocalState();
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
					return receivingNode.isFree() && currentTile.nodeIsNeighbour(receivingNode);
				case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
					Log.d("EG_DROP", " before move");
					move(currentTile, receivingNode);
					if (checkForMill(currentTile)) {
						v.setBackground(null);
						preparePickTile();
					} else
						switchPlayers();
					Log.d("EG_DROP", " before leave");
					return true;
				case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
					v.setBackground(null);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
					try {
						Drawable background = currentTile.getBackground().getConstantState().newDrawable();
						v.setBackground(background);
					} catch (Exception ignored) {}
					break;
				case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
					v.setBackground(null);
					break;
			}
			return false;
		}
	};
	View.OnDragListener dragListenerLateGame = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			Node receivingNode = (Node) v;
			Tile currentTile = (Tile) event.getLocalState();
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
					return receivingNode.isFree();
				case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
					move(currentTile, receivingNode);
					if (checkForMill(currentTile)) {
						v.setBackground(null);
						preparePickTile();
					} else
						switchPlayers();
					break;
				case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
					v.setBackground(null);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
					try {
						Drawable background = currentTile.getBackground().getConstantState().newDrawable();
						v.setBackground(background);
					} catch (Exception ignored) {
					}
					break;
				case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
					v.setBackground(null);
					break;
			}
			return false;
		}
	};
	View.OnDragListener dragListenerPickTile = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
					int bla = v.getId();
					int foo = getActivePlayer().getStartingArea().getId();
					if (bla == foo) {
						return true;
					}
					return false;
				case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
					Log.d("EG_DROP", " before move");
					if (v.getId() == getActivePlayer().getStartingArea().getId()) {
						Tile currentTile = (Tile) event.getLocalState();
						move(currentTile, getActivePlayer().getStartingArea());
						removeTile(currentTile);
						switchNodeListeners();
						if (getInactivePlayer().getTiles().size() == 3)
							getInactivePlayer().setCurrentPhase(Player.Phase.LATE_GAME);
						if (getInactivePlayer().getTiles().size() == 2)
							gameOver();
						switchPlayers();
						return true;
					}
					Log.d("EG_DROP", " before leave");
					break;
				case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
					v.setBackground(null);
					break;
			}
			return false;
		}
	};

	private void move(Tile tile, Node node) {
		tile.animate().x(node.getX() + offset).y(node.getY() + offset).setDuration(0).start();
		tile.getCurrentNode().setOccupied(false);
		tile.getCurrentNode().setCurrentTile(null);
		tile.setCurrentNode(node);
		node.setOccupied(true);
		node.setCurrentTile(tile);
	}

	private boolean checkForMill(Tile tile) {
		if (tile.getCurrentNode() != getActivePlayer().getStartingArea()) {
			ArrayList<Node> neighbours = tile.getCurrentNode().getNeighbourNodes();
			for (Node neighbour : neighbours) {
				if (neighbour.getCurrentTile() != null && tile.isPlayerWhite() == neighbour.getCurrentTile().isPlayerWhite()) {
					int[] index = tile.getCurrentNode().get3rdNodeIndex(neighbour);
					Node thirdNode = nodes[index[0]][index[1]][index[2]];
					//TODO: next if just to make sure that the 3rd node is a neighbour, for additional safety
					if (tile.getCurrentNode().getNeighbourNodes().contains(thirdNode) || neighbour.getNeighbourNodes().contains(thirdNode))
						if (thirdNode.getCurrentTile() != null && tile.isPlayerWhite() == thirdNode.getCurrentTile().isPlayerWhite())
							return true;
				}
			}
		}
		return false;
	}

	private void preparePickTile() {
		Toast.makeText(getApplicationContext(), "Mühle!", Toast.LENGTH_SHORT).show();
		for(Tile tile : getInactivePlayer().getTiles())
			tile.setOnLongClickListener(longClickListenerPickTile);
		switchNodeListeners();
	}

	private void removeTile(Tile tile){
		tile.setVisibility(View.INVISIBLE);
		tile.setEnabled(false);
		getInactivePlayer().getTiles().remove(tile);
	}

	private void switchPlayers() {
		Log.d(TAG, "switch player");
		Player newActivePlayer = getInactivePlayer();
		getActivePlayer().setActive(false);
		newActivePlayer.setActive(true);
		setTileListeners(newActivePlayer);
		setNodeListeners(newActivePlayer);
		Toast.makeText(getApplicationContext(), (String.format(getResources().getString(R.string.Toast_currentPlayer), getResources().getString(getActivePlayer().isPlayerWhite() ? R.string.PlayerColourWhite : R.string.PlayerColourBlack), getActivePlayer().getName())), Toast.LENGTH_SHORT).show();
	}

	private void setNodeListeners(Player player){
		switch (player.getCurrentPhase()){
			case EARLY_GAME:
				setNodeListeners(dragListenerEarlyGame);
				break;
			case MID_GAME:
				setNodeListeners(dragListenerMidGame);
				break;
			case LATE_GAME:
				setNodeListeners(dragListenerLateGame);
				break;
		}
	}

	public void setNodeListeners(View.OnDragListener newListener){
		for (Node[][] node : nodes)
			for (Node[] node2 : node)
				for (Node node3 : node2)
					if (node3 != null)
						node3.setOnDragListener(newListener);
	}

	private void setTileListeners(Player player){
		for( Tile tile : player.getTiles())
			switch(player.getCurrentPhase()) {
				case EARLY_GAME:
					tile.setOnLongClickListener(longClickListenerEarlyGame);
					break;
				case MID_GAME:
				case LATE_GAME:
					tile.setOnLongClickListener(longClickListenerMidGame);
					break;
			}
	}

	private void switchNodeListeners(){
		getActivePlayer().getStartingArea().setEnabled(!getActivePlayer().getStartingArea().isEnabled());
		for (Node[][] node : nodes)
			for (Node[] node2 : node)
				for (Node node3 : node2)
					if (node3 != null)
						node3.setEnabled(!node3.isEnabled());
	}

	private void gameOver(){
		Toast.makeText(getApplicationContext(),"Spieler "+getActivePlayer().getName()+" hat gewonnen", Toast.LENGTH_LONG).show();
		sqLiteDB.t_score_insert(getActivePlayer().getName(),getInactivePlayer().getName(),getActivePlayer().getName());
		onBackPressed();
	}

	private Player getActivePlayer() {
		return players[0].isActive() ? players[0] : players[1];
	}
	private Player getInactivePlayer() {
		return players[0].isActive() ? players[1] : players[0];
	}

	public void onBackPressed() {
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
		dlgBuilder.setTitle(Html.fromHtml("<font color='#190707'>Mühle</font>"));
		dlgBuilder.setMessage(Html.fromHtml("<font color='#190707'>Sind sie sicher dass Sie das Spiel verlassen wollen?</font>"));
		dlgBuilder.setCancelable(true);
		dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(SinglePlayerActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();
				finish();
				//Intent secondAct = new Intent( SinglePlayerActivity.this, SecondActivity.class );
				//startActivity( secondAct );
			}
		});
		dlgBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog alert = dlgBuilder.create();
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		alert.show();
	}
}