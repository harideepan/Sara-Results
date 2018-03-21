package results.sara.com.sararesults;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class Result extends Fragment
{
    String[] scode;
    String[] sname;
    String[] grade;
    String[] result;
    String[] profile;
    String[] credit;
    String[] semester;
    float gpaval;
    int currentSemester;
    public final int MY_REQUEST=123;
    public void findGPA()
    {
        int totalCredits=0,temp=0;
        for(int i=0;i<credit.length;i++)
        {
            if(Integer.parseInt(semester[i])==currentSemester) {
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
        }
        gpaval=((float)temp)/(float)totalCredits;
    }
    private boolean hasPermissions()
    {
        int res=0;
        String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for(String perms:permissions)
        {
            res=checkCallingOrSelfPermission(getContext(),perms);
            if(!(res== PackageManager.PERMISSION_GRANTED))
            {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults)
    {
        boolean allowed=true;
        switch(requestCode)
        {
            case MY_REQUEST:
                for(int res:grantResults)
                {
                    allowed=allowed&&(res==PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed=false;
        }
        if(allowed)
        {
            createFile();
        }

    }
    private void requestPerms()
    {
        String[] permission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            requestPermissions(permission, MY_REQUEST);
        }
    }
    public void createFile()
    {
        try
        {
            Document doc=new Document();
            String outPath= Environment.getExternalStorageDirectory()+"/"+profile[0]+"sem"+currentSemester+"result.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(outPath));
            doc.open();
            PdfPTable table = new PdfPTable(4);
            /*InputStream ims = getActivity().getAssets().open("logosplash.jpg");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            doc.add(image);*/
            Paragraph p=new Paragraph("SARANATHAN COLLEGE OF ENGINEERING\n");
            p.setAlignment(1);
            doc.add(p);
            p=new Paragraph("Affiliated to Anna University, Chennai\n\n");
            p.setAlignment(1);
            doc.add(p);
            p=new Paragraph("Register number : "+profile[0]);
            p.setAlignment(1);
            doc.add(p);
            p=new Paragraph("Name : "+profile[1]);
            p.setAlignment(1);
            doc.add(p);
            p=new Paragraph("Branch : "+profile[2]+"\n\n");
            p.setAlignment(1);
            doc.add(p);
            // the cell object
                               /* PdfPCell cell;
                                // we add a cell with colspan 3
                                cell = new PdfPCell(new Phrase("SARANATHAN COLLEGE OF ENGINEERING"));
                                cell.setColspan(4);
                                table.addCell(cell);*/
                                // now we add a cell with rowspan 2
                             /*   cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
                                cell.setRowspan(2);
                                table.addCell(cell);*/
            // we add the four remaining cells with addCell()
            for (int i = 0; i < scode.length; i++) {
                if (Integer.parseInt(semester[i]) == currentSemester) {
                    table.addCell(scode[i]);
                    table.addCell(sname[i]);
                    table.addCell(grade[i]);
                    table.addCell(result[i]);
                }
            }
            doc.add(table);
            p=new Paragraph("\n\nGPA "+String.format("%.2f",gpaval));
            p.setAlignment(1);
            doc.add(p);
            doc.close();
            Toast.makeText(getContext(), "PDF file has been saved to " + Environment.getExternalStorageDirectory(), Toast.LENGTH_LONG).show();
        }
        catch (DocumentException | FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        TableRow.LayoutParams params;
        params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT);
        scode=getArguments().getStringArray("Scode");
        sname=getArguments().getStringArray("Sname");
        grade=getArguments().getStringArray("Grade");
        result=getArguments().getStringArray("Result");
        profile=getArguments().getStringArray("Profile");
        credit=getArguments().getStringArray("Credit");
        semester=getArguments().getStringArray("Semester");
        int color=1;
        final View rootView = inflater.inflate(R.layout.fragment_result, container, false);//Here i have mapped the code with the layout file
        TextView name=(TextView)rootView.findViewById(R.id.user);
        Button download=(Button)rootView.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(hasPermissions())
                {
                    createFile();
                }
                else
                {
                    requestPerms();
                }
            }
        });
        TableLayout tl=(TableLayout)rootView.findViewById(R.id.table);//here we are mapping with the table
        TableRow r=new TableRow(getContext());
        r.setPadding(10,20,10,20);
        r.setBackgroundColor(Color.parseColor("#c1403d"));
        TextView title=new TextView(getContext());
        title.setText("Subject\nCode");
        title.setTextColor(Color.parseColor("#ffffff"));
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        title=new TextView(getContext());
        title.setText("Subject Name");
        title.setTextColor(Color.parseColor("#ffffff"));
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        title=new TextView(getContext());
        title.setText("Grade");
        title.setTextColor(Color.parseColor("#ffffff"));
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        title=new TextView(getContext());
        title.setText("Result");
        title.setTextColor(Color.parseColor("#ffffff"));
        title.setTextSize(16);
        title.setPadding(0,0,10,0);
        title.setGravity(Gravity.CENTER);
        //title.setLayoutParams(params);
        r.addView(title);
        name.setText("Welcome "+profile[1]+"!");
        currentSemester=Integer.parseInt(semester[scode.length-1]);
        findGPA();
        tl.addView(r,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT,0.1f));
        for(int i=0;i<scode.length;i++)
        {
            if(Integer.parseInt(semester[i])==currentSemester)
            {
                r = new TableRow(getContext());//new row
                if (color == 1) {
                    r.setBackgroundColor(Color.parseColor("#FF31D2C3"));
                    color = 0;//for remaining rows
                } else {
                    r.setBackgroundColor(Color.parseColor("#FF26C4B5"));
                    color = 1;
                }
                r.setPadding(10, 20, 10, 20);
                TextView sc = new TextView(getContext());
                sc.setWidth(0);
                sc.setText(scode[i]);
                sc.setTextColor(Color.parseColor("#000000"));
                sc.setTextSize(16);
                sc.setPadding(0, 0, 10, 0);
                sc.setGravity(Gravity.CENTER);
                sc.setLayoutParams(params);
                TextView sn = new TextView(getContext());
                sn.setWidth(0);
                sn.setText(sname[i]);
                sn.setTextColor(Color.parseColor("#000000"));
                sn.setTextSize(16);
                sn.setPadding(0, 0, 10, 0);
                sn.setGravity(Gravity.CENTER);
                sn.setLayoutParams(params);
                TextView gr = new TextView(getContext());
                gr.setWidth(0);
                gr.setText(grade[i]);
                gr.setTextColor(Color.parseColor("#000000"));
                gr.setTextSize(16);
                gr.setPadding(0, 0, 10, 0);
                gr.setGravity(Gravity.CENTER);
                gr.setLayoutParams(params);
                TextView re = new TextView(getContext());
                re.setWidth(0);
                re.setText(result[i]);
                re.setTextColor(Color.parseColor("#000000"));
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
        }
        r=new TableRow(getContext());
        r.setPadding(10,50,10,50);
        TextView gpa=new TextView(getContext());
        gpa.setTextColor(Color.parseColor("#ffffff"));
        gpa.setTypeface(null, Typeface.BOLD);
        gpa.setTextSize(30);
        gpa.setGravity(Gravity.CENTER);
        gpa.setText("GPA "+ String.format("%.2f",gpaval));
        params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT,1f);
        gpa.setLayoutParams(params);
        r.addView(gpa);
        tl.addView(r,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return rootView;
    }
}
