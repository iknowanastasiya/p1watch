package gatech.cs3300.watchchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

public class GroupActivity extends ActionBarActivity {

    private class GroupMessage {
        public String author;
        public Date date;
        public String content;
        public Boolean received;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.GroupPreferencesButton) {
            startActivity(new Intent(this, GroupPreferencesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
