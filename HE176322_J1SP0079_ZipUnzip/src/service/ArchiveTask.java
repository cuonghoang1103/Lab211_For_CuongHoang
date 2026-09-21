package service;

import constants.Message;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.ArchiveJob;
import utils.FileUtils;

/**
 * TEMPLATE METHOD (design pattern): the skeleton shared by zipping and unzipping.
 *
 * @author HE176322
 */
public abstract class ArchiveTask {

    // The template method: runs the steps in their fixed order; true when every step
    // worked (the brief: the functions return the status).
    public final boolean execute(ArchiveJob job) {
        File destination = new File(job.getDestinationPath());

        // a fresh job: no file has gone through, nothing has failed
        job.setFileNameList(new ArrayList<String>());
        job.setError("");

        // any IO problem stops the job and becomes its reason
        try {
            checkSource(job);

            // both jobs: the destination folder is created when missing
            if (!FileUtils.makeFolder(destination)) {
                throw new IOException(String.format(Message.CANNOT_CREATE_FOLDER,
                        job.getDestinationPath()));
            }

            process(job);
            return true;
        } catch (IOException e) {
            // the reason is shown by the view before "Failed"
            job.setError(e.getMessage());
            return false;
        }
    }

    // Step 1, different for each job: the source must exist (a folder or a file to zip, a
    // zip file to unzip).
    protected abstract void checkSource(ArchiveJob job) throws IOException;

    // Step 3, different for each job: zip or unzip, recording every file name into the
    // job.
    protected abstract void process(ArchiveJob job) throws IOException;
}
