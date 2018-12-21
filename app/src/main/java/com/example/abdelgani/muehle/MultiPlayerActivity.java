package com.example.abdelgani.muehle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelgani.muehle.Classes.Game;
import com.example.abdelgani.muehle.Classes.Node;
import com.example.abdelgani.muehle.Classes.Player;
import com.example.abdelgani.muehle.Classes.Tile;

import java.util.ArrayList;

public class MultiPlayerActivity extends AppCompatActivity {

    Database seconddatabase ;

    private static final String TAG = "__________";
    private enum Phase {EARLY_GAME, MID_GAME, LATE_GAME}

    Node[][][] nodes = new Node[3][3][3];
    ArrayList<Tile> whiteTiles = new ArrayList<>( 9 ), blackTiles = new ArrayList<>( 9 );
    int offset;
    boolean pickTile = false;
    Phase currentPhase = Phase.EARLY_GAME;
    Player playerW, playerB, currentPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_multi_player );
        Intent intent = getIntent();
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
            playerW = new Player(intent.getStringExtra("USER_NAME"));
            playerW.setTiles(whiteTiles).setStartingArea((Node) findViewById(R.id.startAreaP1));
            playerW.getStartingArea().setOccupied(true).setOnDragListener(dragListener);
            playerB = new Player(intent.getStringExtra("USER_NAME2"));
            playerB.setPlayerWhite(false).setTiles(blackTiles).setStartingArea((Node) findViewById(R.id.startAreaP2));
            playerB.getStartingArea().setOccupied(true).setOnDragListener(dragListener);
            changePlayerState(playerB, false);
            currentPlayer = playerW;
            //endregion
            Toast.makeText(MultiPlayerActivity.this, "Welcome " + playerB.getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MultiPlayerActivity.this,  playerW.getName() + " vs " + playerB.getName(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText( MultiPlayerActivity.this, "Muehle!", Toast.LENGTH_LONG ).show();
                        /*
                        AlertDialog.Builder m = new AlertDialog.Builder(MultiPlayerActivity.this);
                        m.setTitle( "Mühle" );
                        m.setNegativeButton( "ja", null);
                        AlertDialog alert2 = m.create();
                        alert2.show();
*/
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
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(MultiPlayerActivity.this);
        dlgBuilder.setTitle( Html.fromHtml("<font color='#190707'>Mühle</font>"));
        dlgBuilder.setMessage(Html.fromHtml( "<font color='#190707'>Sind sie sicher dass Sie das Spiel verlassen wollen?</font>"));
        dlgBuilder.setCancelable(true);
        dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(MultiPlayerActivity.this, "Das Spiel wurde beendet", Toast.LENGTH_SHORT).show();

                Intent secondAct = new Intent( MultiPlayerActivity.this, SecondActivity.class );
                secondAct.putExtra( "USER_NAME", playerW.getName() );
                startActivity( secondAct );
                finish();
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
