package com.yuxuanzhang.hexagonal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.Shape;
import android.widget.LinearLayout;

public abstract class HexagonalFragmentView extends LinearLayout {
	protected int size;
	protected int height;
	protected int width;
	
	protected int borderColor_a;
	protected int borderColor_r;
	protected int borderColor_g;
	protected int borderColor_b;
	protected int innerColor_a;
	protected int innerColor_r;
	protected int innerColor_g;
	protected int innerColor_b;
	protected int strokeWidth;
	
	private Shape shape;
	
	private LinearLayout controlView;
	private boolean hasControlView;
	
	protected HexagonalFragmentView(Context context, boolean hasControlView) {
		super(context);
		this.hasControlView = hasControlView;
	}

	
	protected void setPaint(int borderColor_a, int borderColor_r, int borderColor_g, int borderColor_b, 
			int innerColoer_a, int innerColoer_r, int innerColoer_g, int innerColoer_b, int strokeWidth) {
		this.innerColor_a = innerColoer_a;
		this.innerColor_r = innerColoer_r;
		this.innerColor_g = innerColoer_g;
		this.innerColor_b = innerColoer_b;
		this.borderColor_a = borderColor_a;
		this.borderColor_r = borderColor_r;
		this.borderColor_g = borderColor_g;
		this.borderColor_b = borderColor_b;
		this.strokeWidth = strokeWidth;
	}
	
	
	@SuppressLint("NewApi")
	protected void generateBackground() {
        this.setBackground(new ShapeDrawable(getShape()) {
        	@Override
        	protected void onDraw(Shape shape, Canvas canvas, Paint fillpaint) {
        		Paint borderPaint = new Paint(fillpaint);
        		borderPaint.setARGB(borderColor_a, borderColor_r, borderColor_g, borderColor_b);
        		borderPaint.setStyle(Style.STROKE);
        		borderPaint.setStrokeWidth(strokeWidth);
        		
        		fillpaint.setStyle(Style.FILL);
        		fillpaint.setARGB(innerColor_a, innerColor_r, innerColor_g, innerColor_b);
        		
        		shape.draw(canvas, fillpaint);
        		shape.draw(canvas, borderPaint);
        	}
        });
        
		if (hasControlView) {
			this.controlView = new LinearLayout(this.getContext());
			this.setPadding(0, 0, 0, 0);
			controlView.setLayoutParams(new LayoutParams(width, size));
			addView(controlView);
		}
	}

	public Shape getShape() {
		if (shape == null) {
			Path path = pathHelper();
			shape = new PathShape(path, width, height); 
		}
		return shape;
	}
	
	protected abstract Path pathHelper();
	
	public LinearLayout getControlView() {
		return controlView;
	}
	
//	protected void defaultStyle() {
//		this.innerColor_a = 125;
//		this.innerColor_r = 255;
//		this.innerColor_g = 0;
//		this.innerColor_b = 0;
//		this.borderColor_a = 50;
//		this.borderColor_r = 32;
//		this.borderColor_g = 27;
//		this.borderColor_b = 231;
//		this.strokeWidth = 5;
//	}
	
	
}
