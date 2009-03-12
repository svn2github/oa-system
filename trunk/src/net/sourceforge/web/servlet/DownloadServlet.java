package net.sourceforge.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.utils.SessionTempFile;

public class DownloadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] pathInfo = request.getPathInfo().split("/");
        if (pathInfo.length != 3) {
            return;
        }
        int index = Integer.parseInt(pathInfo[1]);
        File tempFile = SessionTempFile.getTempFile(index, request);
        if (tempFile == null) {
            return;
        }
        response.setContentType("application/octet-stream");
        response.addHeader("Content-disposition", "attachment;  filename=" + URLEncoder.encode(pathInfo[2], "UTF8"));
        ServletOutputStream out = response.getOutputStream();
        try {
            byte[] buffer = new byte[10240];
            FileInputStream in = new FileInputStream(tempFile);
            try {
                for (int i = in.read(buffer); i != -1;) {
                    out.write(buffer, 0, i);
                    i = in.read(buffer);
                }
            } finally {
                in.close();
            }
        } finally {
            out.close();
        }
    }

}
