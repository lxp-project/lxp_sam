package com.lxp.course.model;

import java.sql.Timestamp;

public class CourseListDto {
    private Long courseId;
    private String courseName;
    private String instructorName; // Users 테이블 조인
    private String categoryName;   // Category 테이블 조인
    private Long price;
    private String difficultLevel;
    private Timestamp createdAt;

    // 생성자
    public CourseListDto(Long courseId, String courseName, String instructorName,
                         String categoryName, Long price, String difficultLevel, Timestamp createdAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.categoryName = categoryName;
        this.price = price;
        this.difficultLevel = difficultLevel;
        this.createdAt = createdAt;
    }

    // Getter (출력을 위해 필요)
    public Long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getInstructorName() { return instructorName; }
    public String getCategoryName() { return categoryName; }
    public Long getPrice() { return price; }
    public String getDifficultLevel() { return difficultLevel; }
    public Timestamp getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("[%d] %s | 강사: %s | 분류: %s | 가격: %d원 | 난이도: %s",
            courseId, courseName, instructorName, categoryName, price, difficultLevel);
    }
}
