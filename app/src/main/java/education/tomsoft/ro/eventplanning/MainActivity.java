package education.tomsoft.ro.eventplanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;


public class MainActivity extends Activity {

    public Button buttonLogin;
    public EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Client mKinveyClient = new Client.Builder("yourkey1", "yourkey2", this.getApplicationContext()).build();
        username = (EditText) findViewById(R.id.username_edit);
        password = (EditText) findViewById(R.id.password_edit);
        buttonLogin = (Button) findViewById(R.id.login_button);
        final Intent i = new Intent(this, EventsActivity.class);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mKinveyClient.user().isUserLoggedIn()) {
                    mKinveyClient.user().logout().execute();
                }
                mKinveyClient.user().login(username.getText().toString(), password.getText().toString(), new KinveyUserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        Toast.makeText(getApplicationContext(), "Logged in ", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        MainActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Login failed: "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        } });
    }

    @Override
    protected void onDestroy() {
     //   mKinveyClient.user().logout();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

       int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
