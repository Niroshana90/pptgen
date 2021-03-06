package pptgen.pptwriter;

import org.apache.poi.xslf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Thilina on 6/18/2016.
 */
public class PptCreator {

    private XMLSlideShow pptx;
    private List<XSLFSlide> slides;

    public PptCreator(String fileName) throws IOException {

        pptx = new XMLSlideShow(new FileInputStream(fileName));
        slides = pptx.getSlides();

    }

    public void createCover(String companyName){

        XSLFSlide slide = slides.get(PptReadConstant.COVER_SLIDE_NUMBER);

        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape shape: shapes) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape)shape;
                String slideText = textShape.getText();
                if(slideText.contains(PptReadConstant.COMPANY_NAME_TOKEN)){
                    textShape.setText(companyName.toUpperCase());
                    break;
                }
            }
        }
    }

    public void createContext(String companyName){

        XSLFSlide slide = slides.get(PptReadConstant.CONTEXT_SLIDE_NUMBER);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();
                    text = text.replace(PptReadConstant.COMPANY_NAME_TOKEN, companyName);
                    text = text.replace(PptReadConstant.YEAR_TOKEN,Integer.toString(year));
                    textShape.setText(text);
                }
            }
        }
    }

    public void createObjectives(String companyName){

        XSLFSlide slide = slides.get(PptReadConstant.OBJECTIVES_SLIDE_NUMBER);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();
                    if(text.contains(PptReadConstant.COMPANY_NAME_TOKEN)){
                        text = text.replace(PptReadConstant.COMPANY_NAME_TOKEN, companyName);
                        textShape.setText(text);
                        break;
                    }
                }
            }
        }
    }

    public void surveyConstruct(int numberOfStatements, int numberofStatements, int numberOfEmployees,
                                int numberOfRespondents, String mode, String languages, String benchmark){

        XSLFSlide slide = slides.get(PptReadConstant.SURVEY_CONSTRUCT_SLIDE_NUMBER);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {

                    XSLFTextShape textShape = (XSLFTextShape)aShape;
                    String text = textShape.getText();

                    if(text.contains(PptReadConstant.NUM_OF_STATEMENTS_TOKEN)){
                        String num = Integer.toString(numberOfStatements);
                        text = text.replace(PptReadConstant.NUM_OF_STATEMENTS_TOKEN,num);
                    }
                    else if(text.contains(PptReadConstant.BENCHMARK_TOKEN)){
                        text = text.replace(PptReadConstant.BENCHMARK_TOKEN,benchmark);
                    }
                    else if(text.contains(PptReadConstant.NUMBER_OF_EMPLOYEE_TOKEN)){
                        String val = Integer.toString(numberOfRespondents);
                        text = text.replace(PptReadConstant.NUMBER_OF_RESPONDENT_TOKEN,val);
                        val = Integer.toString(numberOfEmployees);
                        text = text.replace(PptReadConstant.NUMBER_OF_EMPLOYEE_TOKEN,val);
                        int temp =numberOfEmployees - numberOfRespondents;
                        val = Integer.toString(temp);
                        text = text.replace(PptReadConstant.NUMBER_OF_NON_RESPONDENT_TOKEN,val);
                    }
                    else if(text.contains(PptReadConstant.MODE_TOKEN)){
                        text = text.replace(PptReadConstant.MODE_TOKEN,mode);
                    }
                    else if(text.contains(PptReadConstant.LANGUAGES_TOKEN)){
                        text = text.replace(PptReadConstant.LANGUAGES_TOKEN,languages);
                    }
                    textShape.setText(text);
                }
            }
        }
    }


    public void createDemography(int numberOfDemos, String demo[]){

        XSLFSlide slide = slides.get(PptReadConstant.SURVEY_CONSTRUCT_SLIDE_NUMBER);
        List<XSLFShape> shapes = slide.getShapes();

        //insert demos
        int i = 0;

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if(numberOfDemos == i){
                    break;
                }
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();

                    if(text.contains(PptReadConstant.DEMOGRAPHY_TOKEN)){
                        text = text.replace(PptReadConstant.DEMOGRAPHY_TOKEN,demo[i]);
                        i++;
                    }
                    textShape.setText(text);
                }
            }
        }

        //remove extra demos
        for (int j=0;j<shapes.size();j++) {
            XSLFShape aSh = shapes.get(j);
            String name = aSh.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aSh instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape)aSh;
                    String text = textShape.getText();
                    System.out.println(text);
                    if (text.contains(PptReadConstant.DEMOGRAPHY_TOKEN)) {
                        slide.removeShape(aSh);
                    }

                }
            }
        }

        //remove extra numbers
        i++;
        for (int j=0;j<shapes.size();j++) {
            XSLFShape aSh = shapes.get(j);
            String name = aSh.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aSh instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape)aSh;
                    String text = textShape.getText();
                    System.out.println(text);
                    if (text.contains(Integer.toString(i))){
                        i++;
                        slide.removeShape(aSh);
                    }
                }
            }
        }
    }

    public void createAOINAOSList(ArrayList<String> aoi,ArrayList<String> aos){

        if (PptReadConstant.AOI_AND_AOS == 0){
            //// TODO: 6/18/2016 handle this
        }
        XSLFSlide slide = slides.get(PptReadConstant.AOI_AND_AOS);
        List<XSLFShape> shapes = slide.getShapes();
        int i=0;
        int j=0;
        for (XSLFShape aSh : shapes) {

            String name = aSh.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aSh instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape)aSh;
                    String text = textShape.getText();
                    if(text.contains(PptReadConstant.STRENGTH_TOKEN)){
                        text = text.replace(PptReadConstant.STRENGTH_TOKEN,aos.get(i++));
                        textShape.setText(text);
                    }
                    else if(text.contains(PptReadConstant.IMPROVEMENT_TOKEN)){
                        text = text.replace(PptReadConstant.IMPROVEMENT_TOKEN,aoi.get(j++));
                        textShape.setText(text);
                    }
                }
            }
        }

        this.createAO(aos,PptReadConstant.STRENGTH_TOKEN,PptReadConstant.AOI_AND_AOS+PptReadConstant.AOS_DIFF);
        this.createAO(aoi,PptReadConstant.IMPROVEMENT_TOKEN,PptReadConstant.AOI_AND_AOS+PptReadConstant.AOI_DIFF);
    }

    private void createAO(ArrayList<String> ao, String token, int slideNum){

        XSLFSlide slide = slides.get(slideNum);
        List<XSLFShape> shapes = slide.getShapes();
        int i=0;
        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();

                    if(text.contains(token)){
                        text = text.replace(token,ao.get(i++));
                    }
                    textShape.setText(text);
                }
            }
        }
    }

    public void createKeyDrives(ArrayList<String> kds){

        if (PptReadConstant.KEY_DRIVES == 0){
            // TODO: 6/18/2016 handle this 
        }
        XSLFSlide slide = slides.get(PptReadConstant.KEY_DRIVES);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aSh : shapes) {
            if (aSh instanceof XSLFTable){
                XSLFTable tb = (XSLFTable)aSh;
                tb.getCell(1,0).setText(kds.get(0));
                tb.getCell(2,0).setText(kds.get(1));
                tb.getCell(3,0).setText(kds.get(2));
                tb.getCell(4,0).setText(kds.get(3));
                tb.getCell(5,0).setText(kds.get(4));
                break;
            }
        }
    }
}
