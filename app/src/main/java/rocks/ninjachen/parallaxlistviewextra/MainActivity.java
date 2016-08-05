package rocks.ninjachen.parallaxlistviewextra;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Rect rect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View mMapView = findViewById(R.id.mapview);
        final ListView mListView = (ListView) findViewById(R.id.listview);
        final View header = getLayoutInflater().inflate(R.layout.blank_header, mListView, false);
        header.setEnabled(false);
        mListView.addHeaderView(header, null, false);
        BaseAdapter adapter = new Madapter();
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Timber.e("firstVisibleItem "+firstVisibleItem);
                Log.e("Ninja","onScroll:getFirstVisiblePosition"+mListView.getFirstVisiblePosition());
                Log.e("Ninja","onScroll:firstVisibleItem"+firstVisibleItem);
                if (firstVisibleItem == 0) {
                    View firstChild = mListView.getChildAt(0);
                    if (firstChild != null) {
                        mMapView.setTranslationY((float) (firstChild.getTop() / 1.9));
                    }
                }
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                View v = header;
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    boolean contains = rect.contains((int)motionEvent.getX(), (int)motionEvent.getY());
                    if(contains)return false;
                }

                Log.e("Ninja"
                        , String.format(
                                "motionevent x:%f y:%f", motionEvent.getX(), motionEvent.getY()));
                Log.e("Ninja", String.format("header x: %f, y: %f, width: %d, height: %d", header.getX(), header.getY(), header.getWidth(), header.getHeight()));
                //if touch header, not consume
//                motionEvent.get
                if (mListView.getFirstVisiblePosition() == 0
                        && isIn(header.getX(), header.getY(), header.getWidth(), header.getHeight(), motionEvent.getX(),motionEvent.getY())){
                    return false;
                }
                return  mListView.onTouchEvent(motionEvent);
            }
        });
    }

    public static boolean isIn(float x, float y, int width, int height, float targetX, float targetY) {
        if (targetX >= x && targetX <= (x + width)
                && targetY >= y && targetY <= (y + height)) {
            return true;
        }
        return false;
    }

    public static class Madapter extends BaseAdapter {

        private LayoutInflater inflater;

        @Override
        public int getCount() {
            return 20;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (inflater == null) {
                inflater = LayoutInflater.from(BootstrapApplication.getInstance());
            }
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.station_item, parent, false);
                holder = new ViewHolder();
                holder.stationImage = (ImageView) convertView.findViewById(R.id.station_iv);
                holder.stationNameText = (TextView) convertView.findViewById(R.id.station_name_tv);
                holder.connect = convertView.findViewById(R.id.connect_view);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            int tintColor = BootstrapApplication.getInstance().getResources().getColor(R.color.drawer_green);
            Bitmap midStation = ViewUtils.drawDoubleCircle(tintColor, true);
            holder.stationImage.setImageBitmap(midStation);
            holder.stationNameText.setText("长风公园" + position + "站");
            return convertView;
        }
    }

    public static class ViewHolder {
        TextView stationNameText;
        ImageView stationImage;
        View connect;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//    }
}
