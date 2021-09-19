package com.parser.letters.controllers.dao;





import com.parser.letters.models.classes4Hibernate.Email2;

import java.util.List;
import java.util.Map;

public interface DAO {

    public void saveEntry(Email2 email2);

    public void updateEntry(Email2 email2);

    public void delEntry(Email2 email2);

    public void delEntry(int id);

    public String getEmail(int id);

    public Map<String, String> getStatuses(int... ids);

    public List<String> getEmailsByUrl(String url);

    public List<Email2> getAll();
}
