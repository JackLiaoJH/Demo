package org.jacksonliao.messenger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author JacksonLiao
 * @version 1.0
 * @since 2017/1/3
 */
public class RemoteData implements Parcelable {
    public String url;
    public int id;

    public RemoteData(){}

    protected RemoteData(Parcel in) {
        url = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RemoteData> CREATOR = new Creator<RemoteData>() {
        @Override
        public RemoteData createFromParcel(Parcel in) {
            return new RemoteData(in);
        }

        @Override
        public RemoteData[] newArray(int size) {
            return new RemoteData[size];
        }
    };
}
