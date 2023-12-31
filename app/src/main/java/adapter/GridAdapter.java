package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scentific_calculator.R;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<String>names;

    public GridAdapter(Context context,List<String>names)
    {
        this.context=context;
        this.names=names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(null==view)
        {
            view= LayoutInflater.from(context).inflate(R.layout.icon_grid,viewGroup,false);
            viewHolder=new ViewHolder();
            viewHolder.keyTv=(TextView) view.findViewById(R.id.key);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.keyTv.setText(names.get(i));
        return view;
    }
    private class ViewHolder
    {
        TextView keyTv;
    }
}
