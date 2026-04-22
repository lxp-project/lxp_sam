package com.lxp.course.model;

import com.lxp.config.JdbcConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    // LIMIT은 상수로 20 고정
    private static final int PAGE_SIZE = 20;

    public List<CourseListDto> findAllCoursesWithPaging(int page) {
        List<CourseListDto> courseList = new ArrayList<>();

        // OFFSET 계산: 1페이지->0, 2페이지->20, 3페이지->40 ...
        int offset = (page - 1) * PAGE_SIZE;

        // 최신 등록순(course_id DESC)으로 조회하며 Users, Category 테이블과 JOIN
        String sql = "SELECT c.course_id, c.course_name, u.name as instructor_name, " +
            "cat.category_name, c.price, c.difficult_level, c.created_at " +
            "FROM Courses c " +
            "JOIN Users u ON c.user_id = u.user_id " +
            "JOIN Category cat ON c.category_id = cat.category_id " +
            "ORDER BY c.course_id DESC " +
            "LIMIT ? OFFSET ?";

        try (Connection conn = JdbcConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, PAGE_SIZE);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CourseListDto dto = new CourseListDto(
                        rs.getLong("course_id"),
                        rs.getString("course_name"),
                        rs.getString("instructor_name"),
                        rs.getString("category_name"),
                        rs.getLong("price"),
                        rs.getString("difficult_level"),
                        rs.getTimestamp("created_at")
                    );
                    courseList.add(dto);
                }
            }
        } catch (SQLException e) {
            System.err.println("강의 목록 조회 중 DB 오류 발생: " + e.getMessage());
        }

        return courseList;
    }
}
