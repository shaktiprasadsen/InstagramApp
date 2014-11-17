package com.shaktiprasadsen.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class PhotosActivity extends Activity {

    private static final String CLIENT_ID = "fdf3d1c0b2ed4a89a6561ee5be5b611b";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotoAdapter aPhotos;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        fetchPopularPhotos();
    }


    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>();
    	aPhotos = new InstagramPhotoAdapter(this, photos);
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
    	lvPhotos.setAdapter(aPhotos);
    	
		String popularURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
		AsyncHttpClient client = new AsyncHttpClient();
		
		client.get(popularURL, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				//Log.i("INFO", response.toString());
				JSONArray photoJSONArray = null;
				try {
					photoJSONArray = response.getJSONArray("data");
					photos.clear();
					for (int i=0; i< photoJSONArray.length(); i++) {
						JSONObject photoJSON = photoJSONArray.getJSONObject(i);
						InstagramPhoto photo = new InstagramPhoto();
						photo.userName = photoJSON.getJSONObject("user").getString("username");
						if ( !photoJSON.isNull("caption")) {
							photo.caption = photoJSON.getJSONObject("caption").getString("text");;
						}
						photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
						photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						//Log.i("DEBUG", photo.toString());
						photos.add(photo);
						
					}
					aPhotos.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				try {
					Log.i("INFO", errorResponse.getString(RESULT_OK));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

