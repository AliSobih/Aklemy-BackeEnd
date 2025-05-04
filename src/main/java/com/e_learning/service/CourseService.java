package com.e_learning.service;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.NationalityDao;
import com.e_learning.dao.specification.CourseSpecification;
import com.e_learning.dao.specification.EnrollmentSpecification;
import com.e_learning.dao.specification.FilterCourseSpecification;
import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.CourseDetailsDTO;
import com.e_learning.dto.EnrollmentDTO;
import com.e_learning.dto.requestDTO.EnrollmentSearchRequestDTO;
import com.e_learning.dto.requestDTO.SearchRequestDTO;
import com.e_learning.entities.Course;
import com.e_learning.entities.Enrollment;
import com.e_learning.entities.Nationality;
import com.e_learning.entities.mapper.CourseMapper;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CourseService extends BaseServiceImp<Course> {
    @Autowired
    private final CourseDao courseDao;
    @Autowired
    private final NationalityDao nationalityDao;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public JpaRepository<Course, Long> Repository() {
        return courseDao;
    }

    public CourseDetailsDTO getDetails(Long id) {
        CourseDetailsDTO dto = new CourseDetailsDTO();
        AtomicInteger totalDuration = new AtomicInteger(0);
        AtomicInteger totalArticles = new AtomicInteger(0);
        Course course = courseDao.findById(id).orElse(null);
        course.getSections().forEach(section -> {
            section.getLessons().forEach(lesson -> {
                totalDuration.addAndGet(lesson.getDuration());
                if (Objects.equals(lesson.getContentType(), "pdf")) {
                    totalArticles.addAndGet(1);
                }
            });
        });

        int totalHours = Math.round(totalDuration.get() / 60.0f);
        if (totalHours < 1) {
            totalHours++;
        }
        dto.setTotalHours(totalHours);
        dto.setTotalArticles(totalArticles.get());
        return dto;
    }

    public PageResult<CourseDTO> getCoursesByCategoryId(Long categoryId, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Course> page = courseDao.findByCategoryId(categoryId, pageable);
        PageResult<Course> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());
        return courseMapper.toDataPage(pageResult);
    }

    public PageResult<CourseDTO> getCoursesByCategoryIdAndCountryCode(Long categoryId, String countryCode, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Course> page = courseDao.findByCategoryId(categoryId, pageable);
        PageResult<Course> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());
        PageResult<CourseDTO> dtoPageResult = courseMapper.toDataPage(pageResult);

        for (CourseDTO courseDTO : dtoPageResult.getData()) {
            setFormattedAmountToCourseDTO(courseDTO, countryCode);
        }

        return dtoPageResult;
    }

    public PageResult<CourseDTO> searchCourses(PageQueryUtil pageUtil, SearchRequestDTO searchRequestDTO, String countryCode) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit(), constructSortObject(searchRequestDTO));
        CourseSpecification courseSpecification = new CourseSpecification(searchRequestDTO.getSearchValue());

        Page<Course> coursePage = courseDao.findAll(courseSpecification, pageable);
        PageResult<Course> pageResult = new PageResult<>(coursePage.getContent(), (int) coursePage.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());

        PageResult<CourseDTO> dtoPageResult = courseMapper.toDataPage(pageResult);

        for (CourseDTO courseDTO : dtoPageResult.getData()) {
            setFormattedAmountToCourseDTO(courseDTO, countryCode);
        }
        return dtoPageResult ;
    }

    private Sort constructSortObject(SearchRequestDTO requestDTO) {
        if (requestDTO.getSortDirection() == null) {
            return Sort.by(Sort.Direction.DESC, "id");
        }
        return Sort.by(Sort.Direction.valueOf(requestDTO.getSortDirection()), requestDTO.getSortBy());
    }

    /**
     * @param id
     * @param countryCode
     * @return CourseDTO
     */
    public CourseDTO getById(Long id, String countryCode) {
        Course course = courseDao.getById(id);
        CourseDTO courseDTO = courseMapper.toDTO(course);
        setFormattedAmountToCourseDTO(courseDTO, countryCode);
        return courseDTO;
    }

    /**
     * @param countryCode String CountryCode
     * @return List of CourseDTO
     */
    public List<CourseDTO> getAllCourses(String countryCode) {
        List<Course> courses = courseDao.findAll();
        List<CourseDTO> courseDTOS = courseMapper.toDTOs(courses);
        for (CourseDTO courseDTO : courseDTOS) {
            setFormattedAmountToCourseDTO(courseDTO, countryCode);
        }

        return courseDTOS;
    }

    /**
     * @param countryCode
     * @param pageUtil
     * @return
     */
    public PageResult<CourseDTO> getCoursesPage(String countryCode, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Course> page = courseDao.findAll(pageable);
        PageResult<Course> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());

        PageResult<CourseDTO> dtoPageResult = courseMapper.toDataPage(pageResult);

        for (CourseDTO courseDTO : dtoPageResult.getData()) {
            setFormattedAmountToCourseDTO(courseDTO, countryCode);
        }

        return dtoPageResult;
    }


    /**
     * @param courseDTO
     * @param countryCode
     */

    private void setFormattedAmountToCourseDTO(CourseDTO courseDTO, String countryCode) {
        Long courseId = courseDTO.getId();
        Nationality nationality = nationalityDao.findByCountryCodeAndCourseId(countryCode, courseId);
        double price = courseDTO.getPrice();
        BigDecimal priceBeforeDiscount = new BigDecimal(courseDTO.getFixedPrice());

        if (nationality == null) {
            BigDecimal amount = new BigDecimal(price).setScale(0, RoundingMode.CEILING); // Round to the nearest integer, always rounding up
            String formattedAmountEn = amount.toString().concat(" ").concat("$");
            String formattedAmountAr = "\u202B".concat(amount.toString()).concat(" ").concat("دولار");

            BigDecimal amountBeforeDiscount = priceBeforeDiscount.setScale(0, RoundingMode.CEILING); // Round to the nearest integer, always rounding up
            String formattedAmountEnBeforeDiscount = amountBeforeDiscount.toString().concat(" ").concat("$");
            String formattedAmountArBeforeDiscount = "\u202B".concat(amountBeforeDiscount.toString()).concat(" ").concat("دولار");

            courseDTO.setFormattedAmountArBeforeDiscount(formattedAmountArBeforeDiscount);
            courseDTO.setFormattedAmountEnBeforeDiscount(formattedAmountEnBeforeDiscount);
            courseDTO.setFormattedAmountEn(formattedAmountEn);
            courseDTO.setFormattedAmountAr(formattedAmountAr);
        } else {
            double factor = nationality.getFactor();
            double rateExchange = nationality.getRateExchange();

            // calculate
            BigDecimal amount = new BigDecimal(price * (factor * rateExchange))
                    .setScale(0, RoundingMode.CEILING);  // Round to the nearest integer, always rounding up
            BigDecimal amountBeforeDiscount = new BigDecimal(courseDTO.getFixedPrice() * (factor * rateExchange))
                    .setScale(0, RoundingMode.CEILING);  // Round to the nearest integer, always rounding up
            String currencyEn = nationality.getCurrency();
            String currencyAr = nationality.getCurrencyAr();
            String formattedAmountEn = amount.toString().concat(" ").concat(currencyEn);
            String formattedAmountAr = "\u202B".concat(amount.toString()).concat(" ").concat(currencyAr);

            String formattedAmountEnBeforeDiscount = amountBeforeDiscount.toString().concat(" ").concat(currencyEn);
            String formattedAmountArBeforeDiscount = "\u202B".concat(amountBeforeDiscount.toString()).concat(" ").concat(currencyAr);

            courseDTO.setFormattedAmountEn(formattedAmountEn);
            courseDTO.setFormattedAmountAr(formattedAmountAr);

            courseDTO.setFormattedAmountEnBeforeDiscount(formattedAmountEnBeforeDiscount);
            courseDTO.setFormattedAmountArBeforeDiscount(formattedAmountArBeforeDiscount);
        }
    }

    /**
     * @param countryCode
     * @param pageUtil
     * @return
     */
    public PageResult<CourseDTO> approvedCoursesPage(String countryCode, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Course> page = courseDao.findAllApproved(pageable);
        PageResult<Course> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());

        PageResult<CourseDTO> dtoPageResult = courseMapper.toDataPage(pageResult);

        for (CourseDTO courseDTO : dtoPageResult.getData()) {
            setFormattedAmountToCourseDTO(courseDTO, countryCode);
        }
        return dtoPageResult;
    }

    public void approveCourse(Long courseId) {
        Course course = findById(courseId);
        if (course != null) {
            course.setApproved(true);
            courseDao.save(course);
        }
    }

    /**
     *
     * @param pageUtil
     * @param searchRequestDTO
     * @return
     */
    public PageResult<CourseDTO> filterCoursesForTeacher(PageQueryUtil pageUtil, SearchRequestDTO searchRequestDTO) {
        Page<Course> coursesPage;

        String loggedInUserEmail = searchRequestDTO.getLoggedInUserEmail();
        Boolean isAdminUser = searchRequestDTO.getIsAdminUser();

        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit(), constructSortObject(searchRequestDTO));

        if ((loggedInUserEmail != null && !loggedInUserEmail.trim().isEmpty()) || isAdminUser != null) {
            FilterCourseSpecification courseSpecification =
                    new FilterCourseSpecification(loggedInUserEmail, isAdminUser);

            coursesPage = courseDao.findAll(courseSpecification, pageable);
        } else {
            coursesPage = courseDao.findAll(pageable);
        }
        PageResult<Course> pageResult = new PageResult<>(coursesPage.getContent(), (int) coursesPage.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());

        return courseMapper.toDataPage(pageResult);
    }


    public boolean softDeleteCourseById(Long id) {
        Course course = courseDao.getById(id);

        if (course == null) {
            return false;
        }
        course.setDeleted(true);
        courseDao.save(course);
        return true;
    }
}
