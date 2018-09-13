package cz.analogic.multicastlock;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.os.Build;
import android.content.Context;
import android.net.wifi.WifiManager;

/** MulticastLockPlugin */
public class MulticastLockPlugin implements MethodCallHandler {
  private static final String CHANNEL = "multicast_lock";
  private WifiManager.MulticastLock multicastLock;
  private final Context context;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "multicast_lock");
    channel.setMethodCallHandler(new MulticastLockPlugin(registrar.activeContext()));
  }

    private MulticastLockPlugin(Context context) {
        this.context = context;
    }

  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("acquire")) {
      result.success(acquireMulticastLock());
    } else if (call.method.equals("release")) {
      result.success(releaseMulticastLock());
    } else {
      result.notImplemented();
    }
  }

  private boolean acquireMulticastLock() throws NullPointerException {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) {
      return false;
    }

    Context applicationContext = context.getApplicationContext();
    WifiManager wifi = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);

    if(wifi == null) {
      return false;
    }

    multicastLock = wifi.createMulticastLock("discovery");

    try {
      multicastLock.release();//always release before acquiring for safety just in case
    } catch(Exception e){
      //probably already released
    }

    multicastLock.acquire();
    return multicastLock.isHeld();
  }

  private boolean releaseMulticastLock() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) {
      return false;
    }

    try {
      multicastLock.release();
    } catch(Exception e) {
      //probably already released
    }

    return true;
  }
}
