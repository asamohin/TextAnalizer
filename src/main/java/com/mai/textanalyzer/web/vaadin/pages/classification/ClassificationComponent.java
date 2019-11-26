package com.mai.textanalyzer.web.vaadin.pages.classification;

import com.mai.textanalyzer.Application;
import com.mai.textanalyzer.classifier.common.ClassifierEnum;
import com.mai.textanalyzer.classifier.common.Prediction;
import com.mai.textanalyzer.classifier.common.TextClassifier;
import com.mai.textanalyzer.indexing.common.Indexer;
import com.mai.textanalyzer.indexing.common.IndexerEnum;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClassificationComponent {  
    
    public static List<String> result = new ArrayList<String>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);
    private static int exp = 85;
    public static String rootdir = "D:\\modeluper";
    
    public static final String model_DOC2VEC = "DOC2VEC";
    public static final String model_TF_IDF = "TF_IDF";
    
    public static final String classifier_NAIVE_BAYES = "NAIVE_BAYES";
    public static final String classifier_SVM = "SVM";
    public static final String classifier_IBK = "IBK";
    public static final String classifier_LR = "LR";
    public static final String classifier_RF = "RF";
    public static final String classifier_MYLTI_CLASSIFIER = "MYLTI_CLASSIFIER";
    public static final String classifier_BAGGING = "BAGGING";
    public static final String classifier_BOOSTING = "BOOSTING";
    public static final String classifier_STACKING = "STACKING";    

           
    @RequestMapping(value = "/classifier", method = RequestMethod.GET)
    public ModelAndView classifier() {
      return new ModelAndView("classifier", "command", new Storage());
   }  
   
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search() {
      return new ModelAndView("search", "command", new SearchStructure());
   }  
    
    @RequestMapping(value = "/searchResult", method = {RequestMethod.POST})
    public String searchResult(@ModelAttribute("SpringWeb")SearchStructure searchStructure, ModelMap model) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Считываем исходный каталог для поиска файлов.
            //System.out.print("Введите исходную директорию для поиска файлов:");
            //final String directoryPath = reader.readLine();
            reader.close();

            File directory = new File(rootdir + "\\DocForLearning\\");
            // Убедимся, что директория найдена и это реально директория, а не файл.
            if (directory.exists() && directory.isDirectory()) {
                searchStructure.setResultList(processDirectory(directory, searchStructure.getInput()));
                model.addAttribute("output", searchStructure.getResultList());                
            } else {
                System.out.println("dir = " + rootdir + "\\DocForLearning\\");
                System.out.println("Не удалось найти директорию по указанному пути.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            return "search_result";        
    }
    
    @RequestMapping(value = "/textMeaning", method = {RequestMethod.POST})
    public String textMeaning(@ModelAttribute("SpringWeb")Storage storage, ModelMap model) throws Exception{
            int i = 0;
            int j = 0;
            System.out.println("Input text = " + storage.getInput());  
            System.out.println("rootdir = " + rootdir);
            //LoadingComponents lc = new LoadingComponents();
            LoadingComponents lc = LoadingComponents.getInstance();
            ArrayList<String> resList = new ArrayList<>();
            //ArrayList<String> classifier = input.getClassifier();
            ArrayList<String> classifier = new ArrayList<>();
            //classifier.add("NAIVE_BAYES");
            //classifier.add("SVM");
            //classifier.add("IBK");
            //classifier.add("LR");
            //classifier.add("RF");
            //classifier.add("BAGGING");
            //classifier.add("BOOSTING");
            //classifier.add("STACKING");
            classifier.add("MYLTI_CLASSIFIER");
            String modelClassification = "DOC2VEC";
            String mainTopic = "";
            ClassifierEnum selectedClassifier = null;
            HashMap<String, Double> map = new HashMap<>();
            HashMap<String,  ArrayList<String>>  newmap = new HashMap<>();
            IndexerEnum indexerEnum = null;
            Indexer indexer = null;
            TextClassifier textClassifier = null;
            List<Prediction> predictions = null;
            
            if(modelClassification.equals(model_DOC2VEC)) {
                indexerEnum = IndexerEnum.DOC2VEC;
                indexer = lc.getIndexer(IndexerEnum.DOC2VEC);
            }
            if(modelClassification.equals(model_TF_IDF)) {
                indexerEnum = IndexerEnum.TF_IDF;
                indexer = lc.getIndexer(IndexerEnum.TF_IDF);                
            }
            //String str = new String(storage.getInput().getBytes(), "UTF-8");
            INDArray iNDArray = indexer.getIndex(storage.getInput());           
            for(i = 0; i< classifier.size(); i++) {
            map.clear();
            resList.clear();
            selectedClassifier = null;
            switch (classifier.get(i)) {
                case classifier_NAIVE_BAYES:  selectedClassifier = ClassifierEnum.NAIVE_BAYES;
                         break;
                case classifier_SVM:  selectedClassifier = ClassifierEnum.SVM;
                         break;
                case classifier_IBK:  selectedClassifier = ClassifierEnum.IBK;
                         break;
                case classifier_LR:  selectedClassifier = ClassifierEnum.LR;
                         break;
                case classifier_RF:  selectedClassifier = ClassifierEnum.RF;
                         break;
                case classifier_MYLTI_CLASSIFIER:  selectedClassifier = ClassifierEnum.MYLTI_CLASSIFIER;
                         break;
                case classifier_BAGGING:  selectedClassifier = ClassifierEnum.BAGGING;
                         break;
                case classifier_BOOSTING:  selectedClassifier = ClassifierEnum.BOOSTING;
                         break;
                case classifier_STACKING: selectedClassifier = ClassifierEnum.STACKING;
                         break;
            }
            System.out.println(selectedClassifier);
            //System.out.println(map.size());
            if (iNDArray == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В тексте недостаточно информации для его классификации");
            }            
            if (selectedClassifier == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не выбран классификатор");
            }
            textClassifier = null;
            textClassifier = lc.getClassifier(selectedClassifier, indexerEnum);
            if (textClassifier == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Классификатор "+ classifier.get(i) +" еще не обучен");
            }
            predictions = textClassifier.getDistribution(iNDArray);
            //System.out.println(predictions.get(0).getValue());
            //for (Prediction prediction : predictions) {
            for(j = 0; j < predictions.size(); j++) {
            System.out.println(predictions.get(j).getTopic() + " - " + predictions.get(j).getValue() + " - " + predictions.get(j));
            if (predictions.get(j).toString() != null) {
                BigDecimal value = new BigDecimal(predictions.get(j).toString().replace(',', '.'));
                map.put(predictions.get(j).getTopic(), value.doubleValue());
            }
            //map.put(prediction.getTopic(), new BigDecimal(prediction.getValue()).setScale(10, RoundingMode.UP).doubleValue());  
            }
            mainTopic = getTopClassifierResult(map);
            
            resList.add(mainTopic);            
            resList.add(Double.toString(map.get(mainTopic)));
            
            newmap.put(classifier.get(i), resList);
            
            }
            storage.setOutput(mainTopic);
            System.out.println("mainTopic = " + mainTopic);
            //model.addAttribute("output", storage.getOutput());
            model.addAttribute("output", "Исходный текст с вероятностью " + resList.get(1) + "% относится к разделу '" +  resList.get(0) + "'");
            model.addAttribute("input", storage.getInput());
            
            return "classifier_result";
    }
    
      
    public static String getTopClassifierResult(HashMap<String,  Double>  map) {
        Object[] list = map.entrySet().stream()
            .sorted(HashMap.Entry.<String,  Double>comparingByValue().reversed()).toArray();
            String str = list[0].toString();
            return str.substring(0, list[0].toString().lastIndexOf("="));
    }  
    
    private static ArrayList<File> processDirectory(File directory, String search) {
        // Получаем список доступных файлов в указанной директории.
        ArrayList<File> result = new ArrayList<>();
        ArrayList<File> files = new ArrayList();
        File[] files0 = directory.listFiles();
        for(int i = 0; i < files0.length; i++) {
            files.add(files0[i]);
        }
        //System.out.println("files= " + files[0].getAbsolutePath());
        if (files.isEmpty()) {
            System.out.println("Нет доступных файлов для обработки.");
            return null;
        } else {
            System.out.println("Количество файлов для обработки: " + files.size());
        }

        // Непосредственно многопоточная обработка файлов.
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int j = 0; j < files.size(); j++) {
            for (final File f : files.get(j).listFiles()) {
                if (!f.isFile()) {
                    System.out.println(f.getAbsolutePath() + " is not file!");
                    continue;
                }

                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        byte[] array;
                        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
                        try {
                            /*
                            array = Files.readAllBytes(Paths.get(f.getPath()));
                            outputBuffer.write(array);
                            String text = new String(outputBuffer.toByteArray(), "UTF-8");
                            if(text.toLowerCase().contains(search.toLowerCase())) {
                                System.out.println("YES");
                                System.out.println(Paths.get(f.getPath()));
                            }
                                    */
                            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f.getPath()), "UTF8"));
                            // считаем сначала первую строку
                            String line = reader.readLine();
                            while (line != null) {
                                //System.out.println(line);
                                if(FuzzySearch.partialRatio(line.toLowerCase(), search.toLowerCase()) > exp) {
                                    //result.add("file:///" + Paths.get(f.getPath()).toString().replace(" ", "%20").replace("\\", "/"));
                                    result.add(f);
                                    break;
                                }
                                line = reader.readLine();
                            }
                        } catch (IOException ex) {
                        }
                    }
                });
            }
        }
        // Новые задачи более не принимаем, выполняем только оставшиеся.
        service.shutdown();
        // Ждем завершения выполнения потоков не более 10 минут.
        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }     
      
}