/**
 * 
 */
package sekth.droid.parcelabletutorial;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author SekthDroid
 * 
 */
public class Model implements Parcelable {
	private long unLong;
	private double unDouble;
	private float unFloat;
	private String unString;
	private boolean unBoolean;
	private OtraClase unParcelable;

	public Model() {

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		// Escribimos nuestros valores
		dest.writeLong(unLong);
		dest.writeDouble(unDouble);
		dest.writeFloat(unFloat);
		dest.writeString(unString);
		dest.writeByte((byte) (unBoolean == true ? 1 : 0));
		dest.writeParcelable(unParcelable, flags);
	}

	public long getUnLong() {
		return unLong;
	}

	public void setUnLong(long unLong) {
		this.unLong = unLong;
	}

	public double getUnDouble() {
		return unDouble;
	}

	public void setUnDouble(double unDouble) {
		this.unDouble = unDouble;
	}

	public float getUnFloat() {
		return unFloat;
	}

	public void setUnFloat(float unFloat) {
		this.unFloat = unFloat;
	}

	public String getUnString() {
		return unString;
	}

	public void setUnString(String unString) {
		this.unString = unString;
	}

	public boolean isUnBoolean() {
		return unBoolean;
	}

	public void setUnBoolean(boolean unBoolean) {
		this.unBoolean = unBoolean;
	}

	public OtraClase getUnParcelable() {
		return unParcelable;
	}

	public void setUnParcelable(OtraClase unParcelable) {
		this.unParcelable = unParcelable;
	}

	public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {

		@Override
		public Model createFromParcel(Parcel source) {
			return new Model(source);
		}

		@Override
		public Model[] newArray(int size) {
			return new Model[size];
		}

	};

	/**
	 * Constructor privado para usarlo con un argumento parcel
	 * 
	 * @param source
	 */
	private Model(Parcel source) {

		// Extraemos los valores en el mismo order en el que insertamos en el
		// método writeToParcel
		this.unLong = source.readLong();
		this.unDouble = source.readDouble();
		this.unFloat = source.readFloat();
		this.unString = source.readString();
		this.unBoolean = source.readByte() == 1 ? true : false;
		this.unParcelable = source.readParcelable(OtraClase.class
				.getClassLoader());
	}

	/**
	 * Imprime los valores de los miembros de la variable por el LogCat
	 * 
	 * @param model
	 */
	public static void printModel(Model model) {
		Log.i("unLong", "El id es " + model.unLong);
		Log.i("unDouble", "El double es " + model.unDouble);
		Log.i("unFloat", "El float es " + model.unFloat);
		Log.i("unString", "El String es " + model.unString);
		Log.i("unBoolean", "El boolean es " + String.valueOf(model.unBoolean));
		Log.i("unParcelable",
				"El id de OtraClase es " + model.unParcelable.getId());
	}

}
