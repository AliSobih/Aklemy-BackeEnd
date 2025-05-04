package com.e_learning.service;

import com.e_learning.dao.CertificateDao;
import com.e_learning.dao.CourseDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.CertificateDTO;
import com.e_learning.entities.Certificate;
import com.e_learning.entities.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CertificateService extends BaseServiceImp<Certificate> {
    @Autowired
    private CertificateDao certificateDao;

    @Autowired
    private CertificateMapper certificateMapper;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    @Override
    public JpaRepository<Certificate, Long> Repository() {
        return certificateDao;
    }

    public CertificateDTO getCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = certificateDao.findUserCertificate(certificateDTO.getCourseId(), certificateDTO.getStudentId());
        if (certificate == null) {
            certificate = this.save(certificateMapper.toEntity(certificateDTO));
        }
        return certificateMapper.toDTO(certificate);
    }
}
