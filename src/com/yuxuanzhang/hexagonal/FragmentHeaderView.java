package com.yuxuanzhang.hexagonal;

import android.content.Context;
import android.graphics.Path;

public class FragmentHeaderView extends HexagonalFragmentView {
	private boolean centerEmpty;
	
	private FragmentHeaderView(Context context) {
		super(context, false);
	}


	public FragmentHeaderView(Context context, int size, int strokeWidth, boolean centerEmpty,
			int borderColor_a, int borderColor_r, int borderColor_g, int borderColor_b, 
			int innerColoer_a, int innerColoer_r, int innerColoer_g, int innerColoer_b) {
		super(context, false);
		this.size = size;
		this.height = (int) Math.floor(0.5 * size);
		this.width = (int) Math.floor(1.732 * size);
		this.setLayoutParams(new LayoutParams(width, height));
		this.centerEmpty = centerEmpty;
		
		setPaint(borderColor_a, borderColor_r, borderColor_g, borderColor_b, innerColoer_a, 
				innerColoer_r, innerColoer_g, innerColoer_b, strokeWidth);
		generateBackground();
	}

	@Override
	protected Path pathHelper() {
		Path path = new Path();
		
		if (!centerEmpty) {
			path.moveTo(0, height);
			path.lineTo(width / 2, 0);
			path.lineTo(width, height);
		}
		
		return path;
	}

}
