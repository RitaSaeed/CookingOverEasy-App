package com.example.cookingovereasy;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> categoryCollection;
    private List<String> groupList;

    public MyExpandableListAdapter(GroceryListFragment context, List<String> groupList, Map<String, List<String>> categoryCollection){
        this.context = context;
        this.groupList = groupList;
        this.categoryCollection = categoryCollection;
    }


    @Override
    public int getGroupCount() {
        return categoryCollection.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categoryCollection.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return categoryCollection.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String categoryName = getGroup(groupPosition).toString();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grocery_list_categories, null);
        }
        TextView item = convertView.findViewById(R.id.categories);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(categoryName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String ingredient = getChild(groupPosition, childPosition).toString();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grocery_category_children, null);
        }
        TextView item = convertView.findViewById(R.id.categoryChildren);

        //look into doing deletes with delete button instead of checklist if cant figure out https://www.youtube.com/watch?v=Kla5CQRoh8Y
        //timestamp 11 min
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
