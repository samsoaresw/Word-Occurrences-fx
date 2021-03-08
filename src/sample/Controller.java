package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Controller {

    private ArrayList<Pair> sortedWords;
    private ObservableList<Pair> list;

    @FXML
    private Label fileName;
    @FXML
    private Button chooseBtn;
    @FXML
    private TableView contentTable;
    @FXML
    private HBox limitBox;
    @FXML
    private Slider limitSlider;
    @FXML
    private TextField valueTxt;
    @FXML
    private Button resetBtn;
          

    public Controller()
    {

        sortedWords=new ArrayList<>();

    }

    public void updateTable(ObservableList<Pair> data,int size)
    {
        FilteredList<Pair> filteredData = new FilteredList<>(
                data,
                person -> data.indexOf(person) < size
        );
        SortedList<Pair> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(contentTable.comparatorProperty());
        contentTable.setItems(sortedData);
    }

    public void initialize() {
        limitSlider.setBlockIncrement(1);

        limitSlider.setMajorTickUnit(1);
        limitSlider.setMinorTickCount(0);

        limitSlider.setSnapToTicks(true);
       limitSlider.setOnMouseReleased(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                int size=(int)limitSlider.getValue();
                valueTxt.setText(size+"");
                updateTable(list, size);
            }
        });
    }

    @FXML
    public void fileBtnHandler(ActionEvent event)
    {

        if (event.getSource()==chooseBtn)
        {

            FileChooser fileChooser=new FileChooser();
            File file=fileChooser.showOpenDialog(chooseBtn.getScene().getWindow());
            if (file!=null) {
                limitBox.setVisible(true);
                fileName.setText(file.getName());
                readWord(file);
                limitSlider.setMax(sortedWords.size());
                limitSlider.setValue(sortedWords.size());
                valueTxt.setText(sortedWords.size()+"");
                setupTable();
            }
        }
    }
    
    @FXML
    public void resetBtnHandler(ActionEvent event)
    {

        if (event.getSource()==resetBtn)
        {
            contentTable.setItems(FXCollections.observableList(new ArrayList()));
            
            limitBox.setVisible(false);
            fileName.setText("Choose File");
        }
    }

    private void setupTable()
    {

         list= FXCollections.observableList(sortedWords);
         System.out.println(sortedWords.size());
        TableColumn wordColumn= (TableColumn) contentTable.getColumns().get(0);
        TableColumn countColumn= (TableColumn) contentTable.getColumns().get(1);

        wordColumn.setCellValueFactory(new PropertyValueFactory<Pair,String>("word"));
        countColumn.setCellValueFactory(new PropertyValueFactory<Pair,String>("count"));
        updateTable(list, (int) limitSlider.getValue());

    }

    private void readWord(File file)
    {
        try {
            Map<String,Integer> wordVsCount=new HashMap<>();
            sortedWords.clear();
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] datas = line.split(" ");
                for (String w : datas) {
                    String word=w.replaceAll("[^a-zA-Z0-9]", "").trim();
                    if(word.isEmpty())
                        continue;
                    if(wordVsCount.containsKey(word))
                    {
                        int count=wordVsCount.get(word);
                        count++;
                        wordVsCount.replace(word,count);
                    }
                    else
                    {
                        wordVsCount.put(word,1);
                    }
                }
            }
            reader.close();
            Set<String> keys = wordVsCount.keySet();
            for (String key : keys) {
                Pair pair=new Pair(key,wordVsCount.get(key));
                sortedWords.add(pair);
            }

            sort(sortedWords);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }


    private void sort(ArrayList<Pair> list)
    {
        boolean needSwap=true;
        while (needSwap)
        {
            needSwap=false;
            for (int i = 1; i < list.size(); i++) {
                if(list.get(i-1).getCount()<list.get(i).getCount())
                {
                    Pair t=list.get(i-1);
                    list.set(i-1,list.get(i));
                    list.set(i,t);
                    needSwap=true;
                }
            }
        }
    }


}
