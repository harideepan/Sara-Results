package results.sara.com.sararesults;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class PreviousResult extends AppCompatActivity {
    String[] scode;
    String[] sname;
    String[] grade;
    String[] result;
    String[] credit;
    String semester;
    float gpaval;
    public void findGPA()
    {
        int totalCredits=0,temp=0;
        for(int i=0;i<credit.length;i++)
        {
                if(result[i].equalsIgnoreCase("Pass")) {
                    totalCredits += Integer.parseInt(credit[i]);
                    if (grade[i].equalsIgnoreCase("S")) {
                        temp += 10 * Integer.parseInt(credit[i]);
                    } else if (grade[i].equalsIgnoreCase("A")) {
                        temp += 9 * Integer.parseInt(credit[i]);
                    } else if (grade[i].equalsIgnoreCase("B")) {
                        temp += 8 * Integer.parseInt(credit[i]);
                    } else if (grade[i].equalsIgnoreCase("C")) {
                        temp += 7 * Integer.parseInt(credit[i]);
                    } else if (grade[i].equalsIgnoreCase("D")) {
                        temp += 6 * Integer.parseInt(credit[i]);
                    } else if (grade[i].equalsIgnoreCase("E")) {
                        temp += 5 * Integer.parseInt(credit[i]);
                    }
            }
        }
        gpaval=((float)temp)/(float)totalCredits;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_result);
        // Inflate the layout for this fragment
        Bundle extras = getIntent().getExtras();
        TableRow.LayoutParams params;
        params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT);
        scode=extras.getStringArray("Scode");
        sname=extras.getStringArray("Sname");
        grade=extras.getStringArray("Grade");
        result=extras.getStringArray("Result");
        credit=extras.getStringArray("Credit");
        semester=extras.getString("Semester");
        int color=1;//Here i have mapped the code with the layout file
        TextView name=(TextView)findViewById(R.id.sem);
        name.setText("SEMESTER "+semester);
        TableLayout tl=(TableLayout)findViewById(R.id.table);//here we are mapping with the table
        TableRow r=new TableRow(getApplicationContext());
        r.setPadding(10,20,10,20);
        r.setBackgroundColor(Color.parseColor("#FF26A69A"));
        TextView title=new TextView(getApplicationContext());
        title.setText("Subject\nCode");
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        title=new TextView(getApplicationContext());
        title.setText("Subject Name");
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        title=new TextView(getApplicationContext());
        title.setText("Grade");
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        title=new TextView(getApplicationContext());
        title.setText("Result");
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        findGPA();
        tl.addView(r,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT,0.1f));
        for(int i=0;i<scode.length;i++)
        {
                r = new TableRow(getApplicationContext());//new row
                if (color == 1) {
                    r.setBackgroundColor(Color.parseColor("#FF31D2C3"));
                    color = 0;//for remaining rows
                } else {
                    r.setBackgroundColor(Color.parseColor("#FF26C4B5"));
                    color = 1;
                }
                r.setPadding(10, 20, 10, 20);
                TextView sc = new TextView(getApplicationContext());
                sc.setWidth(0);
                sc.setText(scode[i]);
                sc.setTextSize(16);
                sc.setPadding(0, 0, 10, 0);
                sc.setGravity(Gravity.CENTER);
                sc.setLayoutParams(params);
                TextView sn = new TextView(getApplicationContext());
                sn.setWidth(0);
                sn.setText(sname[i]);
                sn.setTextSize(16);
                sn.setPadding(0, 0, 10, 0);
                sn.setGravity(Gravity.CENTER);
                sn.setLayoutParams(params);
                TextView gr = new TextView(getApplicationContext());
                gr.setWidth(0);
                gr.setText(grade[i]);
                gr.setTextSize(16);
                gr.setPadding(0, 0, 10, 0);
                gr.setGravity(Gravity.CENTER);
                gr.setLayoutParams(params);
                TextView re = new TextView(getApplicationContext());
                re.setWidth(0);
                re.setText(result[i]);
                if (!result[i].equalsIgnoreCase("Pass")) {
                    re.setTextColor(Color.parseColor("#ff0000"));
                }
                re.setTextSize(16);
                re.setPadding(0, 0, 10, 0);
                re.setGravity(Gravity.CENTER);
                re.setLayoutParams(params);
                r.addView(sc);
                r.addView(sn);
                r.addView(gr);
                r.addView(re);
                tl.addView(r, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 0.1f));//adding the row to out table

        }
        r=new TableRow(getApplicationContext());
        r.setPadding(10,50,10,50);
        TextView gpa=new TextView(getApplicationContext());
        gpa.setTextSize(30);
        //gpa.setTextColor(Color.parseColor("#ffffff"));
        gpa.setGravity(Gravity.CENTER);
        gpa.setText("GPA "+ String.format("%.2f",gpaval));
        params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT,1f);
        gpa.setLayoutParams(params);
        r.addView(gpa);
        tl.addView(r,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }
}
