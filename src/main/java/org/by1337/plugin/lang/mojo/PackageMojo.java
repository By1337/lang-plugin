package org.by1337.plugin.lang.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
public class PackageMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;
    @Parameter
    private List<Resource> packageList;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        File input = this.project.getArtifact().getFile();
        for (Resource resource : packageList) {
            apply(input, resource);
        }
    }

    public static void apply(File input, Resource resource) {
        File out = new File(input.getParent(), resource.getOutput());

        String dir = resource.getDir(); // e.g., en_us/
        String relocate = resource.getRelocate(); // e.g., /

        try (JarFile jarFile = new JarFile(input);
             JarOutputStream outJar = new JarOutputStream(Files.newOutputStream(out.toPath()))) {

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith(dir)) {
                    entryName = entryName.substring(dir.length());
                    if (!relocate.equals("/")) {
                        entryName += relocate;
                    }
                }
                if (entryName.isEmpty()) continue;

                try (InputStream in = jarFile.getInputStream(entry)) {

                    ZipEntry zipEntry = new ZipEntry(entryName);
                    outJar.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        outJar.write(buffer, 0, bytesRead);
                    }
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
