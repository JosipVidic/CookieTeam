package hr.foi.cookie.core;

import hr.foi.cookie.R;
import hr.foi.cookie.types.ShowableCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

@SuppressWarnings("unused")
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<ShowableCategory> groups = new ArrayList<ShowableCategory>();
	private ArrayList<ArrayList<ShowableCategory>> categories = new ArrayList<ArrayList<ShowableCategory>>();
	private LayoutInflater inflater;

	HashMap<String, Boolean> mCheckBoxData = new HashMap<String, Boolean>();

	
	
	public MyExpandableListAdapter(
			Context context,
			ArrayList<ShowableCategory> groups,
			ArrayList<ArrayList<ShowableCategory>> categories
	) {
		this.context = context;
		this.groups = groups;
		this.categories = categories;

		inflater = LayoutInflater.from(context);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return categories.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return (long) (groupPosition * 1024 + childPosition);
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.listview_child_row, parent, false);

		final ShowableCategory cat = (ShowableCategory) getChild(
				groupPosition, childPosition);

		TextView color = (TextView) v.findViewById(R.id.childname);
		if (color != null)
			color.setText(cat.getName());

		CheckBox cb = (CheckBox) v.findViewById(R.id.check1);

		cb.setChecked(categories.get(groupPosition).get(childPosition).isSelected());
		cb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cat.setSelected(!cat.isSelected());
			}
		});

		return v;
	}

	public HashMap<String, Boolean> getCheckBoxData() {
		return mCheckBoxData;
	}

	public int getChildrenCount(int groupPosition) {
		return categories.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return (long) (groupPosition * 1024); // To be consistent with
												// getChildId
	}

	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = null;
		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.listview_group_row, parent, false);

		String gt = ((ShowableCategory) getGroup(groupPosition)).getName();
		// final TopCategoryItem tcItem =
		// (TopCategoryItem)groupsChecked.get(groupPosition);

		TextView colorGroup = (TextView) v.findViewById(R.id.childname);
		if (gt != null)
			colorGroup.setText(gt);
		
		final CheckBox cb = (CheckBox) v.findViewById(R.id.parentcheck1);
		cb.setChecked(groups.get(groupPosition).isSelected());

		cb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				groups.get(groupPosition).setSelected(!groups.get(groupPosition).isSelected());
				Log.d("Grupa Checked", " " + groupPosition + " " +
						groups.get(groupPosition).getName() +
						groups.get(groupPosition).isSelected());
			}
		});

		return v;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void onGroupCollapsed(int groupPosition) {
	}

	public void onGroupExpanded(int groupPosition) {
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ShowableCategory> getCheckedCategories()
	{
		List<ShowableCategory> checkedCategories = new ArrayList<ShowableCategory>();
		checkedCategories.clear();
		
		for (ShowableCategory group : groups)
		{
			if (group.isSelected())
			{
				checkedCategories.add(group);
			}
		}
		
		for (int i = 0; i < getGroupCount(); i++)
		{
			for (int j = 0; j < getChildrenCount(i); j++)
			{
				if (categories.get(i).get(j).isSelected())
				{
					checkedCategories.add(categories.get(i).get(j));
				}
			}
		}

		return checkedCategories;
	}

}
