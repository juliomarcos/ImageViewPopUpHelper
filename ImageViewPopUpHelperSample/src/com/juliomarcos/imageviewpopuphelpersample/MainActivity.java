package com.juliomarcos.imageviewpopuphelpersample;

import com.juliomarcos.ImageViewPopUpHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the image you want to enable a pop up on click
		ImageView popThisImageView = (ImageView) findViewById(R.id.pop_me);
		// and enable it!
		ImageViewPopUpHelper.enablePopUpOnClick(this, popThisImageView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
