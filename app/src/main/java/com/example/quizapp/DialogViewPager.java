package com.example.quizapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogViewPager extends DialogFragment {
    private ArrayList<QaModel> arrayList;
    private int result[]; // 0 for unattempted, 1 for wrong, 2 for correct
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TextView countText;
    private int selectedPosition = 0;
    private Button next;
    private Button back;

    private String TAG = "DialogViewPager";

    public DialogViewPager() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_view_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("running", "beast");
        viewPager = view.findViewById(R.id.viewpager);
        countText = view.findViewById(R.id.count);
        next = view.findViewById(R.id.next);
        back = view.findViewById(R.id.back);
        arrayList = (ArrayList<QaModel>) getArguments().getSerializable("data");
        result = new int[arrayList.size()];
        countText.setText( 1 + " / " + arrayList.size());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(next.getText().toString()=="Submit") {

                    Intent i =new Intent(getActivity(),ResultActivity.class);
                    i.putExtra("result",result);
                    startActivity(i);
                    getActivity().finish();

                    //ArrayList<Integer> result =  getIntent().getIntegerArrayListExtra("result");

//
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//                    try {
//
//                            dialog.setTitle("Correct answers :" + data.get(2).toString() + "\nIncorrect Answers :" + data.get(1).toString());
//
//                            //dialog.setTitle("Unattempted Answers" + data.get(0).toString());
//
//                            Fragment temp = getActivity().getSupportFragmentManager().findFragmentByTag("BEast");
//                            if (temp != null) {
//                                ((DialogViewPager) temp).dismiss();
//                            }
//                            dialog.show();
//
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
                }

                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        PageAdapter pagerAdapter = new PageAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    };

    private void displayMetaInfo(int position) {
        countText.setText((position + 1) + " / " + arrayList.size());
        if(position == arrayList.size()-1){
            next.setText("Submit");
        }
        else{
            next.setText("Next");
        }
        if(position==0){
            back.setVisibility(View.INVISIBLE);
        }else {
            back.setVisibility(View.VISIBLE);
        }
    }
        class PageAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {


            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.question_list, container, false);


            Log.d("Test","For position " + position);
            TextView q = view.findViewById(R.id.questionDataPage);

            q.setText(arrayList.get(position).getQuestion());
            RadioGroup mcq = view.findViewById(R.id.radioGroup);

            ArrayList<RadioButton> ra = new ArrayList<>();
            ra.add((RadioButton) mcq.findViewById(R.id.radioButton));
            ra.add((RadioButton) mcq.findViewById(R.id.radioButton2));
            ra.add((RadioButton) mcq.findViewById(R.id.radioButton3));
            ra.add((RadioButton) mcq.findViewById(R.id.radioButton4));

           // SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPreferences.edit();



            int index, answer;
            Random n = new Random();
            answer = n.nextInt(4);//2

            if(result[position] == 1 || result[position] == 2)
            {
                ra.get(answer).setChecked(true);
            }
            else
            {
                ra.get(answer).setChecked(false);
            }

            ra.get(answer).setText(arrayList.get(position).getAnswer());
             final int answerId =ra.get(answer).getId();

             ArrayList<Integer> x = new ArrayList<>();
             x.add(position); //This was the bug ! Done finally

             int flag;
                for(int i=0 ; i<4; i++) {
                         flag=0;
                     if (i != answer) {
                         while(flag!=1) {
                             index = n.nextInt(arrayList.size());
                             Log.d("Test", "Random no => "+String.valueOf(index));
                             if (!x.contains(index)) {
                                 x.add(index);
                                 Log.d("Test", index + "at " +i + "instantiateItem: "+ arrayList.get(index).getAnswer());
                                 ra.get(i).setText(arrayList.get(index).getAnswer());
                                 flag = 1;
                             }
                         }
                     }
                 }
                for(QaModel t : arrayList){
                    Log.d("Test", "instantiateItem: "+ t.getAnswer());
                }

                 for(int y : x){
                     Log.d("Test",y+"");
                 }

            mcq.getCheckedRadioButtonId();
            mcq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
            try {
                if (answerId == checkedId) {
                    result[position] = 2;
                } else {
                    result[position]=1;
                }
            }
            catch(Exception e)
            {
                Log.v(TAG,"onChecked listener");
                e.printStackTrace();
            }
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return (view == o);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
