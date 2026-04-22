package com.lxp.course.repository;

import com.lxp.config.JdbcConnectionManager;
import com.lxp.course.model.CourseListDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private static final int PAGE_SIZE = 20;

    public List<CourseListDto> findAllCoursesWithPaging(int page) {
        List<CourseListDto> courseList = new ArrayList<>();
        int offset = (page - 1) * PAGE_SIZE;

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
            // 실제 환경에서는 예외를 던져 Controller/Application에서 처리하도록 하는 것이 좋습니다.
            System.err.println("[Repository 오류] DB 처리 중 문제 발생: " + e.getMessage());
        }

        return courseList;
    }
}
