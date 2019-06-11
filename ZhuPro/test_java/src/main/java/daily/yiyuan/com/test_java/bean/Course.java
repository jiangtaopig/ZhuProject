package daily.yiyuan.com.test_java.bean;

import java.io.Serializable;

/**
 * 课程类
 */
public class Course implements Serializable {
    private String courseName;
    private float courseScore;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(float courseScore) {
        this.courseScore = courseScore;
    }
}
