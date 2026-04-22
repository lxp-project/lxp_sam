package com.lxp.course.service;

import com.lxp.course.model.CourseListDto;
import com.lxp.course.model.CourseRegisterDto;
import com.lxp.course.model.CourseUpdateDto;
import com.lxp.course.repository.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService() {
        this.courseRepository = new CourseRepository();
    }

    public List<CourseListDto> getCourseList(int page) {
        if (page < 1) page = 1;
        return courseRepository.findAllCoursesWithPaging(page);
    }

    // (신규) 비즈니스 규칙 검증 및 등록 처리
    public boolean registerCourse(CourseRegisterDto dto) {
        // 간단한 검증 로직 예시 (비즈니스 룰)
        if (dto.getCourseName() == null || dto.getCourseName().trim().isEmpty()) {
            System.out.println(">> [서비스 오류] 강의명은 필수입니다.");
            return false;
        }
        if (dto.getPrice() != null && dto.getPrice() < 0) {
            System.out.println(">> [서비스 오류] 가격은 0보다 작을 수 없습니다.");
            return false;
        }

        return courseRepository.insertCourse(dto);
    }

    public boolean updateCourse(CourseUpdateDto dto) {
        // 기본 검증 로직
        if (dto.getCourseId() == null || dto.getCourseId() <= 0) {
            System.out.println(">> [오류] 유효하지 않은 강의 ID입니다.");
            return false;
        }
        if (dto.getCourseName() == null || dto.getCourseName().trim().isEmpty()) {
            System.out.println(">> [오류] 강의명은 필수입니다.");
            return false;
        }

        return courseRepository.updateCourse(dto);
    }

    public boolean deleteCourse(Long courseId) {
        // 검증 로직: ID 유효성 체크
        if (courseId == null || courseId <= 0) {
            System.out.println(">> [오류] 유효하지 않은 강의 ID입니다.");
            return false;
        }
        return courseRepository.deleteCourse(courseId);
    }
}
