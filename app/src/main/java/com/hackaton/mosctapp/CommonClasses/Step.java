package com.hackaton.mosctapp.CommonClasses;

/**
 * Created by tema on 26.04.15.
 */

 enum Steps {
    Straight,
    Right,
    Left
}

public class Step {
    public Steps step;
    public String description;

    public Step(Steps step, String description) {
        this.step = step;
        this.description = description;
    }
}
