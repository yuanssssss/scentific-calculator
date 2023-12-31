package com.example.scentific_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.GridAdapter;
import database.HistoryDB;

public class MainActivity extends AppCompatActivity {
    private List<String>names;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private HistoryDB historyDB;

    protected  static   List<IconList>datas;

    private  int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count=0;
        DBInit();
        GridViewInit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(R.id.history==item.getItemId())
        {
            Intent intent=new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void GridViewInit()
    {
        gridView=(GridView) findViewById(R.id.keyboard);
        names=new ArrayList<>();
        for(int i=0;i<35;i++)names.add(i+"");
        gridAdapter=new GridAdapter(this,names);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==34)
                {
                    TextView expTv=(TextView) findViewById(R.id.expression);
                    TextView resTv=(TextView) findViewById(R.id.result);
                    String expression;
                    String result;
                    if(expTv.getText()==null) expression="empty";
                    else expression=expTv.getText().toString();
                    if(resTv.getText()==null) result="empty";
                    else result=resTv.getText().toString();
                    AddRecord(count++,expression,result);
                    //datas.add(new IconList(expression,result));
                }
            }
        });
    }

    private void DBInit()
    {
        historyDB=new HistoryDB(this);
        sqLiteDatabase=historyDB.getReadableDatabase();
    }
    public void AddRecord(int id,String expression,String result)
    {
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("expression",expression);
        values.put("result",result);
        sqLiteDatabase.insert("historylist",null,values);
    }


}