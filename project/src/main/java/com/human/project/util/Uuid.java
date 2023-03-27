package com.human.project.util;

import java.util.UUID;

public class Uuid {

	public static void main(String[] args) {
		
		UUID uid = UUID.randomUUID();
//		System.out.println(uid.toString());
		
		UUID pw = UUID.randomUUID();
		
		String kakaoPw = pw.toString().substring(0);
		System.out.println(kakaoPw);
	}
}
