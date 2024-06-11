package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String... args) {
        Fraction fr= new Fraction(2,3);
        Fractionable num =Utils.cache(fr);
        Fractionable num2 = Utils.cache(new Fraction(3,4));
        System.out.println("num " + num.doubleValue());// sout сработал
        System.out.println("--------------------");
        System.out.println("num2 " + num2.doubleValue());// sout сработал
        System.out.println("--------------------");
        System.out.println("num " + num.doubleValue());// sout молчит
        System.out.println("--------------------");
        System.out.println("num2 " + num2.doubleValue());// sout молчит
        System.out.println("--------------------");
        num.setNum(5);
        System.out.println("num " + num.doubleValue());// sout сработал
        System.out.println("num " + num.doubleValue());// sout молчит
        System.out.println("num " + num.doubleValue());// sout молчит
        num.setDenum(8);
        System.out.println("num " + num.doubleValue());// sout сработал
        System.out.println("num " + num.doubleValue());// sout молчит
        System.out.println("num " + num.doubleValue());// sout сработал
        System.out.println("num2 " + num2.doubleValue());// sout молчит

    }

}
