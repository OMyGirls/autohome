package cn.lwtAR.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.lwtAR.ARInfoActivity;
import cn.lwtAR.R;
import cn.lwtAR.database.DataLocal;
import cn.lwtAR.entity.AREntity;

public class FindFragment extends Fragment {

    private ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.lv_ar);
        listView.setAdapter(new MyAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ARInfoActivity.class);
                intent.putExtra("a", i);
                getActivity().startActivity(intent);
            }
        });
    }
    private class MyAdapter extends BaseAdapter {

        private Context mContext;
        private List<AREntity> list;

        public MyAdapter(Context context) {
            this.mContext = context;
            this.list = DataLocal.getARData();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_ar_data, null);
            AREntity arEntity = list.get(i);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_ar);
            imageView.setBackgroundResource(arEntity.getImage());
            TextView textView = (TextView) view.findViewById(R.id.tv_info_ar);
            textView.setText(arEntity.getTitle());

            return view;
        }

    }
}
