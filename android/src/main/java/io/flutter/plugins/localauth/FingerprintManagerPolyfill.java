package io.flutter.plugins.localauth;

import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

public class FingerprintManagerPolyfill {
    private final Activity activity;
    private final FingerprintManagerCompat fmCompat;
    private final FingerprintManager fm;

    FingerprintManagerPolyfill(Activity activity) {
        this.activity = activity;
        this.fmCompat = FingerprintManagerCompat.from(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.fm = activity.getApplicationContext().getSystemService(FingerprintManager.class);
        } else {
            this.fm = null;
        }
    }

    boolean isHardwareDetected() {
        if (fmCompat.isHardwareDetected()) {
            if (fmCompat.hasEnrolledFingerprints()) {
                return true;
            } else {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fm = activity.getApplicationContext().getSystemService(FingerprintManager.class);
            return fm != null && fm.isHardwareDetected();
        } else {
            return false;
        }
    }

    boolean hasEnrolledFingerprints() {
        if (fmCompat.hasEnrolledFingerprints()) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return fm != null && fm.hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    public void authenticate( int flags, CancellationSignal cancel,  final FingerprintManagerCompat.AuthenticationCallback callback) {
        if(fmCompat.isHardwareDetected()) {
            fmCompat.authenticate(null, flags, cancel, callback, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fm.authenticate(null, (android.os.CancellationSignal) cancel.getCancellationSignalObject(), flags, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    callback.onAuthenticationError(errorCode, errString);
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    callback.onAuthenticationHelp(helpCode, helpString);
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    callback.onAuthenticationSucceeded(null);
                }

                @Override
                public void onAuthenticationFailed() {
                    callback.onAuthenticationFailed();
                }
            }, null);
        }
    }
}
