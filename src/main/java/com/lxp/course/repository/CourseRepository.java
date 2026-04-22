package com.lxp.course.repository;

import com.lxp.config.JdbcConnectionManager;
import com.lxp.course.model.CourseListDto;
import com.lxp.course.model.CourseRegisterDto;
import com.lxp.course.model.CourseUpdateDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private static final int PAGE_SIZE = 20;

    // 강의 조회 기능
    public List<CourseListDto> findAllCoursesWithPaging(int page) {
        // ... 기존 코드와 동일 ...
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
                    courseList.add(new CourseListDto(
                        rs.getLong("course_id"), rs.getString("course_name"),
                        rs.getString("instructor_name"), rs.getString("category_name"),
                        rs.getLong("price"), rs.getString("difficult_level"), rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("[Repository 오류] " + e.getMessage());
        }
        return courseList;
    }

    // 강의 등록 기능
    public boolean insertCourse(CourseRegisterDto dto) {
        // created_at은 DB의 CURRENT_TIMESTAMP 함수를 사용하여 현재 시간으로 설정
        String sql = "INSERT INTO Courses (user_id, category_id, course_name, course_time, price, difficult_level, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = JdbcConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, dto.getUserId());
            pstmt.setLong(2, dto.getCategoryId());
            pstmt.setString(3, dto.getCourseName());
            pstmt.setInt(4, dto.getCourseTime());
            pstmt.setLong(5, dto.getPrice());
            pstmt.setString(6, dto.getDifficultLevel());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 정상적으로 1줄 이상 삽입되었으면 true 반환

        } catch (SQLException e) {
            System.err.println("[Repository 등록 오류] 외래키 제약조건(존재하지 않는 유저/카테고리) 등을 확인하세요: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCourse(CourseUpdateDto dto) {
        String sql = "UPDATE Courses SET course_name = ?, course_time = ?, price = ?, " +
            "difficult_level = ?, modified_at = CURRENT_TIMESTAMP " +
            "WHERE course_id = ?";

        try (Connection conn = JdbcConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getCourseName());
            pstmt.setInt(2, dto.getCourseTime());
            pstmt.setLong(3, dto.getPrice());
            pstmt.setString(4, dto.getDifficultLevel());
            pstmt.setLong(5, dto.getCourseId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 1줄 이상 수정되었으면 true

        } catch (SQLException e) {
            System.err.println("[Repository 오류] 강의 수정 실패: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCourse(Long courseId) {
        String sql = "DELETE FROM Courses WHERE course_id = ?";

        try (Connection conn = JdbcConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, courseId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 1줄 이상 삭제되었으면 true 반환

        } catch (SQLException e) {
            // 주의: DDL 구조상 해당 강의에 속한 섹션(Sections) 데이터가 존재할 경우,
            // 외래키(FK) 참조 무결성 제약조건에 의해 삭제가 거부될 수 있습니다.
            System.err.println("[Repository 오류] 강의 삭제 실패 (하위 섹션이 존재할 수 있습니다): " + e.getMessage());
            return false;
        }
    }
}
