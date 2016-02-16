/**
 * 
 */
package sekth.droid.parcelabletutorial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author SekthDroid
 * 
 */
public class OtraClase implements Parcelable {
	private long id;

	public OtraClase() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
	}

	private OtraClase(Parcel source) {
		this.id = source.readLong();
	}

	public static final Parcelable.Creator<OtraClase> CREATOR = new Parcelable.Creator<OtraClase>() {

		@Override
		public OtraClase createFromParcel(Parcel source) {
			return new OtraClase(source);
		}

		@Override
		public OtraClase[] newArray(int size) {
			return new OtraClase[size];
		}

	};

}
