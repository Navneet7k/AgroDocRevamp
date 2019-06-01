package navneet.com.agrodocrevamp;

import android.graphics.Bitmap;

/**
 * Created by Navneet Krishna on 27/05/19.
 */
public interface CameraFragmentInterface {
    void onScanDone(String scanResult, Bitmap bitmap);
}
