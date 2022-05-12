package com.technical.test.repository;

import com.technical.test.model.NameModel;
import java.util.List;

public interface SortRepository {
  public List<NameModel> listNames(String[] names);
}
