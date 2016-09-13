package com.xiyuan.http;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.xiyuan.util.XYLog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HttpBean implements IHttpBean {

    private static final int RETRY_DEFAULT_TIME = 5000;

    private int retryTime = -1;

    private Activity context;

    private String url;

    private String data;

    private Map<String, String> postParams;

    protected int uniqueType;

    private boolean isCacheUsed = false;

    private HashMap<String, String> headers = new HashMap<>();

    private static HashMap<Activity, RequestQueue> requestQueues = new HashMap<>();

    public HttpBean(Activity context, String url, int uniqueType) {
        this.context = context;
        this.url = url;
        this.uniqueType = uniqueType;
        this.headers = getDefaultHeaders();
    }

    public HttpBean setRetryTime(int time)
    {
        this.retryTime = time;
        return this;
    }

    public int getUniqueType()
    {
        return this.uniqueType;
    }

    public HttpBean setCacheUsed(){
        this.isCacheUsed = true;
        return this;
    }

    protected HashMap<String, String> getDefaultHeaders()
    {
        return new HashMap<String, String>();
    }

    @Override
    public void onResponseSuccess(JsonObject jsonObject) {
    }

    @Override
    public void onErrorResponse(Exception e) {
        Log.e("xiyuan", e.getMessage(), e);
    }

    public final void start()
    {
        GsonRequest<JsonObject> jsonObjRequest = new GsonRequest<JsonObject>(url, JsonObject.class,
            new Response.Listener<JsonObject>(){
                @Override
                public void onResponse(JsonObject jsonObject) {
                    try {
                        XYLog.d(HttpBean.this.url, "\n", jsonObject);
                        HttpBean.this.onResponseSuccess(jsonObject);
                    }
                    catch(Exception e)
                    {
                        HttpBean.this.onErrorResponse(e);
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    HttpBean.this.onErrorResponse(e);
                }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        String tag = context.toString() + "_" + uniqueType;

        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(retryTime == -1 ? RETRY_DEFAULT_TIME : retryTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjRequest.setTag(tag);
        jsonObjRequest.setShouldCache(isCacheUsed);

        RequestQueue requestQueue = requestQueues.get(context);
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context);
            requestQueues.put(context, requestQueue);
        }
        requestQueue.add(jsonObjRequest);
    }

    public static void cancle(final Activity context, final int uniqueType) {
        final String tag = context.toString() + "_" + uniqueType;
        RequestQueue requestQueue = requestQueues.get(context);
        if(requestQueue != null)
        {
            requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return request.getTag().toString().equals(tag);
                }
            });
            XYLog.d(HttpBean.class.getSimpleName(), tag + " http request queue removed");
        }
    }

    public static void cancleAll(final Activity context)
    {
        final String contextTag = context.toString() + "_";
        RequestQueue requestQueue = requestQueues.get(context);
        if(requestQueue != null)
        {
            requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return request.getTag().toString().startsWith(contextTag);
                }
            });
            requestQueues.remove(context);
            Log.d(HttpBean.class.getSimpleName(), contextTag + " http request queue removed");
        }
    }

    private static Field mDestroyedField = null;

    public static void initialize(Application context) {

        try {
            if (mDestroyedField == null) {
                Class<Activity> activityClass = Activity.class;
                mDestroyedField = activityClass.getDeclaredField("mDestroyed");
                mDestroyedField.setAccessible(true);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                cancleAll(activity);
            }
        });
    }

}
