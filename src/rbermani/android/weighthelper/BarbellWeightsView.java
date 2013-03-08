/*******************************************************************************
 * Copyright (c) 2010 Robert Bermani.
 * All rights reserved. This program and its accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which is included with this distribution and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Robert Bermani - initial API and implementation
 ******************************************************************************/
package rbermani.android.weighthelper;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class BarbellWeightsView extends View {

	private int width, height;
	private int x, y;
	private float displayScaleFactor;
	private float dpiScaleFactor;
	private float bitmapScaleFactor;

	private BitmapDrawable[] weightStackup;
	private BitmapDrawable legend;
	private LayerDrawable stackUp;

	private long num45Plates;
	private long num35Plates;
	private long num25Plates;
	private long num10Plates;
	private long num5Plates;
	private long num2Plates;


	private Resources res;

	private Bitmap bmpFortyFive;
	private Bitmap bmpThirtyFive;
	private Bitmap bmpTwentyFive;
	private Bitmap bmpTen;
	private Bitmap bmpFive;
	private Bitmap bmpTwo;
	private Bitmap bmpBackground;
	private Bitmap bmpLegend;

	public BarbellWeightsView(Context context, AttributeSet attrs){
		super(context, attrs);
		
		//this.setId(attrs.getIdAttributeResourceValue(0));
		if (!isInEditMode())
			initComponents();
		
	}
	public BarbellWeightsView(Context context, AttributeSet attrs, int defStyle)
	{
		super( context,  attrs, defStyle);
		
		initComponents();
	}
	public BarbellWeightsView(Context context) {
		super(context);

		if (!isInEditMode())
			initComponents();
	}

	private void initComponents() {
		res = getContext().getResources();
		DisplayMetrics metrics = new DisplayMetrics();
		Activity mainActivity = (Activity) getContext();

		mainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);


		// Grab bitmap resources
		bmpFortyFive = BitmapFactory.decodeResource(res, R.drawable.fortyfive);
		bmpThirtyFive = BitmapFactory.decodeResource(res, R.drawable.thirtyfive);
		bmpTwentyFive = BitmapFactory.decodeResource(res, R.drawable.twentyfive);
		bmpTen = BitmapFactory.decodeResource(res, R.drawable.ten);
		bmpFive = BitmapFactory.decodeResource(res, R.drawable.five);
		bmpTwo = BitmapFactory.decodeResource(res, R.drawable.two);
		bmpBackground = BitmapFactory.decodeResource(res,R.drawable.barbell);
		bmpLegend = BitmapFactory.decodeResource(res, R.drawable.legend);
		legend = new BitmapDrawable(bmpLegend);

		bitmapScaleFactor = (float)metrics.widthPixels / (float)bmpBackground.getWidth();
		dpiScaleFactor = metrics.densityDpi / 300.0f;
		displayScaleFactor = (float) dpiScaleFactor * bitmapScaleFactor;
		buildStackUp(45);	
	}
	private void calculatePlateCounts(int targetWeight) {

		if (targetWeight <= 45){
			targetWeight = 0;
		} else {
			targetWeight = (targetWeight - 45)/2;
		}
		num45Plates = num35Plates = num25Plates = 0;
		num10Plates = num5Plates = num2Plates = 0;

		while (targetWeight >= 45) {
			num45Plates++;
			targetWeight -= 45;

		}

		while (targetWeight >= 35) {
			num35Plates++;
			targetWeight -= 35;

		}

		while (targetWeight >= 25) {
			num25Plates++;
			targetWeight -= 25;
		}

		while (targetWeight >= 10){
			num10Plates++;
			targetWeight -= 10;
		}

		while (targetWeight >= 5) {
			num5Plates++;
			targetWeight -= 5;
		}

		if (targetWeight > 0) {
			num2Plates++;
			targetWeight = 0;
		}

	}

	public void buildStackUp(int targetWeight){

		int runningX = 0;
		int runningY = 0;
		int barOffset = (int)(825.0*dpiScaleFactor);
		int halfPoint45 = (bmpBackground.getHeight()/2+(bmpFortyFive.getHeight()/2));
		int halfPoint35 = (bmpBackground.getHeight()/2+(bmpThirtyFive.getHeight()/2));
		int halfPoint25 = (bmpBackground.getHeight()/2+(bmpTwentyFive.getHeight()/2));
		int halfPoint10 = (bmpBackground.getHeight()/2+(bmpTen.getHeight()/2));
		int halfPoint5 = (bmpBackground.getHeight()/2+(bmpFive.getHeight()/2));
		int halfPoint2 = (bmpBackground.getHeight()/2+(bmpTwo.getHeight()/2));

		calculatePlateCounts(targetWeight);

		ArrayList<BitmapDrawable> bitmapPlates = new ArrayList<BitmapDrawable>();

		BitmapDrawable tmp;

		Rect rctFortyFive = new Rect();
		Rect rctThirtyFive= new Rect();
		Rect rctTwentyFive= new Rect();
		Rect rctTen= new Rect();
		Rect rctFive= new Rect();
		Rect rctTwo= new Rect();
		Rect rctBackground = new Rect();	

		rctBackground.set(0, 0,
				bmpBackground.getWidth(),
				bmpBackground.getHeight());
		tmp = new BitmapDrawable(res, bmpBackground);
		tmp.setBounds(rctBackground);
		bitmapPlates.add(tmp);

		runningX = barOffset;

		if (num45Plates > 0){
			runningY = halfPoint45;
			rctFortyFive.set(runningX-bmpFortyFive.getWidth(),
					runningY-bmpFortyFive.getHeight(), 
					runningX,
					runningY);

			while (num45Plates > 0){
				tmp = new BitmapDrawable(res, bmpFortyFive);
				tmp.setBounds(rctFortyFive);

				bitmapPlates.add(tmp);

				runningX -= (bmpFortyFive.getWidth());
				rctFortyFive.offset(-bmpFortyFive.getWidth(), 0);

				num45Plates--;
			}
		}

		if (num35Plates > 0){

			runningY = halfPoint35;
			rctThirtyFive.set(runningX-bmpThirtyFive.getWidth(),
					runningY-bmpThirtyFive.getHeight(), 
					runningX,
					runningY);

			//			if (runningX != barOffset)
			//				runningX -= bmpThirtyFive.getWidth();

			while (num35Plates > 0){
				tmp = new BitmapDrawable(res, bmpThirtyFive);
				tmp.setBounds(rctThirtyFive);

				bitmapPlates.add(tmp);


				runningX -= (bmpThirtyFive.getWidth());
				rctThirtyFive.offset(-bmpThirtyFive.getWidth(), 0);

				num35Plates--;
			}
		}
		if (num25Plates > 0){
			runningY = halfPoint25;
			rctTwentyFive.set(runningX-bmpTwentyFive.getWidth(),
					runningY-bmpTwentyFive.getHeight(), 
					runningX,
					runningY);

			while (num25Plates > 0){

				tmp = new BitmapDrawable(res, bmpTwentyFive);
				tmp.setBounds(rctTwentyFive);

				bitmapPlates.add(tmp);

				runningX -= (bmpTwentyFive.getWidth());
				rctTwentyFive.offset(-bmpTwentyFive.getWidth(), 0);

				num25Plates--;
			}
		}
		if (num10Plates > 0){
			runningY = halfPoint10;
			rctTen.set(runningX-bmpTen.getWidth(),
					runningY-bmpTen.getHeight(), 
					runningX,
					runningY);

			while (num10Plates > 0){

				tmp = new BitmapDrawable(res, bmpTen);
				tmp.setBounds(rctTen);

				bitmapPlates.add(tmp);
				runningX -= (bmpTen.getWidth());
				rctTen.offset(-bmpTen.getWidth(), 0);


				num10Plates--;
			}
		}

		if (num5Plates > 0){
			runningY =  halfPoint5;
			rctFive.set(runningX-bmpFive.getWidth(),
					runningY-bmpFive.getHeight(), 
					runningX,
					runningY);

			while (num5Plates > 0){

				tmp = new BitmapDrawable(res, bmpFive);
				tmp.setBounds(rctFive);

				bitmapPlates.add(tmp);
				
				runningX -= (bmpFive.getWidth());
				rctFive.offset(-bmpFive.getWidth(), 0);
				
				num5Plates--;
			}
		}
		if (num2Plates > 0){
			runningY =  halfPoint2;
			rctTwo.set(runningX-bmpTwo.getWidth(),
					runningY-bmpTwo.getHeight(), 
					runningX,
					runningY);

			while (num2Plates > 0) {

				tmp = new BitmapDrawable(res, bmpTwo);
				tmp.setBounds(rctTwo);

				bitmapPlates.add(tmp);

				runningX -= (bmpTwo.getWidth());
				rctTwo.offset(-bmpTwo.getWidth(), 0);



				num2Plates--;
			}
		}
		weightStackup = new BitmapDrawable[bitmapPlates.size()];
		bitmapPlates.toArray(weightStackup);


		stackUp = new LayerDrawable(weightStackup);
		invalidate();

	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int targetWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		int targetHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
		
		setMeasuredDimension(targetWidth,targetHeight);
		
		
//		switch(heightMode){
//		case View.MeasureSpec.UNSPECIFIED:
//			break;
//		case View.MeasureSpec.AT_MOST:
//			break;
//		case View.MeasureSpec.EXACTLY:
//			break;
//		}
//		
//		switch(widthMode){
//		case View.MeasureSpec.UNSPECIFIED:
//			break;
//		case View.MeasureSpec.AT_MOST:
//			break;
//		case View.MeasureSpec.EXACTLY:
//			
//			break;
//		}
		
	}
	
	protected void onDraw(Canvas canvas) {
		if (!isInEditMode()){
		super.onDraw(canvas);
		
		int legX=this.getWidth()- bmpLegend.getWidth();
		int legY=this.getHeight() - bmpLegend.getHeight();

		Rect rctLegend = new Rect(legX, legY, legX+bmpLegend.getWidth(),
				legY+bmpLegend.getHeight());

		legend.setBounds(rctLegend);

		canvas.save();
		canvas.drawColor(Color.BLACK);
		canvas.rotate(90);
		int centerPoint = (int)(this.getWidth()*0.75);
		canvas.translate((int)(this.getHeight()*0.10),-centerPoint);
		canvas.scale(bitmapScaleFactor, bitmapScaleFactor);

		stackUp.draw(canvas);
		canvas.restore();
		legend.draw(canvas);
		
		}

	}
}
