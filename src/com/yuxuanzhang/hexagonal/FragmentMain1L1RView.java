package com.yuxuanzhang.hexagonal;

import android.content.Context;
import android.graphics.Path;

public class FragmentMain1L1RView extends HexagonalFragmentView {
	private boolean centerEmpty;
	
	private FragmentMain1L1RView(Context context) {
		super(context, false);
	}

	public FragmentMain1L1RView(Context context, int size, int strokeWidth, boolean centerEmpty,
			int borderColor_a, int borderColor_r, int borderColor_g, int borderColor_b, 
			int innerColoer_a, int innerColoer_r, int innerColoer_g, int innerColoer_b) {
		super(context, !centerEmpty);
		this.size = size;
		this.height = (int) Math.floor(size * 1.5);
		this.width = (int) Math.floor(size * 1.732);
		this.setLayoutParams(new LayoutParams(width, height));
		this.centerEmpty = centerEmpty;
		
		setPaint(borderColor_a, borderColor_r, borderColor_g, borderColor_b, innerColoer_a, 
				innerColoer_r, innerColoer_g, innerColoer_b, strokeWidth);
		this.centerEmpty = centerEmpty;
		generateBackground();
	}
	
	protected Path pathHelper() {
        Path path = new Path();
        
        if (!centerEmpty)
        	path.lineTo(0, size);
        else 
        	path.moveTo(0, size);
        path.lineTo(width / 2, height);
        path.lineTo(width, size);
        if (!centerEmpty) {
	        path.lineTo(width, 0);
	        path.lineTo(width, 0 - strokeWidth);
	        path.lineTo(0 - strokeWidth, 0 - strokeWidth);
	        path.lineTo(0 - strokeWidth, height + strokeWidth);
	        path.lineTo(width + strokeWidth, height + strokeWidth);
	        path.lineTo(width + strokeWidth, 0 - strokeWidth);
	        path.lineTo(0 - strokeWidth, 0 - strokeWidth);
        } else {
        	path.lineTo(width + strokeWidth, size);
        	path.lineTo(width + strokeWidth, height + strokeWidth);
        	path.lineTo(0 - strokeWidth, height + strokeWidth);
        	path.lineTo(0 - strokeWidth, size);
        	path.lineTo(0, size);
        }
        
        return path;
	}
}
