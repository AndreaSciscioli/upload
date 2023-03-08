package Upload.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Value("{fileRepositoryFolder")
    private String fileRepositoryFolder;
    @SneakyThrows
    public String upload(MultipartFile file) {;
        String newFileName = UUID.randomUUID().toString()+"."+
                FilenameUtils.getExtension(file.getOriginalFilename());
        File finalFolder = new File(fileRepositoryFolder);
        if (!finalFolder.exists())throw new IOException("file non exists");
        if (!finalFolder.isDirectory())throw new IOException("file isn't a directory");
        File finalDestination = new File(fileRepositoryFolder+"\\"+newFileName);
        if (finalDestination.exists())throw new IOException("file conflict");
        file.transferTo(finalDestination);
        return newFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository= new File(fileRepositoryFolder+"\\"+fileName);
        if(!fileFromRepository.exists()) throw new IOException("file doesn't exist");
        return IOUtils.toByteArray(new FileInputStream(fileName));
    }
}
