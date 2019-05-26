package com.team_project2.hans.whatcatdo.profile;

import java.util.Scanner;

public class Driver {

     public void main(String[] args){
         Scanner sc = new Scanner(System.in);
         Setprofile profile= new Setprofile();//Setprofile 객체를 profile이라는 이름으로 새로 만든다

         System.out.println("고양이 이름이 뭐냥? :");
         profile.setCatname(sc.nextLine());//입력받은 값을 setCatename함수에 넣어준다
         System.out.println("고양이 종류가 뭐냥? :");
         profile.setSpecies(sc.nextLine());//입력받은 값을 setSpecies함수에 넣어준다
         System.out.println("고양이 혈액형이 뭐냥? :");
         profile.setBlood(sc.nextLine());//입력받은 값을 setBlood함수에 넣어준다
         System.out.println("고양이 몇살 이냥? :");
         profile.setAge(sc.nextInt());//입력받은 값을 seAge함수에 넣어준다

         System.out.println(profile.toString());//

    }
}
