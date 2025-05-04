package com.e_learning.controller;

import com.e_learning.dto.CertificateDTO;
import com.e_learning.entities.Certificate;
import com.e_learning.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificates")
@CrossOrigin
public class CertificateController extends BaseController<Certificate, CertificateDTO> {

    @Autowired
    private CertificateService certificateService;

    @PostMapping(value = "/getCertificate")
    public CertificateDTO generateCertificate(@RequestBody CertificateDTO certificateDTO) {
        return certificateService.getCertificate(certificateDTO);
    }
}
