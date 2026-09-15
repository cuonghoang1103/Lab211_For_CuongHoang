package service;

import constants.Constants;
import constants.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import model.ArchiveJob;
import utils.FileUtils;

/**
 * CONCRETE CLASS of the Template Method: zips every file of a folder (and of its
 * sub-folders) into one zip file, with java.util.zip.ZipOutputStream.
 *
 * @author HE176322
 */
public class CompressTask extends ArchiveTask {

    // Step 1 for zipping: the source must be an existing folder.
    @Override
    protected void checkSource(ArchiveJob job) throws IOException {
        // a missing path, or a file, cannot be zipped as a folder
        if (!new File(job.getSourcePath()).isDirectory()) {
            throw new IOException(String.format(Message.SOURCE_FOLDER_NOT_EXIST,
                    job.getSourcePath()));
        }
    }

    // Step 3 for zipping: one zip entry per folder and per file.
    @Override
    protected void process(ArchiveJob job) throws IOException {
        File source = new File(job.getSourcePath());
        File archive = new File(job.getDestinationPath(), zipFileName(job.getZipName()));
        ArrayList<File> items = new ArrayList<>();
        FileUtils.listAll(source, items);
        // closing the stream writes the central directory of the zip
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(archive))) {
            // one entry per folder or file, depth-first, sorted by name
            for (File item : items) {
                // the zip being written may live inside the source folder:
                // zipping it into itself would never end
                if (FileUtils.isSameFile(item, archive)) {
                    continue;
                }
                String name = FileUtils.relativeName(source, item);
                out.putNextEntry(new ZipEntry(name));
                // a folder entry ("docs/") has no content; a file is copied
                if (item.isFile()) {
                    // the file stream is closed, the zip stream stays open
                    try (InputStream in = new FileInputStream(item)) {
                        FileUtils.copyStream(in, out);
                    }
                    job.addFileName(name);
                }
                out.closeEntry();
            }
        }
    }

    // Adds ".zip" when the user typed a name without it: a file called "backup" is not
    // recognised as a zip by any tool.
    private String zipFileName(String zipName) {
        // already ends with .zip (in any case): keep it as typed
        if (zipName.toLowerCase().endsWith(Constants.ZIP_EXTENSION)) {
            return zipName;
        }
        return zipName + Constants.ZIP_EXTENSION;
    }
}
