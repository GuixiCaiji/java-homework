package com.java.yesheng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PeopleActivity extends AppCompatActivity {

    class ViewPageAdapter extends PagerAdapter {
        ViewPageAdapter() {

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = null;
            if (position == 0) {
                v = peopleListView;
            } else if (position == 1) {
                v = peopleListView2;
            }
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int postion, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "知疫学者";
            } else if (position == 1) {
                return "追忆学者";
            }
            return null;
        }

    }


    class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(0, 0, 0, 1);
        }
    }

    class People {
        public String name;
        public int hindex;
        public double activity;
        public double sociability;
        public int citations;
        public String position;
        public double newStar;


        People(String n, int h, double a, double s, int c, String pos, double star) {
            name = n;
            hindex = h;
            activity = a;
            sociability = s;
            citations = c;
            position = pos;
            newStar = star;
        }
    }

    private ViewPager VP;
    private TabLayout tabLayout;

    private RecyclerView peopleListView;
    private RecyclerView peopleListView2;

    private List<People> peopleData1 = new ArrayList<>();
    private List<People> peopleData2 = new ArrayList<>();

    private List<String> peopleInfo1 = new ArrayList<>();
    private List<String> peopleInfo2 = new ArrayList<>();
    private List<String> peopleAvater1 = new ArrayList<>();
    private List<String> peopleAvater2 = new ArrayList<>();


    private PeopleActivity.MyAdapter adapter = new PeopleActivity.MyAdapter();
    private PeopleActivity.MyAdapter2 adapter2 = new PeopleActivity.MyAdapter2();

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnMyItemClickListener {
        void myClick(View v, int pos);
    }


    private class MyAdapter extends RecyclerView.Adapter<PeopleActivity.MyViewHolder> {

        private OnMyItemClickListener listener;

        public void setOnMyItemClickListener(OnMyItemClickListener listener) {
            this.listener = listener;
        }


        @Override
        public PeopleActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.people_list_item, parent, false);
            PeopleActivity.MyViewHolder viewHolder = new PeopleActivity.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PeopleActivity.MyViewHolder holder, int position) {
            View v = holder.itemView;
            TextView viewName = v.findViewById(R.id.text_name);
            TextView viewActivity = v.findViewById(R.id.text_activity);
            TextView viewHindex = v.findViewById(R.id.text_hindex);
            TextView viewCitation = v.findViewById(R.id.text_citation);
            TextView viewSocial = v.findViewById(R.id.text_social);
            TextView viewPos = v.findViewById(R.id.text_position);
            RatingBar rb = v.findViewById(R.id.rating_star);

            People p = peopleData1.get(position);
            viewName.setText(p.name);
            viewActivity.setText("a: " + new DecimalFormat("0.0000").format(p.activity));
            viewHindex.setText("h: " + Integer.valueOf(p.hindex));
            viewCitation.setText("c: " + Integer.valueOf(p.citations));
            viewSocial.setText("s: " + new DecimalFormat("0.00").format(p.sociability));
            viewPos.setText(p.position);
            rb.setRating((float) p.newStar);


            final int pos = position;
            if (listener != null) {
                v.findViewById(R.id.tot_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v, pos);
                    }
                });
            }


        }

        @Override
        public int getItemCount() {
            return peopleData1.size();
        }
    }


    private class MyAdapter2 extends RecyclerView.Adapter<PeopleActivity.MyViewHolder> {

        private OnMyItemClickListener listener;

        public void setOnMyItemClickListener(OnMyItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public PeopleActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.people_list_item, parent, false);
            PeopleActivity.MyViewHolder viewHolder = new PeopleActivity.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PeopleActivity.MyViewHolder holder, int position) {
            View v = holder.itemView;
            TextView viewName = v.findViewById(R.id.text_name);
            TextView viewActivity = v.findViewById(R.id.text_activity);
            TextView viewHindex = v.findViewById(R.id.text_hindex);
            TextView viewCitation = v.findViewById(R.id.text_citation);
            TextView viewSocial = v.findViewById(R.id.text_social);
            TextView viewPos = v.findViewById(R.id.text_position);
            RatingBar rb = v.findViewById(R.id.rating_star);

            People p = peopleData2.get(position);
            viewName.setText(p.name);
            viewActivity.setText("a: " + new DecimalFormat("0.0000").format(p.activity));
            viewHindex.setText("h: " + Integer.valueOf(p.hindex));
            viewCitation.setText("c: " + Integer.valueOf(p.citations));
            viewSocial.setText("s: " + new DecimalFormat("0.00").format(p.sociability));
            viewPos.setText(p.position);
            rb.setRating((float) p.newStar);


            final int pos = position;
            if (listener != null) {
                v.findViewById(R.id.tot_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.myClick(v, pos);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return peopleData2.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);


        androidx.appcompat.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        peopleListView = (RecyclerView) new RecyclerView(this);
        peopleListView2 = (RecyclerView) new RecyclerView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        peopleListView.setLayoutManager(layoutManager);
        peopleListView2.setLayoutManager(layoutManager2);
        peopleListView.addItemDecoration(new MyItemDecoration());
        peopleListView2.addItemDecoration(new MyItemDecoration());
        peopleListView.setAdapter(adapter);
        peopleListView2.setAdapter(adapter2);

        VP = findViewById(R.id.people_page);
        VP.setAdapter(new ViewPageAdapter());


        adapter.setOnMyItemClickListener(new PeopleActivity.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Intent intent = new Intent(PeopleActivity.this, PeopleTextActivity.class);
                intent.putExtra("Info", peopleInfo1.get(pos));
                intent.putExtra("Avater", peopleAvater1.get(pos));
                startActivity(intent);
            }
        });

        adapter2.setOnMyItemClickListener(new PeopleActivity.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Intent intent = new Intent(PeopleActivity.this, PeopleTextActivity.class);
                intent.putExtra("Info", peopleInfo2.get(pos));
                intent.putExtra("Avater", peopleAvater2.get(pos));
                startActivity(intent);
            }
        });


        tabLayout = findViewById(R.id.people_tab_layout);
        tabLayout.setupWithViewPager(VP);
        init();

    }

    protected void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getHTML();
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPeople() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });
    }

    private void getHTML() {
        try {
            URL urlObj = new URL("https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2");
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("url fail");
            }

            InputStream is = connection.getInputStream();

            byte[] buffer = new byte[4096];
            StringBuffer stringBuffer = new StringBuffer();
            int ret = is.read(buffer);
            while (ret >= 0) {
                if (ret > 0) {
                    String html = new String(buffer, 0, ret);
                    stringBuffer.append(html);
                    ret = is.read(buffer);
                }
            }
            is.close();
            String info = stringBuffer.toString();
            JSONObject jsonobj = new JSONObject(info);
            String data = jsonobj.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name") + " (" + obj.getString("name_zh") + ")";
                JSONObject indice = obj.getJSONObject("indices");
                int hindex = indice.getInt("hindex");
                double activity = indice.getDouble("activity");
                double sociability = indice.getDouble("sociability");
                int citations = indice.getInt("citations");
                double newStar = indice.getDouble("newStar");

                JSONObject profile = obj.getJSONObject("profile");
                String position = profile.getString("affiliation");
                String pass = obj.getString("is_passedaway");
                //Log.i("pass", pass);

                People np = new People(name, hindex, activity, sociability, citations, position, newStar);
                if (pass.equals("false")) {
                    peopleData1.add(np);
                    peopleInfo1.add(obj.getString("profile"));
                    peopleAvater1.add(obj.getString("avatar"));
                } else if (pass.equals("true")) {
                    peopleData2.add(np);
                    peopleInfo2.add(obj.getString("profile"));
                    peopleAvater2.add(obj.getString("avatar"));
                }
                //Log.i("name", name);
            }

            showPeople();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
