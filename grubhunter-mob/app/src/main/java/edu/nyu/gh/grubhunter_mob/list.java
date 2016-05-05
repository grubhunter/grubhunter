package edu.nyu.gh.grubhunter_mob;

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

        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                R.layout.list_item,
                myStringArray);

        ListView myList=(ListView)
                findViewById(R.id.listView);
        myList.setAdapter(myAdapter);

        AdapterView.OnItemClickListener
                mMessageClickedHandler =
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView parent,
                                                View v,
                                                int position,
                                                long id) {
                            TextView w=new TextView(getApplicationContext());
                            w.setText(((TextView) v).getText());
                            RelativeLayout myLayout=
                                    (RelativeLayout) findViewById(R.id.rel_layout);
                            myLayout.addView(w);
                        }
                    };
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
