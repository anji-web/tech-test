package com.technical.test.service;

import com.technical.test.model.NameModel;
import com.technical.test.repository.SortRepository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SortNameService implements SortRepository {

  public ByteArrayResource getSortName(MultipartFile file) throws IOException {
    byte[] bytes = file.getBytes();
    String content = new String(bytes);
    String[] splitContent = content.split("\n");
    List<NameModel> newList = listNames(splitContent);
    List<String> listAfterSort = new ArrayList<>();
    Collections.sort(newList, new Comparator<NameModel>() {
      @Override
      public int compare(NameModel o1, NameModel o2) {
        int sortOrder = o1.getLastName().compareToIgnoreCase(o2.getLastName());
        if (sortOrder != 0){
          return sortOrder;
        }
        return o1.getLastName().compareToIgnoreCase(o2.getLastName());
      }

    });
    for (NameModel s : newList){
      String finalList = s.getFirstName() + " " + s.getGivenName() + s.getLastName();
      listAfterSort.add(finalList);
    }

    BufferedWriter result = new BufferedWriter(new FileWriter("sorted-names-list.txt"));
    for (String s : listAfterSort){
      result.write(s + "\n");
    }
    result.close();
    File newFile = new File("sorted-names-list.txt");
    Path path = Paths.get(newFile.getAbsolutePath());
    ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(path));
    return byteArrayResource;
  }

  @Override
  public List<NameModel> listNames(String[] names) {
    List<NameModel> newList = new ArrayList<>();
    for (String s : names){
      String[] findFirstOrLastName = s.split(" ");
      StringBuilder temp = new StringBuilder();
      NameModel nameModel = new NameModel();
      nameModel.setFirstName(findFirstOrLastName[0]);
      nameModel.setLastName(findFirstOrLastName[findFirstOrLastName.length-1]);
      if (findFirstOrLastName.length > 2){
        for (int i = 0; i < findFirstOrLastName.length; i++){
          if (i != 0 && i != (findFirstOrLastName.length - 1)){
              temp.append(findFirstOrLastName[i] + " ");
          }
        }
        nameModel.setGivenName(temp.toString());
      }else {
        nameModel.setGivenName("");
      }
      newList.add(nameModel);
    }
    return newList;
  }
}
