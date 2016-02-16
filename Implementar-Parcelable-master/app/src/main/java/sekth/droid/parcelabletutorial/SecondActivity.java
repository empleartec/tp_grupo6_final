package sekth.droid.parcelabletutorial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		// Obtenemos nuestro Parcelable desde el Intent
		final Model model = getIntent().getParcelableExtra("PARCELABLE");
		
		// Imprimimos sus valores
		Model.printModel(model);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

}
