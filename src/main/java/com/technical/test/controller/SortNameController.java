package com.technical.test.controller;

import com.technical.test.service.SortNameService;
import javax.print.attribute.standard.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("sort")
public class SortNameController {

    @Autowired
    private SortNameService sortNameService;

    @GetMapping("/names")
    public ResponseEntity sortNames(@RequestParam("file") MultipartFile file){
          try {
              ByteArrayResource sortValue = sortNameService.getSortName(file);
              HttpHeaders header = new HttpHeaders();
              header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sorted-names-list.txt");
              header.add("Cache-Control", "no-cache, no-store, must-revalidate");
              header.add("Pragma", "no-cache");
              header.add("Expires", "0");
              return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_OCTET_STREAM).body(sortValue);
          }catch (Exception e){
              return ResponseEntity.status(400).body(e.getMessage());
          }
    }

}
