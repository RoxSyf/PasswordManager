package he2b.esi.g41077.passwordmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.model.Entry;

public class EntryListViewAdapter extends BaseAdapter {

    private Activity mainActivity;
    private List<Entry> entryList;
    private LayoutInflater layoutInflater;

    public EntryListViewAdapter(Activity mainActivity, List<Entry> entryList) {
        this.mainActivity = mainActivity;
        this.entryList = entryList;
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Object getItem(int i) {
        return entryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) mainActivity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entryView = layoutInflater.inflate(R.layout.listview_entry, null);
        TextView tvName = (TextView) entryView.findViewById(R.id.tv_name);

        tvName.setText(entryList.get(i).getmName());

        return entryView;
    }
}
