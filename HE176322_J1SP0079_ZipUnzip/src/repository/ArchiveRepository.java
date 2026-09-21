package repository;

import java.util.ArrayList;
import model.ArchiveJob;

/**
 * REPOSITORY: holds the data of the program - every zip/unzip job of this run (where from,
 * where to, under which name, which files went through) - and only simple CRUD on it. No
 * rule, no print, no file on disk.
 *
 * @author HE176322
 */
public class ArchiveRepository {

    // Every job of this run, in order; the last one is the job being done.
    private ArrayList<ArchiveJob> jobList;

    // Creates an empty store.
    public ArchiveRepository() {
        jobList = new ArrayList<>();
    }

    // Create: a new job (the model) from the two paths every job has, stored at the end of
    // the list and handed back to the service that runs it.
    public ArchiveJob addJob(String sourcePath, String destinationPath) {
        ArchiveJob job = new ArchiveJob();

        // where from and where to, as the user typed them
        job.setSourcePath(sourcePath);
        job.setDestinationPath(destinationPath);
        jobList.add(job);
        return job;
    }

    // Read: the job added last - the one that has just run.
    public ArchiveJob getLastJob() {
        return jobList.get(jobList.size() - 1);
    }
}
