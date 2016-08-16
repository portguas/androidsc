package networkreqyest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by heyulong on 8/9/2016.
 */

public class VolleyNetworkRequest extends JsonRequest<JSONObject>{

    private Priority priority = Priority.HIGH;
    private RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public VolleyNetworkRequest(int method, String url, Map<String, String> param, Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, ((null == param) ? null : paramstoString(param)), listener, errorListener);
        setRetryPolicy(retryPolicy);
    }

    public VolleyNetworkRequest(String url, Map<String, String> param, Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        this(Method.GET, url, param, listener, errorListener);
    }

    public VolleyNetworkRequest(String url, Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        this(Method.GET, url, null, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(new String(response.data, "UTF-8"));
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        }
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    @Override
    public Priority getPriority() {
        return super.getPriority();
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    private static String paramstoString(Map<String, String> params)
    {
        if (params != null && params.size() > 0)
        {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try
            {
                for (Map.Entry<String, String> entry : params.entrySet())
                {
                    encodedParams.append(URLEncoder.encode(entry.getKey(),
                            paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(),
                            paramsEncoding));
                    encodedParams.append('&');

                }
                return encodedParams.toString();
            }
            catch (UnsupportedEncodingException uee)
            {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }
        }
        return null;
    }
}
