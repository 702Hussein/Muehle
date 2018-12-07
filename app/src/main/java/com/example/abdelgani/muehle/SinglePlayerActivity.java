package com.example.abdelgani.muehle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

public class SinglePlayerActivity extends AppCompatActivity {

	private static final String TAG = "*****"+SinglePlayerActivity.class.getSimpleName();

    View node_outer_topLeft, node_outer_topMid, node_outer_topRight, node_outer_midLeft, node_outer_midRight, node_outer_botLeft, node_outer_botMid, node_outer_botRight;
    View node_middle_topLeft, node_middle_topMid, node_middle_topRight, node_middle_midLeft, node_middle_midRight, node_middle_botLeft, node_middle_botMid, node_middle_botRight;
	View node_inner_topLeft, node_inner_topMid, node_inner_topRight, node_inner_midLeft, node_inner_midRight, node_inner_botLeft, node_inner_botMid, node_inner_botRight;
    View gameBoard;
    Button tileP1_1, tileP1_2, tileP1_3, tileP1_4, tileP1_5, tileP1_6, tileP1_7, tileP1_8, tileP1_9;
	Button tileP2_1, tileP2_2, tileP2_3, tileP2_4, tileP2_5, tileP2_6, tileP2_7, tileP2_8, tileP2_9;
	int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_player );
Log.d(TAG,"Singleplayer startup");

        try {
        	//region get Offset
			offset = (int)(getResources().getDimension(R.dimen.nodeSize) - getResources().getDimension(R.dimen.tileSize) + 0.5);
			//endregion
			//region assign nodes
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
			//endregion
			//region set DragListeners to nodes
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
			//endregiont
			// region assign tiles
			tileP1_1 = findViewById(R.id.tileP1_1);
			tileP1_2 = findViewById(R.id.tileP1_2);
			tileP1_3 = findViewById(R.id.tileP1_3);
			tileP1_4 = findViewById(R.id.tileP1_4);
			tileP1_5 = findViewById(R.id.tileP1_5);
			tileP1_6 = findViewById(R.id.tileP1_6);
			tileP1_7 = findViewById(R.id.tileP1_7);
			tileP1_8 = findViewById(R.id.tileP1_8);
			tileP1_9 = findViewById(R.id.tileP1_9);
			tileP2_1 = findViewById(R.id.tileP2_1);
			tileP2_2 = findViewById(R.id.tileP2_2);
			tileP2_3 = findViewById(R.id.tileP2_3);
			tileP2_4 = findViewById(R.id.tileP2_4);
			tileP2_5 = findViewById(R.id.tileP2_5);
			tileP2_6 = findViewById(R.id.tileP2_6);
			tileP2_7 = findViewById(R.id.tileP2_7);
			tileP2_8 = findViewById(R.id.tileP2_8);
			tileP2_9 = findViewById(R.id.tileP2_9);
			//endregion
			//region set LongClickListeners to tiles
			tileP1_1.setOnLongClickListener(longClickListener);
			tileP1_2.setOnLongClickListener(longClickListener);
			tileP1_3.setOnLongClickListener(longClickListener);
			tileP1_4.setOnLongClickListener(longClickListener);
			tileP1_5.setOnLongClickListener(longClickListener);
			tileP1_6.setOnLongClickListener(longClickListener);
			tileP1_7.setOnLongClickListener(longClickListener);
			tileP1_8.setOnLongClickListener(longClickListener);
			tileP1_9.setOnLongClickListener(longClickListener);
			tileP2_1.setOnLongClickListener(longClickListener);
			tileP2_2.setOnLongClickListener(longClickListener);
			tileP2_3.setOnLongClickListener(longClickListener);
			tileP2_4.setOnLongClickListener(longClickListener);
			tileP2_5.setOnLongClickListener(longClickListener);
			tileP2_6.setOnLongClickListener(longClickListener);
			tileP2_7.setOnLongClickListener(longClickListener);
			tileP2_8.setOnLongClickListener(longClickListener);
			tileP2_9.setOnLongClickListener(longClickListener);
			//endregion


		}catch (Exception exc){
        	exc.getMessage();
		}
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {

				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
				String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData clipData = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
				View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
				v.startDrag(clipData, dragShadow, v, 0);


			return true;
		}
	};

    View.OnDragListener dragListener = new View.OnDragListener() {
		@Override
		public boolean onDrag(View v, DragEvent event) {
			try {
				int action = event.getAction();
				Log.d(TAG,"onDrag;	id: " + v.getId() + ";	action: " + action);
				switch (action) {

					case DragEvent.ACTION_DRAG_STARTED:        //start to drag an item

						//return (boolean)v.getTag();

					case DragEvent.ACTION_DRAG_LOCATION:    //enter the listening area (with ACTION_DRAG_ENTERED
						break;

					case DragEvent.ACTION_DROP:                //drop item within the listening area bounds

						View tile = (View) event.getLocalState();
						tile.animate().x(v.getX()).y(v.getY()).setDuration(500).start();

						break;

					case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
							v.setBackground(null);
						break;

					case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED
						try {
							Toast toast = Toast.makeText(getApplicationContext(), (int)v.getId(),Toast.LENGTH_SHORT);
							toast.show();
							Drawable drawable = getResources().getDrawable(R.drawable.white_tile, null);
							v.setBackground(drawable);
						}catch (Exception e){
							Log.d("",e.getMessage());
						}
						break;
					case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area
							v.setBackground(null);
						break;
					default:
						break;

				}


				return true;
			} catch (Exception exc){
				exc.getMessage();
				throw exc;
			}
		}
	};


}
