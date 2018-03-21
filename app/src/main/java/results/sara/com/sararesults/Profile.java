package results.sara.com.sararesults;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Profile extends Fragment {
    String[] scode;
    String[] sname;
    String[] grade;
    String[] result;
    String[] profile;
    String[] credit;
    String[] semester;
    String[] scode1;
    String[] sname1;
    String[] grade1;
    String[] result1;
    String[] credit1;
    String[] sem;
    String[] gpa;
    int gpaIndex,gpaValueIndex;
    ListView list;
    int currentSemester;
    public float findGPA(String[] gr,String[] cr)
    {
        float gpaval=0;
        int totalCredits=0,temp=0;
        for(int i=0;i<cr.length;i++) {
            totalCredits += Integer.parseInt(cr[i]);
            if (gr[i].equalsIgnoreCase("S")) {
                temp += 10 * Integer.parseInt(cr[i]);
            } else if (gr[i].equalsIgnoreCase("A")) {
                temp += 9 * Integer.parseInt(cr[i]);
            } else if (gr[i].equalsIgnoreCase("B")) {
                temp += 8 * Integer.parseInt(cr[i]);
            } else if (gr[i].equalsIgnoreCase("C")) {
                temp += 7 * Integer.parseInt(cr[i]);
            } else if (gr[i].equalsIgnoreCase("D")) {
                temp += 6 * Integer.parseInt(cr[i]);
            } else if (gr[i].equalsIgnoreCase("E")) {
                temp += 5 * Integer.parseInt(cr[i]);
            }
        }
        gpaval=(float)temp/(float)totalCredits;
        return gpaval;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        gpaIndex=0;
        gpaValueIndex=0;
        scode=getArguments().getStringArray("Scode");
        sname=getArguments().getStringArray("Sname");
        grade=getArguments().getStringArray("Grade");
        result=getArguments().getStringArray("Result");
        profile=getArguments().getStringArray("Profile");
        credit=getArguments().getStringArray("Credit");
        semester=getArguments().getStringArray("Semester");
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        TextView t=(TextView)rootView.findViewById(R.id.reg);
        t.setText(profile[0]);
        t=(TextView)rootView.findViewById(R.id.name);
        t.setText(profile[1]);
        t=(TextView)rootView.findViewById(R.id.dept);
        t.setText(profile[2]);
        t=(TextView)rootView.findViewById(R.id.cgpal);
        t.setText("CGPA "+String.valueOf(String.format("%.2f",findGPA(grade,credit))));
        currentSemester=Integer.parseInt(semester[semester.length-1]);
        gpa=new String[currentSemester-1];
        sem=new String[currentSemester-1];
        for(int a=1;a<Integer.parseInt(semester[semester.length-1]);a++)
        {
            int c=0;
            for(int b=0;b<scode.length;b++)
            {
                if(Integer.parseInt(semester[b])==a)
                {
                    c++;
                }
            }
            String[] gr=new String[c];
            String[] cr=new String[c];
            c=0;
            for(int b=0;b<scode.length;b++)
            {
                if(Integer.parseInt(semester[b])==a)
                {
                    gr[c]=grade[gpaIndex];
                    cr[c]=credit[gpaIndex];
                    gpaIndex++;
                    c++;
                }
            }
            if(c!=0) {
                gpa[gpaValueIndex] = String.valueOf(String.format("%.2f",findGPA(gr, cr)));
                sem[gpaValueIndex] = String.valueOf(gpaValueIndex + 1);
                gpaValueIndex++;
            }
        }
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), sem,gpa);
        list=(ListView)rootView.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int count=0;
                for(int h=0;h<scode.length;h++) {
                    if(Integer.parseInt(semester[h])==(position+1)) {
                        count++;
                    }
                }
                scode1=new String[count];
                sname1=new String[count];
                grade1=new String[count];
                credit1=new String[count];
                result1=new String[count];
                count=0;
                for(int h=0;h<scode.length;h++) {
                    if(Integer.parseInt(semester[h])==(position+1)) {
                        scode1[count]=scode[h];
                        sname1[count]=sname[h];
                        grade1[count]=grade[h];
                        result1[count]=result[h];
                        credit1[count]=credit[h];
                        count++;
                    }
                }
                Intent i=new Intent(getActivity(),PreviousResult.class);
                Bundle b1=new Bundle();
                b1.putStringArray("Scode",scode1);
                b1.putStringArray("Sname",sname1);
                b1.putStringArray("Grade",grade1);
                b1.putStringArray("Result",result1);
                b1.putStringArray("Credit",credit1);
                b1.putString("Semester",String.valueOf(position+1));
                i.putExtras(b1);
                startActivity(i);
            }
        });
        return rootView;
    }
}
