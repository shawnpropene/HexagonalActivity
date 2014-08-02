package com.yuxuanzhang.hexagonal;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class HexagonalLayout extends LinearLayout {
	private int size;
	private boolean[][] buckets;
	private int row;
	private int column;
	private LinearLayout[] rowView;
	private Context context;
	private int[][][] inner_rgba;
	private int[][][] stroke_rgba;
	private int strokeWidth;
	private LinearLayout[][] controlViews;
	
	public HexagonalLayout(Context context, int size, boolean[][] buckets) {
		this(context, size, 10, buckets, null, null, null, null, null, null, null, null);
	}
	
	public HexagonalLayout(Context context, int size, int strokeWidth, boolean[][] buckets, int[][] inner_r, int[][] inner_g,
			int[][] inner_b, int[][] inner_a, int[][] stroke_r, int[][] stroke_g, int[][] stroke_b, int[][] stroke_a) {
		super(context);

		if (buckets.length <= 0 || buckets[0].length <= 0) {
			throw new IllegalStateException("Non-positive buckets length is not allowed. ");
		}
		
		this.size = size;
		this.strokeWidth = strokeWidth;
		this.buckets = buckets;
		this.row = buckets.length;
		this.column = buckets[0].length;
		this.context = context;
		
		this.controlViews = new LinearLayout[buckets.length][buckets[0].length];
		
		this.inner_rgba = new int[4][][];
		this.stroke_rgba = new int[4][][];

		if (inner_a == null) {
			this.inner_rgba[0] = setDefaultValue(buckets.length, buckets[0].length, 125);
		} else {
			this.inner_rgba[0] = inner_a;
		}
		if (inner_r == null) {
			this.inner_rgba[1] =  setDefaultValue(buckets.length, buckets[0].length, 255);;
		} else {
			this.inner_rgba[1] = inner_r;
		}
		if (inner_g == null) {
			this.inner_rgba[2] =  setDefaultValue(buckets.length, buckets[0].length, 0);;
		} else {
			this.inner_rgba[2] = inner_g;
		}
		if (inner_b == null) {
			this.inner_rgba[3] =  setDefaultValue(buckets.length, buckets[0].length, 0);;
		} else {
			this.inner_rgba[3] = inner_b;
		}
		
		if (stroke_a == null) {
			this.stroke_rgba[0] = setDefaultValue(buckets.length, buckets[0].length, 50);
		} else {
			this.stroke_rgba[0] = stroke_a;
		}
		if (stroke_r == null) {
			this.stroke_rgba[1] =  setDefaultValue(buckets.length, buckets[0].length, 32);;
		} else {
			this.stroke_rgba[1] = stroke_r;
		}
		if (stroke_g == null) {
			this.stroke_rgba[2] =  setDefaultValue(buckets.length, buckets[0].length, 27);;
		} else {
			this.stroke_rgba[2] = stroke_g;
		}
		if (stroke_b == null) {
			this.stroke_rgba[3] =  setDefaultValue(buckets.length, buckets[0].length, 231);;
		} else {
			this.stroke_rgba[3] = stroke_b;
		}

		setUp();
	}
	
	private int[][] setDefaultValue(int row, int col, int value) {
		int[][] ret = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) 
				ret[i][j] = value;
		}
		return ret;
	}
	
	private void setUp() {
		int height = (int) Math.floor(size * (2 * row + 1) * 0.866);
		int width = (int) Math.floor(size * (1.732 * column + 1));
		this.setLayoutParams(new LayoutParams(width, height));
		this.setOrientation(LinearLayout.VERTICAL);
		this.rowView = new LinearLayout[row + 1];
		for (int i = 0; i <= row; i++) {
			rowView[i] = new LinearLayout(context);
			rowView[i].setOrientation(LinearLayout.HORIZONTAL);
			rowView[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		
		createView(buckets, size);
		for (int i = 0; i < rowView.length; i++) {
			addView(rowView[i]);
		}
		
		this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				double height = 1.5 * size;
				double width = 1.732 * size;
				int rawlayer = (int) Math.floor(x / height);
				int rawcolumn = 0;
				if (rawlayer % 2 == 0) {
					rawcolumn = (int) Math.floor((y - width / 2.0) / width);
				} else {
					rawcolumn = (int) Math.floor(y / width);
				}
				return false;
			}
		});
	}
	
	private HexagonalLayout(Context context) {
		super(context);
	}
	
	private void createView(boolean[][] buckets, int size) {
		createHead(buckets[0], rowView[0]);
		for (int i = 1; i < row; i++) {
			createBody(buckets[i - 1], buckets[i], i, rowView[i]);
		}
		createTail(buckets[row - 1], row, rowView[row]);
	}
	
	
	private void createHead(boolean[] buckets, LinearLayout view) {
		view.addView(new FragmentEmptyView(context, size));
		for (int i = 0; i < column; i++) {
			view.addView(new FragmentHeaderView(context, size, strokeWidth, !buckets[i], inner_rgba[0][0][i], 
					inner_rgba[1][0][i], inner_rgba[2][0][i], inner_rgba[3][0][i], stroke_rgba[0][0][i], stroke_rgba[1][0][i],
					stroke_rgba[2][0][i], stroke_rgba[3][0][i]));
		}
	}
	
	private void createTail(boolean[] buckets, int rowNo, LinearLayout view) {
		if (rowNo % 2 == 1) {
			view.addView(new FragmentEmptyView(context, size));
		}
		for (int i = 0; i < column; i++) {
			HexagonalFragmentView fview = new FragmentMain0L0RView(context, size, strokeWidth, !buckets[i], inner_rgba[0][rowNo - 1][i], 
					inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
					stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]);
			controlViews[rowNo - 1][i] = fview.getControlView();
			view.addView(fview);
		}
	}
	
	private void createBody(boolean[] buckets, boolean[] nextLine, int rowNo, LinearLayout view) {
		for (int i = 0; i < column; i++) {
			boolean left = false;
			boolean right = false;
			
			if (rowNo % 2 == 1) {
				left = nextLine[i];
				if (i + 1 < column) {
					right = nextLine[i + 1];
				}
			} else {
				right = nextLine[i];
				if (i > 0) {
					left = nextLine[i - 1];
				}
			}
			
			if (rowNo % 2 == 1 && i == 0) {
				view.addView(new FragmentLeftView(context, size, strokeWidth, !buckets[0], !left, inner_rgba[0][rowNo - 1][i], 
						inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
						stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]));
			}
			
			view.addView(generateMainView(buckets[i], left, right, rowNo, i));
			
			if (rowNo % 2 == 0 && i == column - 1) {
				// Add the upper right coner of the next row.
				if (right)
					view.addView(new FragmentMain1L0RView(context, size, strokeWidth, true, inner_rgba[0][rowNo - 1][i], 
							inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
							stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]));
			}
		}
	}
	
	private HexagonalFragmentView generateMainView(boolean hasContent, boolean hasLeft, boolean hasRight, int rowNo, int i) {
		HexagonalFragmentView view = null;
		if (hasLeft && hasRight) {
			view = new FragmentMain1L1RView(context, size, strokeWidth, !hasContent, inner_rgba[0][rowNo - 1][i], 
					inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
					stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]);
		}
		else if (hasLeft && !hasRight) {
			view = new FragmentMain1L0RView(context, size, strokeWidth, !hasContent, inner_rgba[0][rowNo - 1][i], 
					inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
					stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]);
		}
		else if (!hasLeft && hasRight) {
			view = new FragmentMain0L1RView(context, size, strokeWidth, !hasContent, inner_rgba[0][rowNo - 1][i], 
					inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
					stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]);
		} else { 
			view = new FragmentMain0L0RView(context, size, strokeWidth, !hasContent, inner_rgba[0][rowNo - 1][i], 
				inner_rgba[1][rowNo - 1][i], inner_rgba[2][rowNo - 1][i], inner_rgba[3][rowNo - 1][i], stroke_rgba[0][rowNo - 1][i], 
				stroke_rgba[1][rowNo - 1][i], stroke_rgba[2][rowNo - 1][i], stroke_rgba[3][rowNo - 1][i]);
		}
		controlViews[rowNo - 1][i] = view.getControlView();
		return view;
	}
	
	public LinearLayout[][] getControlView() {
		return controlViews;
	}
}
