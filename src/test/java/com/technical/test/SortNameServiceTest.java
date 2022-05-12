package com.technical.test;

import com.technical.test.service.SortNameService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class SortNameServiceTest {
  @Autowired
  private SortNameService sortNameService;

  @Test
  public void testSort() throws IOException {
    File file = new File("unsorted-names-list.txt");
    System.out.println(file.getAbsolutePath());
    FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
    MockMultipartFile file1 = new MockMultipartFile("data", file.getName(), "text/plain", fileInputStream.readAllBytes());
    ByteArrayResource res = sortNameService.getSortName(file1);
    Assert.notNull(res,res.getFilename());
  }
}
