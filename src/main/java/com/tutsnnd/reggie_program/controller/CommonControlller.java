package com.tutsnnd.reggie_program.controller;

import com.tutsnnd.reggie_program.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonControlller {
    @Value("${reggie.path}")
    String BasePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.toString().substring(originalFilename.lastIndexOf("."));
        String filename=UUID.randomUUID()+substring;
        File dirs = new File(BasePath);
        if (!dirs.exists()){
            dirs.mkdir();
        }
            file.transferTo(new File(BasePath+filename));

        return R.success(filename);
    }
    @GetMapping("/download")
    public void download (String name, HttpServletResponse response) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(BasePath+name);
        ServletOutputStream outputStream = response.getOutputStream();
        int len =0;
        byte[] bytes = new byte[1024];
        while((len=fileInputStream.read(bytes))!=-1){

            outputStream.write(bytes,0,len);
            outputStream.flush();
        }

        fileInputStream.close();
        outputStream.close();
    }


}
