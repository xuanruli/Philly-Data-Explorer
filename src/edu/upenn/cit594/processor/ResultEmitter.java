package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;

public class ResultEmitter {
    private final List<String> results = new ArrayList<>();

    public void emit(String result) {
        results.add(result);
    }

    public List<String> getResults() {
        return results;
    }
}
