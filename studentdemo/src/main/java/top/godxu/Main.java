package top.godxu;

import top.godxu.pojo.Employee;
import top.godxu.pojo.Position;

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
        mainMenu();
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
    public static void showMainMenu(int second) {
        if (second == 0) {
            System.out.println("\n----- Godxu 学生管理系统 -----\n");
        } else {
            System.out.println("----- Godxu 学生管理系统 -----\n");
        }
        System.out.println("\t 1. 学生列表");
        System.out.println("\t 2. 查询学生");
        System.out.println("\t 3. 添加学生");
        System.out.println("\t 4. 修改学生");
        System.out.println("\t 5. 删除学生");
        System.out.println("\t 6. 退出");
        System.out.print("\n    请输入操作数: ");
    }

    /** 根据班级id，获取学生班级名称 */
    public static String getPositionById(Integer id) {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Position> positions = sqlSession.selectList("PositionMapper.selectAll");
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getId() == id) {
                return positions.get(i).getPosition_name();
            }
        }
        return "不详";
    }

    /** 显示学生信息列表 */
    public static void showEmployees(List<Employee> employees) {
        if (employees.size() < 1) {
            System.out.println("\t >>无学生信息<<");
        }
        System.out.println(" 编号\t姓名\t年龄\t 班级\n");
        for (int i = 0; i < employees.size(); i++) {
            System.out.println(String.format(" %s\t%s\t%s\t %s", employees.get(i).getId(), employees.get(i).getName(),
                    employees.get(i).getAge(), getPositionById(employees.get(i).getPosition())));

        }
    }

    /** 显示学生信息 */
    public static void showEmployee(Employee employee) {
        clearScreen();
        if (employee == null) {
            System.out.println("\n----- Godxu 学生管理系统 -----");
            System.out.println("\t - 学生信息 -\n");
            System.out.println("       >> 无学生信息 <<");
        } else {
            System.out.println("\n----- Godxu 学生管理系统 -----");
            System.out.println("\t - 学生信息 -\n");
            System.out.println("\t  编号\t" + employee.getId());
            System.out.println("\t  姓名\t" + employee.getName());
            System.out.println("\t  年龄\t" + employee.getAge());
            System.out.println("\t  班级\t" + getPositionById(employee.getPosition()));
        }
    }

    /** 按任意键继续 */
    public static void waitForAnyKey() {
        System.out.print("\n\t按任意键继续...");
        try {
            System.in.read(); // 等待用户按下任意键
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 主菜单操作 */
    public static void mainMenu() {
        int err = 0;
        int num = 0;
        int second = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            err = 0;
            showMainMenu(second);
            String snum = scanner.next();
            try {
                num = Integer.parseInt(snum);
                if (num < 1 || num > 6) {
                    clearScreen();
                    err = 1;
                    second = 1;
                    System.out.println("\t >>输入无效<<");
                }
            } catch (NumberFormatException e) {
                clearScreen();
                err = 1;
                second = 1;
                System.out.println("\t >>输入无效<<");
            }
        } while (err == 1);
        if (err == 1) {
            scanner.close();
            return;
        }
        if (num == 1) {
            employeeList();
        } else if (num == 2) {
            clearScreen();
            System.out.println();
            selectEmployee();
        } else if (num == 3) {
            clearScreen();
            System.out.println();
            addEmployee();
        } else if (num == 4) {
            clearScreen();
            System.out.println();
            updateEmployee();
        } else if (num == 5) {
            clearScreen();
            System.out.println();
            deleteEmployee();
        } else {
            logout();
        }
        scanner.close();
    }

    /** 学生列表 */
    public static void employeeList() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Employee> employees = sqlSession.selectList("EmployeeMapper.selectAll");
        // System.out.println(employees);
        clearScreen();
        System.out.println("\n----- Godxu 学生管理系统 -----");
        System.out.println("\t - 学生列表 -\n");
        showEmployees(employees);
        waitForAnyKey();
        clearScreen();
        mainMenu();
    }

    /** 查询学生 */
    public static void selectEmployee() {
        System.out.println("----- Godxu 学生管理系统 -----");
        System.out.println("\t - 查询学生 -\n");
        System.out.print("\t  输入off返回 \n\n");
        System.out.print("      请输入学生编号: ");
        Scanner scanner = new Scanner(System.in);
        String snum = scanner.next();
        int num = 0;
        if (snum.equals("off")) {
            clearScreen();
            mainMenu();
            scanner.close();
            return;
        }
        try {
            num = Integer.parseInt(snum);
            String resource = "mybatis-config.xml";
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            Employee employee = sqlSession.selectOne("EmployeeMapper.selectById", num);
            showEmployee(employee);
            waitForAnyKey();
            clearScreen();
            System.out.println();
            selectEmployee();
        } catch (NumberFormatException e) {
            clearScreen();
            System.out.println("\t >>输入无效<<");
            selectEmployee();
        }
        scanner.close();
    }

    /** 添加学生 */
    public static void addEmployee() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Position> positions = sqlSession.selectList("PositionMapper.selectAll");
        Employee newEmployee = new Employee();
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- Godxu 学生管理系统 -----");
        System.out.println("\t - 添加学生 -\n");
        System.out.print("\t  输入off返回 \n\n");
        System.out.print("    请输入学生姓名: ");
        newEmployee.setName(scanner.next());
        if (newEmployee.getName().equals("off")) {
            clearScreen();
            mainMenu();
            scanner.close();
            return;
        }
        clearScreen();
        System.out.println("----- Godxu 学生管理系统 -----");
        System.out.println("\n\t - 输入年龄 -\n");
        System.out.print("    请输入学生年龄: ");
        try {
            newEmployee.setAge(scanner.nextInt());
        } catch (InputMismatchException e) {
            clearScreen();
            System.out.println("\t >>输入无效<<");
            addEmployee();
            scanner.close();
            return;
        }
        clearScreen();
        System.out.println("----- Godxu 学生管理系统 -----");
        System.out.println("\n\t - 选择班级 -\n");
        for (int i = 0; i < positions.size(); i++) {
            System.out.println("\t    " + positions.get(i).getId() + "." + positions.get(i).getPosition_name());
        }
        System.out.print("\n  请输入学生班级序号: ");
        try {
            newEmployee.setPosition(scanner.nextInt());
            int flag = 0;
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).getId() == newEmployee.getPosition()) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                clearScreen();
                System.out.println("\t >>输入无效<<");
                addEmployee();
                scanner.close();
                return;
            } else {
                sqlSession.insert("EmployeeMapper.insertEmployee", newEmployee);
                sqlSession.commit();
                clearScreen();
                System.out.println("\t >>添加成功<<");
                System.out.println("----- Godxu 学生管理系统 -----");
                System.out.println("\t - 学生信息 -\n");
                System.out.println("\t  姓名\t" + newEmployee.getName());
                System.out.println("\t  年龄\t" + newEmployee.getAge());
                System.out.println("\t  班级\t" + getPositionById(newEmployee.getPosition()));
                waitForAnyKey();
                clearScreen();
                mainMenu();
                scanner.close();
                return;
            }
        } catch (InputMismatchException e) {
            clearScreen();
            System.out.println("\t >>输入无效<<");
            addEmployee();
            scanner.close();
            return;
        }
    }

    /** 修改学生 */
    public static void updateEmployee() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Employee> employees = sqlSession.selectList("EmployeeMapper.selectAll");
        List<Position> positions = sqlSession.selectList("PositionMapper.selectAll");
        // System.out.println(employees);
        System.out.println("----- Godxu 学生管理系统 -----");
        System.out.println("\t - 学生列表 -\n");
        showEmployees(employees);
        System.out.print("\n输入修改的学生编号(off返回):");
        Scanner scanner = new Scanner(System.in);
        String snum = scanner.next();
        int num = 0;
        if (snum.equals("off")) {
            clearScreen();
            mainMenu();
            scanner.close();
            return;
        }
        try {
            num = Integer.parseInt(snum);
            int flag = 0;
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId() == num) {
                    flag = 1;
                    clearScreen();
                    System.out.println("\n----- Godxu 学生管理系统 -----");
                    System.out.println("\t - 学生信息 -\n");
                    System.out.println("\t  编号\t" + employees.get(i).getId());
                    System.out.println("\t  姓名\t" + employees.get(i).getName());
                    System.out.println("\t  年龄\t" + employees.get(i).getAge());
                    System.out.println("\t  班级\t" + getPositionById(employees.get(i).getPosition()));
                    System.out.print("\n    > 1.姓名 2.年龄 3.班级 <\n\n    输入需要修改的序号:");
                    try {
                        int choice = scanner.nextInt();
                        if (choice < 1 || choice > 3) {
                            clearScreen();
                            System.out.println("\t >>输入无效<<");
                            updateEmployee();
                            scanner.close();
                            return;
                        } else if (choice == 1) {
                            clearScreen();
                            System.out.println("\n----- Godxu 学生管理系统 -----");
                            System.out.println("\t - 修改姓名 -\n\n");
                            System.out.print("\n    输入新姓名:");
                            employees.get(i).setName(scanner.next());
                            sqlSession.update("EmployeeMapper.updateEmployeeName", employees.get(i));
                            sqlSession.commit();
                        } else if (choice == 2) {
                            clearScreen();
                            System.out.println("\n----- Godxu 学生管理系统 -----");
                            System.out.println("\t - 修改年龄 -\n\n");
                            System.out.print("\n    输入新年龄:");
                            try {
                                employees.get(i).setAge(scanner.nextInt());
                                sqlSession.update("EmployeeMapper.updateEmployeeAge", employees.get(i));
                                sqlSession.commit();
                            } catch (InputMismatchException e) {
                                clearScreen();
                                System.out.println("\t >>输入无效<<");
                                updateEmployee();
                                scanner.close();
                                return;
                            }
                        } else if (choice == 3) {
                            clearScreen();
                            System.out.println("\n----- Godxu 学生管理系统 -----");
                            System.out.println("\t - 修改班级 -\n\n");
                            for (int j = 0; j < positions.size(); j++) {
                                System.out.println(
                                        "\t    " + positions.get(j).getId() + "."
                                                + positions.get(j).getPosition_name());
                            }
                            System.out.print("\n     输入新班级序号:");
                            try {
                                employees.get(i).setPosition(scanner.nextInt());
                                int flag2 = 0;
                                for (int x = 0; x < positions.size(); x++) {
                                    if (positions.get(x).getId() == employees.get(x).getPosition()) {
                                        flag2 = 1;
                                    }
                                }
                                if (flag2 == 0) {
                                    clearScreen();
                                    System.out.println("\t >>输入无效<<");
                                    updateEmployee();
                                    scanner.close();
                                    return;
                                } else {
                                    sqlSession.update("EmployeeMapper.updateEmployeePosition", employees.get(i));
                                    sqlSession.commit();
                                }
                            } catch (InputMismatchException e) {
                                clearScreen();
                                System.out.println("\t >>输入无效<<");
                                updateEmployee();
                                scanner.close();
                                return;
                            }
                        }
                        clearScreen();
                        System.out.println("\t >>修改成功<<");
                        System.out.println("----- Godxu 学生管理系统 -----");
                        System.out.println("\t - 学生信息 -\n");
                        System.out.println("\t  编号\t" + employees.get(i).getId());
                        System.out.println("\t  姓名\t" + employees.get(i).getName());
                        System.out.println("\t  年龄\t" + employees.get(i).getAge());
                        System.out.println("\t  班级\t" + getPositionById(employees.get(i).getPosition()));
                        waitForAnyKey();
                        clearScreen();
                        mainMenu();
                        scanner.close();
                        return;
                    } catch (InputMismatchException e) {
                        clearScreen();
                        System.out.println("\t >>输入无效<<");
                        updateEmployee();
                        scanner.close();
                        return;
                    }
                }
                break;
            }
            if (flag == 0) {
                clearScreen();
                System.out.println("\t >>输入无效<<");
                updateEmployee();
                scanner.close();
                return;
            }
        } catch (NumberFormatException e) {
            clearScreen();
            System.out.println("\t >>输入无效<<");
            updateEmployee();
            scanner.close();
            return;
        }
    }

    /** 删除学生 */
    public static void deleteEmployee() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Employee> employees = sqlSession.selectList("EmployeeMapper.selectAll");
        // System.out.println(employees);
        System.out.println("----- Godxu 学生管理系统 -----");
        System.out.println("\t - 学生列表 -\n");
        showEmployees(employees);
        System.out.print("\n输入删除的学生编号(off返回):");
        Scanner scanner = new Scanner(System.in);
        String snum = scanner.next();
        int num = 0;
        if (snum.equals("off")) {
            clearScreen();
            mainMenu();
            scanner.close();
            return;
        }
        try {
            num = Integer.parseInt(snum);
            int flag = 0;
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId() == num) {
                    flag = 1;
                    clearScreen();
                    System.out.println("----- Godxu 学生管理系统 -----");
                    System.out.println("\t - 学生信息 -\n");
                    System.out.println("\t  编号\t" + employees.get(i).getId());
                    System.out.println("\t  姓名\t" + employees.get(i).getName());
                    System.out.println("\t  年龄\t" + employees.get(i).getAge());
                    System.out.println("\t  班级\t" + getPositionById(employees.get(i).getPosition()));
                    System.out.print("\n       确认删除(y/n):");
                    String confirm = scanner.next();
                    if (confirm.equals("y") || confirm.equals("Y")) {
                        sqlSession.delete("EmployeeMapper.deleteEmployee", num);
                        sqlSession.commit();
                        clearScreen();
                        System.out.println("\t >>删除成功<<");
                        deleteEmployee();
                        scanner.close();
                        return;
                    } else {
                        clearScreen();
                        System.out.println();
                        deleteEmployee();
                        scanner.close();
                        return;
                    }
                }
            }
            if (flag == 0) {
                clearScreen();
                System.out.println("\t >>输入无效<<");
                deleteEmployee();
                scanner.close();
                return;
            }
        } catch (NumberFormatException e) {
            clearScreen();
            System.out.println("\t >>输入无效<<");
            deleteEmployee();
            scanner.close();
            return;
        }
    }

    /** 退出 */
    public static void logout() {
        clearScreen();
        System.out.println("\n==================================\n");
        System.out.println("\t     感谢使用");
        System.out.println("\n==================================\n");
        System.out.println("made by Godxu >> https://godxu.top\n\n");
    }
}
