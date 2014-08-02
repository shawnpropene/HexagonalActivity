package com.yuxuanzhang.hexagonal;

import android.content.Context;
import android.graphics.Path;

public class FragmentEmptyView extends HexagonalFragmentView {

	private FragmentEmptyView(Context context) {
		super(context, false);
	}
	
	public FragmentEmptyView(Context context, int size) {
		super(context, false);
		this.size = size;
		this.height = (int) Math.floor(0.5 * size);
		this.width = (int) Math.floor(0.866 * size);
		this.setLayoutParams(new LayoutParams(width, height));
		
//		defaultStyle();
		generateBackground();
	}

	@Override
	protected Path pathHelper() {
		return new Path();
	}

}
