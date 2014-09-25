package com.ulewo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LuceneSearchUtil {
	 public static String getId(String path) {
	        String storeId = "";
	        try {
	            File file = new File(path);
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            FileReader fr = new FileReader(path);
	            BufferedReader br = new BufferedReader(fr);
	            storeId = br.readLine();
	            if (storeId == null || storeId == "") storeId = "0";
	            br.close();
	            fr.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return storeId;
	    }

	    // 将ID写入到磁盘文件中
	    public static boolean writeId(String path, String storeId) {
	        boolean b = false;
	        try {
	            File file = new File(path);
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            FileWriter fw = new FileWriter(path);
	            PrintWriter out = new PrintWriter(fw);
	            out.write(storeId);
	            out.close();
	            fw.close();
	            b = true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return b;
	    }
	    
	    /*
	    public static SearchResult getIndexResult(Document doc,String keyWord) {
	    	SearchResult result = new SearchResult();
	    	result.setId(doc.get("id"));
	    	result.setTitle(doc.get("title").replace(keyWord, "<span class='hilight'>"+keyWord+"</span>"));
	    	result.setExtendId(doc.get("extendId"));
	    	result.setUserName(doc.get("userName"));
	    	result.setCreateTime(doc.get("createTime"));
	    	result.setReadCount(doc.get("readCount"));
	    	result.setCommentCount(doc.get("commentCount"));
	    	result.setContent(doc.get("content"));
	    	result.setSummary(doc.get("summary"));
	    	return result;
	    }
	    
	    public static Document getDocument(SearchResult result) {
	        Document doc = new Document();
	        doc.add(new TextField("id",result.getId(), Store.YES));  
            doc.add(new StringField("title",result.getTitle(), Store.YES));  
            doc.add(new StringField("extendId",result.getId(), Store.YES));  
            doc.add(new StringField("userName",result.getTitle(), Store.YES)); 
            doc.add(new StringField("createTime",result.getId(), Store.YES));  
            doc.add(new StringField("readCount",result.getTitle(), Store.YES)); 
            doc.add(new StringField("commentCount",result.getId(), Store.YES));  
            doc.add(new TextField("content",result.getTitle(), Store.YES)); 
            doc.add(new StringField("summary",result.getId(), Store.YES)); 
	        return doc;
	    }
	    */
}
