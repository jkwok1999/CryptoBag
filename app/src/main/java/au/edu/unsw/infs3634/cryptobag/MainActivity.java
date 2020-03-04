package au.edu.unsw.infs3634.cryptobag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Test1
    //private static final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.cryptobag.MESSAGE";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void newActivity(View view) {
        Intent intent = new Intent(this, DetailActivity.class); //Creates new intent, linking the context('this' activity) to a class ('DetailActivity')
        intent.putExtra(EXTRA_MESSAGE, "Bitcoin");
        startActivity(intent); //Starts the intent/instance of the DisplayMessageActivity that is specified by the intent
    }
}
