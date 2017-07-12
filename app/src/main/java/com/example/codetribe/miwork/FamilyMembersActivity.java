package com.example.codetribe.miwork;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

public class FamilyMembersActivity extends AppCompatActivity
{
    String text;
    TextToSpeech tts;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);



        //created a list of array
        ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getResources().getString(R.string.father),"Haptoami",R.drawable.family_father));
        words.add(new Word(getResources().getString(R.string.mother), "Joyce",R.drawable.family_mother));
        words.add(new Word(getResources().getString(R.string.son), "Miz",R.drawable.family_son));
        words.add(new Word(getResources().getString(R.string.daughter), "Lebo",R.drawable.family_daughter));
        words.add(new Word(getResources().getString(R.string.olderbrother), "Edward",R.drawable.family_older_brother));
        words.add(new Word(getResources().getString(R.string.youngerbrother),"Calvin",R.drawable.family_younger_brother));
        words.add(new Word(getResources().getString(R.string.oldersister), "Pontsho",R.drawable.family_older_sister));
        words.add(new Word(getResources().getString(R.string.youngersister), "tinah",R.drawable.family_younger_sister));
        words.add(new Word(getResources().getString(R.string.grandfather), "Life",R.drawable.family_grandfather));
        words.add(new Word(getResources().getString(R.string.grandmother), "zoe",R.drawable.family_grandmother));

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object  listItem = listView.getItemAtPosition(position);
                Word myWord = (Word)listItem;
                text = myWord.getDefaultTranslation();
                speakOut();
            }
        });



    }

    private void speakOut()
{
    if (text.length() == 0) {
        tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
    } else {
        try
        {
            //Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            audioManager.setSpeakerphoneOn(true);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
        catch (Exception e)
        {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

}

    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void onInit(int status)
    {
        // TODO Auto-generated method stub
        // TTS is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            // Setting speech language
            int result = tts.setLanguage(Locale.US);
            // If your device doesn't support language you set above
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported",
                        Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }

            else {
                listView.setEnabled(true);
            }
            // tts is not initialized properly
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
                    .show();
            Log.e("TTS", "Initilization Failed");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                showChangeLangDialog();
                return  true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        dialogBuilder.setTitle("");
        dialogBuilder.setMessage("");
        dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                switch (langpos) {
                    case 0: //English
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                        setLangRecreate("en");
                        return;
                    case 1: //pedi
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "nso").commit();
                        setLangRecreate("nso");
                        return;
                    case 2://tsonga
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "ts").commit();
                        setLangRecreate("ts");
                        return;

                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
}


