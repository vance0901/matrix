package com.vance.asminject;

public class OutClass {

    int j = 100;
    private int i = 200;

    //public static int access$000(){
     //   return i;
    //}

    class InnterClass{
        public InnterClass() {
            System.out.println(i);
            System.out.println(j);
        }
    }
}
