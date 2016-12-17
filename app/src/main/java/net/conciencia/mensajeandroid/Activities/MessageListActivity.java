package net.conciencia.mensajeandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.conciencia.mensajeandroid.Constants;
import net.conciencia.mensajeandroid.ContentLoaders.MessageLoader;
import net.conciencia.mensajeandroid.Fragments.CasoFragment;
import net.conciencia.mensajeandroid.Fragments.InformationFragment;
import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.Fragments.SermonListFragment;

public class MessageListActivity extends AppCompatActivity implements SermonListFragment.MessageListInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onSermonSelected(MessageLoader messageLoader, int sermonIndex) {
        //todo
        //Toast.makeText(this, String.format("Message %s (%s) selected", sermon.getTitle(), sermon.getPubDate()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MessageListActivity.this, SermonViewActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Constants.RSS_EXTRA, messageLoader);
        extras.putInt(Constants.SERMON_INDEX, sermonIndex);
        intent.putExtras(extras);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Fragment fragment = new InformationFragment();
        switch (item.getItemId()) {
            case R.id.go_to_messageList:
                fragment = new SermonListFragment();
                break;
            case R.id.go_to_caseOfTheWeek:
                fragment = new CasoFragment();
                break;
            case R.id.go_to_information:
                fragment = new InformationFragment();
                break;
            default:
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sermonList_Fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }
}
