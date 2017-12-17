package com.rjfun.cordova.byeplugin;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class ByePlugin
  extends CordovaPlugin
{
  private void showOverlay(String message, final int timeout, String color)
  {
    final CordovaActivity localCordovaActivity = (CordovaActivity)this.cordova.getActivity();
    int i = this.cordova.getActivity().getResources().getIdentifier("bye_layout", "layout", this.cordova.getActivity().getPackageName());
    final View localView = LayoutInflater.from(this.cordova.getActivity()).inflate(i, null, false);
    localView.setBackgroundColor(Color.parseColor(color));
    ((TextView)localView.findViewById(this.cordova.getActivity().getResources().getIdentifier("txtBye", "id", this.cordova.getActivity().getPackageName()))).setText(message);
    this.cordova.getActivity().runOnUiThread(new Runnable()
    {
      public void run()
      {
        localCordovaActivity.addContentView(localView, new ViewGroup.LayoutParams(-1, -1));
        new Timer().schedule(new ByePlugin.CloseTimer(cordova.getActivity()), timeout * 1000);
      }
    });
  }
  
  public boolean execute(String funcName, JSONArray params, CallbackContext paramCallbackContext)
    throws JSONException
  {
    if (funcName.equals("bye"))
    {
      String message = params.getString(0);
      int i = params.getInt(1);
      String color = params.getString(2);
      String str = message + ", app will go down in " + i + " seconds with color: " + color;
      Log.i("ByeApp", str);
      paramCallbackContext.success(str);
      showOverlay(message, i, color);
      return true;
    }
    return false;
  }
  
  public class CloseTimer
    extends TimerTask
  {
    private Activity activity;
    
    public CloseTimer(Activity paramActivity)
    {
      this.activity = paramActivity;
    }
    
    public void run()
    {
      this.activity.finish();
    }
  }
}
