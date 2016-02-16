package sekth.droid.parcelabletutorial;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Instanciamos nuestra Clase
		final Model model = new Model();
		
		// Asignamos Valores
		model.setUnLong(10);
		model.setUnDouble(10.0d);
		model.setUnFloat(20.0f);
		model.setUnString("Parcelable");
		model.setUnBoolean(true);
		
		final OtraClase otra = new OtraClase();
		otra.setId(1000);
		
		model.setUnParcelable(otra);
		
		// Imprimimos su resultado en la consola
		Model.printModel(model);
		
		// Lanzamos la siguiente Activity donde comprobaremos los valores de nuevo
		launchSecondActivity(model);
	}
	
	private void launchSecondActivity(Model model){
		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra("PARCELABLE", model);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
