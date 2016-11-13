package com.muliamaulana.diloevent.fragmentActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muliamaulana.diloevent.DetailEventActivity;
import com.muliamaulana.diloevent.R;
import com.muliamaulana.diloevent.setget.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private DatabaseReference databaseEvent;

    private MaterialDialog progressLoading;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutEvent =  inflater.inflate(R.layout.fragment_event, container, false);

        //Deklarasi recyclerview
        myRecyclerView = (RecyclerView) layoutEvent.findViewById(R.id.event_view);
        myRecyclerView.setHasFixedSize(true);

        //Menampilkan Recyclerview di LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(layoutManager);

        //Menampilkan loading data event
        progressLoading = new MaterialDialog.Builder(getActivity())
                .content("Loading...")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();


        databaseEvent = FirebaseDatabase.getInstance().getReference().child("event");
        databaseEvent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressLoading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                new MaterialDialog.Builder(getActivity())
                        .content("Check your connection and try again").canceledOnTouchOutside(false)
                        .positiveText("Retry")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                onStart();
                            }
                        })
                        .show();
            }
        });

        return layoutEvent;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Event, EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(Event.class,R.layout.list_event,EventViewHolder.class,databaseEvent) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                final String idEvent= getRef(position).getKey();
                final String namaEvent = model.getNama_event();
                final String imgEvent = String.valueOf(model.getImage());
                final String tanggalEvent = model.getTanggal();
                final String waktuEvent = model.getWaktu();
                final String kuotaEvent = model.getKuota_event();
                final String deskripsiEvent = model.getDeskripsi();

                //panggil SetGetnya
                viewHolder.setNama_event(model.getNama_event());
                viewHolder.setTanggal(model.getTanggal());
                viewHolder.setWaktu(model.getWaktu());
                viewHolder.setDeskripsi(model.getDeskripsi());
                viewHolder.setKuota(model.getKuota_event());
                viewHolder.setImage(getActivity().getApplicationContext(),model.getImage());

                viewHolder.eventView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(),"clicked"+post_key,Toast.LENGTH_LONG).show();
                        Intent intentDetail = new Intent(getActivity(), DetailEventActivity.class);
                        intentDetail.putExtra("event_id",idEvent);
                        intentDetail.putExtra("nama_event",namaEvent);
                        intentDetail.putExtra("image_event",imgEvent);
                        intentDetail.putExtra("tanggal_event",tanggalEvent);
                        intentDetail.putExtra("waktu_event",waktuEvent);
                        intentDetail.putExtra("kuota_event",kuotaEvent);
                        intentDetail.putExtra("deskripsi_event",deskripsiEvent);
                        startActivity(intentDetail);
                    }
                });

            }
        };

        myRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        View eventView;

        public EventViewHolder(View itemView) {
            super(itemView);

            eventView = itemView;
        }

        public void setNama_event (String nama_event){
            TextView txt_namaEvent = (TextView) eventView.findViewById(R.id.nama_event);
            txt_namaEvent.setText(nama_event);
        }

        public void setTanggal (String tanggal){
            TextView txt_tanggalEvent = (TextView) eventView.findViewById(R.id.tanggal_event);
            txt_tanggalEvent.setText(tanggal);
        }

        public void setWaktu (String waktu){
            TextView txt_waktu = (TextView) eventView.findViewById(R.id.waktu_event);
            txt_waktu.setText(waktu);
        }

        public void setDeskripsi (String deskripsi){
            TextView txt_deskripsi = (TextView) eventView.findViewById(R.id.deskripsi_event);
            txt_deskripsi.setText(deskripsi);
        }

        public void setKuota (String kuota){
            TextView txt_kuota = (TextView) eventView.findViewById(R.id.kuota_event);
            txt_kuota.setText(kuota);
        }

        public void setImage (Context context, String imageEvent){

            final ProgressBar progressBar = (ProgressBar) eventView.findViewById(R.id.imgLoading);

            ImageView imageViewEvent = (ImageView) eventView.findViewById(R.id.image_event);
            Glide.with(context).load(imageEvent).listener(new RequestListener<String, GlideDrawable>() {
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
            }).into(imageViewEvent);
        }
    }
}
