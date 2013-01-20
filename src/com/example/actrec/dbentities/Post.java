package com.example.actrec.dbentities;

import org.kroz.activerecord.ActiveRecordBase;

public class Post extends ActiveRecordBase {
	public String id;
	public String title;
	public String date;
	public String content;
       public Post() {
    	   
       }
       public String toString() {
   		StringBuilder sb = new StringBuilder();
   		return sb.append(id)
   				.append(" ")
   				.append(title)
   				.append(" ")
   				.append(date)
   				.toString();
   	}

}
