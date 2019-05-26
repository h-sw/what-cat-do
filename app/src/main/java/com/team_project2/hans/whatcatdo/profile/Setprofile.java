package com.team_project2.hans.whatcatdo.profile;

public class Setprofile {
    private String catname;//고양이 이름
    private String species;//고양이 종
    private String blood;//혈액형
    private int age;//고양이 이름

    //고양이의 이름을 설정해준다
    public void setCatname(String catename){
       this.catname=catename;
    }

    //고양이의 종을 설정해준다
    public void setSpecies(String species){
        this.species=species;
    }

    //고양이의 혈액형을 설정해준다
    public void setBlood(String blood){
        this.blood=blood;
    }

    //고양이의 나이를 설정해준다
    public void setAge(int age){
        this.age=age;
    }

    //고양이의 이름을 리턴해준다
    public String getCatename(){
        return catname;
    }

    //고양이의 종류를 리턴해준다
    public String getSpecies(){
        return species;
    }

    //고양이의 혈액형을 리턴해준다
    public String getBlood(){
        return blood;
    }

    //고양이의 나이를 리턴해준다
    public int getAge(){
        return age;
    }

    //고양이의 정보들을 다 출력해준다
    public String toString(){
        String str;
        str="고양이 이름: "+getCatename()+"\n고양이 종류: "+getSpecies()+"\n고양이 혈액형: "+getBlood()+"\n고양이 나이: "+getAge();
        return str;
    }

}
