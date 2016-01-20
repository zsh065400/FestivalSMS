package org.laosao.festivalsms.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.laosao.festivalsms.FlowLayout;
import org.laosao.festivalsms.R;
import org.laosao.festivalsms.bean.SendedMsg;
import org.laosao.festivalsms.db.SmsProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SmsHistoryFragment extends android.support.v4.app.ListFragment {

	private static final int LOADER_ID = 1;
	private LayoutInflater mInflater;
	private CursorAdapter mCursorAdapter;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
		initLoader();
		setupListAdapter();
	}

	private void setupListAdapter() {
		mCursorAdapter = new CursorAdapter(getActivity(), null, false) {
			@Override
			public View newView(Context context, Cursor cursor, ViewGroup parent) {
				View v = mInflater.inflate(R.layout.item_sended_msg, parent, false);
				return v;
			}

			@Override
			public void bindView(View view, Context context, Cursor cursor) {
				TextView tvMsg = (TextView) view.findViewById(R.id.tvMsg);
				FlowLayout flContacts = (FlowLayout) view.findViewById(R.id.flContacts);
				TextView tvFes = (TextView) view.findViewById(R.id.tvFes);
				TextView tvDate = (TextView) view.findViewById(R.id.tvDate);

				tvMsg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_MSG)));
				tvFes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_FESTIVAL_NAME)));
				long date = cursor.getLong(cursor.getColumnIndex(SendedMsg.COLUMN_DATE));
				tvDate.setText(parseDate(date));

				String names = cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_NAME));
				if (TextUtils.isEmpty(names)) {
					return;
				}
				flContacts.removeAllViews();
				for (String name : names.split(":")) {
					addTag(name, flContacts);
				}
			}
		};

		setListAdapter(mCursorAdapter);

	}

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private String parseDate(long date) {

		return df.format(date);

	}

	private void addTag(String name, FlowLayout flContacts) {
		TextView v = (TextView) mInflater.inflate(R.layout.tag, flContacts, false);
		v.setText(name);
		flContacts.addView(v);
	}

	private void initLoader() {
		getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {

			@Override
			public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
				CursorLoader loader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);
				return loader;
			}

			@Override
			public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
				if (loader.getId() == LOADER_ID) {
					mCursorAdapter.swapCursor(data);
				}
			}

			@Override
			public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
				mCursorAdapter.swapCursor(null);
			}

		});

	}
}
