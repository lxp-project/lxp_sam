package com.lxp.course.controller;

import com.lxp.course.model.CourseListDto;
import com.lxp.course.model.CourseRegisterDto;
import com.lxp.course.model.CourseUpdateDto;
import com.lxp.course.service.CourseService;

import java.util.List;

public class CourseController {
    private final CourseService courseService;

    public CourseController() {
        this.courseService = new CourseService();
    }

    // Application(View)의 요청을 받아 Service에 전달하고 결과를 반환
    public List<CourseListDto> getCourses(int page) {
        return courseService.getCourseList(page);
    }

    //  Application(View)에서 전달받은 등록 정보를 Service로 전달
    public boolean registerCourse(CourseRegisterDto dto) {
        return courseService.registerCourse(dto);
    }

    public boolean updateCourse(CourseUpdateDto dto) {
        return courseService.updateCourse(dto);
    }

    public boolean deleteCourse(Long courseId) {
        return courseService.deleteCourse(courseId);
    }
}
