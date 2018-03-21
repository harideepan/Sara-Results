package results.sara.com.sararesults;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {
    static TextView reg,bd;
    static String dateOfBirth,RegisterNumber;
    String[] Profile;
    String[] Scode;
    String[] Sname;
    String[] Grade;
    String[] Result;
    String[] Credit;
    String[] Semester;
    boolean flag=false;
    int l,resultIndex=0;
    boolean clear;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        reg=(TextView)findViewById(R.id.regno);
        bd=(TextView) findViewById(R.id.dob);
        bd.setEnabled(false);
    }
    public void getResult(View v)
    {
        resultIndex=0;
        flag=false;
        if(!reg.getText().toString().equals(""))
        {
            if(!bd.getText().toString().equalsIgnoreCase(""))
            {
                RegisterNumber=reg.getText().toString();
                getData();
            }
            else
            {
                Toast.makeText(this,"Enter Date of Birth",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Enter Register number",Toast.LENGTH_LONG).show();
        }

    }
    private void getData()
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL;
        StringRequest stringRequest = new StringRequest(url+"registerno="+RegisterNumber+"&dateofbirth="+dateOfBirth+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                getJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Home.this,"Please check you internet connection"/*error.getMessage()*/,Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getJSON(String response)
    {
        String mess="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            l=result.length();
            if(l>1) {
                Profile = new String[3];
                Scode = new String[l - 2];
                Sname = new String[l - 2];
                Grade = new String[l - 2];
                Result = new String[l - 2];
                Credit = new String[l - 2];
                Semester=new String[l - 2];
            }
            for(int i=0;i<l;i++)
            {
                if(i==0)
                {
                    JSONObject Res=result.getJSONObject(i);
                    String resp=Res.getString(Config.KEY_RESPONSE);
                    if(resp.equalsIgnoreCase("failed"))
                    {
                        Toast.makeText(Home.this,"Invalid data",Toast.LENGTH_LONG).show();
                        flag=true;
                        break;
                    }
                }
                else if(i==1)
                {
                    Profile[0]=RegisterNumber;
                    JSONObject Res=result.getJSONObject(i);
                    String resp=Res.getString(Config.KEY_STUDENT_NAME);
                    Profile[1]=resp;
                    resp=Res.getString(Config.KEY_DEPARTMENT);
                    Profile[2]=resp;
                }
                else
                {
                    JSONObject Res=result.getJSONObject(i);
                    String resp=Res.getString(Config.KEY_SUBJECT_CODE);
                    Scode[resultIndex]=resp;
                    resp=Res.getString(Config.KEY_SUBJECT_NAME);
                    Sname[resultIndex]=resp;
                    resp=Res.getString(Config.KEY_GRADE);
                    Grade[resultIndex]=resp;
                    resp=Res.getString(Config.KEY_RESULT);
                    Result[resultIndex]=resp;
                    resp=Res.getString(Config.KEY_CREDIT);
                    Credit[resultIndex]=resp;
                    resp=Res.getString(Config.KEY_SEMESTER);
                    Semester[resultIndex]=resp;
                    resultIndex++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!flag) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("Profile",Profile);
            i.putExtra("Scode",Scode);
            i.putExtra("Sname",Sname);
            i.putExtra("Grade",Grade);
            i.putExtra("Result",Result);
            i.putExtra("Credit",Credit);
            i.putExtra("Semester",Semester);
            startActivity(i);
        }
    }
    public void showDatePickerDialog(View v) {
        hideKeyboard(v);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void hideKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerFragment = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerFragment.getDatePicker().setMaxDate(new Date().getTime());
            datePickerFragment.getDatePicker().setCalendarViewShown(false);
            return datePickerFragment;
        }
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            month = month + 1;
            if (month < 10) {
                if (day < 10) {
                    bd.setText("0" + day + "/0" + month + "/" + year);
                    dateOfBirth = year + "-0" + month + "-0" + day;
                } else {
                    bd.setText(day + "/0" + month + "/" + year);
                    dateOfBirth = year + "-0" + month + "-" + day;
                }
            } else {
                if (day < 10) {
                    bd.setText("0" + day + "/" + month + "/" + year);
                    dateOfBirth = year + "-" + month + "-0" + day;
                } else {
                    bd.setText(day + "/" + month + "/" + year);
                    dateOfBirth = year + "-" + month + "-" + day;
                }
            }
            bd.setError(null);
        }
    }
}

