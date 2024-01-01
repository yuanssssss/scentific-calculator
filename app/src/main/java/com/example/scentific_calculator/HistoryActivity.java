package com.example.scentific_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ListAdapter;
import database.HistoryDB;

public class HistoryActivity extends AppCompatActivity {
    private ListView listView;
    private ListAdapter listAdapter;
    private List<IconList> datas;

    private HistoryDB historyDB;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        datas=new ArrayList<>();
        historyDB=HistoryDB.getInstance(this);
        sqLiteDatabase=historyDB.getReadableDatabase();
        getData();
        listViewInit();
        TextView cleTv=(TextView) findViewById(R.id.clear);
        cleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDatabase.delete("historylist",null ,null);
                datas.clear();
                listAdapter.notifyDataSetChanged();
            }
        });

    }
    private  void listViewInit()
    {
        listView=(ListView) findViewById(R.id.history_list);
        listAdapter=new ListAdapter(HistoryActivity.this,datas);
        listView.setAdapter(listAdapter);
        listView.setEmptyView((TextView) findViewById(R.id.emptyview));
    }
    private void getData()
    {
        Cursor cursor=sqLiteDatabase.query("historylist",new String[]{"id","expression","result"},null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            IconList iconList=new IconList(cursor.getString(1),cursor.getString(2));
            datas.add(iconList);
        }
        cursor.close();
    }


}