package com.example.actrec.dbentities;

import org.kroz.activerecord.ActiveRecordBase;

public class User extends ActiveRecordBase {
	   public String firstName;
       public String lastName;
       public User() {
    	   
       }
       public String toString() {
   		StringBuilder sb = new StringBuilder();
   		return sb.append(firstName).append(" ")
   				.append(lastName).toString();
   	}

}
