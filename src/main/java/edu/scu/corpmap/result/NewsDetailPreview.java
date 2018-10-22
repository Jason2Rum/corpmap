package edu.scu.corpmap.result;

import edu.scu.corpmap.entity.mysql.NewsDetail;
import scala.reflect.internal.Trees;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vicent_Chen on 2018/7/23.
 */
public class NewsDetailPreview {
    private Integer id;
    private String title;
    private String previewImage;
    private Date time;
    private String previewContent;


    public String getPreviewContent() {
        return previewContent;
    }

    public void setPreviewContent(String previewContent) {
        this.previewContent = previewContent;
    }

    public static NewsDetailPreview constructFromNewsDetail(NewsDetail newsDetail) {
        String oriContent = newsDetail.getContent();
        String s1 = subRangeString(oriContent,"<",">",false);
        String s2 = subRangeString(s1,"（","）",true);

        NewsDetailPreview newsDetailPreview = new NewsDetailPreview();
        newsDetailPreview.setId(newsDetail.getId());
        try{
            newsDetailPreview.setPreviewContent(s2.substring(0,37).trim()+"...");
        }catch (NullPointerException e){

            newsDetailPreview.setPreviewContent("");
        }

        newsDetailPreview.setTitle(newsDetail.getTitle());
        newsDetailPreview.setTime(newsDetail.getTime());

        String previewImage = newsDetail.getPreviewImage();
        if (previewImage == null || previewImage.equals("NULL"))
            newsDetailPreview.setPreviewImage(null);
        else
            newsDetailPreview.setPreviewImage(previewImage);

        return newsDetailPreview;
    }
    private static String subRangeString(String body,String str1,String str2,boolean firstOnce) {

        while (true) {
            int index1 = body.indexOf(str1);
            if (index1 != -1) {
                int index2 = body.indexOf(str2, index1);
                if (index2 != -1) {
                    String str3 = body.substring(0, index1) + body.substring(index2 +    str2.length(), body.length());
                    body = str3;
                }else {
                    return body;
                }
            }else {
                return body;
            }
            if (firstOnce) break;
        }
        return body;
    }

    public static List<NewsDetailPreview> constructFromNewsDetailList(List<NewsDetail> newsDetailList) {
        List<NewsDetailPreview> newsDetailPreviewList = new ArrayList<>();
        for (NewsDetail newsDetail : newsDetailList) {
            NewsDetailPreview newsDetailPreview = constructFromNewsDetail(newsDetail);
            newsDetailPreviewList.add(newsDetailPreview);
        }
        return newsDetailPreviewList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
