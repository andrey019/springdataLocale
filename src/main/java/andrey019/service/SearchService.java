package andrey019.service;


import andrey019.model.json.JsonSearchResult;

import java.util.List;

public interface SearchService {

    List<JsonSearchResult> findTodos(String email, String request);
}
