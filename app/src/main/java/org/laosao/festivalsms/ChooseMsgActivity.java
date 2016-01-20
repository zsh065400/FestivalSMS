package org.laosao.festivalsms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.laosao.festivalsms.bean.FestivalLab;
import org.laosao.festivalsms.bean.Msg;
import org.laosao.festivalsms.fragment.FestivalCategoryFragment;

public class ChooseMsgActivity extends AppCompatActivity {
	
	private ListView lvMsgs;
	private FloatingActionButton fabToSend;
	
	private ArrayAdapter<Msg> mAdapter;
	private int mFestivalId;
	
	private LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_msg);
		
		mInflater = LayoutInflater.from(this);
		mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL, -1);

		setTitle(FestivalLab.getmInstance().getFestivalById(mFestivalId).getName());

		initView();
		initEvent();
	}
	
	private void initEvent() {
		fabToSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: 2015/10/21
				SendMsgActivity.toActivity(ChooseMsgActivity.this,
										  mFestivalId,
										  -1);
			}
		});
	}
	
	private void initView() {
		lvMsgs = (ListView) findViewById(R.id.lvMsgs);
		fabToSend = (FloatingActionButton) findViewById(R.id.fabToSend);

		mAdapter = new ArrayAdapter<Msg>(this,
										ArrayAdapter.IGNORE_ITEM_VIEW_TYPE,
										FestivalLab.getmInstance().getMsgByFestivalId(mFestivalId)) {
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.item_msg, parent, false);
				}

				TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
				Button btnToSend = (Button) convertView.findViewById(R.id.btnToSend);

				tvContent.setText("    " + getItem(position).getContent());
				btnToSend.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						// TODO: 2015/10/21 单击事件
						SendMsgActivity.toActivity(ChooseMsgActivity.this,
												  mFestivalId,
												  getItem(position).getId());
					}
				});
				return convertView;
			}
		};

		lvMsgs.setAdapter(mAdapter);
	}
}
