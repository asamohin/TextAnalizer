/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mai.textanalyzer.web.vaadin.pages.classification;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reportFetch")
public class WebServletClass extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = request.getParameter("filePath");
        String fileName = request.getParameter("fileName");

        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(request.getParameter("fileName"));

        response.setContentType(mimeType);
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(filePath);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();

    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}
