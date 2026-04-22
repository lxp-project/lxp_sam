package com.lxp;

import com.lxp.config.JdbcConnectionManager;
import com.lxp.course.controller.CourseController;
import com.lxp.course.model.CourseListDto;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseController courseController = new CourseController();
        int currentPage = 1;

        System.out.println("온라인 강의 플랫폼 콘솔 프로그램에 오신 것을 환영합니다.");

        while (true) {
            System.out.println("\n=================================");
            System.out.println("       [ 강의 전체 목록 ] - " + currentPage + "페이지");
            System.out.println("=================================");

            // Controller를 통해 데이터를 요청
            List<CourseListDto> courses = courseController.getCourses(currentPage);

            // UI 처리 (콘솔 출력)
            if (courses.isEmpty()) {
                System.out.println("등록된 강의가 없거나 마지막 페이지를 초과했습니다.");
            } else {
                for (CourseListDto course : courses) {
                    System.out.println(course.toString());
                }
            }

            System.out.println("---------------------------------");
            System.out.println("1. 다음 페이지  2. 이전 페이지  3. 다른 페이지 이동  0. 종료");
            System.out.print("메뉴 선택: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (courses.size() < 20) {
                        System.out.println(">> 마지막 페이지입니다.");
                    } else {
                        currentPage++;
                    }
                    break;
                case "2":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println(">> 첫 페이지입니다.");
                    }
                    break;
                case "3":
                    System.out.print("이동할 페이지 번호 입력: ");
                    try {
                        int targetPage = Integer.parseInt(scanner.nextLine());
                        if (targetPage > 0) {
                            currentPage = targetPage;
                        } else {
                            System.out.println(">> 1 이상의 숫자를 입력하세요.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(">> 올바른 숫자를 입력하세요.");
                    }
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    // 애플리케이션 종료 시 DB 커넥션 풀 자원 해제
                    JdbcConnectionManager.closePool();
                    scanner.close();
                    return;
                default:
                    System.out.println(">> 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}
