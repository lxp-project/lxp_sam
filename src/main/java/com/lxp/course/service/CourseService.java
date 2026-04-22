package com.lxp.course.service;

import com.lxp.course.model.CourseDao;
import com.lxp.course.model.CourseListDto;
import com.lxp.course.repository.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService() {
        this.courseRepository = new CourseRepository();
    }

    // 비즈니스 로직 처리 및 Repository 호출
    public List<CourseListDto> getCourseList(int page) {
        // 페이지 번호 검증 방어 로직
        if (page < 1) {
            page = 1;
        }
        return courseRepository.findAllCoursesWithPaging(page);
    }
}
