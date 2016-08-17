package com.gliga.au_notequirrel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String DEBUGTAG = "JWP";
	public static final String TEXTFILE = "noteSquirrel.txt";
	public static final String FILESAVED = "FileSaved";
	public static final String RESET_PASSPOINTS = "ResetPasspoints";
	private static final int BROWSE_GALLERY_REQUEST_CODE = 1;
	public static final String NEW_IMAGE_PATH = "NewImagePath";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean fileSaved = preferences.getBoolean(FILESAVED, false);

		if (fileSaved) {
			loadSavedFile();
		} else {
			EditText editText = (EditText) findViewById(R.id.editText);
			editText.setHint("Nothing in here...");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.menu_passpoints_reset:
			resetPasspoints(null);
			break;

		case R.id.menu_replace_image:
			replaceImage();
			break;

		}

		return true;
	}
	
	
	private void resetPasspoints(String imagePath) {
		Intent intent = new Intent(this, ImageActivity.class);
		intent.putExtra(RESET_PASSPOINTS, true);
		
		if(imagePath!=null){
			intent.putExtra(NEW_IMAGE_PATH,imagePath);
		}
		
		startActivity(intent);
		
	}

	private void replaceImage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		View dialogView = getLayoutInflater().inflate(
				R.layout.replace_image_dialog, null);

		builder.setTitle(R.string.replace_lock_photo).setView(dialogView);
		
		AlertDialog dialog = builder.create();
		
		dialog.show();

	}
	
	public void onTakePhotoButtonPressed(View view){
		Toast.makeText(this, "Photo taken...", Toast.LENGTH_SHORT).show();
	}
	
	public void onBrowseGalleryButtonPressed(View view){
		Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, BROWSE_GALLERY_REQUEST_CODE);
		
	}  
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		Uri image = null;
		
		if(requestCode == BROWSE_GALLERY_REQUEST_CODE){
			String[] columns = {MediaStore.Images.Media.DATA};
			
			Uri imageUri = intent.getData();
			
			Cursor cursor = getContentResolver().query(imageUri, columns, null, null, null);
			
			cursor.moveToFirst();
			
			int columnIndex = cursor.getColumnIndex(columns[0]);
			
			String imagePath = cursor.getString(columnIndex);
			
			cursor.close();
			
			image=Uri.parse(imagePath);
			
			resetPasspoints(imagePath);
		}
	}

	private void loadSavedFile() {
		try {
			FileInputStream fileInputStream = openFileInput(TEXTFILE);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new DataInputStream(fileInputStream)));

			String line;
			EditText editText = (EditText) findViewById(R.id.editText);

			while ((line = reader.readLine()) != null) {
				editText.append(line + "\n");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void onSaveButtonPressed(View view) {
		saveText();
	}
	
	
	
	private void saveText(){
		
		String text = ((EditText) findViewById(R.id.editText)).getText()
				.toString();

		try {
			FileOutputStream fileOutputStream = openFileOutput(TEXTFILE,
					Context.MODE_PRIVATE);
			fileOutputStream.write(text.getBytes());
			fileOutputStream.close();

			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean(FILESAVED, true);
			editor.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onLockButtonPressed(View view) {
		Intent intent = new Intent(MainActivity.this, ImageActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		saveText();
	}

}
