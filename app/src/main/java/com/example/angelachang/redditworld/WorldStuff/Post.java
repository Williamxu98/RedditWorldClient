package com.example.angelachang.redditworld.WorldStuff;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.angelachang.redditworld.MainActivity;
import com.example.angelachang.redditworld.R;
import com.example.angelachang.redditworld.Views.WorldView;

import java.util.ArrayList;

/**
 * Created by angelachang on 9/17/16.
 */
public class Post {
    private int m_id;
    private int m_x; //relative to 0,0
    private int m_y; //relative to 0,0
    private String m_title;
    private String m_url;
    private int m_score;

    private Bitmap m_image;
    private ArrayList<String> displayedText=new ArrayList<String>();
    private boolean formatted=false;

    public String getTitle(){
        return m_title;
    }
    public String getUrl(){
        return m_url;
    }
    public int getScore(){
        return m_score;
    }


    public int getID(){
        return m_id;
    }

    private static boolean opened=false;
    public int getX(){
        return m_x;
    }

    public int getY(){
        return m_y;
    }

    public Bitmap getImage(){
        return m_image;
    }

    public void setID(int id){
        m_id = id;
    }

    public void setX(int x){
        m_x=x;
    }

    public void setY(int y){
        m_y=y;
    }

    public void setImage(Bitmap image){
        m_image = image;
    }

    public Post(int id, int x, int y, String url, String title, int score){
        m_id = id;
        m_x = x;
        m_y = y;
        m_url = url;
        m_title = title;
        m_score = score;
    }



    public void Display(Canvas canvas, Paint painter, int offsetX, int offsetY, int screenX, int screenY,Bitmap image){ //draws the post
        int x = offsetX -m_x+ (screenX/2);//-image.getWidth()/2;
        int y = offsetY-m_y + (screenY/2);//-image.getHeight()/2;

        canvas.drawBitmap(image, x,y,painter);
        painter.setTextSize(45);

        painter.setColor(Color.BLACK);


        //format the string to fit
        if (!formatted) {

            String text = m_title;
            String result="";
            int nCnt=1;
            for (int i=0; i < text.length();i++){
                if (i%18==0){
                    displayedText.add(result);
                    result="";
                    nCnt+=1;
                    if (nCnt==5) {
                        displayedText.add("...");
                        break;
                    }
                }

                result+=String.valueOf(text.charAt(i));
            }
            if (!result.equals("")){
                displayedText.add(result);
            }
            formatted=true;
            //displayedText=result;
        }else{
            int i =0;
            for (String t: displayedText) {
                canvas.drawText(t, x + 30, y + 75+50*i, painter);
                i+=1;
            }
        }
        painter.setColor(Color.GREEN);
        canvas.drawText(String.valueOf(m_score), x+image.getWidth()/2 -50, y + 275, painter);

        int x1 = (int)((WorldView)WorldActivity.rootview.findViewById(R.id.view)).thread.tapx;
        int y1 = (int)((WorldView)WorldActivity.rootview.findViewById(R.id.view)).thread.tapy;
        if(x1 != -1 && y1 != -1) {
            if (x1 >= x && x1 <= x + image.getWidth()) {

                if (y1 >= y && y1 <= y + image.getHeight()) {
                    if ((WorldActivity.wv.hasFocus())) {
                        Open();
                    }
                }
            }
        }
    }



    public void Open(){ //opens the whole post for viewing

        WorldActivity.rootview.runOnUiThread(new Runnable(){
            public void run(){
                WorldActivity.rootview.loadWebView(m_title,m_url);
            }
        });

    }


}
