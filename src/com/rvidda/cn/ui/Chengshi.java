package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.view.MyExpandableListView;
import com.rvidda.cn.view.MyGridView;

public class Chengshi extends BaseActivity {
	private String[] listd1 = { "北京", "上海", "北京", "上海", "北京", "上海", "北京" };
	private MyGridView mGv1;
	private Myadapterlist adapter;
	private List<String> listw1 = new ArrayList<String>();
	private MyExpandableListView expandableGridView;
	private int sign = -1;
	private ArrayList<Map<String, Object>> list;
	private ExpandableGridAdapter adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chengshi);

		initView();

	}

	private void initView() {
		mGv1 = (com.rvidda.cn.view.MyGridView) this.findViewById(R.id.mGv1);
		mGv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		adapter = new Myadapterlist();
		mGv1.setAdapter(adapter);

		expandableGridView = (com.rvidda.cn.view.MyExpandableListView) this
				.findViewById(R.id.list);
		expandableGridView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				if (sign == -1) {
					// 展开被选的group
					expandableGridView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					expandableGridView.setSelectedGroup(groupPosition);
					sign = groupPosition;
				} else if (sign == groupPosition) {
					expandableGridView.collapseGroup(sign);
					sign = -1;
				} else {
					expandableGridView.collapseGroup(sign);
					// 展开被选的group
					expandableGridView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					expandableGridView.setSelectedGroup(groupPosition);
					sign = groupPosition;
				}
				return true;
			}
		});
		initData();

		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listw1.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("txt", "");
			list.add(map);
		}
		adapter1 = new ExpandableGridAdapter(Chengshi.this, list);
		expandableGridView.setAdapter(adapter1);

	}

	private void initData() {
		listw1.add("湖南省");
		listw1.add("河北省");
		listw1.add("广东省");
		listw1.add("湖南省");
		listw1.add("河北省");
		listw1.add("广东省");
		listw1.add("湖南省");
		listw1.add("河北省");
		listw1.add("广东省");
		listw1.add("湖南省");
		listw1.add("河北省");
		listw1.add("广东省");

	}

	class Holderlist {
		TextView mTvg1;
		RelativeLayout mRlg1;
		ImageView mIvg1;
	}

	class Myadapterlist extends BaseAdapter {
		@SuppressLint("NewApi")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			Holderlist holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_gridview_2, null);
				holder = new Holderlist();
				holder.mRlg1 = (RelativeLayout) convertView
						.findViewById(R.id.mRlg1);
				holder.mTvg1 = (TextView) convertView.findViewById(R.id.mTvg1);
				holder.mIvg1 = (ImageView) convertView.findViewById(R.id.mIvg1);
				convertView.setTag(holder);

			} else {
				holder = (Holderlist) convertView.getTag();
			}
			return convertView;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listd1.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	public class ExpandableGridAdapter extends BaseExpandableListAdapter
			implements OnItemClickListener {

		private String[][] child_text_array;
		private Context context;
		private com.rvidda.cn.view.MyGridView gridview;

		private List<Map<String, Object>> list;
		List<String> child_array;

		public ExpandableGridAdapter(Context context,
				List<Map<String, Object>> list) {
			this.context = context;
			this.list = list;
		}

		/**
		 * 获取一级标签总数
		 */
		@Override
		public int getGroupCount() {
			return list.size();
		}

		/**
		 * 获取一级标签下二级标签的总数
		 */
		@Override
		public int getChildrenCount(int groupPosition) {
			// 这里返回1是为了让ExpandableListView只显示一个ChildView，否则在展开
			// ExpandableListView时会显示和ChildCount数量相同的GridView
			return 1;
		}

		/**
		 * 获取一级标签内容
		 */
		@Override
		public Object getGroup(int groupPosition) {
			return list.get(1);
		}

		/**
		 * 获取一级标签下二级标签的内容
		 */
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return child_text_array[groupPosition][childPosition];
		}

		/**
		 * 获取一级标签的ID
		 */
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * 获取二级标签的ID
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		/**
		 * 指定位置相应的组视图
		 */
		@Override
		public boolean hasStableIds() {
			return true;
		}

		/**
		 * 对一级标签进行设置
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			convertView = (LinearLayout) LinearLayout.inflate(context,
					R.layout.item_gridview_group_layout, null);

			TextView group_title = (TextView) convertView
					.findViewById(R.id.group_title);
			ImageView mIvll1 = (ImageView) convertView
					.findViewById(R.id.mIvll1);
			if (isExpanded) {
				mIvll1.setImageResource(R.drawable.shaoshuo);
			} else {
				mIvll1.setImageResource(R.drawable.zhangkai);
			}
			group_title.setText(listw1.get(groupPosition));
			return convertView;
		}

		/**
		 * 对一级标签下的二级标签进行设置
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = (RelativeLayout) RelativeLayout.inflate(context,
					R.layout.item_grid_child_layout, null);
			gridview = (com.rvidda.cn.view.MyGridView) convertView
					.findViewById(R.id.gridview);

			// int size = child_text_array[groupPosition].length;
			/*
			 * child_array = new ArrayList<String>(); for (int i = 0; i < size;
			 * i++) { child_array.add(child_text_array[groupPosition][i]); }
			 */
			gridview.setAdapter(new Myadapter());
			gridview.setOnItemClickListener(this);
			return convertView;
		}

		/**
		 * 当选择子节点的时候，调用该方法
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			/*
			 * Toast.makeText(context, "当前选中的是:" + child_array.get(position),
			 * Toast.LENGTH_SHORT).show();
			 */}
	}

	class Holder {
		TextView mTvg1;
		ImageView mIv2, mIv3, mIv1;
	}

	class Myadapter extends BaseAdapter {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(Chengshi.this).inflate(
						R.layout.item_gridview_3, null);
				holder = new Holder();
				holder.mTvg1 = (TextView) convertView.findViewById(R.id.mTvg1);

				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.mTvg1.setText("长沙" + position);

			return convertView;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 14;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlLogin:
				startActivity(new Intent(Chengshi.this, ShouYe.class));
				break;
			default:
				break;
			}
		}
	};

}
