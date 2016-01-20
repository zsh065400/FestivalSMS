package org.laosao.festivalsms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.laosao.festivalsms.ChooseMsgActivity;
import org.laosao.festivalsms.R;
import org.laosao.festivalsms.bean.Festival;
import org.laosao.festivalsms.bean.FestivalLab;

/**
 * @version 1.0
 * @components: FestivalCategoryFragment
 * @author：赵树豪（Scout）
 * @create date：2015/10/19 21:01
 * @modified by：赵树豪
 * @dodified date：2015/10/19 21:01
 * @why ：
 */
public class FestivalCategoryFragment extends Fragment {
	public static final String ID_FESTIVAL = "festival_id";

	private GridView mGridView;
	private ArrayAdapter<Festival> mAdapter;
	private LayoutInflater mInflater;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = LayoutInflater.from(getActivity());
		return mInflater.inflate(R.layout.fragment_festival_category, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		mAdapter = new ArrayAdapter<Festival>(getActivity(),
											 ArrayAdapter.IGNORE_ITEM_VIEW_TYPE,
											 FestivalLab.getmInstance().getFestivals()) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.item_festival, parent, false);
				}

				TextView textView = (TextView) convertView.findViewById(R.id.tvFestivalName);
				textView.setText(getItem(position).getName());

				return convertView;
			}
		};
		mGridView = (GridView) view.findViewById(R.id.gvFestivalCategory);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ChooseMsgActivity.class);
				//返回的是festival的id，而不是单击项的id
				intent.putExtra(ID_FESTIVAL, mAdapter.getItem(position).getId());
				startActivity(intent);
			}
		});

	}
}
