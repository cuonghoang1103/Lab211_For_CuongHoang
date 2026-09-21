package service;

import constants.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import model.ArchiveJob;
import utils.FileUtils;

/**
 * CONCRETE CLASS of the Template Method: unzips a zip file into a folder, with
 * java.util.zip.ZipFile.
 *
 * @author HE176322
 */
public class ExtractTask extends ArchiveTask {

    // Step 1 for unzipping: the source must be an existing file.
    @Override
    protected void checkSource(ArchiveJob job) throws IOException {
        // a missing path, or a folder, is not a zip file
        if (!new File(job.getSourcePath()).isFile()) {
            throw new IOException(String.format(Message.ZIP_NOT_EXIST,
                    job.getSourcePath()));
        }
    }

    // Step 3 for unzipping: writes every entry under the destination folder.
    @Override
    protected void process(ArchiveJob job) throws IOException {
        File destination = new File(job.getDestinationPath());
        Enumeration<? extends ZipEntry> entryEnumeration = null;
        ZipEntry entry = null;
        File target = null;

        // the zip file is closed even when an entry fails
        try (ZipFile zip = openZip(job.getSourcePath())) {
            // a valid zip with no entry at all: nothing to report as done
            if (zip.size() == 0) {
                throw new IOException(String.format(Message.NO_ENTRY,
                        job.getSourcePath()));
            }

            // the entries, in the order they were zipped
            entryEnumeration = zip.entries();

            // one entry per turn
            while (entryEnumeration.hasMoreElements()) {
                entry = entryEnumeration.nextElement();
                target = new File(destination, entry.getName());

                // zip slip guard: never write outside the destination folder
                if (!FileUtils.isInside(target, destination)) {
                    throw new IOException(String.format(Message.ENTRY_OUTSIDE,
                            entry.getName()));
                }

                // a folder entry only creates the folder
                if (entry.isDirectory()) {
                    FileUtils.makeFolder(target);
                } else {
                    // a file entry: make its folder, then copy its bytes
                    FileUtils.makeFolder(target.getParentFile());

                    // the bytes of this one entry, read from the zip
                    try (InputStream in = zip.getInputStream(entry)) {
                        // written to the target file; both streams are closed afterwards
                        try (OutputStream out = new FileOutputStream(target)) {
                            FileUtils.copyStream(in, out);
                        }
                    }

                    job.addFileName(entry.getName());
                }
            }
        }
    }

    // Opens the zip; a file that is not a (finished) zip gets the program's own message
    // instead of the JDK's, whose wording changes between Java versions.
    private ZipFile openZip(String path) throws IOException {
        // ZipFile reads the central directory right here
        try {
            return new ZipFile(path);
        } catch (ZipException e) {
            // no central directory: a text file, a broken or unfinished zip
            throw new IOException(String.format(Message.NOT_ZIP, path));
        }
    }
}
