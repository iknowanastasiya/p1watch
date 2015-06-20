package gatech.cs3300.watchchat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.LogoutButton:
                logout();
                return true;
            case R.id.CreateGroupButton:
                createGroup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void createGroup(){
        startActivity(new Intent(this, CreateGroupActivity.class));
    }
}
