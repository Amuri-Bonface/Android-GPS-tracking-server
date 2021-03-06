package com.example.florence.mapsagain;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Custom_Volly_Request extends Request<JSONArray> {
    private Response.Listener<JSONArray> listener;
    private Map<String, String> hashMap;

    public Custom_Volly_Request(String url, Map<String, String> hashMap,
                                Response.Listener<JSONArray> reponseListener,
                                Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = reponseListener;
        this.hashMap = hashMap;
    }

    public Custom_Volly_Request(int method, String url, Map<String, String> hashMap,
                                Response.Listener<JSONArray> reponseListener,
                                Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.hashMap = hashMap;
    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return hashMap;
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}