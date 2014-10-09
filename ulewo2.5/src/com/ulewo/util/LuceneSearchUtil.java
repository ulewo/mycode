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
	 
}
