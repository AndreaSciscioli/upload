package Upload.controllers;

import Upload.services.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    private FileService downloadService;
    @GetMapping("/downloadFile")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        switch (FilenameUtils.getExtension(fileName)){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition","attachment; filename+\""+fileName+"\"");
        return downloadService.download(fileName);

    }
}
