package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scentific_calculator.IconList;
import com.example.scentific_calculator.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<IconList>datas;

    public ListAdapter(Context context, List<IconList> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
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
            view= LayoutInflater.from(context).inflate(R.layout.icon_list,viewGroup,false);
            viewHolder=new ViewHolder();
            viewHolder.hisExpTv=(TextView) view.findViewById(R.id.history_expression);
            viewHolder.hisResTv=(TextView)view.findViewById(R.id.history_result);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) view.getTag();
        }
        if (null!=datas.get(i).getExpression())
            viewHolder.hisExpTv.setText(datas.get(i).getExpression());
        else viewHolder.hisExpTv.setText(" ");
        if(null!=datas.get(i).getResult())
            viewHolder.hisResTv.setText(datas.get(i).getResult());
        else viewHolder.hisResTv.setText(" ");
        return view;
    }
    private class ViewHolder
    {
        TextView hisExpTv;
        TextView hisResTv;
    }
    public void AddItem(String expression,String result)
    {
        IconList iconList=new IconList(expression,result);
        datas.add(iconList);
        notifyDataSetChanged();
    }
}
