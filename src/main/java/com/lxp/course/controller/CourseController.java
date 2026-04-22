package com.lxp.course.controller;

import com.lxp.course.model.CourseListDto;
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
}
