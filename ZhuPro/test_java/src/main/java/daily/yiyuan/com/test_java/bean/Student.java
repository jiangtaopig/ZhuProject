package daily.yiyuan.com.test_java.bean;

import java.io.Serializable;

/**
 * 学生类
 * 学生类里面包含了课程类
 */
public class Student implements Serializable {
    private String name;
    private int age;
    private String sex;
    private Course course;

    public Student(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student name = " + name + ", age = " + age + ", sex = " + sex + ", courseName = "
                + course.getCourseName() + ", courseScore = " + course.getCourseScore();
    }
}
