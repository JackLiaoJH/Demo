package com.liaojh.floatwindowdemo.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.liaojh.floatwindowdemo.R;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ScreenUtils.initScreen(this);
		Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
		startFloatWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
				startService(intent);
				//finish();
			}
		});
		
		Button nextBtn=(Button) findViewById(R.id.next_activity);
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,SecondActivity.class);
				startActivity(intent);
			}
		});
	}
}
