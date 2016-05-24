package imgapp.controllers;

import imgapp.utils.FileUtill;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import java.awt.image.BufferedImage;
import java.io.*;


@RestController
public class ImageUploadController {
    private static final Logger logger = Logger.getLogger(ImageUploadController.class);
    @Autowired
    private Environment env;

    @RequestMapping(value = "download/{hub}/{hubId}", method = RequestMethod.GET)

    public void download(@PathVariable(value = "hub") String hub,
                         @PathVariable(value = "hubId") String hubId,
                         @QueryParam("token") String token,
                         @QueryParam("size") String size,
                         HttpServletResponse response) {
        String fullPathToImage = env.getProperty("path.to.uploadedImages") + '\\' + hub + '\\' + hubId + '\\' + token + '_' + size + ".jpg";
        try {
            File f = new File(fullPathToImage);
            if (f.exists() && !f.isDirectory()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + token + '_' + size + ".jpg");
                response.addHeader("Content-Type", "image/pjpeg");

                InputStream in = new FileInputStream(f);
                IOUtils.copy(in, response.getOutputStream());
                in.close();
                response.flushBuffer();


            } else {
                logger.error("Image not found returning 404");
                response.setStatus(404);
            }
        } catch (IOException e) {
            logger.error("Download failed" + e.getCause());
            response.setStatus(500);
        }
    }

    @RequestMapping(value = "upload/{hub}/{hubId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> upload(
            @PathVariable(value = "hub") String hub,
            @PathVariable(value = "hubId") String hubId,
            @RequestParam("upload") MultipartFile upload) {
        BufferedImage image;
        try {
            String filename = upload.getOriginalFilename();
            String directory = env.getProperty("path.to.uploadedImages");
            String extensionRemoved = filename.split("\\.")[0];
            image = ImageIO.read(upload.getInputStream());
            FileUtill.writeImageToFileSystem(image, directory, extensionRemoved, hub + '\\' + hubId);

        } catch (Exception e) {
            logger.error(e.getCause());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } finally {
            try {
                upload.getInputStream().close();
            } catch (IOException e) {
                logger.error(e.getCause());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


