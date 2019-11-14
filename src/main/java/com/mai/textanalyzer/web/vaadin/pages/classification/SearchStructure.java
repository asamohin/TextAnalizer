/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mai.textanalyzer.web.vaadin.pages.classification;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author artee
 */
public class SearchStructure {
    private String  input;  
    private ArrayList<File> resultList = new ArrayList<>(); 

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ArrayList<File> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<File> resultList) {
        this.resultList = resultList;
    }
}
