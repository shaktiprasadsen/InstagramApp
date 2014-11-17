package com.shaktiprasadsen.instagramviewer;


import java.util.List;

import com.squareup.picasso.Picasso;

import android.R.anim;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
	public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.layout_photo, photos);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InstagramPhoto photo = getItem(position);
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_photo, parent, false);
		}
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		
		tvCaption.setText(photo.caption);
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		imgPhoto.setImageResource(0);
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		
		return convertView;
	}
}
