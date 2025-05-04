package com.e_learning.service;

import com.e_learning.dao.ExamDao;
import com.e_learning.dto.ExamDTO;
import com.e_learning.entities.Exam;
import com.e_learning.entities.mapper.ExamMapper;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService extends BaseServiceImp<Exam>{

    @Autowired
    private ExamDao examDao;

    @Autowired
    private ExamMapper examMapper ;

    @Override
    public JpaRepository<Exam, Long> Repository() {
        return examDao;
    }

//    public PageResult<ExamDTO> getExamByCourseId(Long courseID, PageQueryUtil pageUtil) {
//        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
//        Page<Exam> page = examDao.findByCourseId(courseID, pageable);
//        PageResult<Exam> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
//                pageUtil.getLimit(), pageUtil.getPage());
//        return examMapper.toDataPage(pageResult);
//    }

//    public PageResult<ExamDTO> getExamBySectionId(Long sectionId, PageQueryUtil pageUtil) {
//        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
//        Page<Exam> page = examDao.findBySectionId(sectionId, pageable);
//        PageResult<Exam> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
//                pageUtil.getLimit(), pageUtil.getPage());
//        return examMapper.toDataPage(pageResult);
//    }

    public PageResult<ExamDTO> getCourseExamByCourseId(Long courseID, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Exam> page = examDao.findByCourseIdAndSectionIdNull(courseID, pageable);
        PageResult<Exam> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());
        return examMapper.toDataPage(pageResult);
    }

    public List<ExamDTO> getCourseExamByCourseId(Long courseID) {
        List<Exam> exams = examDao.findByCourseIdAndSectionIdNull(courseID);
        return examMapper.toDTOs(exams);
    }

    public List<ExamDTO> getCourseExamBySectionId(Long courseId, Long sectionId) {
        List<Exam> exams   = examDao.findByCourseIdAndSectionId(courseId, sectionId);
        return examMapper.toDTOs(exams);
    }

    public List<ExamDTO> getCourseExams(Long courseId) {
        List<Exam> exams = examDao.findAllByCourseId(courseId);
        return examMapper.toDTOs(exams);
    }
}
