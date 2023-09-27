package top.godxu;

import top.godxu.pojo.Teacher;
import top.godxu.pojo.Class;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    public static void main(String[] args) {
        clearScreen();
        // showMainMenu();
        System.out.println(getAllTeachers());
    }

    /** 清屏 */
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 显示主菜单 */
    public static void showMainMenu() {
        System.out.println(" 1. 所有教师");
        System.out.println(" 2. 查询教师任课信息");
        System.out.println(" 3. 查询课程所有任教老师信息");
        System.out.print("\n 请输入操作数: ");
        mainMenu();
    }

    /** 主菜单操作 */
    public static void mainMenu() {
        int err = 0;
        int num = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            err = 0;
            String snum = scanner.nextLine();
            try {
                num = Integer.parseInt(snum);
                if (num < 1 || num > 6) {
                    clearScreen();
                    err = 1;
                    System.out.println("  >>输入无效<<");
                }
            } catch (NumberFormatException e) {
                clearScreen();
                err = 1;
                System.out.println("  >>输入无效<<");
            }
        } while (err == 1);
        if (err == 1) {
            scanner.close();
            return;
        }
        if (num == 1) {
            System.out.println(getAllTeachers());
            showMainMenu();
        } else if (num == 2) {
            clearScreen();
            
        } else if (num == 3) {
            clearScreen();
            
        } else {
            logout();
        }
        scanner.close();
        return;
    }

    /** 退出 */
    public static void logout() {
        clearScreen();
        System.out.println("\n==================================\n");
        System.out.println("      感谢使用");
        System.out.println("\n==================================\n");
        System.out.println("made by Godxu >> https://godxu.top\n\n");
    }

    /** 查询所有教师 */
    public static List<Teacher> getAllTeachers() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Teacher> Teachers = sqlSession.selectList("TeacherMapper.selectAll");
        return Teachers;
    }
}