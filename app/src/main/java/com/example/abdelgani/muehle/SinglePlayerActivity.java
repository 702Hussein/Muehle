package com.example.abdelgani.muehle;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
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

	boolean tutorialSwitcher_Video = true;

	private static final String TAG = "*****"+SinglePlayerActivity.class.getSimpleName();

    View node_outer_topLeft, node_outer_topMid, node_outer_topRight, node_outer_midLeft, node_outer_midRight, node_outer_botLeft, node_outer_botMid, node_outer_botRight;
    View node_middle_topLeft, node_middle_topMid, node_middle_topRight, node_middle_midLeft, node_middle_midRight, node_middle_botLeft, node_middle_botMid, node_middle_botRight;
	View node_inner_topLeft, node_inner_topMid, node_inner_topRight, node_inner_midLeft, node_inner_midRight, node_inner_botLeft, node_inner_botMid, node_inner_botRight;
    View gameBoard;
    Button tileP1_1, tileP1_2, tileP1_3, tileP1_4, tileP1_5, tileP1_6, tileP1_7, tileP1_8, tileP1_9;
	Button tileP2_1, tileP2_2, tileP2_3, tileP2_4, tileP2_5, tileP2_6, tileP2_7, tileP2_8, tileP2_9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_player );
Log.d(TAG,"Singleplayer startup");
/*
		gameBoard = findViewById(R.id.imgV_Gameboard);
		Point displaySize = new Point();
		getWindowManager().getDefaultDisplay().getSize(displaySize);
		int size = displaySize.x > displaySize.y ? displaySize.y : displaySize.x;
		gameBoard.getLayoutParams().height = size;
		gameBoard.getLayoutParams().width = size;
		*/


        try {
			node_outer_topLeft = findViewById(R.id.node_outer_topLeft);
			node_outer_topLeft.setOnDragListener(dragListener);

			tileP1_1 = findViewById(R.id.tileP1_1);
			tileP1_1.setOnLongClickListener(longClickListener);

		}catch (Exception exc){
        	exc.getMessage();
		}
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (tutorialSwitcher_Video) {
				ClipData clipData = ClipData.newPlainText("", "");
				View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
				v.startDrag(clipData, dragShadowBuilder, v, 0);
			}
			else {
				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
				//ClipData.Item item = new ClipData.Item(Integer.toString( v.getId()));
				String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData clipData = new ClipData(v.getTag().toString(), mimeTypes, item);
				View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
				v.startDrag(clipData, dragShadow, v, 0);
			}

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
						if (v.getId() == R.id.node_outer_topLeft)
							v.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
						return true;
					case DragEvent.ACTION_DRAG_LOCATION:    //enter the listening area (with ACTION_DRAG_ENTERED

						break;
					case DragEvent.ACTION_DROP:                //drop item within the listening area bounds
						ClipData.Item item = event.getClipData().getItemAt(0);
						if (v.getId() == R.id.node_outer_topLeft) {
							v.setBackground(( findViewById(Integer.parseInt(item.getText().toString()))).getBackground());


						}
						break;
					case DragEvent.ACTION_DRAG_ENDED:        //right after ACTION_DROP
						v.getBackground().clearColorFilter();
						break;
					case DragEvent.ACTION_DRAG_ENTERED:        //entered the listening area ( with ACTION_DRAG_ENTERED

						break;
					case DragEvent.ACTION_DRAG_EXITED:        //item left the listening area

						break;
					default:
						break;

				}


				return false;
			} catch (Exception exc){
				exc.getMessage();
				throw exc;
			}
		}
	};


}
