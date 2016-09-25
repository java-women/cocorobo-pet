package jp.co.sharp.openapi.cocorobo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * COCOROBOAPI用クラス.
 */
public class CocoroboApi {
    private Context ctx;
    public CocoroboApi(Context ctx) {
        Intent intent = new Intent(IOpenApiCocoroboService.class.getName());
        intent.setPackage("jp.co.sharp.openapi.cocorobo");
        ctx.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        this.ctx = ctx;
    }

    public void destroy() {
        if (mService!= null) {
            ctx.unbindService(mServiceConnection);
        }
    }

    public String control(String apiKey, String mode) throws RemoteException {
        return mService.control(apiKey, mode);
    }

    public String getData(String apiKey, String dataKind) throws RemoteException {
        return mService.getData(apiKey, dataKind);
    }

    private IOpenApiCocoroboService mService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IOpenApiCocoroboService.Stub.asInterface(service);
        }
    };
}