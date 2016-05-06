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
        final MyData[] myDataArray = new MyData[]{
                new MyData("The River Cafe", "address", "5.0","landmark","price range","no of reviews","dishes"),
                new MyData("Once Upon a Tart", "address", "4.5","landmark","price range","no of reviews","dishes"),
                new MyData("Cheryl's Global Soul", "address", "4.5","landmark","price range","no of reviews","dishes"),
                new MyData("Cafe Luluc", "address", "4.0","landmark","price range","no of reviews","dishes"),
                new MyData("Giorgio's of Gramercy", "address", "4","landmark","price range","no of reviews","dishes"),
                new MyData("Solas", "address", "3.5","landmark","price range","no of reviews","dishes"),
                new MyData("Metaphor", "address", "3.5","landmark","price range","no of reviews","dishes"),
                new MyData("Cafe Henry", "address", "3.5","landmark","price range","no of reviews","dishes"),
                new MyData("Deletica", "address", "3.0","landmark","price range","no of reviews","dishes"),
                new MyData("David Burke Fabrick", "address", "3.0","landmark","price range","no of reviews","dishes"),
                new MyData("Le Defense", "address", "3.0","landmark","price range","no of reviews","dishes"),
                new MyData("Luciano's", "address", "3.0","landmark","price range","no of reviews","dishes"),
                new MyData("Grimaldis", "address", "3.0","landmark","price range","no of reviews","dishes"),
                new MyData("Clinton St Baking Co", "address", "3.0","landmark","price range","no of reviews","dishes")


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
                TextView v = (TextView)view.findViewById(R.id.title); String itemId = v.getText().toString();
                Toast.makeText(getApplicationContext(),
                        String.format("Click ListItem Number %d%s", position, itemId), Toast.LENGTH_LONG)
                        .show();

                // Perform action on click
                Intent i = new Intent(getApplicationContext(), ScrollingActivity.class);
                i.putExtra("myTitle", myDataArray[position].myTitle);
                i.putExtra("myNum", myDataArray[position].myNum);
                i.putExtra("myRating", myDataArray[position].myRating);
                i.putExtra("landmark", myDataArray[position].landmark);
                i.putExtra("noReviews", myDataArray[position].priceRange);
                i.putExtra("noReviews", myDataArray[position].noReviews);
                i.putExtra("dishes", myDataArray[position].dishes);
                startActivity(i);
            }
        });




    }

}
