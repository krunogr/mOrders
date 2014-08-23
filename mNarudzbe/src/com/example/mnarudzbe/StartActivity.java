package com.example.mnarudzbe;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class StartActivity extends Activity implements OnClickListener {
	ImageView startPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		startPicture = (ImageView) findViewById(R.id.start_background_image);
		startPicture.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.start_background_image:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
