package com.lxp.course.model;

public class CourseUpdateDto {
    private Long courseId;        // 수정할 강의 ID
    private String courseName;    // 변경할 강의명
    private Integer courseTime;   // 변경할 강의 시간
    private Long price;           // 변경할 가격
    private String difficultLevel;// 변경할 난이도

    public CourseUpdateDto(Long courseId, String courseName, Integer courseTime,
                           Long price, String difficultLevel) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.price = price;
        this.difficultLevel = difficultLevel;
    }

    // Getter
    public Long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public Integer getCourseTime() { return courseTime; }
    public Long getPrice() { return price; }
    public String getDifficultLevel() { return difficultLevel; }
}
