package com.yuxuanzhang.hexagonal.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hexagonalactivity.R;
import com.yuxuanzhang.hexagonal.HexagonalLayout;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout tv = (LinearLayout) findViewById(R.id.layout_main); 

        int size = 100;
        
        String[] text = new String[48];
        
        boolean[][] test = new boolean[8][6];
        for (int i = 0; i < test.length; i++) {
        	for (int j = 0; j < test[0].length; j++) {
        		test[i][j] = true;
        		text[6 * i + j] = "" + (6 * i + j);
        	}
        }
        test[1][1] = false;
        test[0][3] = false;
        test[3][0] = false;
        test[0][5] = false;
        test[3][4] = false;
        test[4][3] = false;
        test[7][0] = false;
        test[7][5] = false;

        HexagonalLayout mainLayout = new HexagonalLayout(this, size, test);
        tv.addView(mainLayout);

        LinearLayout[][] layouts = mainLayout.getControlView();
        
        for (int i = 0; i < layouts.length; i++) {
        	for (int j = 0; j < layouts[0].length; j++) {
        		LinearLayout layout = layouts[i][j];
        		if (layout != null) {
        			final String content = text[i * 6 + j];
        			TextView view = new TextView(layout.getContext());
        			view.setText(content);
        			layout.addView(view);
        			
        			OnClickListener listener = new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Toast.makeText(MainActivity.this, "You clicked me: " + content, Toast.LENGTH_SHORT / 4).show();
						}
					};
					
					layout.setOnClickListener(listener);
					view.setOnClickListener(listener);
        		}
        	}
        }
        
////		checkSingle(tv, 200);
//       tv.addView(new FragmentHeaderView(this, size, 10, false, 125, 255, 0, 0, 50, 32, 27, 231));
        
	}
	
	public void checkSingle(LinearLayout tv, int size) {
        tv.setBackground(new ShapeDrawable(generateGivenPath(size)) {
        	
        	@Override
        	protected void onDraw(Shape shape, Canvas canvas, Paint fillpaint) {
        		Paint borderPaint = new Paint(fillpaint);
        		borderPaint.setARGB(255, 255, 0, 0);
        		borderPaint.setStyle(Style.STROKE);
        		borderPaint.setStrokeWidth(12);
        		
        		fillpaint.setStyle(Style.FILL);
        		fillpaint.setARGB(255, 0, 0, 255);
        		
        		shape.draw(canvas, fillpaint);
        		shape.draw(canvas, borderPaint);
        	}
        });
	}
	
	public static Shape generateGivenPath(float size) {
        Path path = new Path();
        float width = (float) (size * 1.5); 
        float height = (float) (size * 1.732); 
        float strokeWidth = 10;
        
		path.moveTo(width, height);
		path.lineTo(0, size);
		path.lineTo(0, 0);
		path.lineTo(0 - strokeWidth, 0);
		path.lineTo(0 - strokeWidth, height + strokeWidth);
		path.lineTo(width, height + strokeWidth);
	    
        return new PathShape(path, width, height); 
	}
	
	public static Shape generateMainPath(float size) {
        Path path = new Path();
        float width = (float) (size * 1.5); 
        float height = (float) (size * 1.732); 
        float strokeWidth = 10;
        
        path.lineTo(0, size);
        path.lineTo(width / 2, height);
        path.lineTo(width, size);
        path.lineTo(width, 0);
        path.lineTo(width + strokeWidth, 0);
        path.lineTo(width + strokeWidth, height + strokeWidth);
        path.lineTo(width/2, height + strokeWidth);
        path.lineTo(width/2, height);
        path.lineTo(width, size);
        path.lineTo(width, 0);
        
        return new PathShape(path, width, height); 
	}
	
	public static Shape generateLeftPath(float size) {
		Path path = new Path();
		float height = (int) Math.floor(1.5 * size);
		float width = (int) Math.floor(0.866 * size);
		int strokeWidth = 10;
		
		path.moveTo(0, height);
		path.lineTo(width, size);
		path.lineTo(width, 0);
		path.lineTo(width + strokeWidth, 0);
		path.lineTo(width + strokeWidth, height + strokeWidth);
		path.lineTo(0, height + strokeWidth);
		path.lineTo(0, height);
		
		return new PathShape(path, width, height);
	}
	
	public static Shape generateRightPath(float size) {
		Path path = new Path();
		float height = (int) Math.floor(1.5 * size);
		float width = (int) Math.floor(0.866 * size);
		
		path.moveTo(width, height);
		path.lineTo(0, size);
		path.lineTo(0, 0);
		path.lineTo(0 - 10, 0);
		path.lineTo(0 - 10, height + 10);
		path.lineTo(width, height + 10);
		
		return new PathShape(path, width, height);
	}
	
	public static Shape generateLeftCorner(float size) {
		Path path = new Path();
		float height = (int) Math.floor(1.5 * size);
		float width = (int) Math.floor(0.866 * size);
		
		path.moveTo(width, 0);
		path.lineTo(width, size);
		
		return new PathShape(path, width, height);
	}
	
	public static Shape generateHeader(float size) {
		Path path = new Path();
		float height = (int) Math.floor(0.5 * size);
		float width = (int) Math.floor(1.732 * size);
		
		path.moveTo(0, height);
		path.lineTo(width / 2, 0);
		path.lineTo(width, height);
		
		return new PathShape(path, width, height);
	}
}
