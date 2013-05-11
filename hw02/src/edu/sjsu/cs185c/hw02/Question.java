package edu.sjsu.cs185c.hw02;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class Question implements Serializable {
    private String text;
    private List<String> choices = new ArrayList<String>();
    private List<String> questions = new ArrayList<String>();
    private Map<String, List<String>> quizzes = new HashMap<String, List<String>>();
    private int correctChoice;
    private Map<String, Integer> correctChoices = new HashMap<String, Integer>();

    public void readFromXml(InputStream in) throws IOException {
        class QuestionHandler extends BasicHandler {
            @Override
            public void startElement(String uri, String localName,
                    String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                if ("true".equals(attributes.getValue("value")))
                    correctChoice = choices.size();
            }
            @Override
            public void endElement(String uri, String localName, String qName)
                    throws SAXException {
                if (qName.equals("text")) {
                    text = lastString();
                    questions.add(text);
                }
                else if (qName.equals("choice")) {
                    choices.add(lastString());
                    ArrayList<String> temp = new ArrayList<String>();
                    if ((choices.size() % 4) == 0) {
                        for (int i = 1; i < 5; i++)
                            temp.add(0, choices.get(choices.size() - i));
                        quizzes.put(questions.get(questions.size() - 1), temp);
                        correctChoices.put(questions.get(questions.size() - 1), correctChoice%4);
                    }
                }
            }
        }
        
        try {       
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser(); 
            parser.parse(in, new QuestionHandler());
        } catch (Exception ex) {
            IOException ioEx = new IOException(ex.getMessage());
            ioEx.initCause(ex);
            throw ioEx;
        }       
    }

    public List<String> getChoices() { return Collections.unmodifiableList(choices); }
    public String getText() { return text; }
    public boolean isCorrect(String qn, int choice) { return choice == getcorrectChoices().get(qn); }
    public List<String> getQuestions() { return Collections.unmodifiableList(questions); }
    public Map<String, List<String>> getQuizzes() { return Collections.unmodifiableMap(quizzes); }
    public Map<String, Integer> getcorrectChoices() { return Collections.unmodifiableMap(correctChoices ); }
}