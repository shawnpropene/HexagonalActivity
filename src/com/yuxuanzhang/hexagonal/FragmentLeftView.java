package com.yuxuanzhang.hexagonal;

import android.content.Context;
import android.graphics.Path;

public class FragmentLeftView extends HexagonalFragmentView {
	private boolean centerEmpty;
	private boolean rightEmpty;
	
	private FragmentLeftView(Context context) {
		super(context, false);
	}

	public FragmentLeftView(Context context, int size, int strokeWidth, boolean centerEmpty, boolean rightEmpty,
			int borderColor_a, int borderColor_r, int borderColor_g, int borderColor_b, 
			int innerColoer_a, int innerColoer_r, int innerColoer_g, int innerColoer_b) {
		super(context, false);
		this.size = size;
		this.height = (int) Math.floor(size * 1.5);
		this.width = (int) Math.floor(size * 0.866);
		this.setLayoutParams(new LayoutParams(width, height));
		this.centerEmpty = centerEmpty;
		this.rightEmpty = rightEmpty;
		
		setPaint(borderColor_a, borderColor_r, borderColor_g, borderColor_b, innerColoer_a, 
				innerColoer_r, innerColoer_g, innerColoer_b, strokeWidth);
		generateBackground();
	}
	
	protected Path pathHelper() {
        Path path = new Path();
        
        if (!rightEmpty) {
	        path.moveTo(0, height);
	        path.lineTo(width, size);
        }
        if (!centerEmpty) {
        	path.moveTo(width, size);
        	path.lineTo(width, 0);
        	if (!centerEmpty)
        		path.lineTo(width + strokeWidth, 0);
        } 
        if (!rightEmpty) {
        	path.lineTo(width + strokeWidth, size);
            path.lineTo(width + strokeWidth, height + strokeWidth);
        	path.lineTo(0, height + strokeWidth);
            path.lineTo(0, height);
        }
        
        return path;
	}
}

