package com.e_learning.security.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String[] WHITE_LIST = {
            "/v3-docs/**",
            "/v3-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",

            // basic-info
            "/basicInfo/all",
            "/registration",
            "/registration/confirm",
            // login
            "/login/sign-in",
            "/login/facebook",
            "/login/create-reset-password-token/{email}",
            "/login/conform-reset-password-token",
            "/login/reset-password",

            // categories
            "/categories/{id}",
            "/categories/all",
            "/categories/datapage",
            "/categories/downloadImage/{fileName}",

            //courses
            "/courses/details/{id}",
            "/courses/datapage",
            "/courses/downloadImage/{fileName}",
            "/courses/downloadVideo/{fileName}",
            "/courses/category/{categoryId}",
            "/courses/category/{categoryId}/{countryCode}",
            "/courses/{id}/{countryCode}",
            "/courses/datapage/{countryCode}",
            "/courses/search/{pageNumber}/{size}/{countryCode}",
            "/courses/approvedPage/{countryCode}",

            // description_detail
            "/description_detail/{id}",
            "/description_detail/all",
            "/description_detail/datapage",

            // description_master
            "/description_master/{id}",
            "/description_master/all",
            "/description_master/datapage",
            "/description_master/course/{courseId}",

            // reviews
            "/reviews/{id}",
            "/reviews/all",
            "/reviews/datapage",
            "/reviews/course/{courseId}",

            // images
            "/images/downloadImage/{fileName}",
            "/images/{id}",
            "/images/all",
            "/images/getAllAds",

            // teachers_request
            "/teachers_request/add",
            "/teachers_request/add-teachers-request",

            // users
            "/users/findByEmail",
    };

    public static final String[] AUTHENTICATED_LIST = {
            // certificates
            "/certificates/getCertificate",

            // enrollments
            "/enrollments/search/{pageNumber}/{size}",
            "/enrollments/update/{id}",
            "/enrollments/delete/{id}",
            "/enrollments/softdelete/{id}",
            "/enrollments/{id}",
            "/enrollments/all",
            "/enrollments/datapage",
            "/enrollments/add",
            "/enrollments/user/{userId}",

            // users
            "/users/{id}",
            "/users/add",
            "/users/all",
            "/users/datapage",
            "/users/update/{id}",
            "/users/delete/{id}",
            "/users/softdelete/{id}",

            // courses
            "/courses/filter/{pageNumber}/{size}",

            // coupons
            "/coupons/{id}",
            "/coupons/add",
            "/coupons/all",
            "/coupons/datapage",
            "/coupons/update/{id}",
            "/coupons/delete/{id}",
            "/coupons/softdelete/{id}",
            "/coupons/course/{courseID}",

            // questions
            "/questions/getCorrectAnswers/{questionId}",
            "/questions/downloadImage/{fileName}",

            // reviews
            "/reviews/add",
            "/reviews/update/{id}",
            "/reviews/delete/{id}",
            "/reviews/softdelete/{id}",

            // userexams
            "/userexams/{id}",
            "/userexams/update/{id}",
            "/userexams/delete/{id}",
            "/userexams/softdelete/{id}",
            "/userexams/start_exam",
            "/userexams/mark_exam/{userExamId}",
            "/userexams/pause/{userExamId}/{remainingTime}",
            "/userexams/paused_exams/{userId}",

            // user_exam_answers
            "/user_exam_answers/{id}",
            "/user_exam_answers/update/{id}",
            "/user_exam_answers/delete/{id}",
            "/user_exam_answers/softdelete/{id}",
            "/user_exam_answers/mark_answers",
            "/user_exam_answers/getUserAnswers/{userExamQuestionId}",

            // user_exam_drag_and_drops
            "/user_exam_drag_and_drops/{id}",
            "/user_exam_drag_and_drops/update/{id}",
            "/user_exam_drag_and_drops/delete/{id}",
            "/user_exam_drag_and_drops/softdelete/{id}",
            "/user_exam_drag_and_drops/mark_drag_and_drops",
            "/user_exam_drag_and_drops/getUserAnswers/{userExamQuestionId}",

            // user_exam_question
            "/user_exam_question/{id}",
            "/user_exam_question/add",
            "/user_exam_question/all",
            "/user_exam_question/datapage",
            "/user_exam_question/update/{id}",
            "/user_exam_question/delete/{id}",
            "/user_exam_question/softdelete/{id}",
            "/user_exam_question/tag",
            "/user_exam_question/untag",

            // watched-lessons
            "/watched-lessons/{id}",
            "/watched-lessons/add",
            "/watched-lessons/all",
            "/watched-lessons/datapage",
            "/watched-lessons/update/{id}",
            "/watched-lessons/delete/{id}",
            "/watched-lessons/softdelete/{id}",
            "/watched-lessons/watched-per-course/{courseId}/{userId}",
            "/watched-lessons/add",

    };

    public static final String[] ADMIN_LIST = {
            // basic-info
            "/basicInfo/add",
            "/basicInfo/update/{id}",

            //categories
            "/categories/add",
            "/categories/update/{id}",
            "/categories/delete/{id}",
            "/categories/softdelete/{id}",
            "/categories/uploadImage/",

            // certificates
            "/certificates/add",
            "/certificates/update/{id}",
            "/certificates/delete/{id}",
            "/certificates/softdelete/{id}",

            //courses
            "/courses/approve/{courseId}",

            // images
            "/images/add",
            "/images/update/{id}",
            "/images/delete/{id}",
            "/images/softdelete/{id}",
            "/images/datapage",
            "/images/upload",

            // teachers_request
            "/teachers_request/{id}",
            "/teachers_request/all",
            "/teachers_request/datapage",
            "/teachers_request/update/{id}",
            "/teachers_request/delete/{id}",
            "/teachers_request/softdelete/{id}",
            "/teachers_request/approve/{userEmail}",
    };

    public static final String[] TEACHER_LIST = {
            // courses
            "/courses/add",
            "/courses/update/{id}",
            "/courses/delete/{id}",
            "/courses/softdelete/{id}",
            "/courses/uploadImage",
            "/courses/uploadVideo",

            //enrollments
            "/enrollments/approve/{enrollId}",

            // description_detail
            "/description_detail/add",
            "/description_detail/update/{id}",
            "/description_detail/delete/{id}",
            "/description_detail/softdelete/{id}",

            // description_master
            "/description_master/add",
            "/description_master/update/{id}",
            "/description_master/delete/{id}",
            "/description_master/softdelete/{id}",

            // exams
            "/exams/{id}",
            "/exams/add",
            "/exams/update/{id}",
            "/exams/delete/{id}",
            "/exams/softdelete/{id}",
            "/exams/all",
            "/exams/datapage",
            "/exams/courseExams/{courseId}",
            "/exams/courseExam/{courseId}/{sectionId}",
            "/exams/courseExam/{courseId}",

            // lessons
            "/lessons/{id}",
            "/lessons/add",
            "/lessons/update/{id}",
            "/lessons/delete/{id}",
            "/lessons/softdelete/{id}",
            "/lessons/all",
            "/lessons/datapage",
            "/lessons/updateLesson/{lessonId}",

            // nationalities
            "/nationalities/{id}",
            "/nationalities/add",
            "/nationalities/update/{id}",
            "/nationalities/delete/{id}",
            "/nationalities/softdelete/{id}",
            "/nationalities/all",
            "/nationalities/datapage",
            "/nationalities/course/{courseID}",

            // questions
            "/questions/{id}",
            "/questions/add",
            "/questions/update/{id}",
            "/questions/delete/{id}",
            "/questions/softdelete/{id}",
            "/questions/all",
            "/questions/datapage",
            "/questions/course/{courseId}",
            "/questions/search/{pageNumber}/{size}",
            "/questions/uploadImage",

            // sections
            "/sections/{id}",
            "/sections/add",
            "/sections/update/{id}",
            "/sections/delete/{id}",
            "/sections/softdelete/{id}",
            "/sections/all",
            "/sections/datapage",

    };

    public static final String ROLES_ADMIN = "ADMIN";
    public static final String ROLES_TEACHER = "TEACHER";
    public static final String ROLES_STUDENT = "STUDENT";

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try{
            http
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(WHITE_LIST).permitAll()
                .antMatchers(AUTHENTICATED_LIST).hasAnyRole(ROLES_STUDENT, ROLES_TEACHER, ROLES_ADMIN)
                .antMatchers(TEACHER_LIST).hasAnyRole(ROLES_TEACHER, ROLES_ADMIN)
                .antMatchers(ADMIN_LIST).hasRole(ROLES_ADMIN)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
    }

}
