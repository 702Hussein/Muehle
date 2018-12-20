package com.example.abdelgani.muehle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.DragEvent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import com.example.abdelgani.muehle.Classes.Game;
import com.example.abdelgani.muehle.Classes.Node;
import com.example.abdelgani.muehle.Classes.Player;
import com.example.abdelgani.muehle.Classes.Tile;

import java.util.ArrayList;
import java.util.EnumSet;

public class SinglePlayerActivity extends AppCompatActivity {

	private static final String TAG = "__________";

	private enum Phase {PICK_TILE, EARLY_GAME, MID_GAME, LATE_GAME}
	//TODO: möglichkeit eines Flag Sets wie in C# erkunden
	//private EnumSet<Phase> EarlyGame = EnumSet.of(Phase.EARLY_GAME);


	Node[][][] nodes = new Node[3][3][3];
	ArrayList<Tile> whiteTiles = new ArrayList<>(9), blackTiles = new ArrayList<>(9);
	int offset;
	boolean pickTile = false;
	Phase currentPhase = Phase.EARLY_GAME;
	Player playerW, playerB, currentPlayer;
	IDragEventStartedBehavior dragEventStarted;
	IDragEventDropBehavior dragEventDrop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_player);
		Log.d(TAG, "Singleplayer startup");
		try {
			//region misc
			offset = (int) ((getResources().getDimension(R.dimen.nodeSize) - getResources().getDimension(R.dimen.tileSize) + 0.5) / 2);
			//set up DragEvent.ACTION_DRAG_STARTED  for early game:
			dragEventStarted = new IDragEventStartedBehavior() {
				@Override
				public boolean dragEventStarted(Node receivingNode, Tile currentTile) {
					return earlyGameActionDragStarted(receivingNode, currentTile);
				}
			};
			//set up DragEvent.ACTION_DROP for early game:
			dragEventDrop = new IDragEventDropBehavior() {
				@Override
				public boolean dragEventDrop(Node receivingNode, Tile currentTile) {
					return earlyGameActionDrop(receivingNode, currentTile);
				}
			};
			//endregion
			//region assign nodes
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
			//endregion
			//region set DragListeners to nodes
			nodes[0][0][0].setOnDragListener(dragListener);
			nodes[0][0][1].setOnDragListener(dragListener);
			nodes[0][0][2].setOnDragListener(dragListener);
			nodes[0][1][0].setOnDragListener(dragListener);
			nodes[0][1][2].setOnDragListener(dragListener);
			nodes[0][2][0].setOnDragListener(dragListener);
			nodes[0][2][1].setOnDragListener(dragListener);
			nodes[0][2][2].setOnDragListener(dragListener);
			nodes[1][0][0].setOnDragListener(dragListener);
			nodes[1][0][1].setOnDragListener(dragListener);
			nodes[1][0][2].setOnDragListener(dragListener);
			nodes[1][1][0].setOnDragListener(dragListener);
			nodes[1][1][2].setOnDragListener(dragListener);
			nodes[1][2][0].setOnDragListener(dragListener);
			nodes[1][2][1].setOnDragListener(dragListener);
			nodes[1][2][2].setOnDragListener(dragListener);
			nodes[2][0][0].setOnDragListener(dragListener);
			nodes[2][0][1].setOnDragListener(dragListener);
			nodes[2][0][2].setOnDragListener(dragListener);
			nodes[2][1][0].setOnDragListener(dragListener);
			nodes[2][1][2].setOnDragListener(dragListener);
			nodes[2][2][0].setOnDragListener(dragListener);
			nodes[2][2][1].setOnDragListener(dragListener);
			nodes[2][2][2].setOnDragListener(dragListener);
			//endregiont
			//region set Node neighbours
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
			//endregion
			//region assign millHelpers
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
			// region assign tiles
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
			//endregion
			//region set LongClickListeners to tiles
			for (Tile tile : whiteTiles)
				tile.setOnLongClickListener(longClickListener);
			for (Tile tile : blackTiles)
				tile.setOnLongClickListener(longClickListener);
			//endregion
			//region prepare players
			String nameB = "black", nameW = "white";
			playerB = new Player(nameB);
			playerB.setPlayerWhite(false).setTiles(blackTiles).setStartingArea((Node) findViewById(R.id.startAreaP2));
			playerB.getStartingArea().setOccupied(true).setOnDragListener(dragListener);
			playerW = new Player(nameW);
			playerW.setTiles(whiteTiles).setStartingArea((Node) findViewById(R.id.startAreaP1));
			playerW.getStartingArea().setOccupied(true).setOnDragListener(dragListener);
			changePlayerState(playerB, false);
			currentPlayer = playerW;
			//endregion
		} catch (Exception exc) {
			exc.getMessage();
		}
	}

	View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (pickTile && (checkForMill((Tile) v) || ((Tile) v).getCurrentNode() == null))
				return false;
			if (currentPhase == Phase.EARLY_GAME && !(((Tile) v).getCurrentNode() == null || pickTile))
				return false;
			Log.d(TAG, "enter onLongClick" + v.getId());
			ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			ClipData clipData = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
			View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
			v.startDrag(clipData, dragShadow, v, 0);
			return true;
		}
	};

	Node.OnDragListener dragListener = new Node.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			Log.d("DRAGLISTENER ONDRAG", "enter onDrag " + v.getId());
			boolean returnValue = false;
			Node node = (Node) v;
			Tile tile = (Tile) event.getLocalState();
			if (pickTile) {
				Log.d("DRAGLISTENER ONDRAG", "pickTile true, enter");
				/*/TODO: test with 2nd listener:
				for(Node[][] node2 : nodes)
					for(Node[] node3 : node2)
						for(Node node4: node3){
							if (node4 != null)
								node4.setOnDragListener(earlyGameDragListener);
						}*/
				returnValue = pickTile(v, event);
			}
			else {
				switch (event.getAction()) {
					case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
						returnValue = dragEventStarted.dragEventStarted(node, tile);
						break;
					case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
						Log.d("EG_DROP", " before move");
						returnValue = dragEventDrop.dragEventDrop(node, tile);
						Log.d("EG_DROP", " before leave");
						break;
					case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
						v.setBackground(null);
						break;
					case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
						try {
							Drawable background = tile.getBackground().getConstantState().newDrawable();
							v.setBackground(background);
						} catch (Exception ignored) {						}
						break;
					case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
						v.setBackground(null);
						break;
				}
			}
			return returnValue;
		}
	};
	//TODO: code weiter verfeinern: verschiedene DragListener für die Spielphasen
	/*
	View.OnDragListener earlyGameDragListener = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			return false;
		}
	};
	View.OnDragListener midGameDragListener = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			return false;
		}
	};
*/
	private boolean earlyGameActionDragStarted(Node receivingNode, Tile currentTile){
		return receivingNode.isFree() && currentTile.getCurrentNode() == null;
	}
	private boolean earlyGameActionDrop(Node receivingNode, Tile currentTile){
		move(receivingNode, currentTile);
		Log.d("EG_DROP", " after move");
		currentPlayer.decreaseTilesInHand();
		if (currentPlayer.getTilesInHand() == 0 && !currentPlayer.isPlayerWhite()){
			initializeMidGame();
		}
		if (checkForMill(currentTile)) {
			pickTile = preparePickTile();
		} else
			switchPlayers(playerB, playerW);
		return true;
	}
	private boolean midGameActionDragStarted(Node receivingNode, Tile currentTile){
		return receivingNode.isFree() && currentTile.nodeIsNeighbour(receivingNode);
	}
	private boolean midGameActionDrop(Node receivingNode, Tile currentTile){
		move(receivingNode, currentTile);
		if (checkForMill(currentTile)) {
			pickTile = preparePickTile();
		} else
			switchPlayers(playerB, playerW);
		return true;
	}
	private void initializeMidGame(){
		//change  DragEvent.ACTION_DRAG_STARTED for mid game:
		dragEventStarted = new IDragEventStartedBehavior() {
			@Override
			public boolean dragEventStarted(Node receivingNode, Tile currentTile) {
				return midGameActionDragStarted(receivingNode, currentTile);
			}
		};
		//change DragEvent.ACTION_DROP for mid game:
		dragEventDrop = new IDragEventDropBehavior() {
			@Override
			public boolean dragEventDrop(Node receivingNode, Tile currentTile) {
				return midGameActionDrop(receivingNode,currentTile);
			}
		};
	}

	private boolean pickTile(View v, DragEvent event) {
		Log.d("PICKTILE", "empty body");
		switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				//TODO: compare IDS
				int bla = v.getId();
				int foo = currentPlayer.getStartingArea().getId();
				if (bla == foo)
					return true;
				break;
			case DragEvent.ACTION_DROP:
				if (v.getId() == currentPlayer.getStartingArea().getId()) {
					Tile tile = (Tile) event.getLocalState();
					move(currentPlayer.getStartingArea(), tile);
					tile.setVisibility(View.INVISIBLE);
					currentPlayer.getTiles().remove(tile);
					pickTile = false;
					currentPlayer = playerB.isActive() ? playerB : playerW;
					if (currentPlayer.getTiles().size() < 4)
						currentPhase = Phase.LATE_GAME;
					return true;
				}
		}
		return false;
	}

	private void move(Node node, Tile tile) {
		tile.animate().x(node.getX() + offset).y(node.getY() + offset).setDuration(0).start();
		node.setOccupied(true);
		if (tile.getCurrentNode() != null) {
			tile.getCurrentNode().setOccupied(false);
			tile.getCurrentNode().setCurrentTile(null);
		}
		tile.setCurrentNode(node);
		node.setCurrentTile(tile);
	}

	private boolean checkForMill(Tile tile) {
		if (tile.getCurrentNode() != null) {
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

	private boolean preparePickTile(){
		Toast.makeText(getApplicationContext(), "Mühle!", Toast.LENGTH_SHORT).show();
		changePlayerState(playerB, !playerB.isActive());
		changePlayerState(playerW, !playerW.isActive());
		return true;
	}

	private void changePlayerState(Player toChange, boolean enable) {
		Log.d("CHANGE PLAYER", toChange.getName() + " will be " + enable);
		for (Tile tile : toChange.getTiles()) {
			tile.setEnabled(enable);
			Log.d("CHANGE PLAYER", tile.isEnabled() + " *** " + tile.getId());
		}
		toChange.setActive(enable);
	}

	private void switchPlayers(Player player1, Player player2) {
		Log.d(TAG, "switch player");
		changePlayerState(player1, !player1.isActive());
		changePlayerState(player2, !player2.isActive());
		currentPlayer = player1.isActive() ? player1 : player2;
		Toast.makeText(getApplicationContext(), (String.format(getResources().getString(R.string.Toast_currentPlayer), getResources().getString(currentPlayer.isPlayerWhite() ? R.string.PlayerColourWhite : R.string.PlayerColourBlack), currentPlayer.getName())), Toast.LENGTH_SHORT).show();
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

	private interface IDragEventStartedBehavior {
		boolean dragEventStarted(Node receivingNode, Tile currentTile);
	}
	
	private interface IDragEventDropBehavior {
		boolean dragEventDrop(Node receivingNode, Tile currentTile);
	}
}