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
 * sub-folders), or one single file, into one zip file, with java.util.zip.ZipOutputStream.
 *
 * @author HE176322
 */
public class CompressTask extends ArchiveTask {

    // Step 1 for zipping: the source must exist - a folder, or one file (the brief's
    // Test.docx zips D:/Test2/FileName1.txt).
    @Override
    protected void checkSource(ArchiveJob job) throws IOException {
        // a path that is neither a folder nor a file cannot be zipped
        if (!new File(job.getSourcePath()).exists()) {
            throw new IOException(String.format(Message.SOURCE_FOLDER_NOT_EXIST,
                    job.getSourcePath()));
        }
    }

    // Step 3 for zipping: one zip entry per folder and per file under the source folder,
    // or one entry for the source file, under its own name.
    @Override
    protected void process(ArchiveJob job) throws IOException {
        File source = new File(job.getSourcePath());
        File archive = new File(job.getDestinationPath(), getZipFileName(job.getZipName()));
        File baseFolder = source;
        ArrayList<File> itemList = new ArrayList<>();
        String name = "";

        // one file: that file alone, named relative to its own folder ("FileName1.txt")
        if (source.isFile()) {
            baseFolder = source.getAbsoluteFile().getParentFile();
            itemList.add(source);
        } else {
            // a folder: everything under it, depth-first, sorted by name
            FileUtils.listAll(source, itemList);
        }

        // closing the stream writes the central directory of the zip
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(archive))) {
            // one entry per folder or file
            for (File item : itemList) {
                // the zip being written may live inside the source folder: zipping it into
                // itself would never end
                if (FileUtils.isSameFile(item, archive)) {
                    continue;
                }

                // the name inside the zip, always with "/" ("docs/guide.txt")
                name = FileUtils.getRelativeName(baseFolder, item);
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
    private String getZipFileName(String zipName) {
        // already ends with .zip (in any case): keep it as typed
        if (zipName.toLowerCase().endsWith(Constants.ZIP_EXTENSION)) {
            return zipName;
        }

        return String.format(Constants.ZIP_NAME_FORMAT, zipName);
    }
}
