package com.ulewo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ulewo.model.SearchResult;

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
	    
	    
	    public static  SearchResult getIndexResult(Document doc,List<String> keyWords) {
	    	SearchResult result = new SearchResult();
	    	result.setId(doc.get("id"));
	    	String title = doc.get("title");
	    	String summary = doc.get("summary");
	    	for(String key:keyWords){
	    		title = title.replace(key, "<span class='hilight'>"+key+"</span>");
	    		summary = summary.replace(key, "<span class='hilight'>"+key+"</span>");
	    	}
	    	result.setTitle(title);
	    	result.setExtendId(doc.get("extendId"));
	    	result.setUserName(doc.get("userName"));
	    	result.setUserId(doc.get("userId"));
	    	result.setCreateTime(doc.get("createTime"));
	    	result.setReadCount(doc.get("readCount"));
	    	result.setCommentCount(doc.get("commentCount"));
	    	result.setContent(doc.get("content"));
	    	result.setSummary(summary);
	    	return result;
	    }
	    
	    public static  Document getDocument(SearchResult result) {
	      Document doc = new Document();
	      doc.add(new StringField("id",result.getId(), Store.YES));  
	      doc.add(new StringField("title",result.getTitle(), Store.YES));  
          doc.add(new StringField("extendId",result.getExtendId(), Store.YES));  
          doc.add(new StringField("userName",result.getUserName(), Store.YES)); 
          doc.add(new StringField("userId",result.getUserId(), Store.YES)); 
          doc.add(new StringField("createTime",result.getCreateTime(), Store.YES));  
          doc.add(new StringField("readCount",result.getReadCount(), Store.YES)); 
          doc.add(new StringField("commentCount",result.getCommentCount(), Store.YES));  
          doc.add(new TextField("content",result.getContent(), Store.YES)); 
          doc.add(new StringField("summary",result.getSummary(), Store.YES)); 
	        return doc;
	    }
	    
	 public static  List<String> ik_CAnalyzer(String str) {
	    	List<String> result = new ArrayList<String>();
	    	//构建IK分词器，使用smart分词模式
			Analyzer analyzer = new IKAnalyzer(true);
			//获取Lucene的TokenStream对象
		    TokenStream ts = null;
			try {
				ts = analyzer.tokenStream("myfield", new StringReader(str));
				//获取词元位置属性
			    OffsetAttribute  offset = ts.addAttribute(OffsetAttribute.class); 
			    //获取词元文本属性
			     CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			    //获取词元文本属性
			    TypeAttribute type = ts.addAttribute(TypeAttribute.class);
			    
			    
			    //重置TokenStream（重置StringReader）
				ts.reset(); 
				//迭代获取分词结果
				while (ts.incrementToken()) {
				   result.add(term.toString());
				}
				//关闭TokenStream（关闭StringReader）
				ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				//释放TokenStream的所有资源
				if(ts != null){
			      try {
					ts.close();
			      } catch (IOException e) {
					e.printStackTrace();
			      }
				}
		    }
			return result;
	    }
	 
}
