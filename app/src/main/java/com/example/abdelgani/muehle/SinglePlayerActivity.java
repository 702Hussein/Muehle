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

public class SinglePlayerActivity extends AppCompatActivity {

	private static final String TAG = "__________";
	private enum Phase {EARLY_GAME, MID_GAME, LATE_GAME}

	Node[][][] nodes = new Node[3][3][3];
	Tile[] whiteTiles, blackTiles;
	Tile tileW_1, tileW_2, tileW_3, tileW_4, tileW_5, tileW_6, tileW_7, tileW_8, tileW_9;
	Tile tileB_1, tileB_2, tileB_3, tileB_4, tileB_5, tileB_6, tileB_7, tileB_8, tileB_9;
	int offset;
	boolean pickTile = false;
	Phase currentPhase = Phase.EARLY_GAME;
	Player playerW, playerB, currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_player );
		Log.d(TAG,"Singleplayer startup");
        try {
        	//region misc
			offset = (int)((getResources().getDimension(R.dimen.nodeSize) - getResources().getDimension(R.dimen.tileSize) + 0.5)/2);
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
			nodes[0][0][0].setMillHelper(new int[]{0,0,0});
			nodes[0][0][1].setMillHelper(new int[]{0,0,1});
			nodes[0][0][2].setMillHelper(new int[]{0,0,2});
			nodes[0][1][0].setMillHelper(new int[]{0,1,0});
			nodes[0][1][2].setMillHelper(new int[]{0,1,2});
			nodes[0][2][0].setMillHelper(new int[]{0,2,0});
			nodes[0][2][1].setMillHelper(new int[]{0,2,1});
			nodes[0][2][2].setMillHelper(new int[]{0,2,2});
			nodes[1][0][0].setMillHelper(new int[]{1,0,0});
			nodes[1][0][1].setMillHelper(new int[]{1,0,1});
			nodes[1][0][2].setMillHelper(new int[]{1,0,2});
			nodes[1][1][0].setMillHelper(new int[]{1,1,0});
			nodes[1][1][2].setMillHelper(new int[]{1,1,2});
			nodes[1][2][0].setMillHelper(new int[]{1,2,0});
			nodes[1][2][1].setMillHelper(new int[]{1,2,1});
			nodes[1][2][2].setMillHelper(new int[]{1,2,2});
			nodes[2][0][0].setMillHelper(new int[]{2,0,0});
			nodes[2][0][1].setMillHelper(new int[]{2,0,1});
			nodes[2][0][2].setMillHelper(new int[]{2,0,2});
			nodes[2][1][0].setMillHelper(new int[]{2,1,0});
			nodes[2][1][2].setMillHelper(new int[]{2,1,2});
			nodes[2][2][0].setMillHelper(new int[]{2,2,0});
			nodes[2][2][1].setMillHelper(new int[]{2,2,1});
			nodes[2][2][2].setMillHelper(new int[]{2,2,2});
			//endregion
			// region assign tiles
			tileW_1 = findViewById(R.id.tileW_1);
			tileW_2 = findViewById(R.id.tileW_2);
			tileW_3 = findViewById(R.id.tileW_3);
			tileW_4 = findViewById(R.id.tileW_4);
			tileW_5 = findViewById(R.id.tileW_5);
			tileW_6 = findViewById(R.id.tileW_6);
			tileW_7 = findViewById(R.id.tileW_7);
			tileW_8 = findViewById(R.id.tileW_8);
			tileW_9 = findViewById(R.id.tileW_9);
			tileB_1 = findViewById(R.id.tileB_1);
			tileB_2 = findViewById(R.id.tileB_2);
			tileB_3 = findViewById(R.id.tileB_3);
			tileB_4 = findViewById(R.id.tileB_4);
			tileB_5 = findViewById(R.id.tileB_5);
			tileB_6 = findViewById(R.id.tileB_6);
			tileB_7 = findViewById(R.id.tileB_7);
			tileB_8 = findViewById(R.id.tileB_8);
			tileB_9 = findViewById(R.id.tileB_9);
			//endregion
			//region set LongClickListeners to tiles
			tileW_1.setOnLongClickListener(longClickListener);
			tileW_2.setOnLongClickListener(longClickListener);
			tileW_3.setOnLongClickListener(longClickListener);
			tileW_4.setOnLongClickListener(longClickListener);
			tileW_5.setOnLongClickListener(longClickListener);
			tileW_6.setOnLongClickListener(longClickListener);
			tileW_7.setOnLongClickListener(longClickListener);
			tileW_8.setOnLongClickListener(longClickListener);
			tileW_9.setOnLongClickListener(longClickListener);
			tileB_1.setOnLongClickListener(longClickListener);
			tileB_2.setOnLongClickListener(longClickListener);
			tileB_3.setOnLongClickListener(longClickListener);
			tileB_4.setOnLongClickListener(longClickListener);
			tileB_5.setOnLongClickListener(longClickListener);
			tileB_6.setOnLongClickListener(longClickListener);
			tileB_7.setOnLongClickListener(longClickListener);
			tileB_8.setOnLongClickListener(longClickListener);
			tileB_9.setOnLongClickListener(longClickListener);
			//endregion
			whiteTiles = new Tile[]{tileW_1, tileW_2, tileW_3, tileW_4, tileW_5, tileW_6, tileW_7, tileW_8, tileW_9};
			blackTiles = new Tile[]{tileB_1, tileB_2, tileB_3, tileB_4, tileB_5, tileB_6, tileB_7, tileB_8, tileB_9};
			String nameB = "black", nameW = "white";
			playerB = new Player(nameB);
			playerB.setPlayerWhite(false);
			playerW = new Player(nameW);
			playerB.setTiles(blackTiles);
			playerW.setTiles(whiteTiles);
			Game gamePlayerW = new Game(nodes, whiteTiles);
			Game gamePlayerB = new Game(nodes, blackTiles);
			changePlayerState(playerB, false);
			currentPlayer = playerW;
		}catch (Exception exc){
        	exc.getMessage();
		}
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			Log.d(TAG,"enter onLongClick" + v.getId());
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
				if (pickTile){
					Log.d("DRAGLISTENER ONDRAG","pickTile true, enter");
					returnValue = pickTile(v, event);
					pickTile = false;
					switchPlayers(playerB, playerW);
				}
				else {
					switch (currentPhase) {
						case EARLY_GAME:
							Log.d("DRAGLISTENER","Early_Game enter");
							returnValue = earlyGame(v, event);
							break;
						case MID_GAME:
							Log.d("DRAGLISTENER","Mid_Game enter");
							returnValue = midGame(v, event);
							break;
						case LATE_GAME:
							Log.d("DRAGLISTENER","Late_Game enter");
							returnValue = lateGame(v, event);
							break;
						default:
							break;
					}
				}
				return returnValue;
			}
    };

    private boolean earlyGame(View v, DragEvent event){
		Node node = (Node) v;
		Tile tile = (Tile)event.getLocalState();
		switch (event.getAction())
		{
			case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
				return node.isFree() && tile.getCurrentNode() == null;
			case DragEvent.ACTION_DRAG_LOCATION:    //enter the listening area (with ACTION_DRAG_ENTERED
				break;
			case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
				Log.d("EG_DROP"," before move");
				move(node, tile);
				Log.d("EG_DROP"," after move");
				currentPlayer.decreaseTilesInHand();
				if( checkForMill(tile))
					pickTile = true;
				else
					switchPlayers(playerB, playerW);
				Log.d("EG_DROP"," before leave");
				break;
			case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
				v.setBackground(null);
				break;
			case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
				try {
					Drawable background = tile.getBackground().getConstantState().newDrawable();
					v.setBackground(background);
				}catch(Exception ignored){}
				break;
			case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
				v.setBackground(null);
				break;
			default:
				return false;
		}
    	return true;
	}
    private boolean midGame(View v, DragEvent event){
		Node node = (Node) v;
		Tile tile = (Tile)event.getLocalState();
		switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item
				return node.isFree() && tile.nodeIsNeighbour(node);
			case DragEvent.ACTION_DRAG_LOCATION:    //enter the listening area (with ACTION_DRAG_ENTERED
				break;
			case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
				move(node, tile);
				break;
			case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
				v.setBackground(null);
				return event.getResult();
			case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
				Drawable background = tile.getBackground().getConstantState().newDrawable();
				if (background != null)
					v.setBackground(background);
				break;
			case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
				v.setBackground(null);
				break;
			default:
				break;
		}
		return true;
	}

	private boolean lateGame(View v, DragEvent event){

    	return true;
	}

	private boolean pickTile(View v, DragEvent event){
		Log.d("PICKTILE", "empty body");
    	return true;
	}


    private void move(Node node, Tile tile){
		tile.animate().x(node.getX() + offset).y(node.getY() + offset).setDuration(0).start();
		node.setOccupied(true);
		if ( tile.getCurrentNode() != null) {
			tile.getCurrentNode().setOccupied(false);
			tile.getCurrentNode().setCurrentTile(null);
		}
		tile.setCurrentNode(node);
		node.setCurrentTile(tile);
    }

	private boolean checkForMill(Tile tile) {
		ArrayList<Node> neighbours = tile.getCurrentNode().getNeighbourNodes();
		for (Node neighbour : neighbours) {
			if ( neighbour.getCurrentTile() != null && tile.isPlayerWhite() == neighbour.getCurrentTile().isPlayerWhite()){
				int[] index = tile.getCurrentNode().get3rdNodeIndex(neighbour);
				Node thirdNode = nodes[index[0]][index[1]][index[2]];
				//TODO: next if just to make sure that the 3rd node is a neighbour, for additional safety
				if( tile.getCurrentNode().getNeighbourNodes().contains(thirdNode) || neighbour.getNeighbourNodes().contains(thirdNode))
					if ( thirdNode.getCurrentTile() != null && tile.isPlayerWhite() == thirdNode.getCurrentTile().isPlayerWhite()){
						Toast.makeText(getApplicationContext(),"Mühle!", Toast.LENGTH_LONG).show();
						return true;
					}
			}
		}
		Toast.makeText(getApplicationContext(),"keine Mühle", Toast.LENGTH_SHORT).show();
		return false;
	}

	private void changePlayerState(Player toChange, boolean enable){
    	Log.d("CHANGE PLAYER", toChange.getName() + " will be " + enable);
    	for(Tile tile : toChange.getTiles()){
    		tile.setEnabled(enable);
    		Log.d("CHANGE PLAYER", tile.isEnabled() + " *** " + tile.getId());
		}
		toChange.setActive(enable);
	}

	private void switchPlayers(Player player1, Player player2){
    	Log.d(TAG,"switch player");
		changePlayerState(player1, !player1.isActive());
		changePlayerState(player2, !player2.isActive());
		currentPlayer = player1.isActive() ? player1 : player2;
		Toast.makeText(getApplicationContext(),(String.format(getResources().getString(R.string.Toast_currentPlayer), getResources().getString(currentPlayer.isPlayerWhite()?R.string.PlayerColourWhite:R.string.PlayerColourBlack), currentPlayer.getName())), Toast.LENGTH_LONG).show();
	}

    public void onBackPressed()
	{
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
        dlgBuilder.setTitle( Html.fromHtml("<font color='#190707'>Mühle</font>"));
		dlgBuilder.setMessage(Html.fromHtml( "<font color='#190707'>Sind sie sicher dass Sie das Spiel verlassen wollen?</font>"));
		dlgBuilder.setCancelable(true);
		dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
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
/*
			node_outer_topLeft = findViewById(R.id.node_outer_topLeft);
			node_outer_topMid = findViewById(R.id.node_outer_topMid);
			node_outer_topRight = findViewById(R.id.node_outer_topRight);
			node_outer_midLeft = findViewById(R.id.node_outer_midLeft);
			node_outer_midRight = findViewById(R.id.node_outer_midRight);
			node_outer_botLeft = findViewById(R.id.node_outer_botLeft);
			node_outer_botMid = findViewById(R.id.node_outer_botMid);
			node_outer_botRight = findViewById(R.id.node_outer_botRight);
			node_middle_topLeft = findViewById(R.id.node_middle_topLeft);
			node_middle_topMid = findViewById(R.id.node_middle_topMid);
			node_middle_topRight = findViewById(R.id.node_middle_topRight);
			node_middle_midLeft = findViewById(R.id.node_middle_midLeft);
			node_middle_midRight = findViewById(R.id.node_middle_midRight);
			node_middle_botLeft = findViewById(R.id.node_middle_botLeft);
			node_middle_botMid = findViewById(R.id.node_middle_botMid);
			node_middle_botRight = findViewById(R.id.node_middle_botRight);
			node_inner_topLeft = findViewById(R.id.node_inner_topLeft);
			node_inner_topMid = findViewById(R.id.node_inner_topMid);
			node_inner_topRight = findViewById(R.id.node_inner_topRight);
			node_inner_midLeft = findViewById(R.id.node_inner_midLeft);
			node_inner_midRight = findViewById(R.id.node_inner_midRight);
			node_inner_botLeft = findViewById(R.id.node_inner_botLeft);
			node_inner_botMid = findViewById(R.id.node_inner_botMid);
			node_inner_botRight = findViewById(R.id.node_inner_botRight);

			node_outer_topLeft.setOnDragListener(dragListener);
			node_outer_topMid.setOnDragListener(dragListener);
			node_outer_topRight.setOnDragListener(dragListener);
			node_outer_midLeft.setOnDragListener(dragListener);
			node_outer_midRight.setOnDragListener(dragListener);
			node_outer_botLeft.setOnDragListener(dragListener);
			node_outer_botMid.setOnDragListener(dragListener);
			node_outer_botRight.setOnDragListener(dragListener);
			node_middle_topLeft.setOnDragListener(dragListener);
			node_middle_topMid.setOnDragListener(dragListener);
			node_middle_topRight.setOnDragListener(dragListener);
			node_middle_midLeft.setOnDragListener(dragListener);
			node_middle_midRight.setOnDragListener(dragListener);
			node_middle_botLeft.setOnDragListener(dragListener);
			node_middle_botMid.setOnDragListener(dragListener);
			node_middle_botRight.setOnDragListener(dragListener);
			node_inner_topLeft.setOnDragListener(dragListener);
			node_inner_topMid.setOnDragListener(dragListener);
			node_inner_topRight.setOnDragListener(dragListener);
			node_inner_midLeft.setOnDragListener(dragListener);
			node_inner_midRight.setOnDragListener(dragListener);
			node_inner_botLeft.setOnDragListener(dragListener);
			node_inner_botMid.setOnDragListener(dragListener);
			node_inner_botRight.setOnDragListener(dragListener);

			node_outer_topLeft.setNeighbourNodes(node_outer_topMid, node_outer_midLeft);
			node_outer_topMid.setNeighbourNodes(node_outer_topLeft, node_outer_topRight, node_middle_topMid);
			node_outer_topRight.setNeighbourNodes(node_outer_topMid, node_outer_midRight);
			node_outer_midLeft.setNeighbourNodes(node_outer_topLeft, node_outer_botLeft, node_middle_midLeft);
			node_outer_midRight.setNeighbourNodes(node_outer_topRight, node_outer_botRight, node_middle_midRight);
			node_outer_botLeft.setNeighbourNodes(node_outer_midLeft, node_outer_botMid);
			node_outer_botMid.setNeighbourNodes(node_outer_botLeft, node_outer_botRight, node_middle_botMid);
			node_outer_botRight.setNeighbourNodes(node_outer_botMid, node_outer_midRight);
			node_middle_topLeft.setNeighbourNodes(node_middle_topMid, node_middle_midLeft);
			node_middle_topMid.setNeighbourNodes(node_outer_topMid, node_middle_topLeft, node_middle_topRight, node_inner_topMid);
			node_middle_topRight.setNeighbourNodes(node_middle_topMid, node_middle_midRight);
			node_middle_midLeft.setNeighbourNodes(node_outer_midLeft, node_middle_topLeft, node_middle_botLeft, node_inner_midLeft);
			node_middle_midRight.setNeighbourNodes(node_outer_midRight, node_middle_topRight, node_middle_botRight, node_inner_midRight);
			node_middle_botLeft.setNeighbourNodes(node_middle_midLeft, node_middle_botMid);
			node_middle_botMid.setNeighbourNodes(node_outer_botMid, node_middle_botLeft, node_middle_botRight, node_inner_botMid);
			node_middle_botRight.setNeighbourNodes(node_middle_midRight, node_middle_botMid);
			node_inner_topLeft.setNeighbourNodes(node_inner_topMid, node_inner_midLeft);
			node_inner_topMid.setNeighbourNodes(node_middle_topMid, node_inner_topLeft, node_inner_topRight);
			node_inner_topRight.setNeighbourNodes(node_inner_topMid, node_inner_midRight);
			node_inner_midLeft.setNeighbourNodes(node_middle_midLeft, node_inner_topLeft, node_inner_botLeft);
			node_inner_midRight.setNeighbourNodes(node_middle_midRight, node_inner_topRight, node_inner_botRight);
			node_inner_botLeft.setNeighbourNodes(node_inner_midLeft, node_inner_botMid);
			node_inner_botMid.setNeighbourNodes(node_middle_botMid, node_inner_botLeft,node_inner_botRight);
			node_inner_botRight.setNeighbourNodes(node_inner_midRight, node_inner_botMid);

			node_outer_topLeft.setMillHelper(111);
			node_outer_topMid.setMillHelper(112);
			node_outer_topRight.setMillHelper(113);
			node_outer_midLeft.setMillHelper(121);
			node_outer_midRight.setMillHelper(123);
			node_outer_botLeft.setMillHelper(131);
			node_outer_botMid.setMillHelper(132);
			node_outer_botRight.setMillHelper(133);
			node_middle_topLeft.setMillHelper(211);
			node_middle_topMid.setMillHelper(212);
			node_middle_topRight.setMillHelper(213);
			node_middle_midLeft.setMillHelper(221);
			node_middle_midRight.setMillHelper(223);
			node_middle_botLeft.setMillHelper(231);
			node_middle_botMid.setMillHelper(232);
			node_middle_botRight.setMillHelper(233);
			node_inner_topLeft.setMillHelper(311);
			node_inner_topMid.setMillHelper(312);
			node_inner_topRight.setMillHelper(313);
			node_inner_midLeft.setMillHelper(321);
			node_inner_midRight.setMillHelper(323);
			node_inner_botLeft.setMillHelper(331);
			node_inner_botMid.setMillHelper(332);
			node_inner_botRight.setMillHelper(333);

			nodes[0][0][0].setMillHelper(000);
			nodes[0][0][1].setMillHelper(001);
			nodes[0][0][2].setMillHelper(002);
			nodes[0][1][0].setMillHelper(010);
			nodes[0][1][2].setMillHelper(012);
			nodes[0][2][0].setMillHelper(020);
			nodes[0][2][1].setMillHelper(021);
			nodes[0][2][2].setMillHelper(022);
			nodes[1][0][0].setMillHelper(100);
			nodes[1][0][1].setMillHelper(101);
			nodes[1][0][2].setMillHelper(102);
			nodes[1][1][0].setMillHelper(110);
			nodes[1][1][2].setMillHelper(112);
			nodes[1][2][0].setMillHelper(120);
			nodes[1][2][1].setMillHelper(121);
			nodes[1][2][2].setMillHelper(122);
			nodes[2][0][0].setMillHelper(200);
			nodes[2][0][1].setMillHelper(201);
			nodes[2][0][2].setMillHelper(202);
			nodes[2][1][0].setMillHelper(210);
			nodes[2][1][2].setMillHelper(212);
			nodes[2][2][0].setMillHelper(220);
			nodes[2][2][1].setMillHelper(221);
			nodes[2][2][2].setMillHelper(222);
*/