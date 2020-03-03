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
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DetailActivity.class); //Creates new intent, linking the context('this' activity) to a class ('DetailActivity')
        EditText editText = (EditText) findViewById(R.id.editText); //Declare editText variable and storing information of the editText in the XML
        String message = editText.getText().toString(); //Get the text from the editText view and store in a String variable
        intent.putExtra(EXTRA_MESSAGE, message); //Attaches the string variable to the intent to be passed on
        //EXTRA_MESSAGE is a (public constant) key because the next activity uses the key to retrieve the text value
        startActivity(intent); //Starts the intent/instance of the DisplayMessageActivity that is specified by the intent
    }
}
