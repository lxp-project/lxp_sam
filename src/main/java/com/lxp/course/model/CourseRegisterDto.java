package com.lxp.course.model;

public class CourseRegisterDto {
    private Long userId;          // 강사 ID
    private Long categoryId;      // 카테고리 ID
    private String courseName;    // 강의명
    private Integer courseTime;   // 강의 시간
    private Long price;           // 가격
    private String difficultLevel;// 난이도

    public CourseRegisterDto(Long userId, Long categoryId, String courseName,
                             Integer courseTime, Long price, String difficultLevel) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.price = price;
        this.difficultLevel = difficultLevel;
    }

    // Getter
    public Long getUserId() { return userId; }
    public Long getCategoryId() { return categoryId; }
    public String getCourseName() { return courseName; }
    public Integer getCourseTime() { return courseTime; }
    public Long getPrice() { return price; }
    public String getDifficultLevel() { return difficultLevel; }
}
