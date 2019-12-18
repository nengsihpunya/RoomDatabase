package com.example.roomdatabase.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.roomdatabase.R;
import com.example.roomdatabase.adapter.RecycleAdapter;
import com.example.roomdatabase.room.AppDatabase;
import com.example.roomdatabase.room.Mahasiswa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.roomdatabase.AppApplication.db;

public class DetailActivity extends AppCompatActivity {

    RecyclerView myRecyclerview;
    FloatingActionButton myFab;
    RecycleAdapter recycleAdapter;
    List<Mahasiswa> listMahasiswas = new ArrayList<Mahasiswa>();
    Button editButton;
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        myRecyclerview = findViewById(R.id.myRecyclerview);
        myFab = findViewById(R.id.fab);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        fetchDataFromRoom();
        initRecyclerView();add
        setAdapter();
    }

    private void fetchDataFromRoom() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "mahasiswa").allowMainThreadQueries().build();
        listMahasiswas = db.userDao().getAll();

        //just checking data from db
        for (int i = 0; i < listMahasiswas.size(); i++) {
            Log.e("Aplikasi", listMahasiswas.get(i).getAlamat() + i);
            Log.e("Aplikasi", listMahasiswas.get(i).getKejuruan() + i);
            Log.e("Aplikasi", listMahasiswas.get(i).getNama() + i);
            Log.e("Aplikasi", listMahasiswas.get(i).getNim() + i);
        }
        Log.e("cek list", "" + listMahasiswas.size());
    }

    private void initRecyclerView() {
        myRecyclerview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerview.setLayoutManager(llm);
        recycleAdapter = new RecycleAdapter(this, listMahasiswas);

    }

    private void setAdapter() {
        myRecyclerview.setAdapter(recycleAdapter);

        @Override
                public boolean onItemLongClick(final AdapterView<Mahasiswa> adapter, Vi
        final long id) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_view);
            dialog.setTitle("Pilih aksi");
            dialog.show();
            final Mahasiswa m = (Mahasiswa) getListAdapter().getItem(pos);
            editButton = (Button) dialog.findViewById(R.id.btn_edit);
            delButton = (Button) dialog.findViewById(R.id.btn_del);

            editButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchToEdit(m.getId());
                            dialog.dismiss();
                        }
                    }
            );
            delButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dataSource.deleteMahasiswa(m.getId());
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    }
            );
            return true;
        }
        public void switchToEdit(long id)

        Mahasiswa m = datasource.getMahasiswa(id);
    }
}
