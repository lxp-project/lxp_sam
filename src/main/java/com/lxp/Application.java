package com.lxp;

import com.lxp.config.JdbcConnectionManager;
import com.lxp.course.controller.CourseController;
import com.lxp.course.model.CourseListDto;
import com.lxp.course.model.CourseRegisterDto;
import com.lxp.course.model.CourseUpdateDto;

import java.util.List;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CourseController courseController = new CourseController();

    public static void main(String[] args) {
        System.out.println(">> 강의 플랫폼 콘솔을 시작합니다.");

        while (true) {
            // 메인 메뉴에 '4.삭제' 추가
            System.out.print("\n[메인] 1.등록  2.조회  3.수정  4.삭제  0.종료 : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": handleRegisterCourse(); break;
                case "2": handleViewCourses(); break;
                case "3": handleUpdateCourse(); break;
                case "4": handleDeleteCourse(); break; // 삭제 메뉴 연결
                case "0":
                    System.out.println(">> 종료합니다.");
                    JdbcConnectionManager.closePool();
                    scanner.close();
                    return;
                default:
                    System.out.println(">> 0~4 사이의 숫자를 입력하세요.");
            }
        }
    }

    // [강의 등록 UI]
    private static void handleRegisterCourse() {
        System.out.println("\n[강의 등록]");
        try {
            System.out.print("강사ID: "); Long userId = Long.parseLong(scanner.nextLine());
            System.out.print("카테고리ID: "); Long categoryId = Long.parseLong(scanner.nextLine());
            System.out.print("강의명: "); String courseName = scanner.nextLine();
            System.out.print("시간(분): "); Integer courseTime = Integer.parseInt(scanner.nextLine());
            System.out.print("가격(원): "); Long price = Long.parseLong(scanner.nextLine());
            System.out.print("난이도: "); String difficultLevel = scanner.nextLine();

            CourseRegisterDto dto = new CourseRegisterDto(userId, categoryId, courseName, courseTime, price, difficultLevel);

            if (courseController.registerCourse(dto)) {
                System.out.println(">> 등록 완료!");
            } else {
                System.out.println(">> 등록 실패 (입력값 및 존재 여부 확인).");
            }
        } catch (NumberFormatException e) {
            System.out.println(">> [오류] ID, 시간, 가격은 숫자여야 합니다.");
        }
    }

    // [강의 전체 조회 UI]
    private static void handleViewCourses() {
        int currentPage = 1;

        while (true) {
            System.out.println("\n[강의 목록 - " + currentPage + "페이지]");
            List<CourseListDto> courses = courseController.getCourses(currentPage);

            if (courses.isEmpty()) {
                System.out.println("데이터가 없습니다.");
            } else {
                for (CourseListDto course : courses) {
                    System.out.println(course.toString());
                }
            }

            System.out.print("\n[메뉴] 1.이전  2.다음  3.페이지이동  0.메인 : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (currentPage > 1) currentPage--;
                    else System.out.println(">> 첫 페이지입니다.");
                    break;
                case "2":
                    if (courses.size() == 20) currentPage++;
                    else System.out.println(">> 마지막 페이지입니다.");
                    break;
                case "3":
                    System.out.print("이동할 페이지: ");
                    try {
                        currentPage = Math.max(Integer.parseInt(scanner.nextLine()), 1);
                    } catch (NumberFormatException e) {
                        System.out.println(">> 숫자를 입력하세요.");
                    }
                    break;
                case "0":
                    return; // 메인으로 복귀
                default:
                    System.out.println(">> 잘못된 입력입니다.");
            }
        }
    }

    private static void handleUpdateCourse() {
        System.out.println("\n[강의 수정]");
        try {
            System.out.print("수정할 강의ID: ");
            Long courseId = Long.parseLong(scanner.nextLine());

            System.out.print("새 강의명: ");
            String courseName = scanner.nextLine();

            System.out.print("새 시간(분): ");
            Integer courseTime = Integer.parseInt(scanner.nextLine());

            System.out.print("새 가격(원): ");
            Long price = Long.parseLong(scanner.nextLine());

            System.out.print("새 난이도: ");
            String difficultLevel = scanner.nextLine();

            CourseUpdateDto dto = new CourseUpdateDto(courseId, courseName, courseTime, price, difficultLevel);

            if (courseController.updateCourse(dto)) {
                System.out.println(">> 수정 완료!");
            } else {
                System.out.println(">> 수정 실패 (해당 강의 ID가 없거나 오류 발생).");
            }
        } catch (NumberFormatException e) {
            System.out.println(">> [오류] ID, 시간, 가격은 숫자여야 합니다.");
        }
    }

    private static void handleDeleteCourse() {
        System.out.println("\n[강의 삭제]");
        try {
            System.out.print("삭제할 강의ID: ");
            Long courseId = Long.parseLong(scanner.nextLine());

            // 안전장치: 한 번 더 확인
            System.out.print("정말 삭제하시겠습니까? (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                if (courseController.deleteCourse(courseId)) {
                    System.out.println(">> 삭제 완료!");
                } else {
                    System.out.println(">> 삭제 실패 (해당 강의 ID가 없거나, 등록된 섹션/강의자료가 있어 삭제할 수 없습니다).");
                }
            } else {
                System.out.println(">> 삭제를 취소했습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println(">> [오류] 강의ID는 숫자여야 합니다.");
        }
    }
}
