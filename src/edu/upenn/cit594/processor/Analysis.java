package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;

public abstract class Analysis {

    public String extraArgPrompt() {
        return null;
    }

    public abstract String getId();

    public abstract void analyze(Dataset dataset, ResultEmitter emitter, String extraArg);
}
