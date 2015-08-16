package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryMasterFragment extends Fragment {
    private MainActivity mainActivity;

    public GalleryMasterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gridview = (GridView) inflater.inflate(R.layout.fragment_gallery, container, false);

        gridview.setAdapter(new ImageAdapter(mainActivity));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(mainActivity, GalleryDetailActivity.class);
                i.putExtra(GalleryDetailActivity.ITEM_ID, position);
                startActivity(i);
            }
        });

        return gridview;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return SequencePoint.list.subList(0,SeqManager.getInstance().getCurrentSeqPtNum()).size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                itemView = LayoutInflater.from(mainActivity).inflate(R.layout.view_gallery_item,null);
//                itemView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                itemView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                itemView.setPadding(8, 8, 8, 8);
            } else {
                itemView =  convertView;
            }

            ((ImageView)itemView.findViewById(R.id.banner))
                    .setImageResource(SequencePoint.list.get(position).getBannerResID());
            ((TextView)itemView.findViewById(R.id.title))
                    .setText(getResources().getString(SequencePoint.list.get(position).getNameResID()));


            return itemView;
        }
    }

}
