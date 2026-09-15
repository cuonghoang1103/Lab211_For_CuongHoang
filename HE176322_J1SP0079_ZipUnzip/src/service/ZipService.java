package service;

import dto.ZipRequestDTO;
import dto.ZipResponseDTO;
import model.ArchiveJob;

/**
 * SERVICE: the brief's two operations, compressTo and extractTo.
 *
 * @author HE176322
 */
public class ZipService {

    // Zips a folder.
    private ArchiveTask compressTask;
    // Unzips a zip file.
    private ArchiveTask extractTask;

    // Creates the service with its two tasks.
    public ZipService() {
        compressTask = new CompressTask();
        extractTask = new ExtractTask();
    }

    // The brief's compressTo: zips the job's source folder into destination/zipName.
    public boolean compressTo(ArchiveJob job) {
        return compressTask.execute(job);
    }

    // The brief's extractTo: unzips the job's zip file into the destination.
    public boolean extractTo(ArchiveJob job) {
        return extractTask.execute(job);
    }

    // Option 1: builds the job from the request and runs compressTo.
    public ZipResponseDTO compress(ZipRequestDTO requestDTO) {
        ArchiveJob job = toJob(requestDTO);
        boolean success = compressTo(job);
        return toResponse(job, success);
    }

    // Option 2: builds the job from the request and runs extractTo.
    public ZipResponseDTO extract(ZipRequestDTO requestDTO) {
        ArchiveJob job = toJob(requestDTO);
        boolean success = extractTo(job);
        return toResponse(job, success);
    }

    // Copies the request into a model job.
    private ArchiveJob toJob(ZipRequestDTO requestDTO) {
        ArchiveJob job = new ArchiveJob();
        job.setSourcePath(requestDTO.getSourcePath());
        job.setDestinationPath(requestDTO.getDestinationPath());
        job.setZipName(requestDTO.getZipName());
        return job;
    }

    // Copies a finished job into the DTO the view may see.
    private ZipResponseDTO toResponse(ArchiveJob job, boolean success) {
        ZipResponseDTO response = new ZipResponseDTO();
        response.setSuccess(success);
        response.setFileNames(job.getFileNames());
        response.setError(job.getError());
        return response;
    }
}
