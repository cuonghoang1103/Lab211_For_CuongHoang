package service;

import dto.ZipRequestDTO;
import dto.ZipResponseDTO;
import model.ArchiveJob;
import repository.ArchiveRepository;

/**
 * SERVICE: the brief's two operations, compressTo and extractTo. It takes the job from the
 * repository and runs the matching task on it (Service -> Repository -> Model). Called only
 * by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class ZipService {

    // Keeps the jobs of this run.
    private ArchiveRepository archiveRepository;

    // Zips a folder or one file.
    private ArchiveTask compressTask;

    // Unzips a zip file.
    private ArchiveTask extractTask;

    // Creates the service with its repository and its two tasks.
    public ZipService() {
        archiveRepository = new ArchiveRepository();
        compressTask = new CompressTask();
        extractTask = new ExtractTask();
    }

    // brief: public static boolean compressTo(String pathSrc, String fileZipName,
    // String pathCompress) - its three values come in the RequestDTO (checklist 1.1: a
    // parameter list only when it has fewer than 3 values); not static (see HUONG-DAN).
    // Zips the source (a folder or one file) into pathCompress/fileZipName.
    public boolean compressTo(ZipRequestDTO requestDTO) {
        ArchiveJob job = archiveRepository.addJob(requestDTO.getSourcePath(),
                requestDTO.getDestinationPath());

        // only a compression has a zip name to create
        job.setZipName(requestDTO.getZipName());
        return compressTask.execute(job);
    }

    // brief: public static boolean extractTo(String pathZipFile, String pathExtract) - the
    // brief's two parameters (fewer than 3: allowed); not static (see HUONG-DAN). Unzips the
    // zip file into the destination folder.
    public boolean extractTo(String pathZipFile, String pathExtract) {
        ArchiveJob job = archiveRepository.addJob(pathZipFile, pathExtract);

        // the Template Method: check the zip, make the folder, unzip
        return extractTask.execute(job);
    }

    // Option 1: runs compressTo, then gives what the view shows.
    public ZipResponseDTO compress(ZipRequestDTO requestDTO) {
        boolean success = compressTo(requestDTO);

        return toResponse(archiveRepository.getLastJob(), success);
    }

    // Option 2: runs extractTo with the two paths of the request, then gives what the view
    // shows.
    public ZipResponseDTO extract(ZipRequestDTO requestDTO) {
        boolean success = extractTo(requestDTO.getSourcePath(),
                requestDTO.getDestinationPath());

        return toResponse(archiveRepository.getLastJob(), success);
    }

    // Copies a finished job into the DTO the view may see: the status the brief's functions
    // return, the files that went through, and the reason of a failure.
    private ZipResponseDTO toResponse(ArchiveJob job, boolean success) {
        ZipResponseDTO responseDTO = new ZipResponseDTO();

        // the three things the result screen needs
        responseDTO.setSuccess(success);
        responseDTO.setFileNameList(job.getFileNameList());
        responseDTO.setError(job.getError());
        return responseDTO;
    }
}
