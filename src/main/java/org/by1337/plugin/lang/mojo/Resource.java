package org.by1337.plugin.lang.mojo;

import org.apache.maven.plugins.annotations.Parameter;

public class Resource {
    @Parameter(required = true)
    private String dir;

    @Parameter(required = true)
    private String relocate;

    @Parameter(required = true)
    private String output;

    public Resource() {
    }

    public Resource(String dir, String relocate, String output) {
        this.dir = dir;
        this.relocate = relocate;
        this.output = output;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getRelocate() {
        return relocate;
    }

    public void setRelocate(String relocate) {
        this.relocate = relocate;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
