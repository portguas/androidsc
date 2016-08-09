package networkreqyest;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by heyulong on 8/9/2016.
 */

public class VolleyNetworkRequest extends StringRequest{

    private Priority priority = Priority.HIGH;
    private RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);


    public VolleyNetworkRequest(int method, String url, Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        setRetryPolicy(retryPolicy);
    }

    public VolleyNetworkRequest(String url, String requestBody, Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        this(Method.GET, url, requestBody, listener, errorListener);
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


}
