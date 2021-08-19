package com.javaex.mysite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.javaex.vo.GuestbookVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListActivity extends AppCompatActivity {

    private ListView lvGuestbookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //데이터를 가져온다.(서버로부터 가져옴) --> 2021.08.19 가상으로 만들기. 내일 수업에서 데이터 가져오는거 배움
        List<GuestbookVo> guestbookList = getListFromServer();
        Log.d("javaStudy", "서버로부터 데이터 수신............ok");
        Log.d("javaStudy", "list" + guestbookList.toString());

        //listView를 객체화 한다
        lvGuestbookList = (ListView) findViewById(R.id.lvGuestbookList);

        //어댑터를 생성
        GuestbookListAdapter guestbookListAdapter =
                new GuestbookListAdapter(getApplicationContext(), R.id.lvGuestbookList, guestbookList);

        //리스트뷰테 어댑터를 세팅한다.
        lvGuestbookList.setAdapter(guestbookListAdapter);

        //몇번째거를 클릭했는지 알려줌
        lvGuestbookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //현재 클릭한 뷰의 리스트의 index 값
                Log.d("javaStudy", "index = " + i);

                //화면에 있는 값을 읽어온다.
                TextView txtContent = (TextView)view.findViewById(R.id.txtContent);
                Log.d("javaStudy", "Content = " + txtContent.getText().toString());

                //화면에 출력되지 않은 데이터를 가져올때 -> 리스트에서 가져와야함
                GuestbookVo guestbookVo =(GuestbookVo)adapterView.getItemAtPosition(i);
                Log.d("javaStudy", "vo = " + guestbookVo.toString());
                Log.d("javaStudy", "vo.RegDate = " + guestbookVo.getRegDate());

                //클릭한 아이템의 pk값을 읽어온다.
                int no = guestbookVo.getNo();
                Log.d("javaStudy", "vo.no = " + no);

            }

        });
        {

        }

    }




    // 방명록 정보 만들기(2021.08.19 가상)
    public List<GuestbookVo> getListFromServer(){

        List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();

        for(int i = 1; i <=50; i++) {
            GuestbookVo guestbookVo = new GuestbookVo();
            guestbookVo.setNo(i);
            guestbookVo.setName("정우성");
            guestbookVo.setContent(i + "번째 본문입니다.");
            guestbookVo.setRegDate("2021-08-19-" + i);


            //리스트에 넣기
            guestbookList.add(guestbookVo);
        }

        return guestbookList;


    }
}