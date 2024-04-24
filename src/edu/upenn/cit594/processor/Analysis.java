package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Dataset;

public abstract class Analysis {

    public abstract void analyze(Dataset dataset, ResultEmitter emitter);
}
