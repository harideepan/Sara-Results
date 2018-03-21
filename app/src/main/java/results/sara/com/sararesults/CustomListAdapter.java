package results.sara.com.sararesults;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] semester;
	private final String[] gpa;
	
	public CustomListAdapter(Activity context, String[] semester,String[] gpa) {
		super(context, R.layout.mylist, semester);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.semester=semester;
		this.gpa=gpa;
	}
	
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.mylist, null,true);
		
		TextView sem = (TextView) rowView.findViewById(R.id.semester);
		TextView gpat = (TextView) rowView.findViewById(R.id.gpa);
		
		sem.setText("Semester "+semester[position]);
		gpat.setText("GPA "+gpa[position]);
		return rowView;
	};
}
