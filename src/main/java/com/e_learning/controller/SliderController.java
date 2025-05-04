package com.e_learning.controller;

import com.e_learning.dao.SliderDao;
import com.e_learning.dto.SliderDTO;
import com.e_learning.entities.Slider;
import com.e_learning.entities.mapper.SliderMapper;
import com.e_learning.service.SliderService;
import com.e_learning.service.StorageServiceOffline;
import com.e_learning.util.Constants;
import com.e_learning.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
@CrossOrigin
@Slf4j
public class SliderController extends BaseController<Slider, SliderDTO>{

    @Autowired
    private SliderService sliderService ;

    @Autowired
    private SliderMapper sliderMapper ;

    @Autowired
    private SliderDao sliderDao ;

    @Autowired
    private StorageServiceOffline storageServiceOffline ;

    //
    @GetMapping("/getAllAds")
    public ResponseEntity<List<SliderDTO>> downloadAds() {
        List<Slider> sliders=this.sliderService.getAllAds();
        return new ResponseEntity<>(this.sliderMapper.toDTOs(sliders), HttpStatus.OK);
    }
    // new method to custom delete
    @Override
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(value = "id") Long id) {
        Optional<Slider> slider = sliderDao.findById(id) ;
        if(slider.isPresent()){
            storageServiceOffline.deleteFile(List.of(slider.get().getImageUrl()), Constants.ADS_SEL);
            sliderDao.deleteById(id);
        }
        return new MessageResponse("Slider has been deleted successfully");
    }
      @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
      public ResponseEntity<MessageResponse> updateAds(@RequestParam("files") List<MultipartFile> files){
        this.storageServiceOffline.uploadFile(files,Constants.ADS_SEL);
        return new ResponseEntity<>(new MessageResponse("Images has been Uploaded"),HttpStatus.OK);
    }
    @GetMapping(value = "/downloadImage/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadSliderImage(@PathVariable String fileName) {
        try {
            byte[] data = storageServiceOffline.downloadFile(fileName, Constants.ADS_SEL);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return null;
        }
    }


}
