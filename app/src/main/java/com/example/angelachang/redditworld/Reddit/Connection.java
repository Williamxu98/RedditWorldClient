package com.example.angelachang.redditworld.Reddit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by angelachang on 9/17/16.
 */

public class Connection {
    public static HttpURLConnection getConnection(String url){
        // System.out.println("URL: "+url);
        HttpURLConnection hcon = null;
        try {
            hcon=(HttpURLConnection)new URL(url).openConnection();
            hcon.setReadTimeout(30000); // Timeout at 30 seconds
            hcon.setRequestProperty("User-Agent", "Alien V1.0");
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return hcon;
    }


    /**
     * A very handy utility method that reads the contents of a URL
     * and returns them as a String.
     *
     * @param url
     * @return
     */

    public static String readContents(String url){
        HttpURLConnection hcon=getConnection(url);
        if(hcon==null) return null;
        try{
            StringBuffer sb=new StringBuffer(8192);
            String tmp="";
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(
                            hcon.getInputStream()
                    )
            );
            while((tmp=br.readLine())!=null)
                sb.append(tmp).append("\n");
            br.close();
            return sb.toString();
        }catch(IOException e){
            return null;
        }
    }

    public static void main(String[] args) {
        CommentProcessor cp = new CommentProcessor("https://www.reddit.com/r/uwaterloo/comments/52updr/admission_megathread/");
        ArrayList<Comment> comments = cp.fetchComments();
        for (Comment comment : comments) {
            System.out.println(comment.text);
        }
    }
}
