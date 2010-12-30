package com.android.game.DoubleU.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import com.android.game.DoubleU.DoubleU;
import com.android.game.DoubleU.R;


public class DoubleUActivityTest extends ActivityInstrumentationTestCase2<DoubleU> {

	private Activity mActivity;
	private Instrumentation mInstrumentation;
	
	public DoubleUActivityTest() {
		super("com.android.game.DoubleU", DoubleU.class);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		setActivityInitialTouchMode(true);
		this.mActivity = getActivity();
	    this.mInstrumentation = this.getInstrumentation(); 
	}

	public void testStartBtnOnKeyPress(){
		
		View doubleU = this.mActivity.findViewById(R.layout.doubleu_layout);
		assertNull(doubleU);
		// send key to UI
		this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		this.mInstrumentation.waitForIdleSync();

		doubleU = this.mActivity.findViewById(R.id.doubleu);
		assertNotNull(doubleU);
	}
}
