package edu.nyu.gh.grubhunter_mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String[] myStringArray={"The River Cafe",
                "Once Upon a Tart","Cheryl's Global Soul",
                "Cafe Luluc",
                "Giorgio's of Gramercy",
                "Solas",
                "Metaphor",
                "Cafe Henry",
                "Deletica",
                "David Burke Fabrick",
                "Le Defense",
                "Luciano's",
                "Grimaldis",
                "Clinton St Baking Co"};
        MyData[] myDataArray = new MyData[]{
                new MyData("The River Cafe", 10, "5.0"),
                new MyData("Once Upon a Tart", 20, "4.5"),
                new MyData("Cheryl's Global Soul", 30, "4.5"),
                new MyData("Cafe Luluc", 10, "4.0"),
                new MyData("Giorgio's of Gramercy", 20, "4"),
                new MyData("Solas", 30, "3.5"),
                new MyData("Metaphor", 10, "3.5"),
                new MyData("Cafe Henry", 20, "3.5"),
                new MyData("Deletica", 30, "3.0"),
                new MyData("David Burke Fabrick", 10, "3.0"),
                new MyData("Le Defense", 20, "3.0"),
                new MyData("Luciano's", 30, "3.0"),
                new MyData("Grimaldis", 10, "3.0"),
                new MyData("Clinton St Baking Co", 20, "3.0")


        };

        MyAdapter myAdapter=new
                MyAdapter(this,
                R.layout.mylayout,
                myDataArray);
        ListView myList = (ListView)
                findViewById(R.id.listView);
        myList.setAdapter(myAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
                // Perform action on click
                Intent i = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(i);
            }
        });




    }

}
