package com.muliamaulana.diloevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.muliamaulana.diloevent.fragmentActivity.EventFragment;
import com.muliamaulana.diloevent.setget.Event;

public class DetailEventActivity extends AppCompatActivity {

    private String mpost_key,namaEvent, tanggalEvent, waktuEvent, kuotaEvent, deskripsiEvent, image_url;
    private ImageView imageEvent;
    private TextView txtNamaEvent, txtTanggalEvent, txtWaktuEvent, txtKuota, txtDeskripsi;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mpost_key = getIntent().getExtras().getString("event_id");
        namaEvent = getIntent().getStringExtra("nama_event");
        image_url = getIntent().getStringExtra("image_event");
        tanggalEvent= getIntent().getStringExtra("tanggal_event");
        waktuEvent = getIntent().getStringExtra("waktu_event");
        kuotaEvent = getIntent().getStringExtra("kuota_event");
        deskripsiEvent = getIntent().getStringExtra("deskripsi_event");

        setContentView(R.layout.activity_detail_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(namaEvent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageEvent = (ImageView) findViewById(R.id.image_event);
        progressBar = (ProgressBar) findViewById(R.id.img_loading);

        txtNamaEvent = (TextView) findViewById(R.id.detail_nama_event);
        txtNamaEvent.setText(namaEvent);

        txtTanggalEvent = (TextView) findViewById(R.id.tanggal_event);
        txtTanggalEvent.setText(tanggalEvent);

        txtWaktuEvent = (TextView) findViewById(R.id.waktu_event);
        txtWaktuEvent.setText(waktuEvent);

        txtKuota = (TextView) findViewById(R.id.kuota_event);
        txtKuota.setText(kuotaEvent);

        txtDeskripsi = (TextView) findViewById(R.id.deskripsi_event);
        txtDeskripsi.setText(deskripsiEvent);

        Glide.with(DetailEventActivity.this).load(image_url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageEvent);
        Toast.makeText(this,mpost_key,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Toast.makeText(this,"Share Event",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
