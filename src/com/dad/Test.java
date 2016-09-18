package com.dad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		Intent intent = getIntent();
		
		TextView test = (TextView) findViewById(R.id.testText);
		test.setText(intent.getStringExtra("ID"));
	}
}
