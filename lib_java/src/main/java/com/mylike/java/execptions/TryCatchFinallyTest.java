package com.mylike.java.execptions;

import java.util.ArrayList;
import java.util.List;

/**
 * author: liuxiansheng
 * data:2020/12/22 17:05
 * desc:try_catch_finally 执行顺序
 */
public class TryCatchFinallyTest {

    public static void main(String[] args) {
//        String str = test();
         int str1 = test21();
        System.out.println(str1+"");
    }
    //当没有执行到try里面，finally不会被执行
    public static int test21() {
        int i = 1;
      if(i == 1)
          return 0;
        System.out.println("the previous statement of try block");
        i = i / 0;
        try {
            System.out.println("try block");
            return i;
        }finally {
            System.out.println("finally block");
        }
    }
    //无异常，finally 中的 return 会导致提前返回
    public static String test() {
        try {
            System.out.println("try");
            return "return in try";
        } catch(Exception e) {
            System.out.println("catch");
            return "return in catch";
        } finally {
            System.out.println("finally");
            return "return in finally";
        }
    }
    //无异常，try 中的 return 会导致提前返回
    public static String test1() {
        try {
            System.out.println("try");
            return "return in try";
        } catch(Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
        return "return in function";
    }
    //有异常，finally 中的 return 会导致提前返回
    public static String test2() {
        try {
            System.out.println("try");
            throw new Exception();
        } catch(Exception e) {
            System.out.println("catch");
            return "return in catch";
        } finally {
            System.out.println("finally");
            return "return in finally";
        }
    }
    //有异常，catch 中的 return 会导致提前返回
    public static String test3() {
        try {
            System.out.println("try");
            throw new Exception();
        } catch(Exception e) {
            System.out.println("catch");
            return "return in catch";
        } finally {
            System.out.println("finally");
        }
    }
    //有异常，不会提前返回
    public static String test4() {
        try {
            System.out.println("try");
            throw new Exception();
        } catch(Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
        return "return in function";
    }
    //与我们上面的例子一致，finally 中的 return 导致提前返回，try 中的 return1 不会被执行。
    public static int test11() {
        try {
            return 1;
        } finally {
            return 2;
        }
    }
    //执行 try 中的代码后，再执行 finally 中的代码，最终 i 被赋值为 5，最后返回
    public static int test12() {
        int i;
        try {
            i = 3;
        } finally {
            i = 5;
        }
        return i;
    }
    //这个例子稍微有点意思，按我们通常的思维，应该还是返回 5，毕竟 finally 中把 i 赋值为 5 了嘛，然后由 try 中的 return 返回。然而很不幸，返回值是 3。
    public static int test13() {
        int i = 1;
        try {
            i = 3;
            return i;
        } finally {
            i = 5;
        }
    }
    //这个例子中，基本类型 int 被替换为引用类型 List，虽然 list 是按值传递，但它内部的状态可变（体现在这里，就是可以 add 元素）。
// 扩展：finally 只能保证对象本身不可变，但无法保证对象内部状态不可变。
    public static List test14() {
        List<Integer> list = new ArrayList<>();
        try {
            list.add(1);
            return list;
        } finally {
            list.add(2);
        }
    }
    //该函数没有返回值。原因：jvm 提前退出了。
    public static int test15() {
        try {
            System.exit(0);
        } finally {
            return 2;
        }
    }
    //由于 try 中的无限循环阻塞，永远执行不到 finally 中的代码块。
    public static int test16() {
        try {
            while(true) {
                System.out.println("Infinite loop.");
            }
        } finally {
            return 2;
        }
    }
//    总结
//    执行顺序：
//
//            try 代码块中 return 前面的部分 　　
//            catch 代码块中 return 前面的部分 　　
//            finally 代码块中 return 前面的部分 　　
//            finally 的 return 或 catch 的 return 或 try 的 return。若前面的 return 被执行，会导致提前返回，同时后面的 return 被忽略。 　　
//    方法的其他部分
//    变量：
//
//            　　注意 Java 的按值传递规则
//
//    特殊情况：
//
//            　　注意 finally 不会被执行的情况　
}
