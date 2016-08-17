package gui;

import controller.Utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class PersonFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {

        if (file.isDirectory()) {
            return true;
        }


        String name = file.getName();
        String extension = Utils.getFileExtention(name);

        if (extension == null) {
            return false;
        }

        if (".per".equals(extension)) {
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Person database files (*.per)";
    }
}
