package net.lehre_online.android.recipefinder1;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultListActivity extends AppCompatActivity {

    ListView listview;

    static final boolean DBG = MainActivity.DBG;
    static final String TAG = "ResultListActivity";

    public static  ArrayList<String> zutatenliste = new ArrayList<>();


    RecipeMemoDbHelper myDb;


    protected void onCreate(Bundle savedInstanceState) {
        final String MNAME = "onCreate()";
        if (DBG) Log.d(TAG, MNAME + "entering...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_resultlist);
        myDb = new RecipeMemoDbHelper(this);


        listview = findViewById(R.id.listview_recipelist);



        ArrayAdapter arrayAdapterTest = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RecipeSearchActivity.dummydaten);


        listview.setAdapter(arrayAdapterTest);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> arrayAdapter, View view, final int position, long id) {

                                                ImageView RecipeImage = new ImageView(ResultListActivity.this);
                                                RecipeImage.setImageResource(R.drawable.recipefinderlogo);

                                                AlertDialog.Builder builder = new AlertDialog.Builder(ResultListActivity.this);

                                                builder.setTitle(RecipeSearchActivity.dummydaten.get(position));
                                                //Zutaten bekommen
                                                Cursor fitZut = myDb.getZutatenFit(RecipeSearchActivity.dummydaten.get(position));
                                                zutatenliste.clear();
                if(fitZut.getCount() == 0){
                    //Nachricht zeigen für den Fall, dass keine
                    showMessage("Error", "Keine Zutaten gefunden");
                    return;}


               zutatenliste.clear();
                while (fitZut.moveToNext()) {
                    zutatenliste.add(fitZut.getString(0));
                }


                    builder.setMessage("Zutatenliste" + zutatenliste.toString());




                        builder.setPositiveButton("Go to Recipe!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent3 = new Intent(ResultListActivity.this, RecipeResultActivity.class);
                                intent3.putExtra("Text_RecipeFinder", RecipeSearchActivity.dummydaten.get(position));
                                startActivity(intent3);
                            }
                        });

                //alertDialogRezept.setView(RecipeImage);
                        AlertDialog alertDialogRezept = builder.create();

                        alertDialogRezept.create();
                        alertDialogRezept.show();


            }
        });

        if(DBG)Log.d(TAG,MNAME +"entering...");
    }

    private void showMessage(String error, String keine_rezepte_gefunden) {
    }


}
